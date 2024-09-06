package com.zs.sys.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.sys.user.domain.entity.SysUserRoleEntity;
import com.zs.sys.user.mapper.SysUserRoleMapper;
import com.zs.sys.user.service.ISysUserRoleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements ISysUserRoleService {
    @Override
    public void saveOrUpdate(Long sysUserId, @NotNull List<Long> sysRoleIdList) {
        // 先删除用户与角色关系
        baseMapper.delete(new QueryWrapper<SysUserRoleEntity>().eq("sys_user_id", sysUserId));
        // 在添加用户与角色关系
        if (!sysRoleIdList.isEmpty()) {
            for (Long sysRoleId : sysRoleIdList) {
                SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                sysUserRoleEntity.setSysUserId(sysUserId);
                sysUserRoleEntity.setSysRoleId(sysRoleId);
                baseMapper.insert(sysUserRoleEntity);
            }
        }
    }

    @NotNull
    @Override
    public List<Long> queryRoleIdList(Long sysUserId) {
        List<SysUserRoleEntity> sysUserRoleEntityList = baseMapper.selectList(new QueryWrapper<SysUserRoleEntity>().eq("sys_user_id", sysUserId));
        return sysUserRoleEntityList.stream().map(SysUserRoleEntity::getSysRoleId).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void delByUserId(Long sysUserId) {
        this.baseMapper.delete(new QueryWrapper<SysUserRoleEntity>().eq("sys_user_id", sysUserId));
    }

    @NotNull
    @Override
    public List<Long> queryByRoleId(Long sysRoleId) {
        List<SysUserRoleEntity> sysUserRoleEntityList = baseMapper.selectList(new QueryWrapper<SysUserRoleEntity>().eq("sys_role_id", sysRoleId));
        return sysUserRoleEntityList.stream().map(SysUserRoleEntity::getSysUserId).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
