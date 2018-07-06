package org.bwehrle.rxcstream.impl;

import akka.Done;
import akka.stream.javadsl.Flow;

import org.bwehrle.rxc.api.RxcEvent;
import org.bwehrle.rxc.api.RxcService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

/**
 * This subscribes to the RxcService event stream.
 */
public class RxcStreamSubscriber {
    @Inject
    public RxcStreamSubscriber(RxcService rxcService, RxcStreamRepository repository) {
        // Create a subscriber
        rxcService.helloEvents().subscribe()
                // And subscribe to it with at least once processing semantics.
                .atLeastOnce(
                        // Create a flow that emits a Done for each message it processes
                        Flow.<RxcEvent>create().mapAsync(1, event -> {
                            if (event instanceof RxcEvent.GreetingMessageChanged) {
                                RxcEvent.GreetingMessageChanged messageChanged = (RxcEvent.GreetingMessageChanged) event;
                                // Update the message
                                return repository.updateMessage(messageChanged.getName(), messageChanged.getMessage());
                            } else {
                                // Ignore all other events
                                return CompletableFuture.completedFuture(Done.getInstance());
                            }
                        })
                );
    }
}
