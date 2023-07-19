package com.zs.modules.sys.menu.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.page.PageInfo;
import com.zs.common.page.PageResult;
import com.zs.common.utils.TreeUtil;
import com.zs.modules.sys.menu.domain.params.SysMenuAddParams;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import com.zs.modules.sys.menu.domain.params.SysMenuQueryParams;
import com.zs.modules.sys.menu.domain.vo.SysMenuVo;
import com.zs.modules.sys.menu.mapper.SysMenuMapper;
import com.zs.modules.sys.menu.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 86740
 */

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {


    @Override
    public PageResult<SysMenuVo> page(SysMenuQueryParams sysMenuQueryParams) {
        Page<SysMenuEntity> page = new PageInfo<>(sysMenuQueryParams);
        QueryWrapper<SysMenuEntity> wrapper = new QueryWrapper<>();

        IPage<SysMenuEntity> iPage = baseMapper.selectPage(page, wrapper);
        List<SysMenuVo> list = BeanUtil.copyToList(iPage.getRecords(), SysMenuVo.class);

        return new PageResult<>(list, page.getTotal(), SysMenuVo.class);


    }

    @Override
    public List<SysMenuVo> getNavList() {
        List<SysMenuVo> list = BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<SysMenuEntity>().eq("type", 1).eq("type", 2)), SysMenuVo.class);
        return TreeUtil.build(list, 0L);
    }

    @Override
    public List<SysMenuVo> getList() {
        List<SysMenuVo> list = BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<>()), SysMenuVo.class);
        return TreeUtil.build(list, 0L);
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
}
