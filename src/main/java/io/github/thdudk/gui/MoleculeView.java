package io.github.thdudk.gui;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.construction.builders.WeightedGraphBuilder;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MoleculeView extends Parent {
    @Getter
    private final WeightedGraphBuilder<ComponentIdPair, Bonds> molecule;
    private final Map<ComponentIdPair, VisibleNode> visibleNodes = new HashMap<>();
    private final List<VisibleEdge> visibleEdges = new ArrayList<>();
    private static final double dist = 40;

    ScheduledExecutorService nodeAnimation = Executors.newSingleThreadScheduledExecutor();

    public MoleculeView(double x, double y) {
        // update pos
        setLayoutX(x);
        setLayoutY(y);

        molecule = WeightedGraphBuilder.of();

        addRoot();
    }

    public void addRoot() {
        ComponentIdPair root = AtomicComponents.CARBON.idPair();
        molecule.addNode(root);

        VisibleNode rootNode = new VisibleNode(Vector2D.ZERO, AtomicComponents.CARBON);
        getChildren().add(rootNode); // adds to the hierarchy
        visibleNodes.put(root, rootNode); // adds to the map of visible nodes
        rootNode.setOnMouseClicked(_ -> add(App.getSelectedComponentType(), App.getSelectedBondType(), root)); // adds a new node when clicked

        // repeats the animation every 50 ms
        nodeAnimation.scheduleAtFixedRate(() -> {
            // update visibleNodes
            Collection<VisibleNode> nodes = visibleNodes.values();
            for(VisibleNode node : nodes) node.applyForces(nodes);
            for(VisibleEdge edge : visibleEdges) edge.applyForces();
            for(VisibleNode node : nodes) node.applySelfForces();

            // update visibleEdges
            for(VisibleEdge edge : visibleEdges)
                edge.update();
        }, 0, 50, TimeUnit.MILLISECONDS);
    }
    public void add(AtomicComponents component, Bonds bond, ComponentIdPair root) {
        if(molecule.build().getOutDegree(root) == 4) return;
        if(!component.equals(AtomicComponents.CARBON)) bond = Bonds.SINGLE; // prevent the user from adding other components with a double bond

        // this is done before the node is added bc it will complicate getVectorAwayFromNeighbours(root)
        VisibleNode newNode = new VisibleNode(visibleNodes.get(root).getPos().add(getVectorAwayFromNeighbours(root)), component);

        ComponentIdPair added = component.idPair();
        molecule.addUndirEdge(root, bond, added);

        // add visible node
        visibleNodes.put(added, newNode);
        if(added.getComponent().equals(AtomicComponents.CARBON)) // only allow nodes to be added to carbons
            newNode.setOnMouseClicked(_ -> add(App.getSelectedComponentType(), App.getSelectedBondType(), added));
        getChildren().add(newNode);

        // add visible edge
        VisibleEdge edge = new VisibleEdge(visibleNodes.get(root), visibleNodes.get(added), bond);
        getChildren().addFirst(edge);
        visibleEdges.add(edge);
    }

    private Vector2D getVectorAwayFromNeighbours(ComponentIdPair root) {
        if(molecule.build().getOutDegree(root) == 0) // 1 including the neighbour just added
            return new Vector2D(1, 0);

        List<Vector2D> directions = new ArrayList<>();
        for(ComponentIdPair neighbour : molecule.build().getOutNeighbours(root)) {
            Vector2D direction = visibleNodes.get(neighbour).getPos().subtract(visibleNodes.get(root).getPos());
            if(direction.equals(Vector2D.ZERO)) continue;
            directions.add(direction.normalize());
        }

        Vector2D avgDirection = directions.stream().reduce(Vector2D::add).orElse(Vector2D.ZERO)
            .scalarMultiply(1d / molecule.build().getOutDegree(root));

        if(avgDirection.equals(Vector2D.ZERO)) return new Vector2D(0, 1);

        return avgDirection.scalarMultiply(-1);
    }
}
