package io.github.thdudk.analysis;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.construction.GraphFactory;
import io.github.thdudk.graphs.unweighted.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BranchAnalyzerTest {

    @Test
    void getName() {
        // make a butyl branch
        Graph<ComponentIdPair> graph = new GraphFactory<ComponentIdPair>().undirected().builder()
            .addUndirNeighborChain(List.of(
                AtomicComponents.CARBON.idPair(),
                AtomicComponents.CARBON.idPair(),
                AtomicComponents.CARBON.idPair(),
                AtomicComponents.CARBON.idPair()
            )).build();

        assertEquals("butyl", BranchNamer.getName(graph));
    }
}