package com.gzlg.mapper;

import com.gzlg.pojo.vo.PhotoVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DraftMapper {
    /**
     * 查询草稿
     */
    PhotoVO findDraft();

    /**
     * 删除所有草稿
     */
    @Delete("DELETE FROM image WHERE status = 'draft'")
    void deleteDrafts();
}
