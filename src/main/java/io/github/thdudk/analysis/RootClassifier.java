package io.github.thdudk.analysis;

import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;

import java.util.List;

///
public abstract class RootClassifier {
    public static List<ComponentIdPair> getRoot(Graph<ComponentIdPair> molecule) {
        // Works only for Alkanes rn
        return molecule.longestSpanningPath(molecule.findNode(a -> true).orElseThrow());
    }
}
