package com.zs.modules.assets.scrap.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.model.domain.SysDeptDTO;
import com.zs.common.core.model.domain.SysUserDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.info.service.AssetsInfoService;
import com.zs.modules.assets.scrap.domain.entity.AssetsScrapDetailsEntity;
import com.zs.modules.assets.scrap.domain.params.AssetsScrapDetailsQueryParams;
import com.zs.modules.assets.scrap.domain.vo.AssetsScrapDetailsVo;
import com.zs.modules.assets.scrap.mapper.AssetsScrapDetailsMapper;
import com.zs.modules.assets.scrap.service.AssetsScrapDetailsService;
import com.zs.modules.sys.dept.service.ISysDeptService;
import com.zs.modules.sys.user.service.ISysUserService;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class AssetsScrapDetailsServiceImpl extends ServiceImpl<AssetsScrapDetailsMapper, AssetsScrapDetailsEntity> implements AssetsScrapDetailsService {

    @Resource
    private AssetsInfoService assetInfoService;
    @Resource
    private ISysUserService iSysUserService;
    @Resource
    private ISysDeptService iSysDeptService;

    @NotNull
    @Override
    public PageResult<AssetsScrapDetailsVo> page(@NotNull AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams) {
        Page<AssetsScrapDetailsEntity> page = new PageInfo<>(assetsScrapDetailsQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(assetsScrapDetailsQueryParams);
        IPage<AssetsScrapDetailsEntity> iPage = baseMapper.page(page, params);
        List<AssetsScrapDetailsVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsScrapDetailsVo.class);
        updateItemsWithDetails(list);
        return new PageResult<>(list, page.getTotal(), AssetsScrapDetailsVo.class);
    }

    @Nullable
    @Override
    public List<AssetsScrapDetailsVo> list(AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams) {
        List<AssetsScrapDetailsEntity> entityList = baseMapper.list(assetsScrapDetailsQueryParams);
        List<AssetsScrapDetailsVo> list = BeanUtil.copyToList(entityList, AssetsScrapDetailsVo.class);
        updateItemsWithDetails(list);
        return list;
    }

    @Override
    public void saveBatch(@NotNull List<AssetsScrapDetailsEntity> list) {
        for (AssetsScrapDetailsEntity assetsScrapDetailsEntity : list) {
            baseMapper.insert(assetsScrapDetailsEntity);
        }
        // 修改资产信息表中的资产状态为报废aus3
        List<String> serialNoList = list.stream().map(AssetsScrapDetailsEntity::getAssetsSerialNo).toList();
        assetInfoService.updateUseStatusCode(serialNoList);
    }

    private void updateItemsWithDetails(@NotNull List<AssetsScrapDetailsVo> list) {
        updateItemsWithUser(list);
        updateItemsWithDept(list);
    }

    private void updateItemsWithUser(@NotNull List<AssetsScrapDetailsVo> list) {
        List<SysUserDTO> sysUserDTOList = BeanUtil.copyToList(iSysUserService.list(), SysUserDTO.class);
        Map<Long, SysUserDTO> userMap = sysUserDTOList.stream().collect(Collectors.toMap(SysUserDTO::getSysUserId, Function.identity()));
        list.forEach(item -> item.setManageUserName(userMap.getOrDefault(item.getManageUserId(), new SysUserDTO()).getRealName()));
    }

    private void updateItemsWithDept(@NotNull List<AssetsScrapDetailsVo> list) {
        List<SysDeptDTO> sysDeptDTOList = BeanUtil.copyToList(iSysDeptService.list(), SysDeptDTO.class);
        Map<Long, SysDeptDTO> deptMap = sysDeptDTOList.stream().collect(Collectors.toMap(SysDeptDTO::getSysDeptId, Function.identity()));
        list.forEach(item -> item.setManageOrgName(deptMap.getOrDefault(item.getManageOrgId(), new SysDeptDTO()).getDeptName()));
    }
}
