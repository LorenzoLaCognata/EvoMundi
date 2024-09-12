package Main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Controller controller = new Controller();

        Scene scene = new Scene(controller.getView().getBorderPane(), 800, 600);
        stage.setTitle("EvoMundi");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

}