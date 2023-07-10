package com.zs.common.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.common.constant.Constants;

import java.util.Map;

/**
 * @author 86740
 */
public class PageInfo<T> extends Page<T> {

    public PageInfo(Map<String, Object> params) {
//        String s = params.get("zs").toString();
        long page = Long.parseLong(params.getOrDefault(Constants.PAGE, "0").toString());
        long limit = Long.parseLong(params.getOrDefault(Constants.SIZE, "10").toString());
        String order = params.getOrDefault(Constants.ORDER, "").toString();
        String orderField = params.getOrDefault(Constants.ORDER_FIELD, "").toString();

        this.setCurrent(page);
        this.setSize(limit);

        if (Constants.ASC.equalsIgnoreCase(order)) {
            this.addOrder(OrderItem.asc(orderField));
        } else if (Constants.DESC.equalsIgnoreCase(order)) {
            this.addOrder(OrderItem.desc(orderField));
        }
    }

    public PageInfo(BasePageParams basePageParams) {

        this.setCurrent(basePageParams.getPage());
        this.setSize(basePageParams.getSize());

        if (Constants.ASC.equalsIgnoreCase(basePageParams.getOrder())) {
            this.addOrder(OrderItem.asc(basePageParams.getOrderField()));
        } else if (Constants.DESC.equalsIgnoreCase(basePageParams.getOrder())) {
            this.addOrder(OrderItem.desc(basePageParams.getOrderField()));
        }
    }
}
