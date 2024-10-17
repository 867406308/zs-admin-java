package com.zs.modules.file.factory;

import cn.hutool.json.JSONUtil;
import com.zs.common.core.constant.Constants;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.enums.UploadTypeEnum;
import com.zs.common.redis.config.RedisUtil;
import com.zs.modules.file.strategy.*;
import com.zs.modules.sys.config.domain.vo.SysConfigFileVo;
import com.zs.modules.sys.config.service.ISysConfigService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 文件上传工厂
 */
@Component
public class FileFactory {

    private static final Logger logger = LoggerFactory.getLogger(FileFactory.class);

    private static RedisUtil redisUtil;
    private static ISysConfigService iSysConfigService;

    public FileFactory(ISysConfigService iSysConfigService, RedisUtil redisUtil) {
        FileFactory.iSysConfigService = iSysConfigService;
        FileFactory.redisUtil = redisUtil;
        initialize();
    }



    private void initialize() {
        try {
            SysConfigFileVo sysConfigFileVo = iSysConfigService.fileUploadInfo();
            if (sysConfigFileVo != null) {
                redisUtil.setObject(RedisConstants.SYS_DICT_CONFIG_KEY + Constants.FILE_UPLOAD, sysConfigFileVo);
            } else {
                logger.warn("文件上传配置为空");
            }
        } catch (Exception e) {
            logger.error("获取文件上传配置异常：", e);
        }
    }


    @NotNull
    public static UploadStrategy build() {
        // 获取当前配置文件上传的类型
        Object object = redisUtil.get(RedisConstants.SYS_DICT_CONFIG_KEY + Constants.FILE_UPLOAD);
        SysConfigFileVo sysConfigFileVo = JSONUtil.toBean(JSONUtil.toJsonStr(object), SysConfigFileVo.class);
        UploadTypeEnum uploadTypeEnum = UploadTypeEnum.getEnum(sysConfigFileVo.getType());
        if (uploadTypeEnum == null) {
            return new LocalFileStrategy(sysConfigFileVo);
        }

        return switch (uploadTypeEnum) {
            case LOCAL -> new LocalFileStrategy(sysConfigFileVo);
            case TENCENT -> new TencentCloudStrategy(sysConfigFileVo);
            case ALIYUN -> new AliyunCloudStrategy(sysConfigFileVo);
            case QINIU -> new QiniuCloudStrategy(sysConfigFileVo);
        };
    }


}
