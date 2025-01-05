package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlchoholNamer extends AlkaneNamer {
    private final List<Integer> hydroxylPositions = new ArrayList<>();

    public AlchoholNamer(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        super(molecule);

        for(ComponentIdPair hydroxyl : getHydroxyls(molecule))
            hydroxylPositions.add(getLocation(molecule, hydroxyl, getRootPath()).orElseThrow());
    }

    @Override
    public List<List<ComponentIdPair>> getValidRootPaths(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        List<ComponentIdPair> hydroxyls = getHydroxyls(molecule);

        List<List<ComponentIdPair>> paths = new ArrayList<>();

        // exclude any paths that don't contain all double bonds
        pathsLoop: for(List<ComponentIdPair> path : super.getValidRootPaths(molecule)) {
            for (ComponentIdPair hydroxyl : hydroxyls)
                if (getLocation(molecule, hydroxyl, path).isEmpty())
                    continue pathsLoop;

            paths.add(path);
        }
        return paths;
    }
    public static List<ComponentIdPair> getHydroxyls(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        return molecule.getNodes().stream().filter(a -> a.getComponent().equals(AtomicComponents.HYDROXYL)).toList();
    }

    @Override
    public String getRootNamePortion() {
        String alkaneRoot = super.getRootNamePortion();
        return alkaneRoot.substring(0, alkaneRoot.length() - 1)
            + "-" + hydroxylPositions.toString().substring(1, hydroxylPositions.toString().length() - 1).replaceAll(" ", "")
            + "-" + Prefixes.componentCount.get(hydroxylPositions.size()) + "ol";
    }
}
