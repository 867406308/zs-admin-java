package com.zs.assets.allot.domain.vo;

import lombok.Data;

@Data
public class AssetsAllotDetailsVo {

    
    private Long allotId;
    private String assetsSerialNo;
    /**
     * 名称
     */
    private String assetsName;
    /**
     * 原使用部门ID
     **/
    private Long originalUseOrgId;
    private String originalUseOrgName;
    /**
     * 原使用人ID
     **/
    private Long originalUseUserId;
    private String originalUseUserName;
    /**
     * 原管理部门ID
     **/
    private Long originalManageOrgId;
    private String originalManageOrgName;
    /**
     * 原管理人ID
     **/
    private Long originalManageUserId;
    private String originalManageUserName;
    /**
     * 原存放位置
     **/
    private String originalStorageLocation;
    /**
     * 新使用部门ID
     **/
    private Long currentUseOrgId;
    private String currentUseOrgName;
    /**
     * 新使用人ID
     **/
    private Long currentUseUserId;
    private String currentUseUserName;
    /**
     * 新管理部门ID
     **/
    private Long currentManageOrgId;
    private String currentManageOrgName;
    /**
     * 新管理人ID
     **/
    private Long currentManageUserId;
    private String currentManageUserName;
    /**
     * 新存放位置ID
     **/
    private String currentStorageLocation;
}
