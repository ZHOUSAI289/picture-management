package com.gzlg.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuditMapper {

    /**
     * 批量审核图片
     * @param ids 图片ID列表
     * @param status 状态（pending/approved/rejected/published/draft）
     */
    void batchUpdateStatus(@Param("ids") List<Integer> ids, @Param("status") String status);
}
