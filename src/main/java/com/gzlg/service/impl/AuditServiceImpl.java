package com.gzlg.service.impl;

import com.gzlg.mapper.CategoryMapper;
import com.gzlg.mapper.PhotoManagementMapper;
import com.gzlg.pojo.dto.PhotoQueryDTO;
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

    /**
     * 获取待审核列表
     * @return
     */
    public List<PhotoVO> getAuditList() {
        PhotoQueryDTO queryDTO = new PhotoQueryDTO();
        queryDTO.setStatusStr("pending");
        return photoManagementMapper.findByCondition(queryDTO);
    }

    /**
     * 审核单张图片
     * @param id
     * @param status
     * @return
     */
    public PhotoVO auditImage(Integer id, String status) {
        PhotoVO existing = photoManagementMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("图片不存在");
        }
        Integer statusCode = convertStatusToCode(status);
        photoManagementMapper.updateStatus(id, statusCode);
        return photoManagementMapper.findById(id);
    }

    /**
     * 批量审核图片
     * @param ids
     * @param status
     */
    public void batchAuditImages(List<Integer> ids, String status) {
        Integer statusCode = convertStatusToCode(status);
        photoManagementMapper.batchUpdateStatus(ids, statusCode);
    }

    /**
     * 批量分类图片
     * @param ids
     * @param category
     */
    public void batchUpdateCategory(List<Integer> ids, String category) {
        categoryMapper.batchUpdateCategory(ids, category);
    }

    /**
     * 状态值转换成代码
     * @param status
     * @return
     */
    private Integer convertStatusToCode(String status) {
        switch (status.toLowerCase()) {
            case "approved": return 1;
            case "rejected": return 2;
            default: throw new RuntimeException("无效的状态值: " + status);
        }
    }
}
