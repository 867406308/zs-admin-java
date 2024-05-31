package com.zs.sys.dict.domain.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author 86740
 */
@Data
public class SysDictDataVo implements Serializable {


    private Long sysDictDataId;
    private String dictType;
    private Long sysDictTypeId;
    private String dictLabel;
    private String dictValue;
    private Integer sort;
    private Integer status;

}
