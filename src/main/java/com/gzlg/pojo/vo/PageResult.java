package com.gzlg.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页响应结果VO
 * 用于返回给前端的分页数据
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    //数据列表
    private List<T> list;
    //总记录数
    private Long total;
    //当前页码
    private Integer page;
    //每页数量
    private Integer pageSize;
    //总页数
    private Integer totalPages;

    public PageResult(List<T> list, Long total, Integer page, Integer pageSize) {
        this.list = list; // 数据列表
        this.total = total; // 总记录数
        this.page = page; // 当前页码
        this.pageSize = pageSize; // 每页条数
        this.totalPages = (int) Math.ceil((double) total / pageSize); // 总页数
    }
}