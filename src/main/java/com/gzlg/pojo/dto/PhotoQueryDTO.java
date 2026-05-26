package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 图片查询请求DTO
 */
@Data
public class PhotoQueryDTO {
    //标题关键词搜索
    private String title;
    //图库分类筛选
    private String category;
    //开始日期
    private String startDate;
    //结束日期
    private String endDate;
    //状态筛选（pending/approved/rejected/published/draft）
    private String status;
    //排序字段（date / created_at）
    private String sortField;
    //排序方向（ASC / DESC）
    private String sortOrder;
}
