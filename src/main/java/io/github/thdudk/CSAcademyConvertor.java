package io.github.thdudk;

import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.val;

/// small class that converts molecule graphs into a format supported by the [CSAcademy Graph Editor](https://csacademy.com/app/graph_editor/) so I can visualize them
public abstract class CSAcademyConvertor {
    public static String convert(Graph<ComponentIdPair> graph) {
        String res = "";
        for(val node : graph.getNodes()) {
            for(val neighbour : graph.getOutNeighbours(node)) {
                res += "\n" + node.getUuid().toString().substring(0, 3) + " " + neighbour.getUuid().toString().substring(0, 3);
            }
        }
        return res;
    }
}
