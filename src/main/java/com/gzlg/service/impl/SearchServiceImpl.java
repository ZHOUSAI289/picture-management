package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.mapper.SearchMapper;
import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.dto.PhotoPageQueryDTO;
import com.gzlg.pojo.entity.image;
import com.gzlg.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    /**
     * 图片搜索
     * @param queryDTO
     * @return
     */
    public PageResult query(PhotoPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());

        Page<image> page = searchMapper.query(queryDTO);
        long total = page.getTotal();
        List<image> records = page.getResult();
        log.info("查询结果：{}", records);
        return new PageResult(total, records);
    }
}
