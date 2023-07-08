package com.zs.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class IpUtils {


    private static Searcher searcher;
    private static final String DB_FILE_PATH = "/city/ip2region.xdb";

    @PostConstruct
    private static void initIp2region() {
        try {
            InputStream inputStream = new ClassPathResource(DB_FILE_PATH).getInputStream();
            byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
            // 创建一个完全基于内存的查询对象
            searcher = Searcher.newWithBuffer(dbBinStr);
        } catch (Exception e) {
            log.info("初始化失败", e);
        }
    }

    /**
     * 获取客户端IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String[] headers = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return getFirstValidIpAddress(ip);
            }
        }
        String remoteAddr = request.getRemoteAddr();
        if ("127.0.0.1".equals(remoteAddr) || "0:0:0:0:0:0:0:1".equals(remoteAddr)) {
            return getLocalIpAddress();
        }
        return remoteAddr;
    }

    private static String getFirstValidIpAddress(String ipAddress) {
        if (StringUtils.isNotBlank(ipAddress)) {
            String[] ipAddresses = ipAddress.split(",");
            for (String ip : ipAddresses) {
                if (isValidIpAddress(ip)) {
                    return ip.trim();
                }
            }
        }
        return null;
    }

    private static boolean isValidIpAddress(String ipAddress) {
        return StringUtils.isNotBlank(ipAddress) && !"unknown".equalsIgnoreCase(ipAddress);
    }

    private static String getLocalIpAddress() {
        try {
            InetAddress inet = InetAddress.getLocalHost();
            return inet.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Failed to get local IP address: ", e);
        }
        return null;
    }


    /**
     * 根据ip从 ip2region.db 中获取地理位置
     * //数据格式： 国家|区域|省份|城市|ISP
     * //192.168.31.160 0|0|0|内网IP|内网IP
     * //47.52.236.180 中国|0|香港|0|阿里云
     * //220.248.12.158 中国|0|上海|上海市|联通
     * //164.114.53.60 美国|0|华盛顿|0|0
     *
     * @return map
     */
    public static Map<String, Object> getCityInfo(String ip) {
        Map<String, Object> cityInfo = new HashMap<>();
        try {
            String searchIpInfo = searcher.search(ip);
            String[] splitIpInfo = searchIpInfo.split("\\|");

            cityInfo.put("ip", ip);
            cityInfo.put("searchInfo", searchIpInfo);
            cityInfo.put("country", splitIpInfo[0]);
            cityInfo.put("region", splitIpInfo[1]);
            cityInfo.put("province", splitIpInfo[2]);
            cityInfo.put("city", splitIpInfo[3]);
            cityInfo.put("ISP", splitIpInfo[4]);

            return cityInfo;
        } catch (Exception e) {
            log.info("Failed to search({}): {}", ip, e.getMessage());
        }
        return null;
    }


}