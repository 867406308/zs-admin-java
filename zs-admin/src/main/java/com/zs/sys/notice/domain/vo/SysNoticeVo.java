package com.zs.sys.notice.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 86740
 */
@Data
public class SysNoticeVo implements Serializable {

    /**
     * 通知公告id
     */
    private Long sysNoticeId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 通知公告类型: 通知、公告、其他
     */
    private Integer type;
    /**
     * 通知公告等级: 普通、一般、紧急
     */
    private Integer level;
    /**
     * 状态:0撤销，1草稿，2已发布
     */
    private Integer status;
    /**
     * 发布时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date releaseTime;
    /**
     * 创建人
     */
    private String realName;

 
    private Long creator;
    private String createTime;
    private Long updater;
    private String updateTime;

    private List<SysNoticeDetailsVo> sysNoticeDetailsVos;
}
