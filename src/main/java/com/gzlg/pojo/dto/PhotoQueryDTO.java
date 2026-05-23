package com.gzlg.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 图片查询请求DTO
 * 用于接收前端查询图片列表时提交的筛选条件和分页参数
 */
@Data
public class PhotoQueryDTO {
    //页码（默认第1页）
    @JsonProperty("page")
    private Integer pageNum = 1;
    //每页数量（默认10条）
    private Integer pageSize = 10;
    //标题关键词搜索
    private String title;
    //图库分类筛选
    private String category;
    //开始日期
    private String startDate;
    //结束日期
    private String endDate;
    //状态筛选（0-待审核，1-审核通过，2-审核不通过，3-已发布）
    private Integer status;
    //页码偏移量（计算后使用）
    private Integer offset;

    //计算分页偏移量
    public void calculateOffset() {
        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;
        this.offset = (pageNum - 1) * pageSize;
    }
}
