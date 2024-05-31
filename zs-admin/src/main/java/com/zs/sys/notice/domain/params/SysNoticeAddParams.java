package com.zs.sys.notice.domain.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 86740
 */
@Data
public class SysNoticeAddParams {


    @Schema(description = "标题")
    private String title;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "通知公告类型: 1通知、2公告、3其他")
    private Integer type;
    @Schema(description = "通知公告等级: 1普通、2一般、3紧急")
    private Integer level;
    @Schema(description = "状态:0撤销，1草稿，2已发布")
    private Integer status;
    @Schema(description = "接收人ids")
    private List<Long> receiverIds;
}
