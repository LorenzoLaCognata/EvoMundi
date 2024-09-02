package Model.Simulation;

import Model.Enums.Gender;
import Model.Enums.SimulationStatus;
import Model.Enums.SpeciesType;

public class SimulationSettings {

    private static final int simulationSpeedWeeks = 1;
    private static final int simulationLengthWeeks = 52;
    private static int currentWeek = -1;

    private static SimulationStatus simulationStatus = SimulationStatus.RUNNING;

    private static SpeciesType impersonatingSpeciesType; // = SpeciesType.SNOWSHOE_HARE;
    private static Gender impersonatingGender; // = Gender.FEMALE;

    public static int getCurrentWeek() {
        return currentWeek;
    }

    public static int getSimulationSpeedWeeks() {
        return simulationSpeedWeeks;
    }

    public static int getSimulationLengthWeeks() {
        return simulationLengthWeeks;
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

    public static void setImpersonatingSpeciesType(SpeciesType impersonatingSpeciesType) {
        SimulationSettings.impersonatingSpeciesType = impersonatingSpeciesType;
    }

    public static void setImpersonatingGender(Gender impersonatingGender) {
        SimulationSettings.impersonatingGender = impersonatingGender;
    }

}
