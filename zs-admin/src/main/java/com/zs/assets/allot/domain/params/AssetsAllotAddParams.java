package com.zs.assets.allot.domain.params;

import lombok.Data;

import java.util.List;

/**
 * @author 86740
 */
@Data
public class AssetsAllotAddParams {


    /**
     * 调拨原因
     **/
    private String reason;

    /**
     * 调拨明细
     */
    private List<AssetsAllotDetailsAddParams> assetsAllotDetails;
}
