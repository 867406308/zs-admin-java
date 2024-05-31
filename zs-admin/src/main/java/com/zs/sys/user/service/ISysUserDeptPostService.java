package com.zs.sys.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.sys.user.domain.dto.SysUserDeptPostDTO;
import com.zs.sys.user.domain.entity.SysUserDeptPostEntity;
import com.zs.sys.user.domain.params.SysUserDeptPostAddParams;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysUserDeptPostService extends IService<SysUserDeptPostEntity> {

    void saveOrUpdate(Long sysUserId, List<SysUserDeptPostAddParams> sysDeptIdList);

    List<SysUserDeptPostDTO> getByUserId(Long sysUserId);
}
