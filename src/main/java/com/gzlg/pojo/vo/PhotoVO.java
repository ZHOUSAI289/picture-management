package com.gzlg.pojo.vo;

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
    //图片标题
    private String title;
    //图库分类
    private String category;
    //拍摄者
    private String photographer;
    //拍摄日期
    private String date;
    //图片路径（阿里云OSS地址）
    private String url;
    //图片简介
    private String description;
    //状态（0-待审核，1-审核通过，2-审核不通过，3-已发布）
    private Integer status;
    //创建时间
    private String createdAt;
    //更新时间
    private String updatedAt;
}
