package io.github.thdudk.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ComponentIdPair {
    AtomicComponents component;
    UUID uuid;

    public ComponentIdPair(AtomicComponents component) {
        this(component, UUID.randomUUID());
    }

    public String toString() {
        return "{" + component.toString().substring(0, 2) + ", " + uuid.toString().substring(0, 3) + "}";
    }
}
