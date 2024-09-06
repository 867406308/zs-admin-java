package com.zs.sys.config.service.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.constant.Constants;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.constant.SysConfigConstants;
import com.zs.common.redis.config.RedisUtil;
import com.zs.sys.config.domain.entity.SysConfigEntity;
import com.zs.sys.config.domain.params.SysConfigParams;
import com.zs.sys.config.domain.vo.*;
import com.zs.sys.config.mapper.SysConfigMapper;
import com.zs.sys.config.service.ISysConfigService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigEntity> implements ISysConfigService {

    @Resource
    private RedisUtil redisUtil;

    private SysConfigEntity fetchConfigEntity(String configKey) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<SysConfigEntity>()
                .eq(SysConfigEntity::getConfigKey, configKey));
    }

    private <T> T convertToVo(String configKey, Class<T> voClass) {
        SysConfigEntity sysConfigEntity = fetchConfigEntity(configKey);
        if (sysConfigEntity == null || StringUtils.isEmpty(sysConfigEntity.getConfigValue())) {
            return null;
        }
        return JSONUtil.toBean(sysConfigEntity.getConfigValue(), voClass);
    }

    @Override
    public void update(SysConfigParams sysConfigParams) {
        this.baseMapper.update(
                new LambdaUpdateWrapper<SysConfigEntity>()
                        .set(SysConfigEntity::getConfigValue, sysConfigParams.getConfigValue())
                        .eq(SysConfigEntity::getConfigKey, sysConfigParams.getConfigKey()));

        SysConfigFileVo sysConfigFileVo = convertToVo(SysConfigConstants.SYS_CONFIG_FILE, SysConfigFileVo.class);
        redisUtil.setObject(RedisConstants.SYS_DICT_CONFIG_KEY + Constants.FILE_UPLOAD, sysConfigFileVo);
    }

    @Override
    public SysConfigWebsiteVo websiteInfo() {
        return convertToVo(SysConfigConstants.SYS_CONFIG_WEBSITE, SysConfigWebsiteVo.class);
    }

    @Override
    public SysConfigFileVo fileUploadInfo() {
        return convertToVo(SysConfigConstants.SYS_CONFIG_FILE, SysConfigFileVo.class);
    }

    @Override
    public SysConfigSmsVo smsInfo() {
        return convertToVo(SysConfigConstants.SYS_CONFIG_SMS, SysConfigSmsVo.class);
    }

    @Override
    public SysConfigEmailVo emailInfo() {
        return convertToVo(SysConfigConstants.SYS_CONFIG_EMAIL, SysConfigEmailVo.class);
    }

    @Override
    public SysConfigPayVo payInfo() {
        return convertToVo(SysConfigConstants.SYS_CONFIG_PAY, SysConfigPayVo.class);
    }

    @Override
    public SysConfigOtherVo otherInfo() {
        return convertToVo(SysConfigConstants.SYS_CONFIG_OTHER, SysConfigOtherVo.class);
    }
}
