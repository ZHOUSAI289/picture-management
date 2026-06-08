package com.gzlg.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片分页查询请求DTO
 */
@Data
public class PhotoPageQueryDTO implements Serializable {
    //人物名字
    private String personName;

    //页码
    private int page;

    //每页数量
    private int pageSize;
}
