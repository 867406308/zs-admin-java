package com.zs.modules.assets.classify.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 86740
 */
@Data
@TableName("assets_classify_gb")
public class AssetsClassifyGbEntity {

    @TableId
    private Long id;
    private String code;
    private String name;
    private Long pid;
    private String pids;
    private String remark;
}
