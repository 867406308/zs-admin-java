package com.zs.modules.sys.menu.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.util.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.page.PageInfo;
import com.zs.common.page.PageResult;
import com.zs.common.utils.TreeUtil;
import com.zs.modules.sys.menu.domain.dto.SysMenuDTO;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import com.zs.modules.sys.menu.domain.vo.SysMenuVo;
import com.zs.modules.sys.menu.mapper.SysMenuMapper;
import com.zs.modules.sys.menu.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {


    @Override
    public PageResult<SysMenuVo> page(Map<String, Object> params) {
        Page<SysMenuEntity> page = new PageInfo<>(params);
        QueryWrapper<SysMenuEntity> wrapper = new QueryWrapper(params);
        List<SysMenuVo> list = BeanUtil.copyToList(baseMapper.page(page, wrapper), SysMenuVo.class);

        return new PageResult<>(list, page.getTotal(), SysMenuVo.class);
    }

    @Override
    public List<SysMenuVo> getList() {
        List<SysMenuVo> list = BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<>()), SysMenuVo.class);
        return TreeUtil.build(list, 0L);
    }

    @Override
    public void save(SysMenuDTO sysMenuDTO) {
        baseMapper.insert(BeanUtil.copyProperties(sysMenuDTO, SysMenuEntity.class));
    }

    @Override
    public void update(SysMenuDTO sysMenuDTO) {
        baseMapper.updateById(BeanUtil.copyProperties(sysMenuDTO, SysMenuEntity.class));
    }

    @Override
    public SysMenuVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysMenuVo.class);
    }
}
