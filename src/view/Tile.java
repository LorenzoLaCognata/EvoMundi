package view;

import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantSpecies;

import java.util.Map;

public record Tile(Map<PlantSpecies, TileSpecies> plantTileSpecies,
                   Map<AnimalSpecies, TileSpecies> animalTileSpecies) {

    public Tile(Map<PlantSpecies, TileSpecies> plantTileSpecies, Map<AnimalSpecies, TileSpecies> animalTileSpecies) {
        this.plantTileSpecies = plantTileSpecies;
        this.animalTileSpecies = animalTileSpecies;
    }

}