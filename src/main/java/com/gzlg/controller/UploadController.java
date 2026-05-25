package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.PhotoUploadDTO;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/images")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片（含元数据，保存数据库，进入待审核）
     * POST /api/images/upload
     */
    @PostMapping("/upload")
    public Result<PhotoVO> uploadImage(
            @ModelAttribute PhotoUploadDTO uploadDTO,
            @RequestParam("image") MultipartFile imageFile) {
        PhotoVO vo = uploadService.uploadImage(uploadDTO, imageFile);
        return Result.success(vo);
    }

    /**
     * 通用文件上传（仅上传OSS，返回URL）
     * POST /api/images/upload/file
     */
    @PostMapping("/upload/file")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String url = uploadService.uploadFile(file);
        return Result.success(url);
    }

    /**
     * 保存草稿
     * POST /api/images/draft
     */
    @PostMapping("/draft")
    public Result<PhotoVO> saveDraft(@RequestBody PhotoUploadDTO dto) {
        return Result.success(uploadService.saveDraft(dto));
    }

    /**
     * 获取草稿
     * GET /api/images/draft
     */
    @GetMapping("/draft")
    public Result<PhotoVO> getDraft() {
        return Result.success(uploadService.getDraft());
    }

    /**
     * 删除草稿
     * DELETE /api/images/draft
     */
    @DeleteMapping("/draft")
    public Result<Void> deleteDraft() {
        uploadService.deleteDraft();
        return Result.success(null);
    }
}
