package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.PhotoUploadDTO;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.PhotoManagementService;
import com.gzlg.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 图片上传控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/images")
public class ImageUploadController {

    @Autowired
    private PhotoManagementService photoManagementService;

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 上传图片（含元数据，保存到数据库）
     * POST /api/images/upload
     */
    @PostMapping("/upload")
    public Result<PhotoVO> uploadImage(
            @ModelAttribute PhotoUploadDTO uploadDTO,
            @RequestParam("image") MultipartFile imageFile) {
        PhotoVO vo = photoManagementService.uploadImage(uploadDTO, imageFile);
        return Result.success(vo);
    }

    /**
     * 通用文件上传（仅上传到OSS，返回URL，不保存数据库）
     * POST /api/images/upload/file
     */
    @PostMapping("/upload/file")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("文件上传：{}", file.getOriginalFilename());
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败");
        }
    }
}
