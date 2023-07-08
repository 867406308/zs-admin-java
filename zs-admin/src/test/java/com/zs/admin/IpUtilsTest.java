package com.zs.admin;

import com.zs.common.utils.IpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
public class IpUtilsTest {

    @Test
    public void test(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddr = IpUtils.getIpAddr(request);
        System.out.println(ipAddr);
        Map<String, Object> cityInfo = IpUtils.getCityInfo(ipAddr);
        System.out.println(Objects.requireNonNull(cityInfo));
        //数据格式： 国家|区域|省份|城市|ISP
        //192.168.31.160 0|0|0|内网IP|内网IP
        //47.52.236.180 中国|0|香港|0|阿里云
        //220.248.12.158 中国|0|上海|上海市|联通
        //164.114.53.60 美国|0|华盛顿|0|0
        String ip = "183.213.81.138";
        Map<String, Object> result = IpUtils.getCityInfo(ip);
        System.out.println(Objects.requireNonNull(result));
        System.out.println(Objects.requireNonNull(result).get("province"));
    }
}
