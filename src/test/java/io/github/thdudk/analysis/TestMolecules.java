package io.github.thdudk.analysis;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.construction.WeightedGraphFactory;
import io.github.thdudk.construction.builders.WeightedGraphBuilder;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import lombok.val;

import java.util.List;

public class TestMolecules {
    public static WeightedGraph<ComponentIdPair, Bonds> alkaneOf(int carbons) {
        if(carbons == 1)
            return WeightedGraphBuilder.<ComponentIdPair, Bonds>of()
                .addNode(AtomicComponents.CARBON.idPair()).build();

        val prev = alkaneOf(carbons - 1);
        return WeightedGraphBuilder.of(prev)
            .addUndirEdge(getEdge(prev), Bonds.SINGLE, AtomicComponents.CARBON.idPair()).build();
    }

    public static WeightedGraph<ComponentIdPair, Bonds> oneChloroPropane() {
        val propane = alkaneOf(3);
        return WeightedGraphBuilder.of(propane)
            .addUndirEdge(getEdge(propane), Bonds.SINGLE, AtomicComponents.CHLORINE.idPair())
            .build();
    }
    public static WeightedGraph<ComponentIdPair, Bonds> methylPropane() {
        val propane = alkaneOf(3);
        return WeightedGraphBuilder.of(propane)
            .addUndirEdge(asList(propane).get(1), Bonds.SINGLE, AtomicComponents.CARBON.idPair())
            .build();
    }
    public static WeightedGraph<ComponentIdPair, Bonds> dimethylPropane() {
        val propane = alkaneOf(3);
        return WeightedGraphBuilder.of(propane)
            .addUndirEdge(asList(propane).get(1), Bonds.SINGLE, AtomicComponents.CARBON.idPair())
            .addUndirEdge(asList(propane).get(1), Bonds.SINGLE, AtomicComponents.CARBON.idPair())
            .build();
    }
    public static WeightedGraph<ComponentIdPair, Bonds> ethylPentane() {
        val pentane = alkaneOf(5);
        ComponentIdPair ethylCentre = AtomicComponents.CARBON.idPair();
        return WeightedGraphBuilder.of(pentane)
            .addUndirEdge(asList(pentane).get(2), Bonds.SINGLE, ethylCentre)
            .addUndirEdge(ethylCentre, Bonds.SINGLE, AtomicComponents.CARBON.idPair())
            .build();
    }
    public static WeightedGraph<ComponentIdPair, Bonds> twoMethylEthylPentane() {
        val pentane = alkaneOf(5);
        ComponentIdPair ethylCentre = AtomicComponents.CARBON.idPair();
        return WeightedGraphBuilder.of(pentane)
            .addUndirEdge(asList(pentane).get(1), Bonds.SINGLE, AtomicComponents.CARBON.idPair())
            .addUndirEdge(asList(pentane).get(2), Bonds.SINGLE, ethylCentre)
            .addUndirEdge(ethylCentre, Bonds.SINGLE, AtomicComponents.CARBON.idPair())
            .build();
    }

    public static WeightedGraph<ComponentIdPair, Bonds> ethene() {
        return new WeightedGraphFactory<ComponentIdPair, Bonds>().undirected().builder()
            .addUndirEdge(AtomicComponents.CARBON.idPair(), Bonds.DOUBLE, AtomicComponents.CARBON.idPair())
            .build();
    }
    public static WeightedGraph<ComponentIdPair, Bonds> propene() {
        val ethene = ethene();
        return WeightedGraphBuilder.of(ethene)
            .addUndirEdge(getEdge(ethene), Bonds.SINGLE, AtomicComponents.CARBON.idPair())
            .build();
    }

    public static WeightedGraph<ComponentIdPair, Bonds> ethyne() {
        return new WeightedGraphFactory<ComponentIdPair, Bonds>().undirected().builder()
            .addUndirEdge(AtomicComponents.CARBON.idPair(), Bonds.TRIPLE, AtomicComponents.CARBON.idPair())
            .build();
    }
    public static WeightedGraph<ComponentIdPair, Bonds> propyne() {
        val ethyne = ethyne();
        return WeightedGraphBuilder.of(ethyne)
            .addUndirEdge(getEdge(ethyne), Bonds.TRIPLE, AtomicComponents.CARBON.idPair())
            .build();
    }

    public static List<ComponentIdPair> asList(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        return molecule.allFullyExtendedPathsFrom(getEdge(molecule)).stream().findAny().orElseThrow();
    }
    public static ComponentIdPair getEdge(WeightedGraph<ComponentIdPair, Bonds> molecule) {
        return molecule.getNodes().stream().filter(a -> molecule.getOutDegree(a) <= 1).findAny().orElseThrow();
    }
}
