package io.github.thdudk.analysis;

import io.github.thdudk.CSAcademyConvertor;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlkaneNamerTest {
    @Test
    void testBranchless() {
        assertAll(
            () -> assertEquals("methane", new AlkaneNamer(TestMolecules.alkaneOf(1)).getName()),
            () -> assertEquals("ethane", new AlkaneNamer(TestMolecules.alkaneOf(2)).getName()),
            () -> assertEquals("propane", new AlkaneNamer(TestMolecules.alkaneOf(3)).getName())
        );
    }

    @Test
    void testSingleBranched() {
        System.out.println(CSAcademyConvertor.convert(TestMolecules.methylPropane()));
        assertAll(
            () -> assertEquals("2-methylpropane", new AlkaneNamer(TestMolecules.methylPropane()).getName()),
            () -> assertEquals("3-ethylpentane", new AlkaneNamer(TestMolecules.ethylPentane()).getName()),
            () -> assertTrue(Set.of("1-chloropropane", "3-chloropropane").contains(new AlkaneNamer(TestMolecules.oneChloroPropane()).getName()))
        );
    }

    @Test
    void testMultiBranched() {
        assertAll(
            () -> assertEquals("2,2-dimethylpropane", new AlkaneNamer(TestMolecules.dimethylPropane()).getName()),
            () -> assertTrue(Set.of("3-ethyl-2-methylpentane", "3-ethyl-4-methylpentane").contains(new AlkaneNamer(TestMolecules.twoMethylEthylPentane()).getName()))
        );
    }
}