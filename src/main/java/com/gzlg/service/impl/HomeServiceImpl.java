package com.gzlg.service.impl;

import com.gzlg.mapper.HomeMapper;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeMapper homeMapper;

    /**
     * 获取图片列表（分页）
     * @param photoQueryDTO 图片查询参数
     * @return 分页结果
     */
    public PageResult<PhotoVO> getImageList(PhotoQueryDTO photoQueryDTO) {
        log.info("获取图片列表, 参数: {}", photoQueryDTO);

        // 计算分页偏移量
        photoQueryDTO.calculateOffset();

        // 查询总数
        Long total = (long) homeMapper.countByCondition(photoQueryDTO);

        // 查询当前页数据
        List<PhotoVO> list = homeMapper.findByCondition(photoQueryDTO);

        return new PageResult<>(list, total, photoQueryDTO.getPageNum(), photoQueryDTO.getPageSize());
    }
}
