package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.base.Species;
import model.environment.plants.base.PlantSpecies;
import utils.Log;

public class ToolBarManager {

    private final ToolBar toolBar = new ToolBar();
    private final Label label = new Label();
    private final Button buttonStartStop = new Button("Start the Simulation");

    public ToolBar getToolBar() {
        return toolBar;
    }

    public void initializeToolBar() {

        toolBar.setStyle("-fx-background-color: #1E2A38;");
        label.setTextFill(Color.WHITE);

        VBox simulationTimer = new VBox(5);
        simulationTimer.setAlignment(Pos.CENTER);
        simulationTimer.setPadding(new Insets(5));
        simulationTimer.setMinWidth(128);
        simulationTimer.getChildren().addAll(label, buttonStartStop);
        toolBar.getItems().add(simulationTimer);

    }

    public void setButtonStartStop(Runnable handler) {
        buttonStartStop.setOnAction(ignored -> handler.run());
    }

    public void setLabel(String string) {
        label.setText(string);
    }

    public void updateToolBarLabels(PlantSpecies plantSpecies) {
        String populationFormatted = Log.formatNumber(plantSpecies.getOrganismCount());
        plantSpecies.getToolbarSection().setValue(populationFormatted);
    }

    public void updateToolBarLabels(AnimalSpecies animalSpecies) {
        String populationFormatted = Log.formatNumber(animalSpecies.getOrganismCount());
        animalSpecies.getToolbarSection().setValue(populationFormatted);
    }

    public void addSpeciesIcons(Species species) {
        toolBar.getItems().add(species.getToolbarSection().getvBox());
    }

}
