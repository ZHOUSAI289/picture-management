package com.gzlg.service;

import com.gzlg.pojo.vo.PhotoVO;

import java.util.List;

/**
 * 图片审核服务接口
 */
public interface AuditService {

    /**
     * 获取待审核图片列表
     */
    List<PhotoVO> getAuditList();

    /**
     * 审核单张图片
     */
    PhotoVO auditImage(Integer id, String status);

    /**
     * 批量审核图片
     */
    void batchAuditImages(List<Integer> ids, String status);

    /**
     * 批量更新图片分类
     */
    void batchUpdateCategory(List<Integer> ids, String category);
}
