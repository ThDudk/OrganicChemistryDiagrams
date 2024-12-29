package io.github.thdudk.gui;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class VisibleNode extends Circle {
    private static final double TARGET_DIST = 35, SPEED = 5;

    @Getter
    private Vector2D pos;
    private final List<Vector2D> forces = new ArrayList<>();

    public VisibleNode(Vector2D pos) {
        super();
        this.pos = pos;

        setRadius(10);
        setFill(Color.BLACK);
        updatePos();
    }

    public void applySelfForces() {
        Vector2D net = forces.stream().reduce(Vector2D::add).orElse(Vector2D.ZERO);
        forces.clear();

        pos = pos.add(net);
        updatePos();
    }
    private void updatePos() {
        // update the display position
        setCenterX(pos.getX());
        setCenterY(pos.getY());
    }
    public void addForce(Vector2D force) {
        forces.add(force);
    }
    public void applyForces(Collection<VisibleNode> others) {
        for(VisibleNode other : others) {
            if(other.pos.equals(pos)) continue;
            if(other.pos.distance(pos) > TARGET_DIST) continue;

            // add a force away from this
            other.addForce(other.pos.subtract(pos).normalize().scalarMultiply(getForceMagnitude(other)));
        }
    }
    public double getForceMagnitude(VisibleNode other) {
        return SPEED * (1 - Math.pow(other.pos.distance(pos) / TARGET_DIST, 3));
    }
}
