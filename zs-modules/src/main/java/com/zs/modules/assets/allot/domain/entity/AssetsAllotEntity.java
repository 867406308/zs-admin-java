package com.zs.modules.assets.allot.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 86740
 */
@Data
@TableName("assets_allot")
public class AssetsAllotEntity {

    @TableId
    private Long id;
    /**
     * 调拨单号
     **/
    private String serialNo;

    /**
     * 调拨原因
     **/
    private String reason;
    private Date createDate;


}
