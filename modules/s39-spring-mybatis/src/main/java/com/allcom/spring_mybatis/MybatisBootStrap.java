package com.allcom.spring_mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ljy on 2019/5/1.
 * ok
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.allcom.spring_mybatis.dao"})
public class MybatisBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(MybatisBootStrap.class, args);
    }
}