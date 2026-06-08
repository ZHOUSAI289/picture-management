package com.gzlg.controller;

import com.github.pagehelper.PageHelper;
import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.PhotoPageQueryDTO;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private SearchService searchService;
    /**
     * 图片搜索
     * @param queryDTO
     * @return
     */
    @GetMapping("/search")
    public Result<PageResult> search(PhotoPageQueryDTO queryDTO) {
        log.info("搜索图片信息：{}",queryDTO);
        PageResult pageResult = searchService.query(queryDTO);
        return Result.success(pageResult);
    }
}
