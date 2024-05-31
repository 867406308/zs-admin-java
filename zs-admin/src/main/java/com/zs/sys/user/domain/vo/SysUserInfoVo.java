package com.zs.sys.user.domain.vo;

import com.zs.sys.user.domain.dto.SysUserDeptPostDTO;
import lombok.Data;

import java.util.List;

/**
 * @author 86740
 */
@Data
public class SysUserInfoVo {

    private Long sysUserId;
    private String username;
    private String realName;
    private String phone;
    private String email;
    private Integer age;
    private Integer sex;
    private String employeeNumber;
    private Integer isAdmin;
    private Long sysDeptId;
    private Long sysPostId;
    private Integer status;
    private List<Long> roleIdList;
    private List<SysUserDeptPostDTO> deptPostList;
}
