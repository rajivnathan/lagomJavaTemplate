package com.lightbend.rp.test.lagomendpoints.impl;

import com.lightbend.rp.test.lagomendpoints.api.HelloService;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import akka.NotUsed;
import java.util.concurrent.CompletableFuture;

public class HelloServiceImpl implements HelloService {

    private static final String ROOT_CONTENT = "Dummy root content. See http://localhost:9000/api/hello/world for the sample microservice\n";

    @Override
    public ServiceCall<NotUsed, String> hello(String id) {
        return request -> CompletableFuture.completedFuture("hello: " + id);
    }

    @Override
    public ServiceCall<NotUsed, String> root() {
        return request -> CompletableFuture.completedFuture(ROOT_CONTENT);
    }
}
