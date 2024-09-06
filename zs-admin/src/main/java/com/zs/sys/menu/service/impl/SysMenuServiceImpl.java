package com.zs.sys.menu.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.core.utils.SecurityUtil;
import com.zs.common.core.utils.TreeUtil;
import com.zs.sys.menu.domain.entity.SysMenuEntity;
import com.zs.sys.menu.domain.params.SysMenuAddParams;
import com.zs.sys.menu.domain.params.SysMenuQueryParams;
import com.zs.sys.menu.domain.vo.SysMenuVo;
import com.zs.sys.menu.mapper.SysMenuMapper;
import com.zs.sys.menu.service.ISysMenuService;
import org.apache.logging.log4j.util.Strings;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author 86740
 */

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {


    @NotNull
    @Override
    public PageResult<SysMenuVo> page(@NotNull SysMenuQueryParams sysMenuQueryParams) {
        Page<SysMenuEntity> page = new PageInfo<>(sysMenuQueryParams);
        QueryWrapper<SysMenuEntity> wrapper = new QueryWrapper<>();

        IPage<SysMenuEntity> iPage = baseMapper.selectPage(page, wrapper);
        List<SysMenuVo> list = BeanUtil.copyToList(iPage.getRecords(), SysMenuVo.class);

        return new PageResult<>(list, page.getTotal(), SysMenuVo.class);


    }

    @NotNull
    @Override
    public List<SysMenuVo> getNavList() {
        LoginUserInfo loginUserInfo = SecurityUtil.getUserInfo();
        List<SysMenuVo> list;
        if (loginUserInfo.sysUser.getIsAdmin() == 1) {
            list = BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<SysMenuEntity>().in("type", 1, 2).orderByAsc("sort")), SysMenuVo.class);
        } else {
            list = BeanUtil.copyToList(baseMapper.getMenuList(loginUserInfo.sysUser.getSysUserId()), SysMenuVo.class);
        }
        return TreeUtil.build(list, 0L);
    }

    @NotNull
    @Override
    public List<SysMenuVo> getList(@NotNull SysMenuQueryParams sysMenuQueryParams) {
        QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(sysMenuQueryParams.getTitle()), "title", sysMenuQueryParams.getTitle());
        queryWrapper.orderByAsc("sort");
        List<SysMenuEntity> entityList = baseMapper.selectList(queryWrapper);
        List<SysMenuEntity> menuList = baseMapper.selectList(new QueryWrapper<SysMenuEntity>().orderByAsc("sort"));

        List<SysMenuVo> list = entityList.stream().map(menu -> getTreeParent(menu, menuList)).flatMap(List::stream).map(entity -> BeanUtil.copyProperties(entity, SysMenuVo.class)).sorted(Comparator.comparing(SysMenuVo::getSort)).collect(Collectors.toList());


        return TreeUtil.build(list);
    }

    @NotNull
    public List<SysMenuEntity> getTreeParent(@NotNull SysMenuEntity sysMenuEntity, @NotNull List<SysMenuEntity> deptList) {
        Map<Long, SysMenuEntity> map = deptList.stream().collect(Collectors.toMap(SysMenuEntity::getSysMenuId, Function.identity()));
        List<SysMenuEntity> pidList = new ArrayList<>();
        getTreePid(sysMenuEntity.getPid(), map, pidList);
        pidList.add(sysMenuEntity);
        return pidList;
    }

    public void getTreePid(Long pid, @NotNull Map<Long, SysMenuEntity> map, @NotNull List<SysMenuEntity> pidList) {
        SysMenuEntity parent = map.get(pid);
        if (parent != null) {
            pidList.add(parent);
            getTreePid(parent.getPid(), map, pidList);
        }
    }

    @Override
    public void save(SysMenuAddParams sysMenuAddParams) {
        baseMapper.insert(BeanUtil.copyProperties(sysMenuAddParams, SysMenuEntity.class));
    }

    @Override
    public void update(SysMenuAddParams sysMenuAddParams) {
        baseMapper.updateById(BeanUtil.copyProperties(sysMenuAddParams, SysMenuEntity.class));
    }

    @Override
    public SysMenuVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysMenuVo.class);
    }

    @NotNull
    @Override
    public Set<String> getAllPermissions() {
        List<SysMenuEntity> sysMenuEntityList = baseMapper.selectList(new QueryWrapper<SysMenuEntity>().eq("type", 3));
        return sysMenuEntityList.stream().map(SysMenuEntity::getPermissions).filter(Objects::nonNull).flatMap(permissions -> Arrays.stream(permissions.trim().split(","))).collect(Collectors.toSet());
    }

    @NotNull
    @Override
    public Set<String> getPermissions(Long sysUserId) {
        List<SysMenuEntity> sysMenuEntityList = baseMapper.getPermissions(sysUserId);
        return sysMenuEntityList.stream().map(SysMenuEntity::getPermissions).filter(Objects::nonNull).flatMap(permissions -> Arrays.stream(permissions.trim().split(","))).collect(Collectors.toSet());
    }
}
