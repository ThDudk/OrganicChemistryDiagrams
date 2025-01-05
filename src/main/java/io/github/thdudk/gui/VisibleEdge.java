package io.github.thdudk.gui;

import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
public class VisibleEdge extends Parent {
    private final VisibleNode start, end;
    private final Bonds bond;
    public static final double PULL_STRENGTH = 2, BOND_LINE_DIST = 0.8;
    private final List<Line> lines = new ArrayList<>();

    public VisibleEdge(VisibleNode start, VisibleNode end, Bonds bond) {
        this.start = start;
        this.end = end;
        this.bond = bond;

        for(int i = 0; i < bond.number; i++) {
            Line line = new Line();
            line.setStrokeWidth(3);
            setMouseTransparent(true); // stop edges from consuming mouse clicks

            line.setStartX(start.getPos().getX());
            line.setStartY(start.getPos().getY());
            line.setEndX(end.getPos().getX());
            line.setEndY(end.getPos().getY());

            getChildren().add(line);
            lines.add(line);
        }
    }

    public void update() {
        Vector2D startToEnd = end.getPos().subtract(start.getPos()).normalize();
        Vector2D offset = new Vector2D(-startToEnd.getY(), startToEnd.getX()).scalarMultiply(BOND_LINE_DIST * Math.pow(bond.number, 2)); // normal of the line

        for(Line line : lines) {
            line.setStartX(start.getPos().getX());
            line.setStartY(start.getPos().getY());
            line.setEndX(end.getPos().getX());
            line.setEndY(end.getPos().getY());
        }

        switch(bond){
            case Bonds.DOUBLE -> {
                Line first = lines.getFirst();
                first.setStartX(first.getStartX() + offset.getX());
                first.setStartY(first.getStartY() + offset.getY());
                first.setEndX(first.getEndX() + offset.getX());
                first.setEndY(first.getEndY() + offset.getY());
                Line second = lines.getLast();
                second.setStartX(second.getStartX() - offset.getX());
                second.setStartY(second.getStartY() - offset.getY());
                second.setEndX(second.getEndX() - offset.getX());
                second.setEndY(second.getEndY() - offset.getY());
            }
            case Bonds.TRIPLE -> {
                Line first = lines.getFirst();
                first.setStartX(first.getStartX() + offset.getX());
                first.setStartY(first.getStartY() + offset.getY());
                first.setEndX(first.getEndX() + offset.getX());
                first.setEndY(first.getEndY() + offset.getY());
                Line second = lines.get(1);
                second.setStartX(second.getStartX());
                second.setStartY(second.getStartY());
                second.setEndX(second.getEndX());
                second.setEndY(second.getEndY());
                Line third = lines.getLast();
                third.setStartX(third.getStartX() - offset.getX());
                third.setStartY(third.getStartY() - offset.getY());
                third.setEndX(third.getEndX() - offset.getX());
                third.setEndY(third.getEndY() - offset.getY());
            }
        }
    }

    public void applyForces() {
        if(start.getPos().distance(end.getPos()) < VisibleNode.TARGET_DIST) return;

        start.addForce(end.getPos().subtract(start.getPos()).normalize().scalarMultiply(getForceMagnitude()));
        end.addForce(start.getPos().subtract(end.getPos()).normalize().scalarMultiply(getForceMagnitude()));
    }
    public double getForceMagnitude() {
        return PULL_STRENGTH * (1 - Math.pow(VisibleNode.TARGET_DIST / end.getPos().distance(start.getPos()), 3));
    }
}
