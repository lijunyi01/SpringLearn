package com.allcom;

import com.allcom.grpc.HelloWorldClient;
import com.allcom.grpc.gencode.HelloReply;
import com.allcom.grpc.gencode.HelloRequest;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 * Created by ljy on 15/5/14
 */
@Component
public class ScheduledTasks {

    @Autowired
    HelloWorldClient helloWorldClient;

    //(fixedRate = ...),等号后只能是常量
    //以一个固定速率5s来调用一次执行，这个周期是以上一个任务开始时间为基准，从上一任务开始执行后5s再次调用
    //@Scheduled(fixedRate = rate)
    @Scheduled(fixedRateString = "5000")
    public void job() {
        String name = "James";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = helloWorldClient.getBlockingStub().sayHello(request);
            System.out.println("response:"+response.getMessage());
        } catch (StatusRuntimeException e) {
            // logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            System.out.println("Exception:" + e.getMessage());
            return;
        }
    }

}
