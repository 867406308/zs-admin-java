package com.zs.assets.allot.domain.params;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class AssetsAllotAddParams {

    /**
     * 调拨单号
     **/
    private String serialNo;
    /**
     * 调拨原因
     **/
    private String reason;
}
