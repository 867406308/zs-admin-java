package com.zs.modules.assets.classify.domain.query;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class AssetsClassifySchoolAddParams {

    private Long id;
    private String name;
    private Integer depreciationYears;
    private Long pid;
    private Long gbcId;
    private String remark;
}
