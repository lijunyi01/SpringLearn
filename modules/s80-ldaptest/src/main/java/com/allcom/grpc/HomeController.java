package com.allcom.grpc;

/**
 * Created by ljy on 16/7/5.
 * ok
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "Welcome to the home page!";
    }
}
