package com.zs.modules.sys.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.modules.sys.user.domain.dto.SysUserDTO;
import com.zs.modules.sys.user.domain.entity.SysUserEntity;
import com.zs.modules.sys.user.mapper.SysUserMapper;
import com.zs.modules.sys.user.service.ISysUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author 86740
 */
@Configuration
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {

    @Override
    public void save(SysUserDTO sysUserDTO) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserDTO, SysUserEntity.class);
        sysUserEntity.setPassword(new BCryptPasswordEncoder().encode(sysUserEntity.getPassword()));
        this.baseMapper.insert(sysUserEntity);
    }

    @Override
    public SysUserDTO selectByUserName(String userName) {
        SysUserEntity sysUserEntity = this.baseMapper.selectOne(new QueryWrapper<SysUserEntity>().eq("username", userName));
        return BeanUtil.copyProperties(sysUserEntity, SysUserDTO.class);
    }
}
