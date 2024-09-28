package main;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.simulation.SimulationStatus;
import model.simulation.SimulationSettings;

public class Controller {

    private final View view = new View();
    private final Model model = new Model();
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

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {
            view.addSpeciesIcons(animalSpecies);
        }

    }

    private void addOrganismImagesSpecies() {

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {
            view.addOrganismImages(animalSpecies);
        }

    }

    private void removeDeadOrganismImageViews() {

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {

            for (int i = 0; i < animalSpecies.getDeadOrganisms().size(); i++) {

                AnimalOrganism deadAnimalOrganism = animalSpecies.getDeadOrganisms().get(i);

                animalSpecies.getImageGroup().getChildren().removeAll(deadAnimalOrganism.getImageView());

            }

        }

    }

    private void viewSelectedSpeciesImageViews() {

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {

            for (int i = 0; i < animalSpecies.getDeadOrganisms().size(); i++) {

                if (!animalSpecies.getToolbarSection().getCheckBox().isSelected()) {
                    view.removeCenterRegionGroup(animalSpecies.getImageGroup());
                }
                else if (!view.centerRegionContainsGroup(animalSpecies.getImageGroup())) {
                    view.addCenterRegionGroup(animalSpecies.getImageGroup());
                }
            }

        }

    }

    public Controller() {
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

    public Model getModel() {
        return model;
    }

    public void updateView() {

        if (SimulationSettings.getCurrentWeek() == 0) {
            addOrganismImagesSpecies();
        }

        removeDeadOrganismImageViews();
        viewSelectedSpeciesImageViews();

        view.setWeekLabel("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        for (AnimalSpecies animalSpecies : model.getSimulation().getEcosystem().getAnimalSpeciesMap().values()) {
            view.updateToolBarLabels(animalSpecies);
        }

    }

    public void run() {

        model.getSimulation().simulate();
        Platform.runLater(this::updateView);

    }

}
