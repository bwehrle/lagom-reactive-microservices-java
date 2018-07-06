package org.bwehrle.rxc.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.api.broker.kafka.KafkaProperties;

import static com.lightbend.lagom.javadsl.api.Service.*;

/**
 * The rxc service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the RxcService.
 */
public interface ProfileService extends Service {
    /** curl http://localhost:9000/api/hello/Alice */
    ServiceCall<NotUsed, String> createProfile();

    ServiceCall<NotUsed, Profile> getProfile(String id);

    /**
     * This gets published to Kafka.
     */
    Topic<ProfileEvent> profileEvents();

    @Override
    default Descriptor descriptor() {
        return named("rxc")
                .withCalls(
                        pathCall("/api/profile/:id", this::createProfile),
                        pathCall("/api/hello/:id", this::getProfile)
                )
                .withTopics(
                        topic("profile-events", this::profileEvents)
                                // Kafka partitions messages, messages within the same partition will
                                // be delivered in order, to ensure that all messages for the same user
                                // go to the same partition (and hence are delivered in order with respect
                                // to that user), we configure a partition key strategy that extracts the
                                // name as the partition key.
                                .withProperty(KafkaProperties.partitionKeyStrategy(), ProfileEvent::getName)
                )
                .withAutoAcl(true);
    }
}
