package model.environment.animals.attributes;

import model.environment.animals.base.PositionAttributes;

public class AnimalPositionAttributes extends PositionAttributes {

    private double latitudeSpeed = 0.0;
    private double longitudeSpeed = 0.0;
    private double animationStep = 0.0;

    public AnimalPositionAttributes(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public double getLongitudeSpeed() {
        return longitudeSpeed;
    }

    public void setLongitudeSpeed(double longitudeSpeed) {
        this.longitudeSpeed = longitudeSpeed;
    }

    public double getLatitudeSpeed() {
        return latitudeSpeed;
    }

    public void setLatitudeSpeed(double latitudeSpeed) {
        this.latitudeSpeed = latitudeSpeed;
    }

    public double getAnimationStep() {
        return animationStep;
    }

    public void setAnimationStep(double animationStep) {
        this.animationStep = animationStep;
    }

}