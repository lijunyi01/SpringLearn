package com.allcom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@ComponentScan(basePackageClasses = App.class)
public class Application {

    //private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        // ApplicationContext ctx =
        SpringApplication.run(Application.class, args);

    }

}
