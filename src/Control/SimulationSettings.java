package Control;

public class SimulationSettings {

    private int simulationSpeedWeeks = 1;
    private int currentWeek = -1;
    private int simulationLengthWeeks = 10;

    public int getCurrentWeek() {
        return currentWeek;
    }

    public int getSimulationSpeedWeeks() {
        return simulationSpeedWeeks;
    }

    public int getSimulationLengthWeeks() {
        return simulationLengthWeeks;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }

    public void setSimulationSpeedWeeks(int simulationSpeedWeeks) {
        this.simulationSpeedWeeks = simulationSpeedWeeks;
    }

    public void setSimulationLengthWeeks(int simulationLengthWeeks) {
        this.simulationLengthWeeks = simulationLengthWeeks;
    }

    public int getWeek() {
        return 1 + currentWeek % 52;
    }

    public int getYear() {
        return 1 + currentWeek / 52;
    }

}
