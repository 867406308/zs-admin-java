package com.zs.sys.dict.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author 86740
 */
@Data
public class SysDictTypeVo implements Serializable {


    @TableId
    private Long sysDictTypeId;
    private String dictType;
    private String dictName;
    private Integer sort;
    private Integer status;
    private Long creator;
    private Date createTime;
    private Long updater;
    private Date updateTime;


}