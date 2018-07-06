package org.bwehrle.rxc.domain;

import akka.Done;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Data;
import org.bwehrle.rxc.api.Profile;

import javax.annotation.concurrent.Immutable;

// Commands accepted by the entity
public interface ProfileCommand extends Jsonable {

    @Data
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    final class CreateProfile implements ProfileCommand, PersistentEntity.ReplyType<Done> {
        final public Profile profile;
    }

    @Data
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    final class GetProfile implements ProfileCommand, PersistentEntity.ReplyType<Profile> {

    }
}
