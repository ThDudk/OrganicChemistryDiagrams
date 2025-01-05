package io.github.thdudk.components;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public enum AtomicComponents {
    CARBON(4, "N/A", "Carbon", "C"),
    FLUORINE(1, "floro", "Fluorine", "F"),
    CHLORINE(1, "chloro", "Chlorine", "Cl"),
    BROMINE(1, "bromo", "Bromine", "Br"),
    IODINE(1, "iodo", "Iodine", "I"),
    HYDROXYL(1, "N/A", "Hydroxyl", "OH");

    public final int bondingCapacity;
    public final String halogenRoot;
    public final String displayName;
    public final String IUPACSymbol;
    public static final Set<AtomicComponents> halogens = Set.of(FLUORINE, CHLORINE, BROMINE, IODINE);

    public ComponentIdPair idPair() {
        return new ComponentIdPair(this);
    }
}
