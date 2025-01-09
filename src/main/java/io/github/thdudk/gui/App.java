package io.github.thdudk.gui;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.analysis.MoleculeNamer;
import io.github.thdudk.components.AtomicComponents;
import io.github.thdudk.components.Bonds;
import io.github.thdudk.components.ComponentIdPair;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import javafx.application.Application;
import javafx.geometry.Pos;
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
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class App extends Application {
    private static VBox componentType, bondType;
    private static Optional<WeightedGraph<ComponentIdPair, Bonds>> launchGraph = Optional.empty();

    public static void main(String[] args) {
        launch(args);
    }

    public static void launchGraph(WeightedGraph<ComponentIdPair, Bonds> graph) {
        launchGraph = Optional.of(graph);
        launch();
    }

    @Override
    public void start(Stage stage) {
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
        MoleculeView moleculeViewer;
        moleculeViewer = launchGraph.map(graph -> new MoleculeView(700, 400, graph))
            .orElseGet(() -> new MoleculeView(700, 400));

        VBox bottomRightVBox = new VBox();
        final Vector2D bottomRightVBoxOffset = new Vector2D(130, 100);
        bottomRightVBox.setAlignment(Pos.BOTTOM_RIGHT);

        // add save button
        Button save = new Button();
        save.setText("Save Molecule");
        save.setOnMouseClicked(_ -> {
            // - save the molecule -
            ObjectMapper mapper = new ObjectMapper();

            try{
                File output = new File("./src/main/resources/molecules/" + moleculeName.getText());
                output.createNewFile();
                mapper.writer().writeValue(output, moleculeViewer.getMolecule().build());
            } catch (IOException e) {throw new RuntimeException(e);}
            System.exit(0);
        });

        // add clear button
        Button clear = new Button();
        clear.setText("Clear");
        clear.setOnMouseClicked(_ -> {
            // clear the graph
            moleculeViewer.clear();
        });

        bottomRightVBox.getChildren().add(save);
        bottomRightVBox.getChildren().add(clear);

        // add listeners to recenter the molecule viewer when the window is resized
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            moleculeViewer.setLayoutX(newVal.doubleValue() / 2);
            bottomRightVBox.setLayoutX(newVal.doubleValue() - bottomRightVBoxOffset.getX());
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            moleculeViewer.setLayoutY(newVal.doubleValue() / 2);
            bottomRightVBox.setLayoutY(newVal.doubleValue() - bottomRightVBoxOffset.getY());
        });

        // add executor to refresh the molecule's name 10 times a second
        ScheduledExecutorService refreshName = Executors.newSingleThreadScheduledExecutor();
        refreshName.scheduleAtFixedRate(() -> {
            try {
                moleculeName.setText(new MoleculeNamer(moleculeViewer.getMolecule().build()).getExplicitName());
            } catch(RuntimeException ex) {
                moleculeName.setText("Invalid Molecule");
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        root.getChildren().add(moleculeName);
        root.getChildren().add(bottomRightVBox);
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
