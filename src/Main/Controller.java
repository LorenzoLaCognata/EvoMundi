package Main;

import Model.Entities.Species;
import Model.Enums.SimulationStatus;
import Model.Enums.SpeciesType;
import Model.Simulation.Simulation;
import Model.Simulation.SimulationSettings;
import Utils.Logger;
import javafx.application.Platform;

public class Controller {

    private final View view = new View();
    private final Simulation simulation = new Simulation();

    public Controller() {
        new Thread(() -> this.run()).start();
    }

    public View getView() {
        return view;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void updateView() {

        view.setWeekLabel("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        for (Species species : simulation.getEcosystem().getSpeciesMap().values()) {

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

        simulation.initialize();

        int week = 0;

        while (SimulationSettings.getSimulationStatus() == SimulationStatus.RUNNING && week < SimulationSettings.getSimulationLengthWeeks()) {

            simulation.simulate();
            Platform.runLater(() -> updateView());
            week = week +1;

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
