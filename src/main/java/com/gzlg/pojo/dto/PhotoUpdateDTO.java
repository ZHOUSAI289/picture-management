package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 图片更新请求DTO
 * 用于接收前端更新图片信息时提交的数据
 */
@Data
public class PhotoUpdateDTO {
    //图片id
    private Integer id;
    //图片标题
    private String title;
    //图片描述
    private String description;
    //图库分类
    private String category;
    //拍摄者
    private String photographer;
    //拍摄日期（格式：YYYY-MM-DD）
    private String date;
    //审核状态（pending/approved/rejected/published/draft）
    private String status;
}
