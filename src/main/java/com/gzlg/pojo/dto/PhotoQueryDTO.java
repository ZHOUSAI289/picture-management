package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 图片查询请求DTO
 * 用于接收前端查询图片列表时提交的筛选条件和分页参数
 */
@Data
public class PhotoQueryDTO {
    //图片分类（精确匹配）
    private String type;
    //图片拍摄者（模糊匹配）
    private String author;
    //关键词（搜索图片名称和描述）
    private String keyword;
    //页码（默认第1页）
    private Integer pageNum = 1;
    //每页数量（默认10条）
    private Integer pageSize = 10;
}
