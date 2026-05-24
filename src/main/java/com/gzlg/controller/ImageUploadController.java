package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 图片上传功能
 */
@RestController
@Slf4j
@RequestMapping("/image")
public class ImageUploadController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload (MultipartFile file){
        log.info("文件上传：{}",file);

        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            //文件请求路径
            String filePath = aliOssUtil.upload(file.getBytes(),objectName);
            return Result.success(filePath);
        }catch (Exception e){
            log.error("文件上传失败",e);
        }
        return Result.error("文件上传失败");
    }
}