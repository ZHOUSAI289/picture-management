package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.LoginDTO;
import com.gzlg.pojo.dto.RegisterDTO;
import com.gzlg.pojo.entity.User;
import com.gzlg.pojo.vo.LoginVO;
import com.gzlg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO lo) {
        log.info("登录: jobNumber={}", lo.getJobNumber());
        User user = userService.login(lo);
        LoginVO loginVO = new LoginVO();

        if (user != null) {
            BeanUtils.copyProperties(user, loginVO);
            // 根据 name 设置角色
            loginVO.setRole("管理员".equals(user.getName()) ? "admin" : "user");
            return Result.success(loginVO);
        }
        return Result.error("用户名或密码错误");
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO dto) {
        log.info("注册: name={}, jobNumber={}", dto.getName(), dto.getJobNumber());
        userService.register(dto);
        return Result.success();
    }
}

