package com.zs.modules.sys.config.domain.vo;

import lombok.Data;

/**
 * 网站配置VO
 */
@Data
public class SysConfigWebsiteVo {

    /** 网站名称 */
    private String websiteName;
    /** 网站描述 */
    private String description;
    /** 网站logo */
    private String logo;
    /** 版本 */
    private String version;
    /** 版权 */
    private String copyright;
    /** 备案号 */
    private String icp;
    /** 备案号链接 */
    private String icpLink;
    /** 隐私政策 */
    private String privacyPolicy;
    /** 服务条款 */
    private String termsOfService;
}
