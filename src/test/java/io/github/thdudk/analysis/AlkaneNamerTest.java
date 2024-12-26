package io.github.thdudk.analysis;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.construction.builders.GraphBuilder;
import io.github.thdudk.molecules;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlkaneNamerTest {
    @Test
    void testBranchless() {
        assertAll(
            () -> assertEquals("methane", new AlkaneNamer(molecules.METHANE.molecule).getName()),
            () -> assertEquals("ethane", new AlkaneNamer(molecules.ETHANE.molecule).getName()),
            () -> assertEquals("propane", new AlkaneNamer(molecules.PROPANE.molecule).getName())
        );
    }

    @Test
    void testSingleBranched() {
        System.out.println(new AlkaneNamer(molecules.ONECHLOROPROPANE.molecule).getName());

        assertAll(
            () -> assertEquals("2-monomethylpropane", new AlkaneNamer(molecules.METHYLPROPANE.molecule).getName()),
            () -> assertEquals("3-monoethylpentane", new AlkaneNamer(molecules.ETHYLPENTANE.molecule).getName()),
            () -> assertTrue(Set.of("1-monochloropropane", "3-monochloropropane").contains(new AlkaneNamer(molecules.ONECHLOROPROPANE.molecule).getName()))
        );
    }

    @Test
    void testMultiBranched() {
        assertAll(
            () -> assertEquals("2,2-dimethylpropane", new AlkaneNamer(molecules.DIMETHYLPROPANE.molecule).getName()),
            () -> assertTrue(Set.of("3-monoethyl-2-monomethylpentane", "3-monoethyl-4-monomethylpentane").contains(new AlkaneNamer(molecules.TWOMETHYLETHYLPENTANE.molecule).getName()))
        );
    }

}