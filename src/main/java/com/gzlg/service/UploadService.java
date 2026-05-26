package com.gzlg.service;

import com.gzlg.pojo.dto.PhotoUploadDTO;
import com.gzlg.pojo.vo.PhotoVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传服务接口
 */
public interface UploadService {

    /**
     * 上传图片（OSS上传 + 数据库保存）
     */
    PhotoVO uploadImage(PhotoUploadDTO uploadDTO, MultipartFile imageFile);

    /**
     * 通用文件上传（仅上传OSS，返回URL）
     */
    String uploadFile(MultipartFile file);

    /**
     * 保存草稿（status='draft'）
     */
    PhotoVO saveDraft(PhotoUploadDTO dto);

    /**
     * 获取草稿
     */
    PhotoVO getDraft();

    /**
     * 删除草稿
     */
    void deleteDraft();
}
