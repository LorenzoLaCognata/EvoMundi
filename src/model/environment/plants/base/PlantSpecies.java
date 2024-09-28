package model.environment.plants.base;

import javafx.scene.image.Image;
import model.environment.base.*;
import model.environment.plants.enums.PlantSpeciesType;


public class PlantSpecies extends Species {

    private final PlantSpeciesType plantSpeciesType;

    public PlantSpecies(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image, PlantSpeciesType plantSpeciesType) {
        super(speciesTaxonomy, commonName, image);
        this.plantSpeciesType = plantSpeciesType;
    }

}