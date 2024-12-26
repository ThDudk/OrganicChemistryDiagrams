package io.github.thdudk.components;

import lombok.Data;

import java.util.UUID;

@Data
public class ComponentIdPair {
    AtomicComponents component;
    UUID uuid;

    public ComponentIdPair(AtomicComponents component) {
        this.component = component;
        uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "{" + component.toString().substring(0, 2) + ", " + uuid.toString().substring(0, 3) + "}";
    }
}
