package com.gzlg.dto;

import lombok.Data;

/**
 * 图片更新请求DTO
 * 用于接收前端更新图片信息时提交的数据
 */
@Data
public class PhotoUpdateDTO {
    //图片id（必填，用于定位要更新的图片）
    private Integer id;
    //图片名称
    private String name;
    //图片描述
    private String description;
    //图片分类
    private String type;
    //图片拍摄者
    private String author;
}
