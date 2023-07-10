package com.zs.modules.sys.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.page.PageInfo;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.role.domain.entity.SysRoleEntity;
import com.zs.modules.sys.role.domain.query.SysRoleAddParams;
import com.zs.modules.sys.role.domain.query.SysRoleQueryParams;
import com.zs.modules.sys.role.domain.vo.SysRoleVo;
import com.zs.modules.sys.role.mapper.SysRoleMapper;
import com.zs.modules.sys.role.service.ISysRoleService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86740
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {


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
    public void save(SysRoleAddParams sysRoleAddParams) {
        baseMapper.insert(BeanUtil.copyProperties(sysRoleAddParams, SysRoleEntity.class));
    }

    @Override
    public void update(SysRoleAddParams sysRoleAddParams) {
        baseMapper.updateById(BeanUtil.copyProperties(sysRoleAddParams, SysRoleEntity.class));
    }

    @Override
    public SysRoleVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysRoleVo.class);
    }
}
