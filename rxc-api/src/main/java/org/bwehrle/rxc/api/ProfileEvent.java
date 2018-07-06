package org.bwehrle.rxc.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Data;
import lombok.Value;

import javax.annotation.concurrent.Immutable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProfileEvent.ProfileChanged.class, name = "profile-changed")
})
public interface ProfileEvent {

    String getName();

    String getProfileId();

    String getSelectionRule();

    @Data
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    final class ProfileCreated implements ProfileEvent {
        public final int profileId;
        public final String name;
        public final String selectionRule;

        public ProfileCreated(int profileId, String name, String selectionRule) {
            this.profileId =  Preconditions.checkNotNull(profileId, "profileId");
            this.name = Preconditions.checkNotNull(name, "name");
            this.selectionRule = Preconditions.checkNotNull(selectionRule, "selectionRule");
        }
    }

    @Data
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    final class ProfileChanged implements ProfileEvent {
        public final int profileId;
        public final String name;
        public final String selectionRule;

        public ProfileChanged(int profileId, String name, String selectionRule) {
            this.profileId =  Preconditions.checkNotNull(profileId, "profileId");
            this.name = Preconditions.checkNotNull(name, "name");
            this.selectionRule = Preconditions.checkNotNull(selectionRule, "selectionRule");
        }
    }
}
