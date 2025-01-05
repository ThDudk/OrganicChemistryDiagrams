package io.github.thdudk.analysis;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlchoholNamerTest {
    @Test
    void testBranchless() {
        assertAll(
            () -> assertTrue(Set.of("ethan-1-ol", "ethan-2-ol").contains(new AlchoholNamer(TestMolecules.ethanol()).getName())),
            () -> assertEquals("propan-2-ol", new AlchoholNamer(TestMolecules.prop2Ol()).getName())
        );
    }
}