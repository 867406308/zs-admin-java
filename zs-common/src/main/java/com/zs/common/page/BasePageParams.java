package com.zs.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public abstract class BasePageParams implements Serializable {

    private long page;

    private long size;

    /** 排序方法 **/
    private String order;

    /** 排序字段 **/
    private String orderField;

}
