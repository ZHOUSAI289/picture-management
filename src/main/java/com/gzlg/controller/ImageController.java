package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.dto.PhotoUpdateDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 图片管理控制器（首页列表、详情、更新、删除）
 */
@RestController
@Slf4j
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 获取图片列表（分页、搜索、筛选）
     * GET /api/images
     */
    @GetMapping
    public Result<PageResult<PhotoVO>> getImageList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String statusStr,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder) {

        PhotoQueryDTO queryDTO = new PhotoQueryDTO();
        queryDTO.setPageNum(page);
        queryDTO.setPageSize(pageSize);
        queryDTO.setTitle(title);
        queryDTO.setCategory(category);
        queryDTO.setStartDate(startDate);
        queryDTO.setEndDate(endDate);
        queryDTO.setStatus(status);
        queryDTO.setStatusStr(statusStr);
        queryDTO.setSortField(sortField);
        queryDTO.setSortOrder(sortOrder);

        return Result.success(imageService.getImageList(queryDTO));
    }

    /**
     * 获取图片详情
     * GET /api/images/{id}
     */
    @GetMapping("/{id:\\d+}")
    public Result<PhotoVO> getImageById(@PathVariable Integer id) {
        PhotoVO vo = imageService.getImageById(id);
        if (vo == null) {
            return Result.error("图片不存在");
        }
        return Result.success(vo);
    }

    /**
     * 更新图片信息
     * PUT /api/images/{id}
     */
    @PutMapping("/{id:\\d+}")
    public Result<PhotoVO> updateImage(@PathVariable Integer id, @RequestBody PhotoUpdateDTO dto) {
        return Result.success(imageService.updateImage(id, dto));
    }

    /**
     * 删除图片
     * DELETE /api/images/{id}
     */
    @DeleteMapping("/{id:\\d+}")
    public Result<Void> deleteImage(@PathVariable Integer id) {
        imageService.deleteImage(id);
        return Result.success(null);
    }
}
