package com.zs.file.strategy;

import com.zs.file.domain.entity.SysFileEntity;
import com.zs.sys.config.domain.vo.SysConfigFileVo;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */

public class TencentCloudStrategy implements UploadStrategy{



    public TencentCloudStrategy(SysConfigFileVo sysConfigFileVo) {
    }

    @Override
    public SysFileEntity upload(MultipartFile file) {
        return null;
    }
}
