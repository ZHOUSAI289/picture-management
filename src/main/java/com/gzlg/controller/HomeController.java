package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 */
@RestController
@Slf4j
@RequestMapping("/images")
public class HomeController {

    @Autowired
    private HomeService homeService;
    /**
     * 获取图片列表（分页）
     * @param photoQueryDTO 图片查询参数
     * @return 分页结果
     */
    @GetMapping
    public Result<PageResult<PhotoVO>> getImageList(PhotoQueryDTO photoQueryDTO){
        PageResult<PhotoVO> pageResult = homeService.getImageList(photoQueryDTO);
        return Result.success(pageResult);
    }
}
