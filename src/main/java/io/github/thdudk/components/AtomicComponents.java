package io.github.thdudk.components;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public enum AtomicComponents {
    CARBON(4, "N/A"),
    FLUORINE(1, "floro"),
    CHLORINE(1, "chloro"),
    BROMINE(1, "bromo"),
    IODINE(1, "iodo"),
    HYDROXYL(1, "N/A");

    public final int bondingCapacity;
    public final String name;
    public static final Set<AtomicComponents> halogens = Set.of(FLUORINE, CHLORINE, BROMINE, IODINE);

    public ComponentIdPair idPair() {
        return new ComponentIdPair(this);
    }
}
