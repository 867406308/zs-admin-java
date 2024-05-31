package com.zs.sys.dept.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.sys.dept.domain.entity.SysDeptEntity;
import com.zs.sys.dept.domain.params.SysDeptAddParams;
import com.zs.sys.dept.domain.params.SysDeptQueryParams;
import com.zs.sys.dept.domain.vo.SysDeptVo;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysDeptService extends IService<SysDeptEntity> {

    List<SysDeptVo> getTree(SysDeptQueryParams sysDeptQueryParams);

    List<SysDeptVo> getList(SysDeptQueryParams sysDeptQueryParams);

    void save(SysDeptAddParams sysOrgAddParams);

    void update(SysDeptAddParams sysOrgAddParams);

    SysDeptVo getById(Long sysDeptId);

    void removeById(Long sysDeptId);

    List<Long> getSubDeptIdList(Long sysDeptId);

}
