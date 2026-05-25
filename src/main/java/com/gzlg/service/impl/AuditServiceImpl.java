package com.gzlg.service.impl;

import com.gzlg.mapper.CategoryMapper;
import com.gzlg.mapper.PhotoManagementMapper;
import com.gzlg.pojo.dto.PhotoQueryDTO;
import com.gzlg.pojo.vo.PageResult;
import com.gzlg.pojo.vo.PhotoVO;
import com.gzlg.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片审核服务实现
 */
@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    @Autowired
    private PhotoManagementMapper photoManagementMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult<PhotoVO> getAuditList(Integer page, Integer pageSize, Integer status) {
        PhotoQueryDTO queryDTO = new PhotoQueryDTO();
        queryDTO.setPageNum(page != null ? page : 1);
        queryDTO.setPageSize(pageSize != null ? pageSize : 10);
        queryDTO.setStatus(status != null ? status : 0);
        queryDTO.calculateOffset();
        Long total = (long) photoManagementMapper.countByCondition(queryDTO);
        List<PhotoVO> list = photoManagementMapper.findByCondition(queryDTO);
        return new PageResult<>(list, total, queryDTO.getPageNum(), queryDTO.getPageSize());
    }

    @Override
    public PhotoVO auditImage(Integer id, String status) {
        PhotoVO existing = photoManagementMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("图片不存在");
        }
        Integer statusCode = convertStatusToCode(status);
        photoManagementMapper.updateStatus(id, statusCode);
        return photoManagementMapper.findById(id);
    }

    @Override
    public void batchAuditImages(List<Integer> ids, String status) {
        Integer statusCode = convertStatusToCode(status);
        photoManagementMapper.batchUpdateStatus(ids, statusCode);
    }

    @Override
    public void batchUpdateCategory(List<Integer> ids, String category) {
        categoryMapper.batchUpdateCategory(ids, category);
    }

    private Integer convertStatusToCode(String status) {
        switch (status.toLowerCase()) {
            case "approved": return 1;
            case "rejected": return 2;
            default: throw new RuntimeException("无效的状态值: " + status);
        }
    }
}
