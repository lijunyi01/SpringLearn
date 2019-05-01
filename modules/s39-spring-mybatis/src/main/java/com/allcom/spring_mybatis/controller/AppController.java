package com.allcom.spring_mybatis.controller;

import com.allcom.spring_mybatis.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljy on 2019/5/1.
 * ok
 */
@RestController
@RequestMapping("")
public class AppController {

    @Autowired
    AppService appService;

    @RequestMapping(value = "/stream")
    public void testStream() {
        appService.testStream();
    }


}
