package com.zs.sys.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.sys.user.domain.dto.SysUserDeptPostDTO;
import com.zs.sys.user.domain.entity.SysUserDeptPostEntity;
import com.zs.sys.user.domain.params.SysUserDeptPostAddParams;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysUserDeptPostService extends IService<SysUserDeptPostEntity> {

    /** 根据用户id保存或更新 **/
    void saveOrUpdate(Long sysUserId, List<SysUserDeptPostAddParams> sysDeptIdList);

    /** 根据用户id获取 **/
    @Nullable
    List<SysUserDeptPostDTO> getByUserId(Long sysUserId);

    /** 根据用户id删除 **/
    void delByUserId(Long sysUserId);

    /** 根据岗位id获取用户数量 **/
    Long getByPostId(Long sysPostId);
}
