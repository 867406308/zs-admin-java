package com.zs.sys.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.dict.domain.entity.SysDictDataEntity;
import com.zs.sys.dict.domain.params.SysDictDataAddParams;
import com.zs.sys.dict.domain.params.SysDictDataQueryParams;
import com.zs.sys.dict.domain.vo.SysDictDataVo;

import java.util.List;


/**
 * @author 86740
 */
public interface ISysDictDataService extends IService<SysDictDataEntity> {

    PageResult<SysDictDataVo> page(SysDictDataQueryParams sysDictDataQueryParams);

    List<SysDictDataVo> list(SysDictDataQueryParams sysDictDataQueryParams);

    SysDictDataVo getById(Long id);

    void save(SysDictDataAddParams sysDictDataAddParams);

    void update(SysDictDataAddParams sysDictDataAddParams);

    void deleteById(Long id);


    /**
     * 保存字典数据到redis缓存
     */
    void saveCache();
}
