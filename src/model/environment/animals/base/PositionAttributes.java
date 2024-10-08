package model.environment.animals.base;

import model.simulation.base.SimulationSettings;
import view.Geography;

public class PositionAttributes {

    private double latitude;
    private double longitude;

    public PositionAttributes(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getScreenX() {
        return Geography.geographyToScreen(latitude, longitude).getX();
    }

    public double getScreenY() {
        return Geography.geographyToScreen(latitude, longitude).getY();
    }

}