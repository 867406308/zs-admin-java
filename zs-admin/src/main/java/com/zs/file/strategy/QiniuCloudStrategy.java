package com.zs.file.strategy;

import com.zs.file.domain.entity.SysFileEntity;
import com.zs.sys.config.domain.vo.SysConfigFileVo;
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
