package com.gzlg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片视图对象VO
 * 用于返回给前端展示的图片信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoVO {
    //图片id
    private Integer id;
    //图片名称
    private String name;
    //图片路径（阿里云OSS地址）
    private String url;
    //图片大小
    private String size;
    //图片上传时间
    private String time;
    //图片描述
    private String description;
    //图片分类
    private String type;
    //图片拍摄者
    private String author;
}
