package com.zs.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BaseVo {
    private Long creator;
    private String createTime;
    private Long updater;
    private String updateTime;
}
