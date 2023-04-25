package com.zs.common.page;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {


    private long total;

    private List<T> list;

    public  PageResult(IPage<T> page){
        this.total = page.getTotal();
        this.list = page.getRecords();
    }

    public PageResult(List<T> list, long total){
        this.list = list;
        this.total = total;
    }

    public  PageResult(IPage<T> page, Class<T> target){
        this.total = page.getTotal();
        this.list = Convert.toList(target, page.getRecords());
    }

    public PageResult(List<T> list, long total, Class<T> target){
        this.list = Convert.toList(target, list);
        this.total = total;
    }
}
