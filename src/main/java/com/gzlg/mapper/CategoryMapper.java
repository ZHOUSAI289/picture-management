package com.gzlg.mapper;

import com.gzlg.pojo.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类管理数据访问接口
 */
@Mapper
public interface CategoryMapper {

    /**
     * 获取所有分类
     * @return 分类列表
     */
    List<Category> findAll();

    /**
     * 根据ID查询分类
     * @param id 分类ID
     * @return 分类信息
     */
    Category findById(Integer id);

    /**
     * 根据名称查询分类
     * @param name 分类名称
     * @return 分类信息
     */
    Category findByName(String name);

    /**
     * 新增分类
     * @param category 分类实体
     */
    void insert(Category category);

    /**
     * 根据ID删除分类
     * @param id 分类ID
     */
    @Delete("DELETE FROM category WHERE id = #{id}")
    void deleteById(Integer id);

    /**
     * 统计某分类下的图片数量
     * @param categoryName 分类名称
     * @return 图片数量
     */
    int countImagesByCategory(String categoryName);

    /**
     * 获取所有分类及其图片数量
     * @return 分类列表（带图片数量）
     */
    List<Category> findAllWithCount();

    /**
     * 批量更新图片分类
     * @param ids 图片ID列表
     * @param category 新分类名称
     */
    void batchUpdateCategory(@Param("ids") List<Integer> ids, @Param("category") String category);

}
