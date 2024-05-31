package com.zs.sys.dict.domain.params;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysDictDataAddParams {

    private Long sysDictDataId;
    private String dictType;
    private Long sysDictTypeId;
    private String dictLabel;
    private String dictValue;
    private Integer sort;
    private Integer status;
}
