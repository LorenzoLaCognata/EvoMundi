package main;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantSpecies;
import model.simulation.base.SimulationSettings;
import model.simulation.base.SimulationStatus;
import utils.Log;
import view.CenterPaneManager;
import view.Tile;
import view.TileSpecies;

import java.awt.*;
import java.util.Map;

public class Controller {

    private final Model model = new Model();
    private final View view = new View();

    private final AnimationTimer timer = new AnimationTimer() {

        private long lastUpdateTime = 0;

        @Override
        public void handle(long now) {

            if (now - lastUpdateTime >= SimulationSettings.FRAME_DURATION) {
                lastUpdateTime = now;
                run();
            }

            if (SimulationSettings.getCurrentWeek() == SimulationSettings.SIMULATION_LENGTH_WEEKS) {
                timer.stop();
            }
        }

    };

    private void addSpeciesIconsSpecies() {

        for (PlantSpecies plantSpecies : model.getSimulation().getEcosystem().getPlantSpeciesMap().values()) {
            view.getToolBarManager().addSpeciesIcons(plantSpecies);
        }

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {
            view.getToolBarManager().addSpeciesIcons(animalSpecies);
        }

    }

    private void viewSelectedSpeciesImageViews() {
        viewSelectedPlantSpeciesImageViews();
        viewSelectedAnimalSpeciesImageViews();
    }

    private void viewSelectedAnimalSpeciesImageViews() {

        CenterPaneManager centerPaneManager = view.getCenterPaneManager();

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {

            if (!animalSpecies.getToolbarSection().getCheckBox().isSelected()) {
                for (Map.Entry<Point, Tile> entry : model.getSimulation().getEcosystem().getWorldMap().entrySet()) {
                    Tile tile = entry.getValue();

                    for (Map.Entry<AnimalSpecies, TileSpecies> tileSpeciesEntry : tile.animalTileSpecies().entrySet()) {
                        centerPaneManager.removeCenterPaneGroup(tileSpeciesEntry.getValue().organismImages());
                    }

                }
            }

            else {

                for (Map.Entry<Point, Tile> entry : model.getSimulation().getEcosystem().getWorldMap().entrySet()) {
                    Tile tile = entry.getValue();

                    for (Map.Entry<AnimalSpecies, TileSpecies> tileSpeciesEntry : tile.animalTileSpecies().entrySet()) {

                        if (centerPaneManager.groupMissingFromCenterPane(tileSpeciesEntry.getValue().organismImages())) {
                            centerPaneManager.addCenterPaneGroup(tileSpeciesEntry.getValue().organismImages());
                        }
                    }

                }

            }

        }
    }

    private void viewSelectedPlantSpeciesImageViews() {

        CenterPaneManager centerPaneManager = view.getCenterPaneManager();

        for (PlantSpecies plantSpecies : model.getSimulation().getEcosystem().getPlantSpeciesMap().values()) {

            if (!plantSpecies.getToolbarSection().getCheckBox().isSelected()) {
                for (Map.Entry<Point, Tile> entry : model.getSimulation().getEcosystem().getWorldMap().entrySet()) {
                    Tile tile = entry.getValue();

                    for (Map.Entry<PlantSpecies, TileSpecies> tileSpeciesEntry : tile.plantTileSpecies().entrySet()) {
                        centerPaneManager.removeCenterPaneGroup(tileSpeciesEntry.getValue().organismImages());
                    }

                }
            }
            else {
                for (Map.Entry<Point, Tile> entry : model.getSimulation().getEcosystem().getWorldMap().entrySet()) {
                    Tile tile = entry.getValue();

                    for (Map.Entry<PlantSpecies, TileSpecies> tileSpeciesEntry : tile.plantTileSpecies().entrySet()) {
                        if (centerPaneManager.groupMissingFromCenterPane(tileSpeciesEntry.getValue().organismImages())) {
                            centerPaneManager.removeCenterPaneGroup(tileSpeciesEntry.getValue().organismImages());
                        }
                    }

                }
            }

        }
    }

    public Controller() {
        Log.initializeLog();
        view.getToolBarManager().initializeToolBar();
        view.getToolBarManager().setButtonStartStop(this::handleStartStopButton);
        addSpeciesIconsSpecies();
    }

    private void handleStartStopButton() {

        if (SimulationSettings.getSimulationStatus() == SimulationStatus.RUNNING) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            timer.stop();
        }
        else if (SimulationSettings.getSimulationStatus() == SimulationStatus.PAUSED) {
            SimulationSettings.setSimulationStatus(SimulationStatus.RUNNING);
            timer.start();
        }

    }

    public View getView() {
        return view;
    }

    public void updateView() {

        viewSelectedSpeciesImageViews();

        view.getToolBarManager().setLabel("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        for (PlantSpecies plantSpecies : model.getSimulation().getEcosystem().getPlantSpeciesMap().values()) {
            view.getToolBarManager().updateToolBarLabels(plantSpecies);
        }

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {
            view.getToolBarManager().updateToolBarLabels(animalSpecies);
        }

    }

    public void run() {
        model.getSimulation().getAnimalMovementSimulation().ecosystemMove(model.getSimulation().getEcosystem());
        model.getSimulation().simulate();
        Platform.runLater(this::updateView);
    }

}