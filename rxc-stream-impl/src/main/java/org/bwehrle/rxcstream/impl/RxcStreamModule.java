package org.bwehrle.rxcstream.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import org.bwehrle.rxc.api.RxcService;
import org.bwehrle.rxcstream.api.RxcStreamService;

/**
 * The module that binds the RxcStreamService so that it can be served.
 */
public class RxcStreamModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        // Bind the RxcStreamService service
        bindService(RxcStreamService.class, RxcStreamServiceImpl.class);
        // Bind the RxcService client
        bindClient(RxcService.class);
        // Bind the subscriber eagerly to ensure it starts up
        bind(RxcStreamSubscriber.class).asEagerSingleton();
    }
}
