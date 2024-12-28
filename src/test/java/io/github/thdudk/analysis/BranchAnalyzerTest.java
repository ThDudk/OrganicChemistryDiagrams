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
        assertEquals("butyl", BranchNamer.getName(TestMolecules.alkaneOf(4)));
    }
}