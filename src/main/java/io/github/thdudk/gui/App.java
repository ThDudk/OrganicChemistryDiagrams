package io.github.thdudk.gui;
import io.github.thdudk.analysis.AlkaneNamer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // set the scene
        Group root = new Group();
        Scene scene = new Scene(root);

        MoleculeView moleculeViewer = new MoleculeView(200, 200);

        Text moleculeName = new Text();
        moleculeName.setText("Methane");
        moleculeName.setFont(Font.font("Verdana", 20));
        moleculeName.setX(50);
        moleculeName.setY(100);

        Button refreshMoleculeName = new Button();
        refreshMoleculeName.setLayoutX(50);
        refreshMoleculeName.setLayoutY(50);
        refreshMoleculeName.setText("refresh");
        refreshMoleculeName.setOnMouseClicked(_ -> {
            try {
                moleculeName.setText(new AlkaneNamer(moleculeViewer.getMolecule().build()).getName());
            } catch(RuntimeException ex) {
                moleculeName.setText("Invalid Molecule");
                throw ex;
            }
        });

        root.getChildren().add(refreshMoleculeName);
        root.getChildren().add(moleculeName);
        root.getChildren().add(moleculeViewer);

        stage.setTitle("Organic Chemistry Diagram Demo");
        stage.setWidth(800);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.show();

        // end the program when the stage is closed
        stage.setOnCloseRequest(_ -> {
            System.exit(0);
        });
    }
}
