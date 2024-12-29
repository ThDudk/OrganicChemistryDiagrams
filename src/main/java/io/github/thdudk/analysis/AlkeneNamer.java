package io.github.thdudk.analysis;

import io.github.thdudk.Prefixes;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;
import lombok.Getter;
import lombok.ToString;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class AlkeneNamer extends AlkaneNamer {
    private final List<Integer> doubleBondLocations = new ArrayList<>();

    public AlkeneNamer(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        super(molecule);

        for(int i = 1; i < getRootPath().size(); i++) {
            if(molecule.getEdgeBetween(getRootPath().get(i - 1), getRootPath().get(i)).orElseThrow().equals(Bonds.DOUBLE)) {
                doubleBondLocations.add(i);
            }
        }
    }

    @Override
    public List<List<ComponentIdPair>> getValidRootPaths(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        int numDoubleBonds = getNumDoubleBonds(molecule);

        // exclude any paths that don't contain all double bonds
        return super.getValidRootPaths(molecule).stream()
            .filter(path -> getNumDoubleBonds(molecule, path) == numDoubleBonds).toList();
    }
    private int getNumDoubleBonds(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        int numDoubleBonds = 0;
        for(val node : molecule.getNodes())
            for(EdgeEndpointPair<ComponentIdPair, Bonds> edge : molecule.getEdges(node))
                if(edge.getEdge().equals(Bonds.DOUBLE))
                    numDoubleBonds++;

        return numDoubleBonds / 2; // divided by 2 bc the graph is undirected (so edges are counted twice)
    }
    private int getNumDoubleBonds(WeightedGraph<ComponentIdPair, Bonds> molecule, List<ComponentIdPair> path) {
        int num = 0;

        for(int i = 1; i < path.size(); i++)
            if(molecule.getEdgeBetween(path.get(i - 1), path.get(i)).orElseThrow().equals(Bonds.DOUBLE))
                num++;

        return num;
    }

    @Override
    public String getRootNamePortion() {
        String alkaneRoot = super.getRootNamePortion();
        return alkaneRoot.substring(0, alkaneRoot.length() - 3)
            + "-" + doubleBondLocations.toString().substring(1, doubleBondLocations.toString().length() - 1).replaceAll(" ", "")
            + "-" + Prefixes.componentCount.get(doubleBondLocations.size()) + "ene";
    }
}
