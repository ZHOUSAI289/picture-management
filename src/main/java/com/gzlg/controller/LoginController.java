package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.pojo.dto.LoginDTO;
import com.gzlg.pojo.entity.User;
import com.gzlg.pojo.vo.LoginVO;
import com.gzlg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserService loginService;

    /**
     * 登录
     * @param lo
     * @return
     */
    @PostMapping
    public Result<LoginVO> login(@RequestBody LoginDTO lo) {
        log.info("登录成功");
        User user = loginService.login(lo);
        LoginVO loginVO = new LoginVO();

        if( user != null){
            BeanUtils.copyProperties(user,loginVO);
            return Result.success(loginVO);
        }
        return Result.error("用户名或密码错误");
    }
}

