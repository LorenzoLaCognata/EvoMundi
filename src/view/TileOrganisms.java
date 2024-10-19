package view;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;

import java.util.ArrayList;
import java.util.Map;

public record TileOrganisms(Map<PlantSpecies, ArrayList<PlantOrganism>> plantOrganisms, Map<AnimalSpecies, ArrayList<AnimalOrganism>> animalOrganisms) {
}