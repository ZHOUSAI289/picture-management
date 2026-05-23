package com.gzlg.service.impl;

import com.gzlg.mapper.PhotoMapper;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.entity.image;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoMapper photoMapper;

    /**
     * 获取图片列表
     * @param photoQueryDTO 图片查询参数
     * @return 图片VO列表
     */
    @Override
    public List<PhotoVO> getImageList(PhotoQueryDTO photoQueryDTO) {
        log.info("获取图片列表, 参数: {}", photoQueryDTO);

        // 计算分页偏移量
        photoQueryDTO.calculateOffset();

        // 调用Mapper查询数据库
        List<image> imageList = photoMapper.findByCondition(photoQueryDTO);

        // 转换为VO列表
        List<PhotoVO> voList = new ArrayList<>();
        for (image img : imageList) {
            voList.add(convertToVO(img));
        }

        return voList;
    }

    /**
     * Entity转VO
     */
    private PhotoVO convertToVO(image img) {
        PhotoVO vo = new PhotoVO();
        vo.setId(img.getId());
        vo.setTitle(img.getTitle());
        vo.setCategory(img.getCategory());
        vo.setPhotographer(img.getPhotographer());
        vo.setDate(img.getDate() != null ? img.getDate().toString() : null);
        vo.setUrl(img.getUrl() != null ? img.getUrl() : "");
        vo.setDescription(img.getDescription());
        vo.setStatus(img.getStatus());
        vo.setCreatedAt(img.getCreatedAt() != null ? img.getCreatedAt().toString() : null);
        vo.setUpdatedAt(img.getUpdatedAt() != null ? img.getUpdatedAt().toString() : null);
        return vo;
    }
}
