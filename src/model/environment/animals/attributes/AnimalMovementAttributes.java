package model.environment.animals.attributes;

public class AnimalMovementAttributes {

    private double posX;
    private double posY;
    private double speedX;
    private double speedY;
    private double directionSteps;

    public AnimalMovementAttributes(double posX, double posY, double speedX, double speedY, double directionSteps) {
        this.posX = posX;
        this.posY = posY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.directionSteps = directionSteps;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getDirectionSteps() {
        return directionSteps;
    }

    public void setDirectionSteps(double directionSteps) {
        this.directionSteps = directionSteps;
    }

}