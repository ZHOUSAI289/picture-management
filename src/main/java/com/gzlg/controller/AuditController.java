package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.BatchAuditDTO;
import com.gzlg.pojo.dto.BatchCategoryDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.AuditService;
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
    private AuditService auditService;

    /**
     * 获取待审核列表
     * GET /api/images/audit
     */
    @GetMapping("/audit")
    public Result<PageResult<PhotoVO>> getAuditList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        return Result.success(auditService.getAuditList(page, pageSize, status));
    }

    /**
     * 审核单张图片
     * PUT /api/images/{id}/audit
     */
    @PutMapping("/{id}/audit")
    public Result<PhotoVO> auditImage(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return Result.success(auditService.auditImage(id, status));
    }

    /**
     * 批量审核
     * PUT /api/images/audit/batch
     */
    @PutMapping("/audit/batch")
    public Result<Map<String, Object>> batchAudit(@RequestBody BatchAuditDTO dto) {
        if (dto.getIds() == null || dto.getIds().isEmpty()) {
            return Result.error("请选择要审核的图片");
        }
        if (dto.getStatus() == null || dto.getStatus().isEmpty()) {
            return Result.error("请选择审核结果");
        }
        auditService.batchAuditImages(dto.getIds(), dto.getStatus());
        Map<String, Object> result = new HashMap<>();
        result.put("successCount", dto.getIds().size());
        result.put("failedCount", 0);
        return Result.success(result);
    }

    /**
     * 批量分类
     * PUT /api/images/category/batch
     */
    @PutMapping("/category/batch")
    public Result<Map<String, Object>> batchCategory(@RequestBody BatchCategoryDTO dto) {
        if (dto.getIds() == null || dto.getIds().isEmpty()) {
            return Result.error("请选择要分类的图片");
        }
        if (dto.getCategory() == null || dto.getCategory().isEmpty()) {
            return Result.error("请选择分类");
        }
        auditService.batchUpdateCategory(dto.getIds(), dto.getCategory());
        Map<String, Object> result = new HashMap<>();
        result.put("successCount", dto.getIds().size());
        result.put("failedCount", 0);
        return Result.success(result);
    }
}
