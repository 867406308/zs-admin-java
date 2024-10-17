package com.zs.modules.assets.info.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.modules.assets.info.domain.dto.AssetsInfoDTO;
import com.zs.modules.assets.info.domain.entity.AssetsInfoEntity;
import com.zs.modules.assets.info.domain.excel.AssetsInfoExcel;
import com.zs.modules.assets.info.domain.query.AssetsInfoAddParams;
import com.zs.modules.assets.info.domain.query.AssetsInfoQueryParams;
import com.zs.modules.assets.info.domain.query.AssetsInfoSerialNoImportParams;
import com.zs.modules.assets.info.domain.query.AssetsInfoStockInParams;
import com.zs.modules.assets.info.domain.vo.AssetsInfoVo;
import com.zs.modules.assets.info.mapper.AssetsInfoMapper;
import com.zs.modules.assets.info.service.AssetsInfoService;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.excel.ExcelUtils;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.model.domain.SysDeptDTO;
import com.zs.common.core.model.domain.SysDictDataDTO;
import com.zs.common.core.model.domain.SysUserDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.core.utils.DictRedisUtil;
import com.zs.common.core.utils.SecurityUtil;
import com.zs.modules.sys.dept.service.ISysDeptService;
import com.zs.modules.sys.user.service.ISysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author 86740
 */
@Service
public class AssetsInfoServiceImpl extends ServiceImpl<AssetsInfoMapper, AssetsInfoEntity> implements AssetsInfoService {


    @Resource
    private ISysUserService iSysUserService;
    @Resource
    private ISysDeptService iSysDeptService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @NotNull
    @Override
    public PageResult<AssetsInfoVo> page(@NotNull AssetsInfoQueryParams assetsInfoQueryParams) {

        Page<AssetsInfoEntity> page = new PageInfo<>(assetsInfoQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(assetsInfoQueryParams);
         IPage<AssetsInfoEntity> iPage = baseMapper.page(page, params);
        List<AssetsInfoVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsInfoVo.class);
        updateItemsWithDetails(list);
        BigDecimal totalPrice = this.baseMapper.getTotalPrice(params);
        return new PageResult<>(list, page.getTotal(), totalPrice, AssetsInfoVo.class);
    }

    @Nullable
    @Override
    public List<AssetsInfoVo> list(AssetsInfoQueryParams assetsInfoQueryParams) {
        Map<String, Object> params = BeanUtil.beanToMap(assetsInfoQueryParams);
        List<AssetsInfoEntity> entities = this.baseMapper.list(params);
        List<AssetsInfoVo> list = BeanUtil.copyToList(entities, AssetsInfoVo.class);
        updateItemsWithDetails(list);
        return list;
    }


    @Nullable
    @Override
    public List<AssetsInfoVo> getBySerialNoList(@NotNull AssetsInfoSerialNoImportParams assetsInfoSerialNoImportParams) {


        List<AssetsInfoEntity> list = baseMapper.getBySerialNoList(assetsInfoSerialNoImportParams.getSerialNoList());
        List<AssetsInfoVo> infoVoList = BeanUtil.copyToList(list, AssetsInfoVo.class);
        updateItemsWithDetails(infoVoList);
        return infoVoList;


    }

    @NotNull
    @Override
    public String getTotalPrice() {
        return "";
    }

    @Override
    public void save(@NotNull AssetsInfoAddParams assetsInfoAddParams) {
        // 查询数据库中in_no最大的值，+1做为新的in_no
        String maxInNo = baseMapper.getMaxInNo();
        int inNo = Integer.parseInt(maxInNo) + 1;
        String inNoStr = String.valueOf(inNo);


        for (int i = 0; i < assetsInfoAddParams.getNum(); i++) {
            AssetsInfoEntity assetsInfoEntity = BeanUtil.copyProperties(assetsInfoAddParams, AssetsInfoEntity.class);
            assetsInfoEntity.setMonthlyDepreciationPrice(assetsInfoAddParams.getBuyPrice().divide(new BigDecimal(assetsInfoAddParams.getNum()), 2, RoundingMode.HALF_UP));
            assetsInfoEntity.setAssetsStatusCode("as1");
            assetsInfoEntity.setUseStatusCode("aus2");
            assetsInfoEntity.setStorageLocation(assetsInfoAddParams.getStorageLocationDescription());
            assetsInfoEntity.setInNo(inNoStr);
            String serialNo = String.format("%s%04d", inNoStr, i + 1);
            assetsInfoEntity.setSerialNo(serialNo);
            assetsInfoEntity.setCreateDatetime(new Date());
            LoginUserInfo loginUserInfo = SecurityUtil.getUserInfo();
            assetsInfoEntity.setCreateUser(loginUserInfo.getUsername());

            baseMapper.insert(assetsInfoEntity);


        }

    }

