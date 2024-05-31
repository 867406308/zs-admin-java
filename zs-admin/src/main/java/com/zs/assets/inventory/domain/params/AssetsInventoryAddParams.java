package com.zs.assets.inventory.domain.params;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 86740
 */
@Data
public class AssetsInventoryAddParams implements Serializable {

    /**
     * 盘点任务名称
     **/
    private String name;

    /**
     * 盘点部门ID
     **/
    private Long orgId;

    /**
     * 资产级别
     **/
    private Long levelId;

    /**
     * 盘点人ID
     **/
    private Long userId;

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
