package com.allcom.service;

import com.allcom.grpc.gencode.GreeterGrpc;
import com.allcom.grpc.gencode.HelloReply;
import com.allcom.grpc.gencode.HelloRequest;
import io.grpc.stub.StreamObserver;

/**
 * Created by ljy on 2019-11-26.
 * ok
 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ++++++++++++++++" + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
