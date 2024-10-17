package com.zs.modules.sys.user.domain.params;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysUserPasswordParams {

    private Long sysUserId;
    @NotBlank(message = "新密码不能为空")
    private String password;
}
