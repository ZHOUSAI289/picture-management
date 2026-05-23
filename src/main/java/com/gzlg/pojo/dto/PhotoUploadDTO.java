package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 图片上传请求DTO
 * 用于接收前端上传图片时提交的 multipart/form-data 数据
 */
@Data
public class PhotoUploadDTO {
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
    //是否启用自定义分类（默认false）
    private Boolean enableCustomCategory;
    //自定义分类名称（当enableCustomCategory为true时必填）
    private String customCategory;
}
