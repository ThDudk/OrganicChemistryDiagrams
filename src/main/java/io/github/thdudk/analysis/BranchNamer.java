package io.github.thdudk.analysis;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.Prefixes;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.AllArgsConstructor;

// TO BE REMOVED SHORTLY
/**
 * Small class to return the root and suffix of a branch's name. This will not provide the prefix or location of the branch.
 */
@AllArgsConstructor
public abstract class BranchNamer {
    public static String getName(Graph<ComponentIdPair> graph) {
        if(isHalogen(graph))
            // return the name of the only component
            return graph.findNode(a -> true).orElseThrow().getComponent().name;

        // the prefix plus yl. Ex. meth + yl = methyl
        return Prefixes.carbonLength.get(graph.getNodes().size()) + "yl";
    }
    private static boolean isHalogen(Graph<ComponentIdPair> graph) {
        // the orElseThrow is simply to remove the warning. There is no way findNode will return an empty Optional in this case.
        return graph.getNodes().size() == 1
            && AtomicComponents.halogens.contains(graph.findNode(a -> true).orElseThrow().getComponent());
    }
}
