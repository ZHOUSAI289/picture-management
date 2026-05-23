package com.gzlg.service;

import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;

public interface HomeService {

    /**
     * 获取图片列表（分页）
     * @param photoQueryDTO 图片查询参数
     * @return 分页结果
     */
    PageResult<PhotoVO> getImageList(PhotoQueryDTO photoQueryDTO);
}
