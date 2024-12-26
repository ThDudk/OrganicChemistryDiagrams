package io.github.thdudk.analysis;

import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;

import java.util.List;

public class MoleculeClassifier {
    private final Graph<ComponentIdPair> molecule;
    private final RootNamer rootNamer;

    public MoleculeClassifier(Graph<ComponentIdPair> molecule) {
        this.molecule = molecule;
        rootNamer = getRootNamer(molecule);
    }

    private static RootNamer getRootNamer(Graph<ComponentIdPair> molecule) {
        return new AlkaneNamer(molecule);
    }

    public String getExplicitName() {
        return rootNamer.getName();
    }

}
