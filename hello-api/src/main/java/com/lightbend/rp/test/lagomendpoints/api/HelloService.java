package com.lightbend.rp.test.lagomendpoints.api;

import static com.lightbend.lagom.javadsl.api.Service.*;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

public interface HelloService extends Service {
    ServiceCall<NotUsed, String> hello(String id);
    ServiceCall<NotUsed, String> root();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("hello").withCalls(
                pathCall("/api/hello/:id", this::hello),
                pathCall("/", this::root)
        ).withAutoAcl(true);
        // @formatter:off
    }

}
