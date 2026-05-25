package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.BatchAuditDTO;
import com.gzlg.pojo.dto.BatchCategoryDTO;
import com.gzlg.service.CategoryService;
import com.gzlg.service.PhotoManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片审核控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/images")
public class AuditController {

    @Autowired
    private PhotoManagementService photoManagementService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 3.3.3 批量审核图片
     * PUT /api/images/audit/batch
     */
    @PutMapping("/audit/batch")
    public Result<Map<String, Object>> batchAuditImages(@RequestBody BatchAuditDTO dto) {
        log.info("批量审核图片，数量: {}, 结果: {}", dto.getIds().size(), dto.getStatus());
        if (dto.getIds() == null || dto.getIds().isEmpty()) {
            return Result.error("请选择要审核的图片");
        }
        if (dto.getStatus() == null || dto.getStatus().isEmpty()) {
            return Result.error("请选择审核结果");
        }
        photoManagementService.batchAuditImages(dto.getIds(), dto.getStatus());
        Map<String, Object> result = new HashMap<>();
        result.put("successCount", dto.getIds().size());
        result.put("failedCount", 0);
        return Result.success(result);
    }

    /**
     * 3.3.4 批量分类图片
     * PUT /api/images/category/batch
     */
    @PutMapping("/category/batch")
    public Result<Map<String, Object>> batchCategoryImages(@RequestBody BatchCategoryDTO dto) {
        log.info("批量分类图片，数量: {}, 分类: {}", dto.getIds().size(), dto.getCategory());
        if (dto.getIds() == null || dto.getIds().isEmpty()) {
            return Result.error("请选择要分类的图片");
        }
        if (dto.getCategory() == null || dto.getCategory().isEmpty()) {
            return Result.error("请选择分类");
        }
        categoryService.batchUpdateCategory(dto.getIds(), dto.getCategory());
        Map<String, Object> result = new HashMap<>();
        result.put("successCount", dto.getIds().size());
        result.put("failedCount", 0);
        return Result.success(result);
    }
}
