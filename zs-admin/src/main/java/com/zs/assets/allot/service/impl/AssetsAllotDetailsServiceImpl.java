package com.zs.assets.allot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.allot.domain.entity.AssetsAllotDetailsEntity;
import com.zs.assets.allot.domain.vo.AssetsAllotDetailsVo;
import com.zs.assets.allot.mapper.AssetsAllotDetailsMapper;
import com.zs.assets.allot.service.AssetsAllotDetailsService;
import com.zs.assets.info.service.AssetsInfoService;
import com.zs.common.core.model.domain.SysDeptDTO;
import com.zs.common.core.model.domain.SysUserDTO;
import com.zs.sys.dept.service.ISysDeptService;
import com.zs.sys.user.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class AssetsAllotDetailsServiceImpl extends ServiceImpl<AssetsAllotDetailsMapper, AssetsAllotDetailsEntity> implements AssetsAllotDetailsService {

    @Resource
    private ISysUserService iSysUserService;
    @Resource
    private ISysDeptService iSysDeptService;
    @Resource
    private AssetsInfoService assetsInfoService;

    @Override
    public List<AssetsAllotDetailsVo> getAllotDetails(Long allotId) {

        List<AssetsAllotDetailsEntity> assetsAllotDetailsEntities = this.baseMapper.getAllotDetails(allotId);

        List<AssetsAllotDetailsVo> list = BeanUtil.copyToList(assetsAllotDetailsEntities, AssetsAllotDetailsVo.class);

        updateItemsWithDetails(list);

        return list;
    }

    private void updateItemsWithDetails(List<AssetsAllotDetailsVo> list) {
        updateItemsWithUser(list);
        updateItemsWithDept(list);
    }

    private void updateItemsWithUser(List<AssetsAllotDetailsVo> list) {
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

    private void updateItemsWithDept(List<AssetsAllotDetailsVo> list) {
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