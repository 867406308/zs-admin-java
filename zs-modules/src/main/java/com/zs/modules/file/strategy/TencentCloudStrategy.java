package com.zs.modules.file.strategy;

import com.zs.modules.file.domain.entity.SysFileEntity;
import com.zs.modules.sys.config.domain.vo.SysConfigFileVo;
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
