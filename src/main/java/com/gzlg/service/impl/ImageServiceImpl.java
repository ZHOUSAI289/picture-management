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

    @Override
    public List<PhotoVO> getImageList(PhotoQueryDTO queryDTO) {
        return photoManagementMapper.findByCondition(queryDTO);
    }

    /**
     * 更新图片
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
        if (dto.getTitle() != null) image.setTitle(dto.getTitle());
        if (dto.getCategory() != null) image.setCategory(dto.getCategory());
        if (dto.getPhotographer() != null) image.setPhotographer(dto.getPhotographer());
        if (dto.getDate() != null) image.setDate(LocalDate.parse(dto.getDate()));
        if (dto.getDescription() != null) image.setDescription(dto.getDescription());
        if (dto.getStatus() != null) image.setStatus(dto.getStatus());
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
