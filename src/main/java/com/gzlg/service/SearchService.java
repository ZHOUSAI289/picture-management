package com.gzlg.service;

import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.dto.PhotoPageQueryDTO;
import com.gzlg.pojo.dto.PhotoQueryDTO;

public interface SearchService {

    /**
     * 图片搜索
     * @param queryDTO
     * @return
     */
    PageResult query(PhotoPageQueryDTO queryDTO);
}
