package io.github.thdudk.analysis;

import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;

import java.util.List;

/// Class that will take in a molecule and determine its features.
///
/// Currently, the only feature it determines is the molecule's full name
public class MoleculeAnalyzer {
    private final Graph<ComponentIdPair> molecule;
    private final Graph<ComponentIdPair> root;
    private final List<Feature> features;

    public MoleculeAnalyzer(Graph<ComponentIdPair> molecule) {
        this.molecule = molecule;
        root = RootClassifier.getRoot(molecule);
        features = null;
    }

    public String getExplicitName() {
        throw new RuntimeException("Not yet implemented");
    }

}
