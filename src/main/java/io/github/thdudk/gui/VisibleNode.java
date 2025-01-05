package io.github.thdudk.gui;

import com.sun.javafx.geom.Vec2d;
import io.github.thdudk.components.AtomicComponents;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.*;

public class VisibleNode extends StackPane {
    public static final double TARGET_DIST = 35, SPEED = 5;

    @Getter
    private Vector2D pos;
    private AtomicComponents component;
    private final Circle circle = new Circle();
    private final Text symbol;
    private final List<Vector2D> forces = new ArrayList<>();

    public VisibleNode(Vector2D pos, AtomicComponents component) {
        super();
        this.pos = pos;

        this.component = component;
        symbol = new Text(component.IUPACSymbol);
        symbol.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        symbol.setTranslateY(-0.5); // move text up a little to align with circle
        symbol.setFill(Color.WHITE);

        circle.setRadius(10);
        circle.setFill(Color.BLACK);
        getChildren().add(circle);
        getChildren().add(symbol);
        updatePos();

        setTranslateX(-circle.getRadius());
        setTranslateY(-circle.getRadius());

    }

    public void applySelfForces() {
        Vector2D net = forces.stream().reduce(Vector2D::add).orElse(Vector2D.ZERO);
        forces.clear();

        pos = pos.add(net);
        updatePos();
    }
    private void updatePos() {
        // update the display position
        setLayoutX(pos.getX());
        setLayoutY(pos.getY());
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
