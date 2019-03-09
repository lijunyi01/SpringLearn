package com.allcom.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljy on 17/7/7.
 * ok
 */
@RestController
@RequestMapping("/test1")
@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
public class Test1Controller {

    @RequestMapping()
    public String test1(){
        return "test1";
    }
}
