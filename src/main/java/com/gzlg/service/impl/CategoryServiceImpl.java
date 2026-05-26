package com.gzlg.service.impl;

import com.gzlg.mapper.CategoryMapper;
import com.gzlg.pojo.entity.Category;
import com.gzlg.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类管理服务实现类
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有分类列表
     * @return
     */
    public List<Category> getAllCategories() {
        log.info("获取所有分类列表");
        return categoryMapper.findAllWithCount();
    }

    /**
     * 根据ID获取分类
     * @param id
     * @return
     */
    public Category getCategoryById(Integer id) {
        log.info("根据ID获取分类: {}", id);
        Category category = categoryMapper.findById(id);
        if (category != null) {
            int count = categoryMapper.countImagesByCategory(category.getName());
            category.setCount(count);
        }
        return category;
    }

    /**
     * 添加新分类
     * @param categoryName
     * @return
     */
    public Category addCategory(String categoryName) {
        log.info("添加新分类: {}", categoryName);
        Category existing = categoryMapper.findByName(categoryName);
        if (existing != null) {
            throw new RuntimeException("分类已存在");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setCreatedAt(LocalDateTime.now());
        category.setCount(0);
        categoryMapper.insert(category);
        log.info("分类添加成功，ID: {}", category.getId());
        return category;
    }

    /**
     * 删除分类
     * @param id
     */
    public void deleteCategory(Integer id) {
        log.info("删除分类，ID: {}", id);
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        categoryMapper.deleteById(id);
        log.info("分类删除成功");
    }

    /**
     * 批量更新图片分类
     * @param ids
     * @param category
     */
    public void batchUpdateCategory(List<Integer> ids, String category) {
        log.info("批量更新图片分类，图片数: {}, 分类: {}", ids.size(), category);
        categoryMapper.batchUpdateCategory(ids, category);
    }
}
