package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import lombok.val;

import java.util.*;

public class AlchoholNamer extends AlkaneNamer {
    private final List<Integer> hydroxylPositions = new ArrayList<>();
    private final RootNamer core;

    public AlchoholNamer(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        super(molecule);

        for(ComponentIdPair hydroxyl : getHydroxyls(molecule))
            hydroxylPositions.add(getLocation(molecule, hydroxyl, getRootPath()).orElseThrow());

        val coreMol = molecule.filter(a -> !a.getComponent().equals(AtomicComponents.HYDROXYL));
        core = MoleculeNamer.getRootNamer(coreMol);
    }

    public static List<ComponentIdPair> getHydroxyls(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        return molecule.getNodes().stream().filter(a -> a.getComponent().equals(AtomicComponents.HYDROXYL)).toList();
    }

    @Override
    public String getBranchNamePortion() {
        return core.getBranchNamePortion();
    }

    @Override
    public String getRootNamePortion() {
        String root = core.getRootNamePortion();
        return root.substring(0, root.length() - 1)
            + "-" + hydroxylPositions.toString().substring(1, hydroxylPositions.toString().length() - 1).replaceAll(" ", "")
            + "-" + Prefixes.componentCount.get(hydroxylPositions.size()) + "ol";
    }
}
