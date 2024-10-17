package com.zs.modules.assets.depreciation.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产折旧查询参数
 *
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsDepreciationDetailsQueryParams extends BasePageParams {

    /** 资产折旧ID **/
    private Long depreciationId;
    /** 资产编号 **/
    private String assetsSerialNo;
    /** 资产名称 **/
    private String assetsName;
    /** 管理部门ID **/
    private  Long manageOrgId;
    /** 使用部门ID **/
    private  Long useOrgId;
    /** 国标资产大类ID **/
    private  String  topLevelGbClassicCode;
    /** 学校分类ID **/
    private  Long schoolClassicId;
    /** 折旧状态 **/
    private  String depreciationCode;
    /** 是否当前期折旧的资产 **/
    private  String isCurrentPeriod;

}
