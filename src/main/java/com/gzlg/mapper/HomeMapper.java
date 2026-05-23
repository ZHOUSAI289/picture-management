package com.gzlg.mapper;

import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.entity.image;
import com.gzlg.pojo.vo.PhotoVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 图片数据访问接口
 */
@Mapper
public interface HomeMapper {

    /**
     * 根据条件查询图片列表
     * @param queryDTO 查询条件
     * @return 图片列表
     */
    List<PhotoVO> findByCondition(PhotoQueryDTO queryDTO);

    /**
     * 查询图片总数（用于分页）
     * @param queryDTO 查询条件
     * @return 图片总数
     */
    int countByCondition(PhotoQueryDTO queryDTO);
}
