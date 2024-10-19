package main;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantSpecies;
import model.simulation.base.SimulationSettings;
import model.simulation.base.SimulationStatus;
import utils.Log;

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
            view.addSpeciesIcons(plantSpecies);
        }

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {
            view.addSpeciesIcons(animalSpecies);
        }

    }

    private void viewSelectedSpeciesImageViews() {

        for (PlantSpecies plantSpecies : model.getSimulation().getEcosystem().getPlantSpeciesMap().values()) {

            if (!plantSpecies.getToolbarSection().getCheckBox().isSelected()) {
                view.removeCenterRegionGroup(plantSpecies.getImageGroup());
            }
            else if (view.groupMissingFromCenterRegion(plantSpecies.getImageGroup())) {
                view.addCenterRegionGroup(plantSpecies.getImageGroup());
            }

        }

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {

            if (!animalSpecies.getToolbarSection().getCheckBox().isSelected()) {
                view.removeCenterRegionGroup(animalSpecies.getImageGroup());
            }
            else if (view.groupMissingFromCenterRegion(animalSpecies.getImageGroup())) {
                view.addCenterRegionGroup(animalSpecies.getImageGroup());
            }

        }

    }

    public Controller() {
        Log.initializeLog();
        view.initializeToolBar();
        view.setButtonStartStop(this::handleStartStopButton);
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

        view.setWeekLabel("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        for (PlantSpecies plantSpecies : model.getSimulation().getEcosystem().getPlantSpeciesMap().values()) {
            view.updateToolBarLabels(plantSpecies);
        }

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {
            view.updateToolBarLabels(animalSpecies);
        }

    }

    public void run() {

        model.getSimulation().getAnimalMovementSimulation().animalMove(model.getSimulation().getEcosystem());
        model.getSimulation().simulate();

        Platform.runLater(this::updateView);


    }

}
