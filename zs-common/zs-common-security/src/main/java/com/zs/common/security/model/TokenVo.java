package com.zs.common.security.model;

import lombok.Data;

/**
 * token信息
 */
@Data
public class TokenVo {

    private String accessToken;
    private String refreshToken;
}
