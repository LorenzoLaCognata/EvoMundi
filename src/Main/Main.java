package Main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Controller controller = new Controller();

        Button button = new Button();
        button.setText("Start the Simulation");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Thread(() -> controller.run()).start();
            }
        });

        Group group = new Group(controller.getView().getGridPane(), button);

        Scene scene = new Scene(group, 1000, 500, Color.WHITE);

        stage.setTitle("EvoMundi");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

}