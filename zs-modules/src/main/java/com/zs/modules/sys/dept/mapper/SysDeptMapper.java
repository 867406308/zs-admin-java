package com.zs.modules.sys.dept.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.modules.sys.dept.domain.entity.SysDeptEntity;
import com.zs.modules.sys.dept.domain.params.SysDeptQueryParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 86740
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {

    /**
     * 根据部门ID查询子级部门ID集合
     **/
    List<Long> getSubDeptIdList(Long sysDeptId);

    List<SysDeptEntity> getList(SysDeptQueryParams sysDeptQueryParams);

    SysDeptEntity getBySysDeptId(Long sysDeptId);
}
