package io.github.thdudk.analysis;

import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;

import java.util.List;

/// Class that will take in a molecule and determine its features.
///
/// Currently, the only feature it determines is the molecule's full name
public class MoleculeClassifier {
    private final Graph<ComponentIdPair> molecule;
    private final RootNamer rootNamer;

    public MoleculeClassifier(Graph<ComponentIdPair> molecule) {
        this.molecule = molecule;
        rootNamer = getRootNamer(molecule);
    }

    private static RootNamer getRootNamer(Graph<ComponentIdPair> molecule) {
        return new AlkaneNamer(molecule.longestSpanningPath(molecule.findNode(a -> true).orElseThrow())); // convert to PathGraph
    }

    public String getExplicitName() {
        return rootNamer.getName();
    }

}
