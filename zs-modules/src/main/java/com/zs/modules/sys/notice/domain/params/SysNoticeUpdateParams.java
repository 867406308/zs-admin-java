package com.zs.modules.sys.notice.domain.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 86740
 */
@Data
public class SysNoticeUpdateParams {

    @Schema(description = "通知公告id")
    private Long sysNoticeId;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "通知公告类型: 通知、公告、其他")
    private String type;
    @Schema(description = "通知公告等级: 普通、一般、紧急")
    private String level;
    @Schema(description = "状态:0撤销，1草稿，2已发布")
    private Integer status;
    @Schema(description = "接收人ids")
    private List<Long> receiverIds;
}
