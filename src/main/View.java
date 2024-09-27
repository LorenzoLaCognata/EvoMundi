package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.animals.Organism;
import model.animals.Species;
import utils.Log;

public class View {

    public static final double SCENE_WIDTH = 1600;
    public static final double SCENE_HEIGHT = 500;

    private final BorderPane borderPane = new BorderPane();
    private final ToolBar toolBar = new ToolBar();
    private final Label weekLabel = new Label();

    private final Button buttonStartStop = new Button("Start the Simulation");
    private final Pane centerRegion;

    public View() {

        borderPane.setTop(toolBar);

        HBox statusBar = createStatusBar();
        borderPane.setBottom(statusBar);

        centerRegion = createCenterContent();
        borderPane.setCenter(centerRegion);

    }

    public void setButtonStartStop(Runnable handler) {
        buttonStartStop.setOnAction(e -> handler.run());
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setWeekLabel(String string) {
        weekLabel.setText(string);
    }

    public void initializeToolBar() {

        toolBar.setStyle("-fx-background-color: #1E2A38;");
        weekLabel.setTextFill(Color.WHITE);

        VBox simulationTimer = new VBox(5);
        simulationTimer.setAlignment(Pos.CENTER);
        simulationTimer.setPadding(new Insets(5));
        simulationTimer.setMinWidth(128);
        simulationTimer.getChildren().addAll(weekLabel, buttonStartStop);
        toolBar.getItems().add(simulationTimer);

    }

    private HBox createStatusBar() {

        HBox statusBar = new HBox(10);
        statusBar.setPadding(new Insets(10));
        statusBar.setStyle("-fx-background-color: #CCCCCC;");

        Label statusLabel = new Label("Status: Paused");
        statusLabel.setTextFill(Color.BLACK);

        statusBar.getChildren().add(statusLabel);

        return statusBar;
    }

    private Pane createCenterContent() {

        return new Pane();
    }

    public void addSpeciesIcons(Species species) {
        toolBar.getItems().add(species.getToolbarSection().getvBox());
    }

    public boolean centerRegionContainsGroup(Group group) {
        return  centerRegion.getChildren().contains(group);
    }

    public void addCenterRegionGroup(Group group) {
        centerRegion.getChildren().add(group);
    }

    public void removeCenterRegionGroup(Group group) {
        centerRegion.getChildren().remove(group);
    }

    public void addOrganismImages(Species species) {

        for (int i = 0; i < species.getOrganisms().size(); i++) {
            Organism organism = species.getOrganisms().get(i);
            species.getImageGroup().getChildren().add(organism.getImageView());
        }

        addCenterRegionGroup(species.getImageGroup());
    }

    public void updateToolBarLabels(Species species) {
        String populationFormatted = " ".repeat(7 - Log.formatNumber(species.getPopulation()).length()) + Log.formatNumber(species.getPopulation());
        species.getToolbarSection().setPopulation(populationFormatted);
    }

}
