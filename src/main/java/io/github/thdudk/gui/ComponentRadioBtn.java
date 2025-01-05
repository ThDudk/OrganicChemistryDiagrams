package io.github.thdudk.gui;

import io.github.thdudk.components.AtomicComponents;
import javafx.scene.control.RadioButton;
import lombok.Getter;

@Getter
public class ComponentRadioBtn extends RadioButton {
    private final AtomicComponents component;

    public ComponentRadioBtn(AtomicComponents component) {
        super(component.displayName);
        this.component = component;
    }
}
