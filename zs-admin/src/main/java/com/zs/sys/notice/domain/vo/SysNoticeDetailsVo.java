package com.zs.sys.notice.domain.vo;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysNoticeDetailsVo {


    /** 通知公告详情id */
    private Long sysNoticeDetailsId;
    /** 通知公告id */
    private Long sysNoticeId;
    /** 用户id */
    private Long receiverId;
    /** 用户名 */
    private String realName;
    /** 状态:1已读，2未读 */
    private Integer status;
    /** 阅读时间 */
    private String readTime;
}

