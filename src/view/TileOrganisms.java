package view;

import model.environment.animals.base.AnimalOrganism;
import model.environment.plants.base.PlantOrganism;

import java.util.Set;

public record TileOrganisms(Set<PlantOrganism> PlantOrganisms, Set<AnimalOrganism> AnimalOrganisms) {

}