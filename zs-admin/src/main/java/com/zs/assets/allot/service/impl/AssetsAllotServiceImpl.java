package com.zs.assets.allot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.allot.domain.entity.AssetsAllotEntity;
import com.zs.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.assets.allot.domain.vo.AssetsAllotVo;
import com.zs.assets.allot.mapper.AssetsAllotMapper;
import com.zs.assets.allot.service.AssetsAllotService;
import com.zs.common.core.model.domain.SysDeptDTO;
import com.zs.common.core.model.domain.SysUserDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.sys.dept.service.ISysDeptService;
import com.zs.sys.user.service.ISysUserService;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class AssetsAllotServiceImpl extends ServiceImpl<AssetsAllotMapper, AssetsAllotEntity> implements AssetsAllotService {

    @Resource
    private ISysUserService iSysUserService;
    @Resource
    private ISysDeptService iSysDeptService;

    @Override
    public PageResult<AssetsAllotVo> page(AssetsAllotQueryParams assetsAllotQueryParams) {
        Page<AssetsAllotEntity> page = new PageInfo<>(assetsAllotQueryParams);
        IPage<AssetsAllotEntity> iPage = baseMapper.selectPage(page, getWrapper(assetsAllotQueryParams));

        List<AssetsAllotVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsAllotVo.class);
        updateItemsWithDetails(list);

        return new PageResult<>(list, page.getTotal(), AssetsAllotVo.class);
    }

    public QueryWrapper<AssetsAllotEntity> getWrapper(AssetsAllotQueryParams assetsAllotQueryParams) {
        QueryWrapper<AssetsAllotEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Strings.isNotEmpty(assetsAllotQueryParams.getSerialNo()), "serial_no", assetsAllotQueryParams.getSerialNo());
        return queryWrapper;
    }

    private void updateItemsWithDetails(List<AssetsAllotVo> list) {
        updateItemsWithUser(list);
        updateItemsWithDept(list);
    }

    private void updateItemsWithUser(List<AssetsAllotVo> list) {
        List<SysUserDTO> sysUserDTOList = BeanUtil.copyToList(iSysUserService.list(), SysUserDTO.class);
        Map<Long, SysUserDTO> userMap = sysUserDTOList.stream().collect(Collectors.toMap(SysUserDTO::getSysUserId, Function.identity()));
        list.forEach(item -> {
            // 原使用人
            item.setOriginalUseUserName(userMap.getOrDefault(item.getOriginalUseUserId(), new SysUserDTO()).getRealName());
            // 原管理人
            item.setOriginalManageUserName(userMap.getOrDefault(item.getOriginalManageUserId(), new SysUserDTO()).getRealName());
            // 新使用人
            item.setCurrentUseUserName(userMap.getOrDefault(item.getCurrentUseUserId(), new SysUserDTO()).getRealName());
            // 新管理人
            item.setCurrentManageUserName(userMap.getOrDefault(item.getCurrentManageUserId(), new SysUserDTO()).getRealName());
        });
    }

    private void updateItemsWithDept(List<AssetsAllotVo> list) {
        List<SysDeptDTO> sysDeptDTOList = BeanUtil.copyToList(iSysDeptService.list(), SysDeptDTO.class);
        Map<Long, SysDeptDTO> deptMap = sysDeptDTOList.stream().collect(Collectors.toMap(SysDeptDTO::getSysDeptId, Function.identity()));
        list.forEach(item -> {
            // 原使用部门
            item.setOriginalUseOrgName(deptMap.getOrDefault(item.getOriginalUseOrgId(), new SysDeptDTO()).getDeptName());
            // 原管理部门
            item.setOriginalManageOrgName(deptMap.getOrDefault(item.getOriginalManageOrgId(), new SysDeptDTO()).getDeptName());
            // 新使用部门
            item.setCurrentUseOrgName(deptMap.getOrDefault(item.getCurrentUseOrgId(), new SysDeptDTO()).getDeptName());
            // 新管理部门
            item.setCurrentManageOrgName(deptMap.getOrDefault(item.getCurrentManageOrgId(), new SysDeptDTO()).getDeptName());
        });
    }
}
