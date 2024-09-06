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

    public View getView() {
        return view;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void updateView() {

        view.setTextArea1("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        for (Species species : simulation.getEcosystem().getSpeciesMap().values()) {
            if (species.getSpeciesType() == SpeciesType.WHITE_TAILED_DEER) {
                view.setTextArea2(species.getCommonName() + " : " + " ".repeat(7 - Logger.formatNumber(species.getPopulation()).length()) + Logger.formatNumber(species.getPopulation()));
            }
            else if (species.getSpeciesType() == SpeciesType.MOOSE) {
                view.setTextArea3(species.getCommonName() + " : " + " ".repeat(7 - Logger.formatNumber(species.getPopulation()).length()) + Logger.formatNumber(species.getPopulation()));
            }
            else if (species.getSpeciesType() == SpeciesType.GRAY_WOLF) {
                view.setTextArea4(species.getCommonName() + " : " + " ".repeat(7 - Logger.formatNumber(species.getPopulation()).length()) + Logger.formatNumber(species.getPopulation()));
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
                Thread.sleep(100); // Simulate some delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
