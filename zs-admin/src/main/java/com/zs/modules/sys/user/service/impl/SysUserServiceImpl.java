package com.zs.modules.sys.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.menu.service.ISysMenuService;
import com.zs.modules.sys.user.domain.dto.SysUserDTO;
import com.zs.modules.sys.user.domain.query.SysUserAddParams;
import com.zs.modules.sys.user.domain.entity.SysUserEntity;
import com.zs.modules.sys.user.domain.query.SysUserQueryParams;
import com.zs.modules.sys.user.domain.vo.SysUserVo;
import com.zs.modules.sys.user.mapper.SysUserMapper;
import com.zs.modules.sys.user.service.ISysUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author 86740
 */
@Configuration
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {


    @Resource
    private ISysMenuService iSysMenuService;

    @Override
    public PageResult<SysUserVo> page(SysUserQueryParams sysUserQueryParams) {
        return null;
    }

    @Override
    public void save(SysUserAddParams sysUserAddParams) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserAddParams, SysUserEntity.class);
        sysUserEntity.setPassword(new BCryptPasswordEncoder().encode(sysUserEntity.getPassword()));
        this.baseMapper.insert(sysUserEntity);
    }

    @Override
    public void update(SysUserAddParams sysUserAddParams) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserAddParams, SysUserEntity.class);
        this.baseMapper.insert(sysUserEntity);
    }

    @Override
    public SysUserVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysUserVo.class);
    }


    @Override
    public SysUserDTO selectByUserName(String userName) {
        SysUserEntity sysUserEntity = this.baseMapper.selectOne(new QueryWrapper<SysUserEntity>().eq("username", userName));
        return BeanUtil.copyProperties(sysUserEntity, SysUserDTO.class);
    }

    @Override
    public Set<String> getPermissions(SysUserDTO sysUserDTO) {
        // 超级管理员获取全部,其他根据角色获取
        if(sysUserDTO.getIsAdmin() == 1){
            return iSysMenuService.getAllPermissions();
        }else{
            return iSysMenuService.getPermissions(sysUserDTO.getSysUserId());
        }
    }
}
