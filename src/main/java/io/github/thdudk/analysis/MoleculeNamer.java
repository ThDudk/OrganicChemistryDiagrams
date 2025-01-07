package io.github.thdudk.analysis;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;

import java.util.List;
import java.util.Set;

public class MoleculeNamer {
    private final WeightedGraph<ComponentIdPair, Bonds> molecule;
    private final RootNamer rootNamer;

    public MoleculeNamer(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        this.molecule = molecule;
        rootNamer = getRootNamer(molecule);
    }

    public static RootNamer getRootNamer(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        // is an alcohol
        for(ComponentIdPair node : molecule.getNodes())
            if(node.getComponent().equals(AtomicComponents.HYDROXYL))
                return new AlchoholNamer(molecule);

        // is an alkene or alkyne
        for(ComponentIdPair node : molecule.getNodes()) {
            for (EdgeEndpointPair<ComponentIdPair, Bonds> pair : molecule.getEdges(node)) {
                if (pair.getEdge().equals(Bonds.DOUBLE))
                    return new AlkeneNamer(molecule);

                if (pair.getEdge().equals(Bonds.TRIPLE))
                    return new AlkyneNamer(molecule);
            }
        }

        // use default AlkaneNamer
        return new AlkaneNamer(molecule);
    }

    public String getExplicitName() {
        return rootNamer.getName();
    }
}
