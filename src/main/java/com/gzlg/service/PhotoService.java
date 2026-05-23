package com.gzlg.service;

import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.vo.PhotoVO;

import java.util.List;

public interface PhotoService {

    /**
     * 获取图片列表
     * @param photoQueryDTO 图片查询参数
     * @return 图片列表
     */
    List<PhotoVO> getImageList(PhotoQueryDTO photoQueryDTO);
}
