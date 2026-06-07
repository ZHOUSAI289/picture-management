package com.gzlg.service.impl;

import com.gzlg.mapper.CategoryMapper;
import com.gzlg.mapper.PhotoManagementMapper;
import com.gzlg.pojo.dto.PhotoUploadDTO;
import com.gzlg.pojo.entity.Category;
import com.gzlg.pojo.entity.image;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.UploadService;
import com.gzlg.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

/**
 * 图片上传服务实现
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private PhotoManagementMapper photoManagementMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 上传图片
     * @param uploadDTO
     * @param imageFile
     * @return
     */
    public PhotoVO uploadImage(PhotoUploadDTO uploadDTO, MultipartFile imageFile) {
        log.info("上传图片 - title: {}, category: {}, photographer: {}",
                uploadDTO.getTitle(), uploadDTO.getCategory(), uploadDTO.getPhotographer());

        if (imageFile.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        // 校验文件格式
        String originalFilename = imageFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!".jpg".equalsIgnoreCase(suffix) && !".png".equalsIgnoreCase(suffix)
                && !".jpeg".equalsIgnoreCase(suffix) && !".gif".equalsIgnoreCase(suffix)) {
            throw new RuntimeException("仅支持 jpg、jpeg、png、gif 格式");
        }

        // 上传到OSS
        String newFilename = UUID.randomUUID() + suffix;
        String url;
        try {
            url = aliOssUtil.upload(imageFile.getBytes(), "photos/" + newFilename);
            log.info("OSS上传成功，URL: {}", url);
        } catch (Exception e) {
            log.error("OSS上传失败", e);
            throw new RuntimeException("图片上传失败: " + e.getMessage());
        }

        // 处理自定义分类：使用自定义分类名称，不存在则自动创建
        String category = uploadDTO.getCategory();
        if (Boolean.TRUE.equals(uploadDTO.getEnableCustomCategory())
                && uploadDTO.getCustomCategory() != null
                && !uploadDTO.getCustomCategory().isEmpty()) {
            category = uploadDTO.getCustomCategory();
        }
        if (category != null && !category.isEmpty()) {
            Category existing = categoryMapper.findByName(category);
            if (existing == null) {
                Category newCategory = new Category();
                newCategory.setName(category);
                newCategory.setCreatedAt(LocalDateTime.now());
                newCategory.setCount(0);
                categoryMapper.insert(newCategory);
                log.info("自动创建分类: {}", category);
            }
        }

        // 解析日期
        LocalDate date = parseDate(uploadDTO.getDate());

        // 保存到数据库
        image image = new image();
        image.setTitle(uploadDTO.getTitle());
        image.setCategory(category);
        image.setPhotographer(uploadDTO.getPhotographer());
        image.setPersonName(uploadDTO.getPersonName());
        image.setDate(date);
        image.setUrl(url);
        image.setDescription(uploadDTO.getDescription());
        image.setStatus("pending");
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());

        photoManagementMapper.insert(image);
        log.info("图片保存成功，ID: {}", image.getId());

        return convertToVO(image);
    }

    /**
     * 保存草稿
     * @param dto
     * @return
     */
    public PhotoVO saveDraft(PhotoUploadDTO dto) {
        log.info("保存草稿 - title: {}", dto.getTitle());
        // 先删除旧草稿
        photoManagementMapper.deleteDrafts();

        LocalDate date = null;
        if (dto.getDate() != null && !dto.getDate().isEmpty()) {
            date = parseDate(dto.getDate());
        }

        image image = new image();
        image.setTitle(dto.getTitle() != null ? dto.getTitle() : "");
        image.setCategory(dto.getCategory() != null ? dto.getCategory() : "");
        image.setPhotographer(dto.getPhotographer() != null ? dto.getPhotographer() : "");
        image.setDate(date);
        image.setUrl("");
        image.setDescription(dto.getDescription() != null ? dto.getDescription() : "");
        image.setStatus("draft");
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());

        photoManagementMapper.insert(image);
        log.info("草稿保存成功，ID: {}", image.getId());
        return convertToVO(image);
    }

    /**
     * 获取草稿
     * @return
     */
    public PhotoVO getDraft() {
        return photoManagementMapper.findDraft();
    }

    /**
     * 删除草稿
     */
    public void deleteDraft() {
        photoManagementMapper.deleteDrafts();
    }

    /**
     * 解析日期
     * @param dateStr
     * @return
     */
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                if (formatter == DateTimeFormatter.ISO_LOCAL_DATE_TIME) {
                    return LocalDateTime.parse(dateStr, formatter).toLocalDate();
                }
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new RuntimeException("日期格式不正确，请使用 yyyy-MM-dd 或 yyyy/MM/dd 格式");
    }

    /**
     * 转换为VO
     * @param image
     * @return
     */
    private PhotoVO convertToVO(image image) {
        PhotoVO vo = new PhotoVO();
        vo.setId(image.getId());
        vo.setTitle(image.getTitle());
        vo.setCategory(image.getCategory());
        vo.setPhotographer(image.getPhotographer());
        vo.setPersonName(image.getPersonName());
        vo.setDate(image.getDate() != null ? image.getDate().toString() : null);
        vo.setUrl(image.getUrl());
        vo.setDescription(image.getDescription());
        vo.setStatus(image.getStatus());
        vo.setCreatedAt(image.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        vo.setUpdatedAt(image.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return vo;
    }

}
