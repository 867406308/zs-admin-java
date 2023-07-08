package com.zs.modules.sys.dept.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.modules.sys.dept.domain.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {

    List<String> getSubDeptIdList(String sysDeptId);
}
