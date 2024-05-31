package com.zs.assets.info.domain.query;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsInfoQueryParams extends BasePageParams {

    private String name;
    /**
     * 资产分级
     */
    private Long levelId;

    /**
     * 分类
     */
    private List<Long> classicIdList;

    /**
     * 开始编号
     */
    private String serialNoStart;
    /**
     * 结束编号
     */
    private String serialNoEnd;

    /**
     * 价格开始范围
     */
    private BigDecimal priceStart;
    /**
     * 价格结束范围
     */
    private BigDecimal priceEnd;

    /**
     * 管理部门id
     */
    private Long manageOrgId;

    /**
     * 使用部门id
     */
    private Long useOrgId;

    /**
     * 使用用户id
     */
    private Long useUserId;

    /**
     * 存放位置
     */
    private String storageLocationDescription;

    /**
     * 入库日期范围
     */
    private String entryDateStart;
    private String entryDateEnd;

    /**
     * 供应商
     */
    private String manufacturer;

    private String saveState;

}
