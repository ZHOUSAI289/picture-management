package com.gzlg.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class image {
    // 图片ID（主键，自增）
    private Integer id;

    // 图片标题
    private String title;

    // 图库分类
    private String category;

    // 拍摄者
    private String photographer;

    // 拍摄日期
    private LocalDate date;

    // 图片URL（阿里云OSS地址）
    private String url;

    // 图片简介
    private String description;

    // 状态：0-待审核，1-审核通过，2-审核不通过，3-已发布
    private Integer status;

    // 创建时间（自动填充）
    private LocalDateTime createdAt;

    // 更新时间（自动填充）
    private LocalDateTime updatedAt;
}
