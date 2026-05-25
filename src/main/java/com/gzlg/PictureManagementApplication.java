package com.gzlg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gzlg.mapper")
public class PictureManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureManagementApplication.class, args);
    }

}