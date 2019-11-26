package com.allcom;

import com.allcom.grpc.HelloWorldServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    private static Logger log = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Autowired
    HelloWorldServer helloWorldServer;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        helloWorldServer.start();
        helloWorldServer.blockUntilShutdown();
    }

}
