package org.bwehrle.rxc.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import org.bwehrle.rxc.api.RxcService;

/**
 * The module that binds the RxcService so that it can be served.
 */
public class RxcModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(RxcService.class, RxcServiceImpl.class);
    }
}
