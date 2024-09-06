package com.zs.assets.allot.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField(exist = false)
    private String assetsName;
}
