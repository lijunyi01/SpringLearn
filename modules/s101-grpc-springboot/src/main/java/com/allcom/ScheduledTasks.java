package com.allcom;

import com.allcom.grpc.AsyncHelloWorldClient;
import com.allcom.grpc.BlockHelloWorldClient;
import com.allcom.grpc.FutureHelloWorldClient;
import com.allcom.grpc.gencode.HelloReply;
import com.allcom.grpc.gencode.HelloRequest;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ljy on 15/5/14
 */
@Component
public class ScheduledTasks {

    @Autowired
    BlockHelloWorldClient blockHelloWorldClient;
    @Autowired
    AsyncHelloWorldClient asyncHelloWorldClient;
    @Autowired
    FutureHelloWorldClient futureHelloWorldClient;

    //job1 以同步/阻塞方式 进行rpc调用
    @Scheduled(fixedRateString = "500000")
    public void job1() {
        String name = "James";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockHelloWorldClient.getBlockingStub().sayHello(request);
            System.out.println("block response:"+response.getMessage());
        } catch (StatusRuntimeException e) {
            // logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            System.out.println("Exception:" + e.getMessage());
            return;
        }
    }

    //job2 以异步方式 进行rpc调用
    @Scheduled(fixedRateString = "3000000")
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

    //job3 以异步(Future)方式 进行rpc调用
    @Scheduled(fixedRateString = "3000")
    public void job3() {
        String name = "GeorgeF";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        ListenableFuture<HelloReply> listenableFuture = futureHelloWorldClient.getFutureStub().sayHello(request);
        listenableFuture.addListener(()-> {
            try{
                HelloReply response = listenableFuture.get();
                System.out.println("future response: " + response.getMessage());

            }catch(Exception e) {
                System.out.println("exception:" + e.getMessage());
            }

          }, Executors.newFixedThreadPool(1));

        // TimeUnit.SECONDS.sleep(3);
        // Runtime.getRuntime().exit(0);
    }
}
