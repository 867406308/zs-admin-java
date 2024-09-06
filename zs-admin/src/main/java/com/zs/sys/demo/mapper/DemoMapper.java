package com.zs.sys.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.common.mp.annotation.DataScope;
import com.zs.sys.demo.domain.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper extends BaseMapper<DemoEntity> {

    @DataScope
    List<DemoEntity> getList();
}
