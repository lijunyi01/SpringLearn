package com.allcom.conf;

/**
 * Created by ljy on 16/2/19.
 * ok
 */
import com.allcom.grpc.HelloWorldClient;
import com.allcom.grpc.HelloWorldServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    HelloWorldServer helloWorldServer(){
        return new HelloWorldServer(50051);
    }

    @Bean
    HelloWorldClient helloWorldClient() {
        return new HelloWorldClient("localhost", 50051);
    }

}