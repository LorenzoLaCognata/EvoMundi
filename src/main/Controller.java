package main;

import model.animals.Organism;
import model.animals.Species;
import model.enums.SimulationStatus;
import model.simulation.SimulationSettings;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

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

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {
            view.addSpeciesIcons(species);
        }

    }

    private void addOrganismImagesSpecies() {

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {
            view.addOrganismImages(species);
        }

    }

    private void removeDeadOrganismImageViews() {

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {

            for (int i = 0; i < species.getDeadOrganisms().size(); i++) {

                Organism deadOrganism = species.getDeadOrganisms().get(i);

                species.getImageGroup().getChildren().removeAll(deadOrganism.getImageView());

            }

        }

    }

    private void viewSelectedSpeciesImageViews() {

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {

            for (int i = 0; i < species.getDeadOrganisms().size(); i++) {

                if (!species.getToolbarSection().getCheckBox().isSelected()) {
                    view.removeCenterRegionGroup(species.getImageGroup());
                }
                else if (!view.centerRegionContainsGroup(species.getImageGroup())) {
                    view.addCenterRegionGroup(species.getImageGroup());
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

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {
            view.updateToolBarLabels(species);
        }

    }

    public void run() {

        model.getSimulation().simulate();
        Platform.runLater(this::updateView);

    }

}
