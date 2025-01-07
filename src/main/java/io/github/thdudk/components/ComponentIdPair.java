package io.github.thdudk.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.thdudk.ComponentIdPairKeyDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class ComponentIdPair {
    AtomicComponents component;
    UUID uuid;
    
    @JsonCreator
    public ComponentIdPair(@JsonProperty("component") AtomicComponents component, @JsonProperty("uuid") UUID uuid) {
        this.component = component;
        this.uuid = uuid;
    }

    public ComponentIdPair(AtomicComponents component) {
        this(component, UUID.randomUUID());
    }
}
