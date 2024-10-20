package model.environment.plants.base;

import javafx.scene.image.ImageView;
import model.environment.common.base.Organism;
import model.environment.plants.attributes.PlantOrganismAttributes;
import utils.Log;

public class PlantOrganism extends Organism {

    private final PlantSpecies plantSpecies;

    private final PlantOrganismAttributes plantOrganismAttributes;
    private double quantity;

    public PlantOrganism(PlantSpecies plantSpecies, ImageView imageView, PlantOrganismAttributes plantOrganismAttributes, double quantity) {
        super(imageView);
        this.plantSpecies = plantSpecies;
        this.plantOrganismAttributes = plantOrganismAttributes;
        this.quantity = quantity;
    }

    public PlantSpecies getPlantSpecies() {
        return plantSpecies;
    }

    public PlantOrganismAttributes getPlantOrganismAttributes() {
        return plantOrganismAttributes;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.setOrganismIconLabel(Log.formatNumber(quantity));
    }

}
