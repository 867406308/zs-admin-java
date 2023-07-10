package com.zs.modules.sys.dept.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.modules.sys.dept.domain.entity.SysDeptEntity;
import com.zs.modules.sys.dept.domain.query.SysDeptAddParams;
import com.zs.modules.sys.dept.domain.vo.SysDeptVo;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysDeptService extends IService<SysDeptEntity> {

    List<SysDeptVo> getList();

    void save(SysDeptAddParams sysOrgAddParams);

    void update(SysDeptAddParams sysOrgAddParams);

    SysDeptVo getById(Long sysDeptId);

    void removeById(Long sysDeptId);

    List<String> getSubDeptIdList(String sysDeptId);
}
