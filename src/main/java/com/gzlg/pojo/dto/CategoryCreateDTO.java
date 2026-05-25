package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 创建分类请求DTO
 */
@Data
public class CategoryCreateDTO {

    /** 分类名称（2-20个字符） */
    private String name;
}
