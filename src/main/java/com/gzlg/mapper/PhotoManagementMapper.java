package com.gzlg.mapper;

import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.entity.image;
import com.gzlg.pojo.vo.PhotoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图片管理数据访问接口
 */
@Mapper
public interface PhotoManagementMapper {

    /**
     * 根据条件查询图片列表
     * @param queryDTO 查询条件
     * @return 图片列表
     */
    List<PhotoVO> findByCondition(PhotoQueryDTO queryDTO);

    /**
     * 根据ID查询单个图片
     * @param id 图片ID
     * @return 图片详情
     */
    PhotoVO findById(Integer id);

    /**
     * 新增图片
     * @param image 图片实体
     */
    void insert(image image);

    /**
     * 更新图片信息
     * @param image 图片实体
     */
    void update(image image);

    /**
     * 根据ID删除图片
     * @param id 图片ID
     */
    void deleteById(Integer id);

    /**
     * 根据分类名称查询图片列表
     * @param categoryName 分类名称
     * @return 图片列表
     */
    List<PhotoVO> findByCategory(String categoryName);

    /**
     * 更新单张图片状态
     */
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 批量更新图片状态(用于批量审核)
     */
    void batchUpdateStatus(@Param("ids") List<Integer> ids, @Param("status") Integer status);

    /**
     * 查询草稿
     */
    PhotoVO findDraft();

    /**
     * 删除所有草稿
     */
    void deleteDrafts();
}