    @Override
    public AssetsInfoVo getById(Long id) {
        AssetsInfoEntity assetsInfoEntity = baseMapper.getById(id);
        List<AssetsInfoVo> list = BeanUtil.copyToList(Collections.singletonList(assetsInfoEntity), AssetsInfoVo.class);
        updateItemsWithDetails(list);
        return list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stockIn(@NotNull AssetsInfoStockInParams assetsInfoStockInParams) {
        List<AssetsInfoEntity> assetsInfoEntities = BeanUtil.copyToList(assetsInfoStockInParams.getAssetsInfoList(), AssetsInfoEntity.class);
        for (AssetsInfoEntity assetsInfoEntity : assetsInfoEntities) {
            assetsInfoEntity.setAccountingVoucher(assetsInfoStockInParams.getAccountingVoucher());
            assetsInfoEntity.setEntryDate(assetsInfoStockInParams.getEntryDate());
            assetsInfoEntity.setProjectCode(assetsInfoStockInParams.getProjectCode());
            assetsInfoEntity.setInvoiceNumber(assetsInfoStockInParams.getInvoiceNumber());
            assetsInfoEntity.setSaveState(1);
            assetsInfoEntity.setUpdateDatetime(new Date());
            LoginUserInfo loginUserInfo = SecurityUtil.getUserInfo();
            assetsInfoEntity.setUpdateUser(loginUserInfo.getUsername());

            baseMapper.updateById(assetsInfoEntity);
        }

    }

    @Override
    public void updateUseStatusCode(List<String> serialNoList) {
        QueryWrapper<AssetsInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.in("serial_no", serialNoList);
        List<AssetsInfoEntity> assetsInfoEntities = baseMapper.selectList(wrapper);
        assetsInfoEntities.forEach(it -> it.setUseStatusCode("aus3"));

        for (AssetsInfoEntity assetsInfoEntity : assetsInfoEntities) {
            baseMapper.updateUseStatusCodeBySerialNo(assetsInfoEntity);
        }

    }


    @Nullable
    @Override
    public List<AssetsInfoVo> getList(Long useOrgId, Long levelId) {
        QueryWrapper<AssetsInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("use_org_id", useOrgId);
        wrapper.eq("level_id", levelId);
        List<AssetsInfoEntity> assetsInfoEntityList = baseMapper.selectList(wrapper);
        return BeanUtil.copyToList(assetsInfoEntityList, AssetsInfoVo.class);
    }

    @Nullable
    @Override
    public List<AssetsInfoVo> getList(@NotNull List<String> serialNoList) {
        QueryWrapper<AssetsInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.in(!serialNoList.isEmpty(), "serial_no", serialNoList);
        List<AssetsInfoEntity> assetsInfoEntityList = baseMapper.selectList(wrapper);
        List<AssetsInfoVo> list = BeanUtil.copyToList(assetsInfoEntityList, AssetsInfoVo.class);
        updateItemsWithDetails(list);
        return list;
    }

    @Override
    public List<AssetsInfoEntity> getDepreciationList() {
        QueryWrapper<AssetsInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("depreciation_code", "depre1");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void updateAssetsInfoBySerialNo(AssetsInfoDTO assetsInfoDTO) {
        AssetsInfoEntity assetsInfoEntity = BeanUtil.copyProperties(assetsInfoDTO, AssetsInfoEntity.class);
        UpdateWrapper<AssetsInfoEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("serial_no", assetsInfoEntity.getSerialNo());
        this.baseMapper.update(assetsInfoEntity, wrapper);
    }

    @Override
    public void export(HttpServletResponse response, AssetsInfoQueryParams assetsInfoQueryParams) {
        threadPoolTaskExecutor.execute(() -> {
            List<AssetsInfoVo> list = this.list(assetsInfoQueryParams);
            List<AssetsInfoExcel> excelList = BeanUtil.copyToList(list, AssetsInfoExcel.class);
            try {
                ExcelUtils.exportExcel(response, "资产明细" + DateUtil.format(new Date(), "yyyyMMdd"), AssetsInfoExcel.class, excelList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }




    private void updateItemsWithDetails(@NotNull List<AssetsInfoVo> list) {
        updateItemsWithDictLabels(list);
        updateItemsWithUser(list);
        updateItemsWithDept(list);
        updateItemsFormOfProcurement(list);
    }



    private void updateItemsWithDictLabels(@NotNull List<AssetsInfoVo> list) {
        List<String> dictKeys = Arrays.asList(RedisConstants.SYS_DICT_KEY + "assets_use_status", RedisConstants.SYS_DICT_KEY + "assets_status", RedisConstants.SYS_DICT_KEY + "depreciationCode");
        // 获取字典使用状态
        List<SysDictDataDTO> sysDictDataDTOList = DictRedisUtil.getMulti(dictKeys);
        Map<String, SysDictDataDTO> dictMap = sysDictDataDTOList.stream().collect(Collectors.toMap(SysDictDataDTO::getDictValue, Function.identity()));
        list.forEach(item -> {
            Optional.ofNullable(dictMap.get(item.getUseStatusCode())).ifPresent(useStatusDict -> item.setUseStatusDictLabel(useStatusDict.getDictLabel()));
            Optional.ofNullable(dictMap.get(item.getAssetsStatusCode())).ifPresent(assetsStatusDict -> item.setAssetsStatusDictLabel(assetsStatusDict.getDictLabel()));
            Optional.ofNullable(dictMap.get(item.getDepreciationCode())).ifPresent(depreciationCodeDict -> item.setDepreciationCodeDictLabel(depreciationCodeDict.getDictLabel()));
        });
    }

    private void updateItemsWithUser(@NotNull List<AssetsInfoVo> list) {
        List<SysUserDTO> sysUserDTOList = BeanUtil.copyToList(iSysUserService.list(), SysUserDTO.class);
        Map<Long, SysUserDTO> userMap = sysUserDTOList.stream().collect(Collectors.toMap(SysUserDTO::getSysUserId, Function.identity()));
        list.forEach(item -> {
            item.setBuyUserName(userMap.getOrDefault(item.getBuyUserId(), new SysUserDTO()).getRealName());
            item.setAcceptUserName(userMap.getOrDefault(item.getAcceptUserId(), new SysUserDTO()).getRealName());
            item.setManageUserName(userMap.getOrDefault(item.getManageUserId(), new SysUserDTO()).getRealName());
            item.setUseUserName(userMap.getOrDefault(item.getUseUserId(), new SysUserDTO()).getRealName());
        });
    }

    private void updateItemsWithDept(@NotNull List<AssetsInfoVo> list) {
        List<SysDeptDTO> sysDeptDTOList = BeanUtil.copyToList(iSysDeptService.list(), SysDeptDTO.class);
        Map<Long, SysDeptDTO> deptMap = sysDeptDTOList.stream().collect(Collectors.toMap(SysDeptDTO::getSysDeptId, Function.identity()));
        list.forEach(item -> {
            item.setBuyOrgName(deptMap.getOrDefault(item.getBuyOrgId(), new SysDeptDTO()).getDeptName());
            item.setAcceptOrgName(deptMap.getOrDefault(item.getAcceptOrgId(), new SysDeptDTO()).getDeptName());
            item.setManageOrgName(deptMap.getOrDefault(item.getManageOrgId(), new SysDeptDTO()).getDeptName());
            item.setUseOrgName(deptMap.getOrDefault(item.getUseOrgId(), new SysDeptDTO()).getDeptName());
        });
    }

    private void updateItemsFormOfProcurement(@NotNull List<AssetsInfoVo> list) {
        List<String> dictKeys = List.of(RedisConstants.SYS_DICT_KEY + "formOfProcurementCode");
        // 获取字典使用状态
        List<SysDictDataDTO> sysDictDataDTOList = DictRedisUtil.getMulti(dictKeys);
        Map<String, SysDictDataDTO> dictMap = sysDictDataDTOList.stream().collect(Collectors.toMap(SysDictDataDTO::getDictValue, Function.identity()));
        list.forEach(item -> Optional.ofNullable(dictMap.get(item.getFormOfProcurementCode())).ifPresent(formOfProcurementCodeDict -> item.setFormOfProcurementCodeDictLabel(formOfProcurementCodeDict.getDictLabel())));
    }
}




