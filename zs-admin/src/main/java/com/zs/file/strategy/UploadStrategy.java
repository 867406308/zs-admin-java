package com.zs.file.strategy;


import com.zs.file.domain.entity.SysFileEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public interface UploadStrategy {


    /**
     * 文件上传
     * @param file 文件
     * @return 文件访问地址
     */
    SysFileEntity upload(MultipartFile file);
}
