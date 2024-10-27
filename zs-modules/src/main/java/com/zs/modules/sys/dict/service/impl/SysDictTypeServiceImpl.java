package com.zs.modules.sys.dict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.dict.domain.entity.SysDictTypeEntity;
import com.zs.modules.sys.dict.domain.params.SysDictTypeAddParams;
import com.zs.modules.sys.dict.domain.params.SysDictTypeQueryParams;
import com.zs.modules.sys.dict.domain.vo.SysDictTypeVo;
import com.zs.modules.sys.dict.mapper.SysDictTypeMapper;
import com.zs.modules.sys.dict.service.ISysDictTypeService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86740
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictTypeEntity> implements ISysDictTypeService {


    @NotNull
    @Override
    public PageResult<SysDictTypeVo> page(@NotNull SysDictTypeQueryParams sysDictTypeQueryParams) {
        Page<SysDictTypeEntity> page = new PageInfo<>(sysDictTypeQueryParams);
        QueryWrapper<SysDictTypeEntity> wrapper = new QueryWrapper<>();
        wrapper.like(Strings.isNotEmpty(sysDictTypeQueryParams.getDictType()), "dict_type", sysDictTypeQueryParams.getDictType());
        wrapper.like(Strings.isNotEmpty(sysDictTypeQueryParams.getDictName()), "dict_name", sysDictTypeQueryParams.getDictName());

        IPage<SysDictTypeEntity> iPage = baseMapper.selectPage(page, wrapper);
        List<SysDictTypeVo> list = BeanUtil.copyToList(iPage.getRecords(), SysDictTypeVo.class);

        return new PageResult<>(list, page.getTotal(), SysDictTypeVo.class);
    }

    @Nullable
    @Override
    public List<SysDictTypeVo> list(SysDictTypeQueryParams sysDictTypeQueryParams) {
        return BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<SysDictTypeEntity>().orderByAsc("sort")), SysDictTypeVo.class);
    }

    @Override
    public SysDictTypeVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysDictTypeVo.class);
    }

    @Override
    public void save(SysDictTypeAddParams sysDictTypeAddParams) {
        SysDictTypeEntity sysDictTypeEntity = BeanUtil.copyProperties(sysDictTypeAddParams, SysDictTypeEntity.class);
        baseMapper.insert(sysDictTypeEntity);
    }

    @Override
    public void update(SysDictTypeAddParams sysDictTypeAddParams) {
        SysDictTypeEntity sysDictTypeEntity = BeanUtil.copyProperties(sysDictTypeAddParams, SysDictTypeEntity.class);
        baseMapper.updateById(sysDictTypeEntity);
    }

    @Override
    public void deleteById(Long id) {

        baseMapper.deleteById(id);
    }


}
