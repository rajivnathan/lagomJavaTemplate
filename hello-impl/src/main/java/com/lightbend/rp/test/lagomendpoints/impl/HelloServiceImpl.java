package com.lightbend.rp.test.lagomendpoints.impl;

import com.lightbend.rp.test.lagomendpoints.api.HelloService;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import akka.NotUsed;
import akka.japi.Pair;
import java.util.concurrent.CompletableFuture;
import java.util.Optional;

import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;
import org.pcollections.PSequence;

public class HelloServiceImpl implements HelloService {

    private static final String ROOT_CONTENT = "<html><head>" +
                                               "<meta http-equiv=\"refresh\" content=\"0; url=/api/hello/world\">" +
                                               "</head></html>";

    @Override
    public ServiceCall<NotUsed, String> hello(String id) {
        return request -> CompletableFuture.completedFuture("hello: " + id);
    }

    @Override
    public HeaderServiceCall<NotUsed, String> root() {
        return (reqHeaders, request) -> {
            String responseBody = ROOT_CONTENT;
            MessageProtocol proto = new MessageProtocol(Optional.of("text/html"), Optional.empty(), Optional.empty());
            HashPMap<String, PSequence<String>> resHeaderMap = HashTreePMap.<String, PSequence<String>>empty();
            ResponseHeader resHeaders = new ResponseHeader(200, proto, resHeaderMap);
            return CompletableFuture.completedFuture(Pair.create(resHeaders, responseBody));
        };
    }
}
