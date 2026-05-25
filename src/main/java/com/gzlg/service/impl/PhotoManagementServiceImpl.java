package com.gzlg.service.impl;

import com.gzlg.mapper.CategoryMapper;
import com.gzlg.mapper.PhotoManagementMapper;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.dto.PhotoUpdateDTO;
import com.gzlg.pojo.dto.PhotoUploadDTO;
import com.gzlg.pojo.entity.Category;
import com.gzlg.pojo.entity.image;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.PhotoManagementService;
import com.gzlg.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

/**
 * 图片管理服务实现类
 */
@Service
@Slf4j
public class PhotoManagementServiceImpl implements PhotoManagementService {

    @Autowired
    private PhotoManagementMapper photoManagementMapper;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult<PhotoVO> getImageList(PhotoQueryDTO photoQueryDTO) {
        log.info("获取图片列表, 参数: {}", photoQueryDTO);
        photoQueryDTO.calculateOffset();
        Long total = (long) photoManagementMapper.countByCondition(photoQueryDTO);
        List<PhotoVO> list = photoManagementMapper.findByCondition(photoQueryDTO);
        return new PageResult<>(list, total, photoQueryDTO.getPageNum(), photoQueryDTO.getPageSize());
    }

    @Override
    public PhotoVO getImageById(Integer id) {
        log.info("获取图片详情, ID: {}", id);
        return photoManagementMapper.findById(id);
    }

    @Override
    public PhotoVO updateImage(Integer id, PhotoUpdateDTO photoUpdateDTO) {
        log.info("更新图片信息, ID: {}, 参数: {}", id, photoUpdateDTO);

        PhotoVO existing = photoManagementMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("图片不存在");
        }

        image image = new image();
        image.setId(id);
        if (photoUpdateDTO.getTitle() != null) {
            image.setTitle(photoUpdateDTO.getTitle());
        }
        if (photoUpdateDTO.getCategory() != null) {
            image.setCategory(photoUpdateDTO.getCategory());
        }
        if (photoUpdateDTO.getPhotographer() != null) {
            image.setPhotographer(photoUpdateDTO.getPhotographer());
        }
        if (photoUpdateDTO.getDate() != null) {
            image.setDate(LocalDate.parse(photoUpdateDTO.getDate()));
        }
        if (photoUpdateDTO.getDescription() != null) {
            image.setDescription(photoUpdateDTO.getDescription());
        }
        if (photoUpdateDTO.getStatus() != null) {
            image.setStatus(photoUpdateDTO.getStatus());
        }
        image.setUpdatedAt(LocalDateTime.now());

        photoManagementMapper.update(image);
        return photoManagementMapper.findById(id);
    }

    @Override
    public void deleteImage(Integer id) {
        log.info("删除图片, ID: {}", id);
        PhotoVO existing = photoManagementMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("图片不存在");
        }
        photoManagementMapper.deleteById(id);
    }

    @Override
    public PageResult<PhotoVO> getAuditList(Integer page, Integer pageSize, Integer status) {
        log.info("获取待审核图片列表，page: {}, pageSize: {}, status: {}", page, pageSize, status);
        PhotoQueryDTO queryDTO = new PhotoQueryDTO();
        queryDTO.setPageNum(page != null ? page : 1);
        queryDTO.setPageSize(pageSize != null ? pageSize : 10);
        queryDTO.setStatus(status != null ? status : 0);
        return getImageList(queryDTO);
    }

    @Override
    public PhotoVO auditImage(Integer id, String status) {
        log.info("审核图片，ID: {}, 结果: {}", id, status);
        PhotoVO existing = photoManagementMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("图片不存在");
        }
        Integer statusCode = convertStatusToCode(status);
        photoManagementMapper.updateStatus(id, statusCode);
        return photoManagementMapper.findById(id);
    }

    @Override
    public void batchAuditImages(List<Integer> ids, String status) {
        log.info("批量审核图片，图片数: {}, 结果: {}", ids.size(), status);
        Integer statusCode = convertStatusToCode(status);
        photoManagementMapper.batchUpdateStatus(ids, statusCode);
    }

    @Override
    public List<PhotoVO> getImagesByCategory(String categoryName) {
        log.info("获取分类下的图片，分类: {}", categoryName);
        return photoManagementMapper.findByCategory(categoryName);
    }

    @Override
    public PhotoVO uploadImage(PhotoUploadDTO uploadDTO, MultipartFile imageFile) {
        log.info("上传图片 - title: {}, category: {}, photographer: {}, date: {}",
                uploadDTO.getTitle(), uploadDTO.getCategory(),
                uploadDTO.getPhotographer(), uploadDTO.getDate());

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
        // 如果该分类在 category 表中不存在，自动创建
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
        image.setDate(date);
        image.setUrl(url);
        image.setDescription(uploadDTO.getDescription());
        image.setStatus(0); // 待审核
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());

        photoManagementMapper.insert(image);
        log.info("图片保存成功，ID: {}", image.getId());

        // 转换为VO返回
        return convertToVO(image);
    }

    /**
     * 将状态字符串转换为整数编码
     */
    private Integer convertStatusToCode(String status) {
        switch (status.toLowerCase()) {
            case "approved": return 1;
            case "rejected": return 2;
            default: throw new RuntimeException("无效的状态值: " + status);
        }
    }

    /**
     * 解析日期字符串，支持多种格式
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
     * 将image实体转换为PhotoVO
     */
    private PhotoVO convertToVO(image image) {
        PhotoVO vo = new PhotoVO();
        vo.setId(image.getId());
        vo.setTitle(image.getTitle());
        vo.setCategory(image.getCategory());
        vo.setPhotographer(image.getPhotographer());
        vo.setDate(image.getDate() != null ? image.getDate().toString() : null);
        vo.setUrl(image.getUrl());
        vo.setDescription(image.getDescription());
        vo.setStatus(convertStatusToString(image.getStatus()));
        vo.setCreatedAt(image.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        vo.setUpdatedAt(image.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return vo;
    }

    /**
     * 将状态整数转换为字符串
     */
    private String convertStatusToString(Integer status) {
        if (status == null) return "pending";
        switch (status) {
            case 0: return "pending";
            case 1: return "approved";
            case 2: return "rejected";
            case 3: return "published";
            default: return "pending";
        }
    }
}
