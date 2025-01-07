package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import lombok.Getter;
import lombok.ToString;
import lombok.val;

import java.util.*;

@ToString
@Getter
public class AlkyneNamer extends AlkaneNamer{
    private final List<Integer> tripleBondLocations = new ArrayList<>();

    public AlkyneNamer(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        super(molecule);

        for(int i = 1; i < getRootPath().size(); i++) {
            if(molecule.getEdgeBetween(getRootPath().get(i - 1), getRootPath().get(i)).orElseThrow().equals(Bonds.TRIPLE)) {
                tripleBondLocations.add(i);
            }
        }
    }

    @Override
    public List<List<ComponentIdPair>> getValidRootPaths(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        int numTripleBonds = getNumTripleBonds(molecule);

        // exclude any paths that don't contain all double bonds
        return super.getValidRootPaths(molecule).stream()
            .filter(path -> getNumTripleBonds(molecule, path) == numTripleBonds).toList();
    }
    private int getNumTripleBonds(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        int numTripleBonds = 0;
        for(val node : molecule.getNodes())
            for(WeightedGraph.EdgeEndpointPair<ComponentIdPair, Bonds> edge : molecule.getEdges(node))
                if(edge.getEdge().equals(Bonds.TRIPLE))
                    numTripleBonds++;

        return numTripleBonds / 2; // divided by 2 bc the graph is undirected (so each triple bond will be counted twice)
    }
    private int getNumTripleBonds(WeightedGraph<ComponentIdPair, Bonds> molecule, List<ComponentIdPair> path) {
        int num = 0;

        for(int i = 1; i < path.size(); i++)
            if(molecule.getEdgeBetween(path.get(i - 1), path.get(i)).orElseThrow().equals(Bonds.TRIPLE))
                num++;

        return num;
    }

    @Override
    public String getRootNamePortion() {
        String alkaneRoot = super.getRootNamePortion();
        return alkaneRoot.substring(0, alkaneRoot.length() - 3)
            + "-" + tripleBondLocations.toString().substring(1, tripleBondLocations.toString().length() - 1).replaceAll(" ", "")
            + "-" + Prefixes.BondComponentCount.get(tripleBondLocations.size()) + "yne";
    }
}
