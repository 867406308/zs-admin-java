package com.zs.sys.notice.domain.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 86740
 */
@Data
public class SysNoticeDetailsUpdateParams implements Serializable {
    private Long sysNoticeDetailsId;
    private Integer status;
    private String readTime;
}
