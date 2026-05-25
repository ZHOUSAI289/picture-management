package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.CategoryCreateDTO;
import com.gzlg.pojo.entity.Category;
import com.gzlg.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类列表
     * GET /api/categories
     */
    @GetMapping
    public Result<List<Category>> getAllCategories() {
        log.info("获取分类列表");
        List<Category> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }

    /**
     * 添加新分类
     * POST /api/categories
     */
    @PostMapping
    public Result<Category> addCategory(@RequestBody CategoryCreateDTO dto) {
        log.info("添加分类: {}", dto.getName());
        
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return Result.error("分类名称不能为空");
        }
        
        try {
            Category category = categoryService.addCategory(dto.getName().trim());
            return Result.success(category);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除分类
     * DELETE /api/categories/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Integer id) {
        log.info("删除分类，ID: {}", id);
        
        try {
            categoryService.deleteCategory(id);
            return Result.success(null);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
