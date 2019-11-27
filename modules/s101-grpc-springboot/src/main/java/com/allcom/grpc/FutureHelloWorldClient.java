package com.allcom.grpc;

/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.allcom.grpc.gencode.GreeterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class FutureHelloWorldClient {
    private static final Logger logger = Logger.getLogger(FutureHelloWorldClient.class.getName());

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterFutureStub futureStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public FutureHelloWorldClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    /** Construct client for accessing HelloWorld server using the existing channel. */
    FutureHelloWorldClient(ManagedChannel channel) {
        this.channel = channel;
        futureStub = GreeterGrpc.newFutureStub(channel);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC future client since JVM is shutting down");
                try {
                    FutureHelloWorldClient.this.shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("*** future client shut down");
            }
        });
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public GreeterGrpc.GreeterFutureStub getFutureStub(){
        return this.futureStub;
    }

}