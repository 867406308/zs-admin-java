package com.zs.modules.file.controller;

import com.zs.common.core.core.Result;
import com.zs.modules.file.domain.entity.SysFileEntity;
import com.zs.modules.file.factory.FileFactory;
import com.zs.modules.file.service.ISysFileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 *
 */
@RestController
@RequestMapping("/file")
public class SysFileController {

    @Resource
    private ISysFileService sysFileService;


    @PostMapping("/upload")
    public Result<?> upload( @RequestParam("file") MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            return new Result<>(500, "上传失败，请选择文件");
        }

        try {
            //根据不同的策略选择不同的上传方式
            SysFileEntity sysFileEntity = Objects.requireNonNull(FileFactory.build()).upload(file);
            // 保存到数据库
            sysFileService.save(sysFileEntity);

            String url = sysFileEntity.getFileUrl();

            if (url == null) {
                return new Result<>(500, "上传失败");
            }
            return new Result<>(200, "上传成功", url);
        }catch (Exception e) {
            return new Result<>(500, "上传失败");
        }
    }



}
