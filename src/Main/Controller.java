package Main;

import Model.Entities.Species;
import Model.Enums.SimulationStatus;
import Model.Enums.SpeciesType;
import Model.Simulation.SimulationSettings;
import Utils.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Controller {

    private final View view = new View();
    private final Model model = new Model();
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            run();
        }
    };

    public Controller() {
        view.setButtonStartStop(() -> handleStartStopButton());
    }

    private void handleStartStopButton() {

        if (SimulationSettings.getSimulationStatus() == SimulationStatus.RUNNING) {
            timer.stop();
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
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

        view.setImageViewOrganismX(view.getImageViewOrganism().getX() + 1.0);
        view.setImageViewOrganismY(view.getImageViewOrganism().getY() + 1.0);

        view.setWeekLabel("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        for (Species species : model.getSimulation().getEcosystem().getSpeciesMap().values()) {

            String string = " ".repeat(7 - Logger.formatNumber(species.getPopulation()).length()) + Logger.formatNumber(species.getPopulation());

            if (species.getSpeciesType() == SpeciesType.WHITE_TAILED_DEER) {
                view.setPopulationLabel1(string);
            }
            else if (species.getSpeciesType() == SpeciesType.MOOSE) {
                view.setPopulationLabel2(string);
            }
            else if (species.getSpeciesType() == SpeciesType.GRAY_WOLF) {
                view.setPopulationLabel3(string);
            }
            else if (species.getSpeciesType() == SpeciesType.EUROPEAN_BEAVER) {
                view.setPopulationLabel4(string);
            }
            else if (species.getSpeciesType() == SpeciesType.SNOWSHOE_HARE) {
                view.setPopulationLabel5(string);
            }
        }

    }

    public void run() {

        model.getSimulation().simulate();
        Platform.runLater(() -> updateView());

    }

}