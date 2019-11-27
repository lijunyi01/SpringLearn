package com.allcom;

import com.allcom.grpc.AsyncHelloWorldClient;
import com.allcom.grpc.BlockHelloWorldClient;
import com.allcom.grpc.gencode.HelloReply;
import com.allcom.grpc.gencode.HelloRequest;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ljy on 15/5/14
 */
@Component
public class ScheduledTasks {

    @Autowired
    BlockHelloWorldClient blockHelloWorldClient;
    @Autowired
    AsyncHelloWorldClient asyncHelloWorldClient;

    //job1 以同步/阻塞方式 进行rpc调用
    @Scheduled(fixedRateString = "500000")
    public void job1() {
        String name = "James";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockHelloWorldClient.getBlockingStub().sayHello(request);
            System.out.println("response:"+response.getMessage());
        } catch (StatusRuntimeException e) {
            // logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            System.out.println("Exception:" + e.getMessage());
            return;
        }
    }

    //job2 以异步方式 进行rpc调用
    @Scheduled(fixedRateString = "3000")
    public void job2() {
        String name = "George";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();

        StreamObserver<HelloReply> responseObserver = new StreamObserver<HelloReply>() {
            public void onNext(HelloReply value) {
                System.out.println("async response: " + value.getMessage());
            }

            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            public void onCompleted() {
            }
        };
        asyncHelloWorldClient.getAsyncStub().sayHello(request,responseObserver);
    }
}
