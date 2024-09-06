package com.zs.sys.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.dict.domain.entity.SysDictTypeEntity;
import com.zs.sys.dict.domain.params.SysDictTypeAddParams;
import com.zs.sys.dict.domain.params.SysDictTypeQueryParams;
import com.zs.sys.dict.domain.vo.SysDictTypeVo;
import jakarta.annotation.Nullable;

import java.util.List;


/**
 * @author 86740
 */
public interface ISysDictTypeService extends IService<SysDictTypeEntity> {

    PageResult<SysDictTypeVo> page(SysDictTypeQueryParams sysDictTypeQueryParams);

    @Nullable
    List<SysDictTypeVo> list(SysDictTypeQueryParams sysDictTypeQueryParams);

    SysDictTypeVo getById(Long id);

    void save(SysDictTypeAddParams sysDictTypeAddParams);

    void update(SysDictTypeAddParams sysDictTypeAddParams);

    void deleteById(Long id);

}
