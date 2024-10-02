package model.environment.plants.base;

import javafx.scene.image.ImageView;
import model.environment.animals.attributes.AnimalOrganismAttributes;
import model.environment.plants.attributes.PlantOrganismAttributes;

public class PlantOrganism {

    private final PlantSpecies plantSpecies;
    private final ImageView imageView;

    private final PlantOrganismAttributes plantOrganismAttributes;
    private double quantity;

    public PlantOrganism(PlantSpecies plantSpecies, ImageView imageView, PlantOrganismAttributes plantOrganismAttributes, double quantity) {
        this.plantSpecies = plantSpecies;
        this.imageView = imageView;
        this.plantOrganismAttributes = plantOrganismAttributes;
        this.quantity = quantity;
    }

    public PlantSpecies getPlantSpecies() {
        return plantSpecies;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public PlantOrganismAttributes getPlantOrganismAttributes() {
        return plantOrganismAttributes;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
