package com.allcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Created by ljy on 2017/8/29.
 * ok
 */
@SpringBootApplication(scanBasePackageClasses = App.class)
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
