package com.gzlg.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片响应DTO
 * 用于后端内部数据传递，返回图片的完整信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseDTO {
    //图片id
    private Integer id;
    //图片标题
    private String title;
    //图片路径（阿里云OSS地址）
    private String url;
    //图片大小
    private String size;
    //图片上传时间
    private String createdAt;
    //图片描述
    private String description;
    //图库分类
    private String category;
    //拍摄者
    private String photographer;
}
