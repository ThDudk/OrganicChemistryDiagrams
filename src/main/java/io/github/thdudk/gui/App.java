package io.github.thdudk.gui;
import io.github.thdudk.analysis.MoleculeNamer;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.val;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App extends Application {
    private static VBox componentType, bondType;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // set the scene
        Group root = new Group();
        Scene scene = new Scene(root);

        // create molecule name text
        Text moleculeName = new Text();
        moleculeName.setText("Methane");
        moleculeName.setFont(Font.font("Verdana", 20));
        moleculeName.setX(50);
        moleculeName.setY(50);

        // create the component type radio
        componentType = new VBox();
        componentType.setLayoutX(50);
        componentType.setLayoutY(75);

        Label label = new Label("Component type:");
        componentType.getChildren().add(label);

        ToggleGroup componentsGroup = new ToggleGroup();
        for(AtomicComponents component : AtomicComponents.values()) {
            val radioBtn = new ComponentRadioBtn(component);
            if(component.equals(AtomicComponents.CARBON)) radioBtn.setSelected(true); // make carbon selected by default
            radioBtn.setToggleGroup(componentsGroup);
            componentType.getChildren().add(radioBtn);
        }

        // create the bond type radio
        bondType = new VBox();
        bondType.setLayoutX(200);
        bondType.setLayoutY(75);

        Label bondTypeLabel = new Label("Bond type:");
        bondType.getChildren().add(bondTypeLabel);

        ToggleGroup bondsGroup = new ToggleGroup();
        for(Bonds component : Bonds.values()) {
            val radioBtn = new BondsRadioBtn(component);
            if(component.equals(Bonds.SINGLE)) radioBtn.setSelected(true); // make single bonds selected by default
            radioBtn.setToggleGroup(bondsGroup);
            bondType.getChildren().add(radioBtn);
        }

        // create molecule diagram
        MoleculeView moleculeViewer = new MoleculeView(700, 400);

        // add listeners to recenter the molecule viewer when the window is resized
        stage.widthProperty().addListener((obs, oldVal, newVal) ->
            moleculeViewer.setLayoutX(newVal.doubleValue() / 2)
        );
        stage.heightProperty().addListener((obs, oldVal, newVal) ->
            moleculeViewer.setLayoutY(newVal.doubleValue() / 2)
        );

        // add clear button
        Button clear = new Button();
        clear.setText("Clear");
        clear.setLayoutX(50);
        clear.setLayoutY(100);
        clear.setOnMouseClicked(_ -> {
            root.getChildren().remove(moleculeViewer);
            root.getChildren().add(new MoleculeView(700, 400));
        });

        // add executor to refresh the molecule's name 10 times a second
        ScheduledExecutorService refreshName = Executors.newSingleThreadScheduledExecutor();
        refreshName.scheduleAtFixedRate(() -> {
            try {
                moleculeName.setText(new MoleculeNamer(moleculeViewer.getMolecule().build()).getExplicitName());
            } catch(RuntimeException ex) {
                moleculeName.setText("Invalid Molecule");
                throw ex;
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        root.getChildren().add(moleculeName);
        root.getChildren().add(moleculeViewer);
        root.getChildren().add(componentType);
        root.getChildren().add(bondType);

        // set stage display settings
        stage.setTitle("Organic Chemistry Diagram Demo");
        stage.setWidth(1400);
        stage.setHeight(1000);
        stage.setScene(scene);
        stage.show();

        // end the program when the stage is closed
        stage.setOnCloseRequest(_ -> {
            refreshName.close();
            System.exit(0);
        });
    }

    public static AtomicComponents getSelectedComponentType() {
        for(Node child : componentType.getChildren())
            if(child instanceof ComponentRadioBtn btn && btn.isSelected())
                return btn.getComponent();

        throw new RuntimeException("No component type selected"); // should be unreachable
    }
    public static Bonds getSelectedBondType() {
        for(Node child : bondType.getChildren())
            if(child instanceof BondsRadioBtn btn && btn.isSelected())
                return btn.getComponent();

        throw new RuntimeException("No bond type selected"); // should be unreachable
    }
}
