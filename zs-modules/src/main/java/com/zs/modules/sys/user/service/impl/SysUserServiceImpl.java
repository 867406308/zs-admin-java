package com.zs.modules.sys.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.model.SysUser;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.security.service.CustomUserDetailsService;
import com.zs.modules.sys.dept.service.ISysDeptService;
import com.zs.modules.sys.menu.service.ISysMenuService;
import com.zs.modules.sys.user.domain.dto.SysUserDeptPostDTO;
import com.zs.modules.sys.user.domain.entity.SysUserEntity;
import com.zs.modules.sys.user.domain.params.SysUserAddParams;
import com.zs.modules.sys.user.domain.params.SysUserPasswordParams;
import com.zs.modules.sys.user.domain.params.SysUserQueryParams;
import com.zs.modules.sys.user.domain.params.SysUserUpdateParams;
import com.zs.modules.sys.user.domain.vo.SysUserInfoVo;
import com.zs.modules.sys.user.domain.vo.SysUserVo;
import com.zs.modules.sys.user.mapper.SysUserMapper;
import com.zs.modules.sys.user.service.ISysUserDeptPostService;
import com.zs.modules.sys.user.service.ISysUserRoleService;
import com.zs.modules.sys.user.service.ISysUserService;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 86740
 */
@Configuration
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService, CustomUserDetailsService {


    @Resource
    private ISysMenuService iSysMenuService;
    @Resource
    private ISysDeptService iSysDeptService;
    @Resource
    private ISysUserRoleService iSysUserRoleService;
    @Resource
    private ISysUserDeptPostService iSysUserDeptPostService;

    @NotNull
    @Override
    public PageResult<SysUserVo> page(@NotNull SysUserQueryParams sysUserQueryParams) {
        Page<SysUserEntity> page = new PageInfo<>(sysUserQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(sysUserQueryParams);
        List<Long> deptList = iSysDeptService.getSubDeptIdList(sysUserQueryParams.getSysDeptId());
        params.put("deptList", deptList);
        IPage<SysUserEntity> iPage = baseMapper.page(page, params);
        page.getRecords().forEach(item -> {
            item.setDeptName(iSysDeptService.getBySysDeptId(item.getSysDeptId()));
        });
        List<SysUserVo> list = BeanUtil.copyToList(iPage.getRecords(), SysUserVo.class);

        return new PageResult<>(list, page.getTotal(), SysUserVo.class);
    }

    @Nullable
    @Override
    public List<SysUserVo> list(@NotNull SysUserQueryParams sysUserQueryParams) {
        List<Long> deptList = iSysDeptService.getSubDeptIdList(sysUserQueryParams.getSysDeptId());

        Map<String, Object> params = BeanUtil.beanToMap(sysUserQueryParams);
        params.put("status", 1);
        params.put("deptList", deptList);
        List<SysUserEntity> list = this.baseMapper.getList(params);

        return BeanUtil.copyToList(list, SysUserVo.class);
    }

    @Override
    public List<SysUserVo> getUserList(Long[] sysUserIds) {

        sysUserIds = Arrays.stream(sysUserIds).filter(Objects::nonNull).toArray(Long[]::new);
        if (sysUserIds.length == 0) {
            return Collections.emptyList();
        }
        List<Long> idList = List.of(sysUserIds);
        List<SysUserEntity> list = this.baseMapper.getUserList(idList);
        return BeanUtil.copyToList(list, SysUserVo.class);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@NotNull SysUserAddParams sysUserAddParams) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserAddParams, SysUserEntity.class);
        sysUserEntity.setPassword(new BCryptPasswordEncoder().encode(sysUserEntity.getPassword()));
        this.baseMapper.insert(sysUserEntity);
        // 保存用户与部门关系
        iSysUserDeptPostService.saveOrUpdate(sysUserEntity.getSysUserId(), sysUserAddParams.getDeptPostList());
        // 保存用户与角色关系
        iSysUserRoleService.saveOrUpdate(sysUserEntity.getSysUserId(), sysUserAddParams.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(@NotNull SysUserUpdateParams sysUserUpdateParams) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserUpdateParams, SysUserEntity.class);
        this.baseMapper.updateById(sysUserEntity);

        if (sysUserUpdateParams.getDeptPostList() != null && !sysUserUpdateParams.getDeptPostList().isEmpty()) {
            // 保存用户与部门关系
            iSysUserDeptPostService.saveOrUpdate(sysUserEntity.getSysUserId(), sysUserUpdateParams.getDeptPostList());
        }
        if(  sysUserUpdateParams.getRoleIdList() != null && !sysUserUpdateParams.getRoleIdList().isEmpty()){
            // 先删除用户与角色关系
            iSysUserRoleService.saveOrUpdate(sysUserEntity.getSysUserId(), sysUserUpdateParams.getRoleIdList());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelById(@NotNull Long[] ids) {
        for (Long id : ids) {
            // 删除用户与部门职位关系
            iSysUserDeptPostService.delByUserId(id);
            // 删除用户与角色关系
            iSysUserRoleService.delByUserId(id);
            this.baseMapper.updateDeleted(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delById(Long id) {
        // 删除用户与部门职位关系
        iSysUserDeptPostService.delByUserId(id);
        // 删除用户与角色关系
        iSysUserRoleService.delByUserId(id);
        this.baseMapper.updateDeleted(id);
    }


    @Override
    public void resetPassword(@NotNull SysUserPasswordParams sysUserPasswordParams) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserId(sysUserPasswordParams.getSysUserId());
        sysUserEntity.setPassword(new BCryptPasswordEncoder().encode(sysUserPasswordParams.getPassword()));

        this.baseMapper.updateById(sysUserEntity);
    }

    @NotNull
    @Override
    public SysUserInfoVo getById(Long id) {
        SysUserEntity sysUserEntity = baseMapper.selectById(id);
        SysUserInfoVo sysUserInfoVo = BeanUtil.copyProperties(sysUserEntity, SysUserInfoVo.class);
        // 查询用户的角色
        List<Long> roleIdList = iSysUserRoleService.queryRoleIdList(sysUserEntity.getSysUserId());
        // 查询用户所属的任职部门
        List<SysUserDeptPostDTO> deptPostList = iSysUserDeptPostService.getByUserId(sysUserEntity.getSysUserId());
        sysUserInfoVo.setRoleIdList(roleIdList);
        sysUserInfoVo.setDeptPostList(deptPostList);

        return sysUserInfoVo;
    }


    public Set<String> getPermissions(@NotNull SysUserEntity sysUserEntity) {
        // 超级管理员获取全部,其他根据角色获取
        if (sysUserEntity.getIsAdmin() == 1) {
            return iSysMenuService.getAllPermissions();
        } else {
            return iSysMenuService.getPermissions(sysUserEntity.getSysUserId());
        }
    }


    @NotNull
    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUserEntity sysUserEntity = baseMapper.selectByUserName(username);
        // 查询数据库用户是否存在
        if (Objects.isNull(sysUserEntity)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new LoginUserInfo(BeanUtil.toBean(sysUserEntity, SysUser.class), getPermissions(sysUserEntity));
    }


}
