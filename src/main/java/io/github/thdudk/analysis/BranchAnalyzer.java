package io.github.thdudk.analysis;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.Prefixes;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.AllArgsConstructor;

/**
 * Small class to return the root and suffix of a branch's name. This will not provide the prefix or location of the branch.
 */
@AllArgsConstructor
public class BranchAnalyzer implements MoleculeAnalyzer {
    private final Graph<ComponentIdPair> graph;

    @Override
    public String getName() {
        if(isHalogen())
            // return the name of the only component
            return graph.findNode(a -> true).orElseThrow().getComponent().name;

        // the prefix plus yl. Ex. meth + yl = methyl
        return Prefixes.carbonLength.get(graph.getNodes().size()) + "yl";
    }
    private boolean isHalogen() {
        // the orElseThrow is simply to remove the warning. There is no way findNode will return an empty Optional in this case.
        return graph.getNodes().size() == 1
            && AtomicComponents.halogens.contains(graph.findNode(a -> true).orElseThrow().getComponent());
    }
}
