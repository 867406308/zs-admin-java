package com.zs.modules.sys.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.mp.service.MpSysRoleService;
import com.zs.modules.sys.role.domain.entity.SysRoleEntity;
import com.zs.modules.sys.role.domain.entity.SysRoleMenuEntity;
import com.zs.modules.sys.role.domain.params.SysRoleAddParams;
import com.zs.modules.sys.role.domain.params.SysRoleQueryParams;
import com.zs.modules.sys.role.domain.vo.SysRoleVo;
import com.zs.modules.sys.role.mapper.SysRoleMapper;
import com.zs.modules.sys.role.service.ISysRoleDeptService;
import com.zs.modules.sys.role.service.ISysRoleMenuService;
import com.zs.modules.sys.role.service.ISysRoleService;
import com.zs.modules.sys.user.service.ISysUserRoleService;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 86740
 */

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService,MpSysRoleService {

    @Resource
    private ISysRoleMenuService iSysRoleMenuService;
    @Resource
    private ISysUserRoleService iSysUserRoleService;
    @Resource
    private ISysRoleDeptService iSysRoleDeptService;

    @NotNull
    @Override
    public PageResult<SysRoleVo> page(@NotNull SysRoleQueryParams sysRoleQueryParams) {

        Page<SysRoleEntity> page = new PageInfo<>(sysRoleQueryParams);
        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Strings.isNotEmpty(sysRoleQueryParams.getRoleName()), "role_name", sysRoleQueryParams.getRoleName());

        IPage<SysRoleEntity> iPage = baseMapper.selectPage(page, wrapper);
        List<SysRoleVo> list = BeanUtil.copyToList(iPage.getRecords(), SysRoleVo.class);

        return new PageResult<>(list, page.getTotal(), SysRoleVo.class);
    }


    @Nullable
    @Override
    public List<SysRoleVo> getList(@NotNull SysRoleQueryParams sysRoleQueryParams) {
        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Strings.isNotEmpty(sysRoleQueryParams.getRoleName()), "role_name", sysRoleQueryParams.getRoleName());
        return BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<>()), SysRoleVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@NotNull SysRoleAddParams sysRoleAddParams) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleAddParams, SysRoleEntity.class);
        baseMapper.insert(sysRoleEntity);
        if (!sysRoleAddParams.getMenuList().isEmpty()) {
            iSysRoleMenuService.save(sysRoleEntity.getSysRoleId(), sysRoleAddParams.getMenuList());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(@NotNull SysRoleAddParams sysRoleAddParams) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleAddParams, SysRoleEntity.class);
        baseMapper.updateById(sysRoleEntity);
        if (!sysRoleAddParams.getMenuList().isEmpty()) {
            iSysRoleMenuService.save(sysRoleEntity.getSysRoleId(), sysRoleAddParams.getMenuList());
        }

        if (!sysRoleAddParams.getDeptList().isEmpty()) {
            iSysRoleDeptService.save(sysRoleEntity.getSysRoleId(), sysRoleAddParams.getDeptList());
        }
    }

    @NotNull
    @Override
    public SysRoleVo getById(Long id) {
        SysRoleVo sysRoleVo = BeanUtil.copyProperties(baseMapper.selectById(id), SysRoleVo.class);
        // 角色对应的菜单权限
        List<Long> menuList = iSysRoleMenuService.getMenuList(id);
        sysRoleVo.setMenuList(menuList);

        // 角色对应的部门权限
        List<Long> deptList = iSysRoleDeptService.getDeptIds(id);
        sysRoleVo.setDeptList(deptList);
        return sysRoleVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        List<Long> userRoleIds = iSysUserRoleService.queryByRoleId(id);
        if (!userRoleIds.isEmpty()) {
            throw new ZsException("角色正在使用中，不能删除");
        }

        baseMapper.deleteById(id);
        // 删除角色对应的菜单权限
        iSysRoleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("sys_role_id", id));
    }

    @Override
    public void batchDelById(@NotNull Long[] sysRoleIds) {

        for (Long sysRoleId : sysRoleIds) {

            List<Long> userRoleIds = iSysUserRoleService.queryByRoleId(sysRoleId);
            if (!userRoleIds.isEmpty()) {
                throw new ZsException("角色正在使用中，不能删除");
            }
        }

        baseMapper.deleteByIds(Arrays.asList(sysRoleIds));
    }

    @NotNull
    @Override
    public Set<Integer> getDataScope(Long userId) {
        List<SysRoleEntity> roleEntityList = this.baseMapper.getList(userId);
        return roleEntityList.stream().map(SysRoleEntity::getDataScope).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getRoleDeptIds(Long userId) {
        return this.baseMapper.getDataScopeDeptIds(userId);
    }
}
