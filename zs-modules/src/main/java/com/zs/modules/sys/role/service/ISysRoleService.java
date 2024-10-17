package com.zs.modules.sys.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.role.domain.entity.SysRoleEntity;
import com.zs.modules.sys.role.domain.params.SysRoleAddParams;
import com.zs.modules.sys.role.domain.params.SysRoleQueryParams;
import com.zs.modules.sys.role.domain.vo.SysRoleVo;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysRoleService extends IService<SysRoleEntity> {

    /** 分页 **/
    PageResult<SysRoleVo> page(SysRoleQueryParams sysRoleQueryParams);

    /** 列表 **/
    @Nullable
    List<SysRoleVo> getList(SysRoleQueryParams sysRoleQueryParams);

    /** 新增 **/
    void save(SysRoleAddParams sysRoleAddParams);

    /** 更新 **/
    void update(SysRoleAddParams sysRoleAddParams);

    /** 根据id查询 **/
    SysRoleVo getById(Long id);

    /** 单个删除 **/
    void deleteById(Long sysRoleId);

    /** 批量删除 **/
    void batchDelById(Long[] sysRoleIds);

}
