package com.zs.file.strategy;


import com.zs.common.core.exception.ZsException;
import com.zs.file.domain.entity.SysFileEntity;
import com.zs.file.service.ISysFileService;
import com.zs.sys.config.domain.vo.Local;
import com.zs.sys.config.domain.vo.SysConfigFileVo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 *
 */

public class LocalFileStrategy implements UploadStrategy {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileStrategy.class);


    @Resource
    private ISysFileService iSysFileService;

    // 本地保存路径
    private final String FILE_PATH;
    // 访问地址
    private final String ACCESS_URL;

    // 访问前缀
    private final String PREFIX;



    public LocalFileStrategy(SysConfigFileVo sysConfigFileVo) {
        Local local = sysConfigFileVo.getLocal();
        this.FILE_PATH = local.getPath();
        this.PREFIX = local.getPrefix();
        this.ACCESS_URL = local.getDomain();
    }

    /**
     * 上传文件到指定目录，并返回新文件名。
     *
     * @param file 要上传的文件
     * @return 新文件名
     */
    @NotNull
    public SysFileEntity upload(@NotNull MultipartFile file) {
        SysFileEntity result;
        String newFileName;
        try {
            if (file.isEmpty()) {
                throw new ZsException("文件对象为空");
            }
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                throw new ZsException("文件名为空");
            }

            String extension = "";
            int dotIndex = originalFilename.lastIndexOf(".");
            // 避免空字符串或纯扩展名的情况
            if (dotIndex > 0 && dotIndex < originalFilename.length() - 1) {
                extension = originalFilename.substring(dotIndex);
            }

            // 生成新文件名
            newFileName = UUID.randomUUID().toString().replace("-", "") + extension;

            // 构建文件路径
            Path filePath = Paths.get(FILE_PATH, newFileName);

            // 创建文件夹
            createDirectoryIfNotExists(filePath.getParent());

            // 将文件写入本地
            file.transferTo(filePath.toFile());

            // 数据库保存文件信息
            result = buildSysFileEntity(file, newFileName, filePath);

        } catch (Exception e) {
            logger.error("文件上传失败:", e);
            throw new RuntimeException("文件上传失败");
        }

        return result;
    }

    private void createDirectoryIfNotExists(@NotNull Path directoryPath) {
        try {
            Files.createDirectories(directoryPath);
        } catch (IOException e) {
            logger.error("创建目录失败:{}", directoryPath, e);
            throw new RuntimeException("创建目录失败: " + directoryPath, e);
        }
    }


    @NotNull
    private SysFileEntity buildSysFileEntity(@NotNull MultipartFile file, String newFileName, @NotNull Path filePath) {
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setFileName(newFileName);
        sysFileEntity.setFileOriginalName(file.getOriginalFilename());
        sysFileEntity.setFileType(file.getContentType());
        sysFileEntity.setFileSize(file.getSize());

        // 使用 StringBuilder 来构建 URL 更安全可靠
        String fileUrlBuilder = (ACCESS_URL.endsWith("/") ? ACCESS_URL : ACCESS_URL + "/") +
                (PREFIX.endsWith("/") ? PREFIX : PREFIX + "/") +
                newFileName;

        sysFileEntity.setFileUrl(fileUrlBuilder);

        sysFileEntity.setFilePath(filePath.toFile().getPath());

        return sysFileEntity;
    }

}
