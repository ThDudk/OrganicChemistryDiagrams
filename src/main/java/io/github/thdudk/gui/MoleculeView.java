package io.github.thdudk.gui;

import com.sun.javafx.geom.Edge;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.construction.builders.WeightedGraphBuilder;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.iterators.DepthFirstIterator;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.val;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MoleculeView extends Parent {
    @Getter
    private WeightedGraphBuilder<ComponentIdPair, Bonds> molecule;
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
    public MoleculeView(double x, double y, WeightedGraph<ComponentIdPair, Bonds> graph) {
        // update pos
        setLayoutX(x);
        setLayoutY(y);

        molecule = WeightedGraphBuilder.of();

        // adds the given graph to the builder (done using this.add() to generate the visuals)
        ComponentIdPair root = graph.getNodes().stream().filter(n -> n.getComponent().equals(AtomicComponents.CARBON)).findAny().orElseThrow();
        val iterator = new DepthFirstIterator<>(graph, root);

        addRoot(iterator.next());
        while(iterator.hasNext()) {
            ComponentIdPair next = iterator.next();
            ComponentIdPair parent = iterator.getParent(next);

            add(next, graph.getEdgeBetween(next, iterator.getParent(next)).orElseThrow(), iterator.getParent(next));
        }
    }

    public void addRoot() {
        addRoot(AtomicComponents.CARBON.idPair());
    }
    public void addRoot(ComponentIdPair root) {
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
        if(!component.equals(AtomicComponents.CARBON)) bond = Bonds.SINGLE; // prevent the user from adding other components with a double bond

        ComponentIdPair added = component.idPair();
        add(added, bond, root);
    }
    public void add(ComponentIdPair component, Bonds bond, ComponentIdPair root) {
        if(molecule.build().getOutDegree(root) == 4) return;

        // this is done before the node is added bc it will complicate getVectorAwayFromNeighbours(root)
        VisibleNode newNode = new VisibleNode(visibleNodes.get(root).getPos().add(getVectorAwayFromNeighbours(root)), component.getComponent());

        molecule.addUndirEdge(root, bond, component);

        // add visible node
        visibleNodes.put(component, newNode);
        if(component.getComponent().equals(AtomicComponents.CARBON)) // only allow nodes to be added to carbons
            newNode.setOnMouseClicked(_ -> add(App.getSelectedComponentType(), App.getSelectedBondType(), component));
        getChildren().add(newNode);

        // add visible edge
        VisibleEdge edge = new VisibleEdge(visibleNodes.get(root), visibleNodes.get(component), bond);
        getChildren().addFirst(edge);
        visibleEdges.add(edge);
    }

    public void clear() {
        getChildren().clear();
        visibleEdges.clear();
        visibleNodes.clear();
        molecule = WeightedGraphBuilder.of();
        addRoot();
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
