package com.zs.sys.user.domain.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 86740
 */
@Data
public class SysUserAddParams {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(description = "真实姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String realName;
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "年龄")
    private Integer age;
    @Schema(description = "性别")
    private Integer sex;
    @Schema(description = "工号")
    private String employeeNumber;
    @Schema(description = "部门id")
    private Long sysDeptId;
    @Schema(description = "岗位id")
    private Long sysPostId;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "角色id")
    private List<Long> roleIdList;
    @Schema(description = "部门岗位关联信息")
    private List<SysUserDeptPostAddParams> deptPostList;
}
