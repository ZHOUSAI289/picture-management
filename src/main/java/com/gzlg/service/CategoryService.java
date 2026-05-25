package com.gzlg.service;

import com.gzlg.pojo.entity.Category;

import java.util.List;

/**
 * 分类管理服务接口
 */
public interface CategoryService {

    /**
     * 获取所有分类（带图片数量）
     */
    List<Category> getAllCategories();

    /**
     * 根据ID获取分类
     */
    Category getCategoryById(Integer id);

    /**
     * 添加分类
     */
    Category addCategory(String categoryName);

    /**
     * 删除分类
     */
    void deleteCategory(Integer id);

    /**
     * 批量更新图片分类
     */
    void batchUpdateCategory(List<Integer> ids, String category);
}
