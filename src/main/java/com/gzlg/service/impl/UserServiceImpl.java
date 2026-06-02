package com.gzlg.service.impl;

import com.gzlg.mapper.UserMapper;
import com.gzlg.pojo.dto.LoginDTO;
import com.gzlg.pojo.entity.User;
import com.gzlg.service.UserService;
import com.gzlg.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 登录
     * @param lo
     * @return
     */
    public User login(LoginDTO lo) {
        User user = userMapper.selectUser(lo);
        //2.判断是否存在这个员工,存在则返回员工信息
        if(user != null){
            //生成JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("jobNumber",lo.getJobNumber());
            String jwt = JwtUtils.generateJwt(claims);
            return new User(user.getId(),user.getName(),user.getJobNumber(),user.getPassword(),user.getCreateTime(),jwt);
        }
        //3.不存在，返回null
        return null;
    }
}
