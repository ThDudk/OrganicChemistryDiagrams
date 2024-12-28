package io.github.thdudk.analysis;

import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.weighted.WeightedGraph;

import java.util.List;

public class MoleculeClassifier {
    private final WeightedGraph<ComponentIdPair, Bonds> molecule;
    private final RootNamer rootNamer;

    public MoleculeClassifier(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        this.molecule = molecule;
        rootNamer = getRootNamer(molecule);
    }

    private static RootNamer getRootNamer(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        return new AlkaneNamer(molecule);
    }

    public String getExplicitName() {
        return rootNamer.getName();
    }

}
