package Main;

import Model.Simulation.Simulation;

public class Model {

    private final Simulation simulation = new Simulation();

    public Model() {
        simulation.initialize();
    }

    public Simulation getSimulation() {
        return simulation;
    }

}
