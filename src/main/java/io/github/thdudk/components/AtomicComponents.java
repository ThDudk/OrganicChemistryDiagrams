package io.github.thdudk.components;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public enum AtomicComponents {
    CARBON(4, "N/A", "Carbon", "", Color.BLACK),
    FLUORINE(1, "fluoro", "Fluorine", "F", Color.GREEN),
    CHLORINE(1, "chloro", "Chlorine", "Cl", Color.ORANGE),
    BROMINE(1, "bromo", "Bromine", "Br", Color.DARKRED),
    IODINE(1, "iodo", "Iodine", "I", Color.PURPLE),
    HYDROXYL(1, "N/A", "Hydroxyl", "OH", Color.RED);

    public final int bondingCapacity;
    public final String halogenRoot;
    public final String displayName;
    public final String IUPACSymbol;
    public final Color color;
    public static final Set<AtomicComponents> halogens = Set.of(FLUORINE, CHLORINE, BROMINE, IODINE);

    public ComponentIdPair idPair() {
        return new ComponentIdPair(this);
    }
}
