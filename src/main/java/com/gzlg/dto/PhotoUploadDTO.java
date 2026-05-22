package com.gzlg.dto;

import lombok.Data;

/**
 * 图片上传请求DTO
 * 用于接收前端上传图片时提交的数据
 */
@Data
public class PhotoUploadDTO {
    //图片名称
    private String name;
    //图片描述
    private String description;
    //图片分类
    private String type;
    //图片拍摄者
    private String author;
    //图片URL（阿里云OSS存储地址）
    private String url;
    //图片大小
    private String size;
}
