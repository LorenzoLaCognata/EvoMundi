package model.simulation.base;

import model.environment.animals.enums.Gender;
import model.environment.common.enums.TaxonomySpecies;

public class SimulationSettings {

    public static final int SIMULATION_SPEED_WEEKS = 1;
    public static final int SIMULATION_LENGTH_WEEKS = 52;
    public static final double DEGREES_PER_TILE = 0.25;
    public static final double PIXELS_PER_TILE = 64;
    public static final double IMAGE_SIZE = 64;
    public static final double MOVEMENT_SPEED_PER_FRAME = DEGREES_PER_TILE / PIXELS_PER_TILE;

    public static final int TARGET_FPS = 30;
    public static final long FRAME_DURATION = 1_000_000_000 / TARGET_FPS;
    private static int currentWeek = -1;

    private static SimulationStatus simulationStatus = SimulationStatus.PAUSED;

    public static final double SCENE_WIDTH = 1536;
    public static final double SCENE_HEIGHT = 1024;
    public static final double MIN_LATITUDE = 50.0;
    public static final double MAX_LATITUDE = 54.0;
    public static final double MIN_LONGITUDE = -104.0;
    public static final double MAX_LONGITUDE = -98.0;


    private static final TaxonomySpecies impersonatingTaxonomySpecies = TaxonomySpecies.ODOCOILEUS_VIRGINIANUS;
    private static final Gender impersonatingGender = Gender.MALE;

    private SimulationSettings() {
    }

    public static int getCurrentWeek() {
        return currentWeek;
    }

    public static SimulationStatus getSimulationStatus() {
        return simulationStatus;
    }

    public static TaxonomySpecies getImpersonatingTaxonomySpecies() {
        return impersonatingTaxonomySpecies;
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
