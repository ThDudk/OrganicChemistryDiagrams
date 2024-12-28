package io.github.thdudk.analysis;

import io.github.thdudk.CSAcademyConvertor;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlkyneNamerTest {
    @Test
    void testBranchless() {
        val propyne = TestMolecules.propyne();
        System.out.println(new AlkyneNamer(propyne).getName());
        assertAll(
            () -> assertEquals("eth-1-yne", new AlkyneNamer(TestMolecules.ethyne()).getName()),
            () -> assertTrue(Set.of("prop-1,2-diyne", "prop-2,1-diyne").contains(new AlkyneNamer(propyne).getName()))
        );
    }
}