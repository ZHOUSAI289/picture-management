package com.gzlg.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 批量审核请求DTO
 */
@Data
public class BatchAuditDTO {
    /**
     * 图片ID列表
     */
    private List<Integer> ids;
    
    /**
     * 审核结果（approved/rejected）
     */
    private String status;
}
