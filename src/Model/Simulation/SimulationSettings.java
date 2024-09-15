package Model.Simulation;

import Model.Enums.Gender;
import Model.Enums.SimulationStatus;
import Model.Enums.SpeciesType;

public class SimulationSettings {

    public static final int SIMULATION_SPEED_WEEKS = 1;
    public static final int SIMULATION_LENGTH_WEEKS = 52;
    public static final int TARGET_FPS = 30;
    public static final long FRAME_DURATION = 1_000_000_000 / TARGET_FPS;
    private static int currentWeek = -1;

    private static SimulationStatus simulationStatus = SimulationStatus.PAUSED;

    private static final SpeciesType impersonatingSpeciesType = SpeciesType.WHITE_TAILED_DEER;
    private static final Gender impersonatingGender = Gender.FEMALE;

    private SimulationSettings() {

    }

    public static int getCurrentWeek() {
        return currentWeek;
    }

    public static SimulationStatus getSimulationStatus() {
        return simulationStatus;
    }

    public static SpeciesType getImpersonatingSpeciesType() {
        return impersonatingSpeciesType;
    }

    public static Gender getImpersonatingGender() {
        return impersonatingGender;
    }

    public static int getWeek() {
        return 1 + currentWeek % 52;
    }

    public static int getYear() {
        return 1 + currentWeek / 52;
    }

    public static void setCurrentWeek(int w) {
        currentWeek = w;
    }

    public static void setSimulationStatus(SimulationStatus simulationStatus) {
        SimulationSettings.simulationStatus = simulationStatus;
    }

}
