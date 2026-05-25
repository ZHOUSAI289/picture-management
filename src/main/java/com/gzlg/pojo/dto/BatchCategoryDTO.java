package com.gzlg.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 批量分类请求DTO
 */
@Data
public class BatchCategoryDTO {
    /**
     * 图片ID列表
     */
    private List<Integer> ids;
    
    /**
     * 目标分类名称
     */
    private String category;
}
