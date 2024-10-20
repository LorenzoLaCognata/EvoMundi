package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class StatusBarManager {

    private final HBox statusBar = new HBox(10);

    public StatusBarManager() {
        createStatusBar();
    }

    public HBox getStatusBar() {
        return statusBar;
    }

    private void createStatusBar() {

        statusBar.setPadding(new Insets(10));
        statusBar.setStyle("-fx-background-color: #CCCCCC;");

        Label statusLabel = new Label("Status: Paused");
        statusLabel.setTextFill(Color.BLACK);

        statusBar.getChildren().add(statusLabel);

    }

}
