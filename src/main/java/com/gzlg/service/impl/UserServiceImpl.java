package com.gzlg.service.impl;

import com.gzlg.mapper.UserMapper;
import com.gzlg.pojo.dto.LoginDTO;
import com.gzlg.pojo.dto.RegisterDTO;
import com.gzlg.pojo.entity.User;
import com.gzlg.service.UserService;
import com.gzlg.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     */
    public User login(LoginDTO lo) {
        User user = userMapper.selectUser(lo);
        if (user != null) {
            // 判断角色：name为"管理员"时是admin，否则user
            String role = "管理员".equals(user.getName()) ? "admin" : "user";
            // 生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("jobNumber", lo.getJobNumber());
            claims.put("role", role);
            String jwt = JwtUtils.generateJwt(claims);
            return new User(user.getId(), user.getName(), user.getJobNumber(),
                    user.getPassword(), user.getCreateTime(), jwt);
        }
        return null;
    }

    /**
     * 注册
     */
    public void register(RegisterDTO dto) {
        // 校验姓名
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("姓名不能为空");
        }
        User existName = userMapper.selectByName(dto.getName());
        if (existName != null && "管理员".equals(existName.getName())) {
            throw new RuntimeException("名字不能为管理员");
        }
        // 校验工号：必须是8位
        String jobStr = String.valueOf(dto.getJobNumber());
        if (jobStr.length() != 8) {
            throw new RuntimeException("工号必须为8位");
        }
        // 校验密码：20位以内
        if (dto.getPassword() == null || dto.getPassword().length() > 20) {
            throw new RuntimeException("密码不能超过20位");
        }
        // 查重
        User exist = userMapper.selectByJobNumber(dto.getJobNumber());
        if (exist != null) {
            throw new RuntimeException("该工号已注册");
        }
        // 入库
        User user = new User();
        user.setName(dto.getName().trim());
        user.setJobNumber(dto.getJobNumber());
        user.setPassword(dto.getPassword());
        user.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userMapper.insert(user);

    }
}
