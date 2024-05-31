package com.zs.assets.allot.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 86740
 */
@Data
@TableName("assets_allot_details")
public class AssetsAllotDetailsEntity {

    @TableId
    private Long id;
    private Long allotId;
    private String assetsSerialNo;
}
