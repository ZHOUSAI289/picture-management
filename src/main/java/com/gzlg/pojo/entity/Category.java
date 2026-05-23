package com.gzlg.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 分类实体类
 * 对应数据库 category 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    // 分类ID
    private Integer id;

    // 分类名称
    private String name;

    // 该分类下图片数量
    private Integer count;

    // 创建时间
    private LocalDateTime createdAt;
}
