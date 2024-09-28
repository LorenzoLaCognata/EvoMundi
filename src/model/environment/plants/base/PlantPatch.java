package model.environment.plants.base;

import model.environment.plants.enums.PlantSpeciesType;

import java.util.HashSet;
import java.util.Set;

public class PlantPatch {

    private final PlantSpeciesType plantSpeciesType;
    private double quantity;

    public PlantPatch(PlantSpeciesType plantSpeciesType, double quantity) {
        this.plantSpeciesType = plantSpeciesType;
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public static Set<PlantPatch> initializePlantPatchSet() {

        Set<PlantPatch> plantPatchSet = new HashSet<>();

        // TODO: add additional biomass
        PlantPatch plantPatch = new PlantPatch(PlantSpeciesType.NA, 100.0);

        plantPatchSet.add(plantPatch);

        return plantPatchSet;

    }

}
