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
     * 原使用部门ID
     **/
    private Long originalUseOrgId;
    /**
     * 原使用人ID
     **/
    private Long originalUseUserId;
    /**
     * 原管理部门ID
     **/
    private Long originalManageOrgId;
    /**
     * 原管理人ID
     **/
    private Long originalManageUserId;
    /**
     * 原存放位置
     **/
    private String originalStorageLocation;
    /**
     * 新使用部门ID
     **/
    private Long currentUseOrgId;
    /**
     * 新使用人ID
     **/
    private Long currentUseUserId;
    /**
     * 新管理部门ID
     **/
    private Long currentManageOrgId;
    /**
     * 新管理人ID
     **/
    private Long currentManageUserId;
    /**
     * 新存放位置ID
     **/
    private String currentStorageLocation;
    /**
     * 调拨原因
     **/
    private String reason;
}
