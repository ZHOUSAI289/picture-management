package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 图片上传请求DTO
 */
@Data
public class PhotoUploadDTO {

    /** 图片标题（必填） */
    private String title;

    /** 图片描述（必填） */
    private String description;

    /** 图库分类（必填） */
    private String category;

    /** 拍摄者（必填） */
    private String photographer;

    /** 拍摄日期，格式：YYYY-MM-DD（必填） */
    private String date;

    /** 是否启用自定义分类 */
    private Boolean enableCustomCategory;

    /** 自定义分类名称（enableCustomCategory为true时必填） */
    private String customCategory;
}
