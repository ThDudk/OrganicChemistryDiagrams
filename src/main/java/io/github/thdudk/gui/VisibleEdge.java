package io.github.thdudk.gui;

import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.RequiredArgsConstructor;

public class VisibleEdge extends Line {
    private final VisibleNode start, end;
    private final Bonds bond;

    public VisibleEdge(VisibleNode start, VisibleNode end, Bonds bond) {
        this.start = start;
        this.end = end;
        this.bond = bond;
        setStrokeWidth(5);
    }

    public void update() {
        setStartX(start.getPos().getX());
        setStartY(start.getPos().getY());
        setEndX(end.getPos().getX());
        setEndY(end.getPos().getY());
    }
}
