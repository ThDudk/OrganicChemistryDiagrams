package io.github.thdudk.analysis;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlkeneNamerTest {
    @Test
    void testBranchless() {
        assertAll(
            () -> assertEquals("eth-1-ene", new AlkeneNamer(TestMolecules.ethene()).getName()),
            () -> assertTrue(Set.of("prop-1-ene", "prop-2-ene").contains(new AlkeneNamer(TestMolecules.propene()).getName()))
        );
    }

}