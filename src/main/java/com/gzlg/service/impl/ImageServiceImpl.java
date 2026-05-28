package com.gzlg.service.impl;

import com.gzlg.mapper.PhotoManagementMapper;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.dto.PhotoUpdateDTO;
import com.gzlg.pojo.entity.image;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图片管理服务实现
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private PhotoManagementMapper photoManagementMapper;

    /**
     * 获取图片列表
     * @param queryDTO
     * @return
     */
    public List<PhotoVO> getImageList(PhotoQueryDTO queryDTO) {
        return photoManagementMapper.findByCondition(queryDTO);
    }

    /**
     * 获取图片详情
     * @param id
     * @return
     */
    public PhotoVO getImageById(Integer id) {
        return photoManagementMapper.findById(id);
    }

    /**
     * 更新图片
     * @param id
     * @param dto
     * @return
     */
    public PhotoVO updateImage(Integer id, PhotoUpdateDTO dto) {
        PhotoVO existing = photoManagementMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("图片不存在");
        }

        image image = new image();
        image.setId(id);
        image.setTitle(dto.getTitle());
        image.setCategory(dto.getCategory());
        image.setPhotographer(dto.getPhotographer());
        image.setDate(LocalDate.parse(dto.getDate()));
        image.setDescription(dto.getDescription());
        image.setStatus(dto.getStatus());
        image.setUpdatedAt(LocalDateTime.now());

        photoManagementMapper.update(image);
        return photoManagementMapper.findById(id);
    }

    /**
     * 删除图片
     * @param id
     */
    public void deleteImage(Integer id) {
        PhotoVO existing = photoManagementMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("图片不存在");
        }
        photoManagementMapper.deleteById(id);
    }

    /**
     * 按分类获取图片
     * @param categoryName
     * @return
     */
    public List<PhotoVO> getImagesByCategory(String categoryName) {
        return photoManagementMapper.findByCategory(categoryName);
    }
}
