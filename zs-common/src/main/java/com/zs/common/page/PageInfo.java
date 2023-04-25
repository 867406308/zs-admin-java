package com.zs.common.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.common.constant.Constants;

import java.util.Map;
import java.util.Objects;

public class PageInfo<T> extends Page<T>{

    private Page<T> page;
    public  PageInfo(Map<String, Object> params){
        Long current = params.containsKey(Constants.PAGE) && params.get(Constants.PAGE) != null
                ? Long.parseLong(params.get(Constants.PAGE).toString()) : 0;

        Long size = params.containsKey(Constants.SIZE) && params.get(Constants.SIZE) != null
                ? Long.parseLong(params.get(Constants.SIZE).toString()) : 10;

        this.page = new Page<>(current,size);

    }
}
