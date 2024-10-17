package com.zs.modules.assets.inventory.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 86740
 */
@Data
public class AssetsInventoryVo implements Serializable {

    private Long id;
    /**
     * 盘点任务名称
     **/
    private String name;

    /**
     * 盘点部门ID
     **/
    private Long orgId;

    /**
     * 盘点部门名称
     **/
    private String orgName;

    /**
     * 资产级别
     **/
    private Long levelId;
    private String levelName;
    /**
     * 盘点人ID
     **/
    private Long userId;
    /**
     * 盘点人名称
     **/
    private String userName;

    /**
     * 盘点状态  1-未开始 2-进行中 3-已完成
     **/
    private Integer status;

    /**
     * 盘点任务创建时间
     **/
    private Date createDate;
    /**
     * 盘点任务开始时间
     **/
    private Date startDate;
    /**
     * 盘点任务结束时间
     **/
    private Date finishDate;

    /**
     * 盘点任务描述
     **/
    private String description;
}
