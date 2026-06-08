package com.gzlg.mapper;

import com.github.pagehelper.Page;
import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.dto.PhotoPageQueryDTO;
import com.gzlg.pojo.entity.image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SearchMapper {

    /**
     * 图片搜索
     * @param queryDTO
     * @return
     */
    Page<image> query(PhotoPageQueryDTO queryDTO);
}
