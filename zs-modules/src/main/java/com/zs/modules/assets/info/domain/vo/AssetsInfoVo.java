package com.zs.modules.assets.info.domain.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 86740
 */
@Data
public class AssetsInfoVo implements Serializable {


    private Long id;

    /**
     * 编号
     */
    private String serialNo;

    /**
     * 入库单号
     */
    private String inNo;

    /**
     * RFID编码
     */
    private String rfid;

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
     * 名称
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
     * 采购组织形式字典标签
     */
    private String formOfProcurementCodeDictLabel;

    /**
     * 采购部门编码
     */
    private Long buyOrgId;
    /**
     * 采购部门名称
     */
    private String buyOrgName;

    /**
     * 采购人
     */
    private Long buyUserId;
    /**
     * 采购人名称
     */
    private String buyUserName;

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
     * 资金来源
     */
    private String fundsId;

    /**
     * 验收部门
     */
    private Long acceptOrgId;

    /**
     * 验收部门名称
     */
    private String acceptOrgName;

    /**
     * 验收人
     */
    private Long acceptUserId;
    /**
     * 验收人名称
     */
    private String acceptUserName;

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
     * 资产使用方向
     */
    private String usefulId;

    /**
     * 资产状态
     */
    private String assetsStatusCode;

    /**
     * 使用状态
     */
    private String useStatusCode;

    /**
     * 借用部门
     */
    private String borrowDept;

    /**
     * 借用人
     */
    private String borrowUser;

    /**
     * 借用开始时间
     */
    private Date borrowStart;

    /**
     * 借用到期时间
     */
    private Date borrowEnd;

    /**
     * 借用原因
     */
    private String borrowReason;

    /**
     * 借用是否到期
     */
    private Integer borrowExpire;

    /**
     * 调出单位
     */
    private String recallCompany;

    /**
     * 调出原因
     */
    private String recallReason;

    /**
     * 调出类型
     */
    private String recallType;

    /**
     * 调出价格
     */
    private Double recallPrice;

    /**
     * 附件
     */
    private String attachedFile;


    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新时间
     */
    private Date updateDatetime;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 保留字段
     */
    private String remark;

    /**
     * 存放位置描述
     */
    private String storageLocationDescription;

    /**
     * 资产分级
     */
    private Long levelId;


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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 项目代码
     */
    private String projectCode;
    /**
     * 发票号
     */
    private String invoiceNumber;


    /**
     * 资产状态字典标签
     */
    private String assetsStatusDictLabel;
    /**
     * 使用状态字典标签
     */
    private String useStatusDictLabel;
    private String depreciationCodeDictLabel;
    /**
     * 资产分级名称
     */
    private String levelName;
    /**
     * 资产大类
     */
    private String topClassifyName;
    /**
     * 资产分类
     */
    private String classifyName;
    /**
     * 管理部门
     */
    private String manageOrgName;
    /**
     * 管理人员
     */
    private String manageUserName;
    /**
     * 使用部门
     */
    private String useOrgName;
    /**
     * 使用人员
     */
    private String useUserName;

}
