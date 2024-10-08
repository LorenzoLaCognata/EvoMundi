package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.simulation.base.SimulationSettings;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Controller controller = new Controller();

        Scene scene = new Scene(controller.getView().getBorderPane(), SimulationSettings.SCENE_WIDTH, SimulationSettings.SCENE_HEIGHT);
        stage.setTitle("EvoMundi");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

}