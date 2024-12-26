package io.github.thdudk;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.construction.GraphFactory;
import io.github.thdudk.construction.builders.GraphBuilder;
import io.github.thdudk.construction.builders.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

/// Enum of example molecules used for testing. Will be replaced by files in a later version, so readability is not a focus
///
/// The way molecules are made is kind of a mess here (so don't worry about it)
@RequiredArgsConstructor
public enum molecules {
    METHANE(new GraphFactory<ComponentIdPair>().undirected().builder()
        .addNode(AtomicComponents.CARBON.idPair()).build()),
    ETHANE(new GraphFactory<ComponentIdPair>().undirected().builder()
        .addUndirNeighborChain(List.of(
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair())).build()),
    PROPANE(new GraphFactory<ComponentIdPair>().undirected().builder()
        .addUndirNeighborChain(List.of(
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair())).build()),
    PENTANE(new GraphFactory<ComponentIdPair>().undirected().builder()
        .addUndirNeighborChain(List.of(
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair())).build()),

    METHYLPROPANE(GraphBuilder.of(PROPANE.molecule)
        .addNeighbor(PROPANE.molecule.findNode(a -> PROPANE.molecule.getOutNeighbours(a).size() == 2).get(),
            AtomicComponents.CARBON.idPair()).build()),
    ONECHLOROPROPANE(GraphBuilder.of(PROPANE.molecule)
        .addNeighbor(PROPANE.molecule.findNode(a -> PROPANE.molecule.getOutNeighbours(a).size() == 1).get(),
            AtomicComponents.CHLORINE.idPair()).build()),
    ETHYLPENTANE(GraphBuilder.of(PENTANE.molecule)
        .addUndirNeighborChain(List.of(
            PENTANE.molecule.allFullyExtendedPathsFrom(PENTANE.getEdge()).stream().findAny().orElseThrow().get(2),
            AtomicComponents.CARBON.idPair(),
            AtomicComponents.CARBON.idPair()
        )).build()),

    DIMETHYLPROPANE(GraphBuilder.of(PROPANE.molecule) // add root
        .addNeighbor(PROPANE.molecule.findNode(a -> PROPANE.molecule.getOutNeighbours(a).size() == 2).get(),
            AtomicComponents.CARBON.idPair()) // add methyl
        .addNeighbor(PROPANE.molecule.findNode(a -> PROPANE.molecule.getOutNeighbours(a).size() == 2).get(),
            AtomicComponents.CARBON.idPair()).build()), // add methyl
    TWOMETHYLETHYLPENTANE(GraphBuilder.of(ETHYLPENTANE.molecule)
        .addNeighbor(
            PENTANE.molecule.allFullyExtendedPathsFrom(PENTANE.getEdge()).stream().findAny().orElseThrow().get(1),
            AtomicComponents.CARBON.idPair()
        ).build());

    public final Graph<ComponentIdPair> molecule;
    public final ComponentIdPair getEdge() {
        return molecule.findNode(a -> molecule.getOutNeighbours(a).size() == 1).orElseThrow();
    }
}
