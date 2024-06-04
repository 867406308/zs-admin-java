package com.zs.assets.allot.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author 86740
 */
@Data
public class AssetsAllotVo {

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
