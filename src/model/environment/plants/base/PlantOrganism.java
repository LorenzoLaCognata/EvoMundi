package model.environment.plants.base;

public class PlantOrganism {

    private final PlantSpecies plantSpecies;
    private double quantity;

    public PlantOrganism(PlantSpecies plantSpecies, double quantity) {
        this.plantSpecies = plantSpecies;
        this.quantity = quantity;
    }

    public PlantSpecies getPlantSpecies() {
        return plantSpecies;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
