package com.zs.modules.file.strategy;

import com.zs.modules.file.domain.entity.SysFileEntity;
import com.zs.modules.sys.config.domain.vo.SysConfigFileVo;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */

public class QiniuCloudStrategy implements UploadStrategy {


    public QiniuCloudStrategy(SysConfigFileVo sysConfigFileVo) {
    }

    @Nullable
    @Override
    public SysFileEntity upload(MultipartFile file) {
        return null;
    }
}
