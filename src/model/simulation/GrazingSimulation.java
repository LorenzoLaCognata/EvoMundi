package model.simulation;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantPatch;

import java.util.Set;

public class GrazingSimulation {

    public void speciesGraze(Set<PlantPatch> plantPatchSet, AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            // TODO: selection of the plant patch to consume and not always the first one
            PlantPatch plantPatch = plantPatchSet.iterator().next();

            if (plantPatch.getQuantity() > 0.0) {
                graze(plantPatch, animalOrganism);
            }

        }
    }

    public void graze(PlantPatch plantPatch, AnimalOrganism animalOrganism) {

        // TODO: plant consumption following a formula variable by species (use variables for parameters)
        double quantityConsumed = Math.min(0.001, plantPatch.getQuantity());

        // TODO: energy gain following a formula variable by species (use variables for parameters)
        double energyGained = Math.min(70.0, 100.0 - animalOrganism.getEnergy());

        plantPatch.setQuantity(plantPatch.getQuantity() - quantityConsumed);
        animalOrganism.setEnergy(animalOrganism.getEnergy() + energyGained);

    }

}
