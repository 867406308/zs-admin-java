package com.zs.common.core.page;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author 86740
 */
@Data
public class PageResult<T> {


    private Integer total;

    private List<T> list;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object  data;


    public PageResult(@NotNull IPage<T> page) {
        this.total = (int) page.getTotal();
        this.list = page.getRecords();
    }

    public PageResult(List<T> list, long total) {
        this.list = list;
        this.total = (int) total;
    }

    public PageResult(@NotNull IPage<T> page, Class<T> target) {
        this.total = (int) page.getTotal();
        this.list = Convert.toList(target, page.getRecords());
    }

    public PageResult(List<T> list, long total, Class<T> target) {
        this.list = Convert.toList(target, list);
        this.total = (int) total;
    }

    public PageResult(List<T> list, long total,Object data, Class<T> target) {
        this.list = Convert.toList(target, list);
        this.total = (int) total;
        this.data = data;
    }

}
