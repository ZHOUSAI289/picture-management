package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页控制器
 */
@RestController
@Slf4j
@RequestMapping("/images")
public class HomeController {

    @Autowired
    private PhotoService photoService;
    /**
     * 获取图片列表
     * @param photoQueryDTO 图片查询参数
     * @return 图片列表
     */
    @GetMapping
    public Result<List<PhotoVO>> getImageList(PhotoQueryDTO photoQueryDTO){
        List<PhotoVO> list = photoService.getImageList(photoQueryDTO);
        return Result.success(list);
    }
}
