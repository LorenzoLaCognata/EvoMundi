package main;

import model.simulation.Simulation;

public class Model {

    private final Simulation simulation;

    public Model() {
        simulation = new Simulation();
    }

    public Simulation getSimulation() {
        return simulation;
    }

}
