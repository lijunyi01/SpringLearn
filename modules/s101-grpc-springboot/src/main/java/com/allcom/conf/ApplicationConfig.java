package com.allcom.conf;

/**
 * Created by ljy on 16/2/19.
 * ok
 */
import com.allcom.grpc.AsyncHelloWorldClient;
import com.allcom.grpc.BlockHelloWorldClient;
import com.allcom.grpc.HelloWorldServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    HelloWorldServer helloWorldServer(){
        return new HelloWorldServer(50051);
    }

    // 同步client
    @Bean
    BlockHelloWorldClient blockHelloWorldClient() {
        return new BlockHelloWorldClient("localhost", 50051);
    }

    // 异步client
    @Bean
    AsyncHelloWorldClient asyncHelloWorldClient(){
        return new AsyncHelloWorldClient("localhost",50051);
    }


}