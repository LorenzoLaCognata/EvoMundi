package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;

import java.awt.*;

public record AnimalMovementPoint(AnimalSpecies animalSpecies, AnimalOrganism animalOrganism, Point point) {
}
