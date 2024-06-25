package com.zs.assets.info.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.info.domain.dto.AssetsInfoDTO;
import com.zs.assets.info.domain.entity.AssetsInfoEntity;
import com.zs.assets.info.domain.query.AssetsInfoAddParams;
import com.zs.assets.info.domain.query.AssetsInfoQueryParams;
import com.zs.assets.info.domain.query.AssetsInfoSerialNoImportParams;
import com.zs.assets.info.domain.query.AssetsInfoStockInParams;
import com.zs.assets.info.domain.vo.AssetsInfoVo;
import com.zs.assets.info.mapper.AssetsInfoMapper;
import com.zs.assets.info.service.AssetsInfoService;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.model.domain.SysDeptDTO;
import com.zs.common.core.model.domain.SysDictDataDTO;
import com.zs.common.core.model.domain.SysUserDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.core.utils.SecurityUtil;
import com.zs.common.redis.utils.DictRedisUtil;
import com.zs.sys.dept.service.ISysDeptService;
import com.zs.sys.user.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public PageResult<AssetsInfoVo> page(AssetsInfoQueryParams assetsInfoQueryParams) {
        Page<AssetsInfoEntity> page = new PageInfo<>(assetsInfoQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(assetsInfoQueryParams);
        IPage<AssetsInfoEntity> iPage = baseMapper.page(page, params);

        List<AssetsInfoVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsInfoVo.class);

        updateItemsWithDetails(list);

        return new PageResult<>(list, page.getTotal(), AssetsInfoVo.class);
    }

    @Override
    public List<AssetsInfoVo> getBySerialNoList(AssetsInfoSerialNoImportParams assetsInfoSerialNoImportParams) {


        List<AssetsInfoEntity> list = baseMapper.getBySerialNoList(assetsInfoSerialNoImportParams.getSerialNoList());
        List<AssetsInfoVo> infoVoList = BeanUtil.copyToList(list, AssetsInfoVo.class);
        updateItemsWithDetails(infoVoList);
        return infoVoList;


    }

    @Override
    public void save(AssetsInfoAddParams assetsInfoAddParams) {
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
    public void stockIn(AssetsInfoStockInParams assetsInfoStockInParams) {
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


    @Override
    public List<AssetsInfoVo> getList(Long useOrgId, Long levelId) {
        QueryWrapper<AssetsInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("use_org_id", useOrgId);
        wrapper.eq("level_id", levelId);
        List<AssetsInfoEntity> assetsInfoEntityList = baseMapper.selectList(wrapper);
        return BeanUtil.copyToList(assetsInfoEntityList, AssetsInfoVo.class);
    }

    @Override
    public List<AssetsInfoVo> getList(List<String> serialNoList) {
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


    private void updateItemsWithDetails(List<AssetsInfoVo> list) {
        updateItemsWithDictLabels(list);
        updateItemsWithUser(list);
        updateItemsWithDept(list);
    }

    private void updateItemsWithDictLabels(List<AssetsInfoVo> list) {
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

    private void updateItemsWithUser(List<AssetsInfoVo> list) {
        List<SysUserDTO> sysUserDTOList = BeanUtil.copyToList(iSysUserService.list(), SysUserDTO.class);
        Map<Long, SysUserDTO> userMap = sysUserDTOList.stream().collect(Collectors.toMap(SysUserDTO::getSysUserId, Function.identity()));
        list.forEach(item -> {
            item.setManageUserName(userMap.getOrDefault(item.getManageUserId(), new SysUserDTO()).getRealName());
            item.setUseUserName(userMap.getOrDefault(item.getUseUserId(), new SysUserDTO()).getRealName());
        });
    }

    private void updateItemsWithDept(List<AssetsInfoVo> list) {
        List<SysDeptDTO> sysDeptDTOList = BeanUtil.copyToList(iSysDeptService.list(), SysDeptDTO.class);
        Map<Long, SysDeptDTO> deptMap = sysDeptDTOList.stream().collect(Collectors.toMap(SysDeptDTO::getSysDeptId, Function.identity()));
        list.forEach(item -> {
            item.setManageOrgName(deptMap.getOrDefault(item.getManageOrgId(), new SysDeptDTO()).getDeptName());
            item.setUseOrgName(deptMap.getOrDefault(item.getUseOrgId(), new SysDeptDTO()).getDeptName());
        });
    }
}




