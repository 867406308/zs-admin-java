package com.zs.sys.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.sys.role.domain.entity.SysRoleEntity;
import com.zs.sys.role.domain.entity.SysRoleMenuEntity;
import com.zs.sys.role.domain.params.SysRoleAddParams;
import com.zs.sys.role.domain.params.SysRoleQueryParams;
import com.zs.sys.role.domain.vo.SysRoleVo;
import com.zs.sys.role.mapper.SysRoleMapper;
import com.zs.sys.role.service.ISysRoleMenuService;
import com.zs.sys.role.service.ISysRoleService;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 86740
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {

    @Resource
    private ISysRoleMenuService iSysRoleMenuService;

    @Override
    public PageResult<SysRoleVo> page(SysRoleQueryParams sysRoleQueryParams) {

        Page<SysRoleEntity> page = new PageInfo<>(sysRoleQueryParams);
        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Strings.isNotEmpty(sysRoleQueryParams.getRoleName()), "role_name", sysRoleQueryParams.getRoleName());

        IPage<SysRoleEntity> iPage = baseMapper.selectPage(page, wrapper);
        List<SysRoleVo> list = BeanUtil.copyToList(iPage.getRecords(), SysRoleVo.class);

        return new PageResult<>(list, page.getTotal(), SysRoleVo.class);
    }


    @Override
    public List<SysRoleVo> getList() {
        return BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<>()), SysRoleVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleAddParams sysRoleAddParams) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleAddParams, SysRoleEntity.class);
        baseMapper.insert(sysRoleEntity);
        if (!sysRoleAddParams.getMenuList().isEmpty()) {
            iSysRoleMenuService.save(sysRoleEntity.getSysRoleId(), sysRoleAddParams.getMenuList());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleAddParams sysRoleAddParams) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleAddParams, SysRoleEntity.class);
        baseMapper.updateById(sysRoleEntity);
        if (!sysRoleAddParams.getMenuList().isEmpty()) {
            iSysRoleMenuService.save(sysRoleEntity.getSysRoleId(), sysRoleAddParams.getMenuList());
        }
    }

    @Override
    public SysRoleVo getById(Long id) {
        SysRoleVo sysRoleVo = BeanUtil.copyProperties(baseMapper.selectById(id), SysRoleVo.class);
        // 角色对应的菜单权限
        List<Long> menuList = iSysRoleMenuService.getMenuList(id);
        sysRoleVo.setMenuList(menuList);
        return sysRoleVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
        iSysRoleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("sys_role_id", id));
    }
}
