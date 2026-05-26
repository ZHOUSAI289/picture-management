package com.gzlg.service;

import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.dto.PhotoUpdateDTO;
import com.gzlg.pojo.vo.PhotoVO;

import java.util.List;

/**
 * 图片管理服务接口
 */
public interface ImageService {

    /**
     * 获取图片列表
     */
    List<PhotoVO> getImageList(PhotoQueryDTO queryDTO);

    /**
     * 根据ID获取图片详情
     */
    PhotoVO getImageById(Integer id);

    /**
     * 更新图片信息
     */
    PhotoVO updateImage(Integer id, PhotoUpdateDTO dto);

    /**
     * 删除图片
     */
    void deleteImage(Integer id);

    /**
     * 根据分类获取图片列表
     */
    List<PhotoVO> getImagesByCategory(String categoryName);
}
