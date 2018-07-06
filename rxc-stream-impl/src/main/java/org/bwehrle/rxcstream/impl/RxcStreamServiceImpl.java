package org.bwehrle.rxcstream.impl;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import org.bwehrle.rxc.api.RxcService;
import org.bwehrle.rxcstream.api.RxcStreamService;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Implementation of the RxcStreamService.
 */
public class RxcStreamServiceImpl implements RxcStreamService {
    private final RxcService rxcService;
    private final RxcStreamRepository repository;

    @Inject
    public RxcStreamServiceImpl(RxcService rxcService, RxcStreamRepository repository) {
        this.rxcService = rxcService;
        this.repository = repository;
    }

    @Override
    public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> directStream() {
        return hellos -> completedFuture(
                hellos.mapAsync(8, name -> rxcService.hello(name).invoke()));
    }

    @Override
    public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> autonomousStream() {
        return hellos -> completedFuture(
                hellos.mapAsync(8, name -> repository.getMessage(name).thenApply(message ->
                        String.format("%s, %s!", message.orElse("Hello"), name)
                ))
        );
    }
}
