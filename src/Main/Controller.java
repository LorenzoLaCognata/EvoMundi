package Main;

import Model.Animals.Organism;
import Model.Animals.Species;
import Model.Enums.SimulationStatus;
import Model.Enums.SpeciesType;
import Model.Simulation.SimulationSettings;
import Utils.Log;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;

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
        }

    };

    private void addImageViews() {

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {

            for (int i = 0; i < species.getOrganisms().size(); i++) {

                Organism organism = species.getOrganisms().get(i);

                if (true || organism.isImpersonatedOrganism()) {
                    species.getImageGroup().getChildren().add(organism.getImageView());
                }

            }

            view.getCenterRegion().getChildren().addAll(species.getImageGroup());
        }

    }

    private void clearDeadOrganismImageViews() {

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {

            for (int i = 0; i < species.getDeadOrganisms().size(); i++) {

                Organism deadOrganism = species.getDeadOrganisms().get(i);

                species.getImageGroup().getChildren().removeAll(deadOrganism.getImageView());

            }

        }

    }

    public Controller() {
        view.setButtonStartStop(this::handleStartStopButton);
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
            addImageViews();
        }

        clearDeadOrganismImageViews();

        view.setWeekLabel("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {

            String string = " ".repeat(7 - Log.formatNumber(species.getPopulation()).length()) + Log.formatNumber(species.getPopulation());

            if (species.getSpeciesType() == SpeciesType.WHITE_TAILED_DEER) {
                view.setPopulationLabel1(string);
            }
            else if (species.getSpeciesType() == SpeciesType.MOOSE) {
                view.setPopulationLabel2(string);
            }
            else if (species.getSpeciesType() == SpeciesType.GRAY_WOLF) {
                view.setPopulationLabel3(string);
            }
            else if (species.getSpeciesType() == SpeciesType.SNOWSHOE_HARE) {
                view.setPopulationLabel5(string);
            }
            else if (species.getSpeciesType() == SpeciesType.EUROPEAN_BEAVER) {
                view.setPopulationLabel4(string);
            }
            else if (species.getSpeciesType() == SpeciesType.BOBCAT) {
                view.setPopulationLabel6(string);
            }
        }

    }

    public void run() {

        model.getSimulation().simulate();
        Platform.runLater(this::updateView);

    }

}
