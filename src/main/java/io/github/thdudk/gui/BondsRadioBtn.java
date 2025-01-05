package io.github.thdudk.gui;

import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import javafx.scene.control.RadioButton;
import lombok.Getter;

@Getter
public class BondsRadioBtn extends RadioButton {
    private final Bonds component;

    public BondsRadioBtn(Bonds component) {
        super(component.displayName);
        this.component = component;
    }
}
