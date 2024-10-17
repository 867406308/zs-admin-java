package com.zs.modules.assets.info.domain.query;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 86740
 */
@Data
public class AssetsInfoAddParams implements Serializable {

    private Long id;
    /**
     * 编号
     */
    private String serialNo;
    /**
     * 资产分级
     */
    private Long levelId;
    /**
     * 顶级国标分类
     */
    private Long topLevelGbClassicId;
    /**
     * 分类
     */
    private Long classicId;
    /**
     * 验收单号
     */
    private String stockCode;
    /**
     * 资产名称
     */
    private String name;
    /**
     * 资产照片
     */
    private String pic;
    /**
     * 描述
     */
    private String description;
    /**
     * 型号
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 品牌商标
     */
    private String brand;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 出厂编号
     */
    private String factoryNo;

    /**
     * 生产日期
     */
    private Date productionDate;

    /**
     * 质保日期
     */
    private Date qualityDate;

    /**
     * 采购组织形式
     */
    private String formOfProcurementCode;
    /**
     * 采购部门编码
     */
    private Long buyOrgId;

    /**
     * 采购人
     */
    private Long buyUserId;

    /**
     * 采购日期
     */
    private Date buyDate;

    /**
     * 采购单价
     */
    private BigDecimal buyPrice;

    /**
     * 应折旧月数
     */
    private Integer depreciationMonths;

    /**
     * 月均折旧额
     */
    private BigDecimal monthlyDepreciationPrice;

    /**
     * 已经折旧的月份
     */
    private Integer depreciatedMonths;

    /**
     * 已经折旧的金额
     */
    private BigDecimal depreciatedPrice;

    /**
     * 当月折旧金额
     */
    private BigDecimal currentPeriodDepreciatedPrice;
    /**
     * 验收部门
     */
    private Long acceptOrgId;

    /**
     * 验收人
     */
    private Long acceptUserId;

    /**
     * 验收日期
     */
    private Date acceptDate;

    /**
     * 保管部门
     */
    private Long manageOrgId;

    /**
     * 保管负责人
     */
    private Long manageUserId;

    /**
     * 使用部门
     */
    private Long useOrgId;

    /**
     * 使用人
     */
    private Long useUserId;

    /**
     * 资产存放位置
     */
    private String storageLocation;
    /**
     * 存放位置描述
     */
    private String storageLocationDescription;
    /**
     * 资产状态
     */
    private String assetsStatusCode;

    /**
     * 使用状态
     */
    private String useStatusCode;
    /**
     * 附件
     */
    private String attachedFile;


    /**
     * 折旧状态
     */
    private String depreciationCode;

    /**
     * 入库状态：0，未入账，1已入账
     */
    private Integer saveState;

    /**
     * 会计凭证号
     */
    private String accountingVoucher;

    /**
     * 入账日期
     */
    private Date entryDate;

    /**
     * 期初折旧金额
     */
    private BigDecimal bopDepreciationPrice;

    /**
     * 期初折旧月数
     */
    private Integer bopDepreciationMonths;

    /**
     * 报废日期
     */
    private Integer scrapD;

    /**
     * 报废日期
     */
    private Date scrapDate;


    /**
     * 数量
     **/
    private Integer num;

}
