package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.dto.PhotoUpdateDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.PhotoManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 图片管理控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/images")
public class PhotoManagementController {

    @Autowired
    private PhotoManagementService photoManagementService;

    /**
     * 3.1.1 获取图片列表（支持分页、搜索、筛选）
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
            @RequestParam(required = false) String statusStr) {

        log.info("获取图片列表 - page: {}, pageSize: {}, title: {}, category: {}, status: {}, statusStr: {}",
                page, pageSize, title, category, status, statusStr);

        PhotoQueryDTO queryDTO = new PhotoQueryDTO();
        queryDTO.setPageNum(page);
        queryDTO.setPageSize(pageSize);
        queryDTO.setTitle(title);
        queryDTO.setCategory(category);
        queryDTO.setStartDate(startDate);
        queryDTO.setEndDate(endDate);
        queryDTO.setStatus(status);
        queryDTO.setStatusStr(statusStr);

        PageResult<PhotoVO> result = photoManagementService.getImageList(queryDTO);
        return Result.success(result);
    }

    /**
     * 3.1.2 获取单个图片详情
     * GET /api/images/{id}
     */
    @GetMapping("/{id}")
    public Result<PhotoVO> getImageById(@PathVariable Integer id) {
        log.info("获取图片详情 - ID: {}", id);
        PhotoVO photoVO = photoManagementService.getImageById(id);
        if (photoVO == null) {
            return Result.error("图片不存在");
        }
        return Result.success(photoVO);
    }

    /**
     * 3.1.4 更新图片信息
     * PUT /api/images/{id}
     */
    @PutMapping("/{id}")
    public Result<PhotoVO> updateImage(@PathVariable Integer id, @RequestBody PhotoUpdateDTO photoUpdateDTO) {
        log.info("更新图片信息 - ID: {}, 参数: {}", id, photoUpdateDTO);
        PhotoVO photoVO = photoManagementService.updateImage(id, photoUpdateDTO);
        return Result.success(photoVO);
    }

    /**
     * 3.1.5 删除图片
     * DELETE /api/images/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteImage(@PathVariable Integer id) {
        log.info("删除图片 - ID: {}", id);
        photoManagementService.deleteImage(id);
        return Result.success(null);
    }

    /**
     * 3.3.1 获取待审核图片列表
     * GET /api/images/audit
     */
    @GetMapping("/audit")
    public Result<PageResult<PhotoVO>> getAuditList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        log.info("获取待审核图片列表 - page: {}, pageSize: {}, status: {}", page, pageSize, status);
        PageResult<PhotoVO> result = photoManagementService.getAuditList(page, pageSize, status);
        return Result.success(result);
    }

    /**
     * 3.3.2 审核单张图片
     * PUT /api/images/{id}/audit
     * 请求体：{ "status": "approved" } 或 { "status": "rejected" }
     */
    @PutMapping("/{id}/audit")
    public Result<PhotoVO> auditImage(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String status = body.get("status");
        log.info("审核图片 - ID: {}, 结果: {}", id, status);
        PhotoVO photoVO = photoManagementService.auditImage(id, status);
        return Result.success(photoVO);
    }
}
