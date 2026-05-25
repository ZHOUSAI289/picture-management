package com.gzlg.service;

import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.dto.PhotoUpdateDTO;
import com.gzlg.pojo.dto.PhotoUploadDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 图片管理服务接口
 */
public interface PhotoManagementService {

    /**
     * 获取图片列表（分页）
     */
    PageResult<PhotoVO> getImageList(PhotoQueryDTO photoQueryDTO);

    /**
     * 根据ID获取图片详情
     */
    PhotoVO getImageById(Integer id);

    /**
     * 更新图片信息
     */
    PhotoVO updateImage(Integer id, PhotoUpdateDTO photoUpdateDTO);

    /**
     * 删除图片
     */
    void deleteImage(Integer id);

    /**
     * 获取待审核图片列表
     */
    PageResult<PhotoVO> getAuditList(Integer page, Integer pageSize, Integer status);

    /**
     * 审核单张图片
     */
    PhotoVO auditImage(Integer id, String status);

    /**
     * 批量审核图片
     */
    void batchAuditImages(List<Integer> ids, String status);

    /**
     * 根据分类获取图片列表
     */
    List<PhotoVO> getImagesByCategory(String categoryName);

    /**
     * 上传图片（包含OSS上传和数据库保存）
     */
    PhotoVO uploadImage(PhotoUploadDTO uploadDTO, MultipartFile imageFile);
}
