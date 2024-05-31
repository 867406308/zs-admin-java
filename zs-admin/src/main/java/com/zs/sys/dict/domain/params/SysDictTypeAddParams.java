package com.zs.sys.dict.domain.params;

import lombok.Data;


/**
 * @author 86740
 */
@Data
public class SysDictTypeAddParams {

    private Long sysDictTypeId;
    private String dictType;
    private String dictName;
    private Integer sort;
    private Integer status;


}