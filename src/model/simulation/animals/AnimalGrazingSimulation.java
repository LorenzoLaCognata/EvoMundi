package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.enums.TaxonomySpecies;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import utils.Log;

import java.util.Map;

public class AnimalGrazingSimulation {

    public void speciesGraze(Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap, AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            // TODO: selection of the plant species and organism to consume and not always the first organism of any species
            PlantOrganism plantOrganism = plantSpeciesMap.entrySet().iterator().next().getValue().getOrganisms().getFirst();

            if (plantOrganism.getQuantity() > 0.0) {
                graze(plantOrganism, animalOrganism);
            }

        }
    }

    public void graze(PlantOrganism plantOrganism, AnimalOrganism animalOrganism) {

        double consumptionConstant = animalOrganism.getOrganismAttributes().animalNutritionAttributes().plantConsumption();
        double consumptionQuantity = consumptionConstant * Math.pow(animalOrganism.getOrganismAttributes().animalVitalsAttributes().weight(), 0.75);
        double quantityConsumed = Math.min(consumptionQuantity, plantOrganism.getQuantity());

        // TODO: revert to log7 function when debugging is complete
        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " graze a " + plantOrganism.getPlantSpecies());
        }

        plantOrganism.setQuantity(plantOrganism.getQuantity() - quantityConsumed);

        // TODO: energy gain following a formula variable by species (use variables for parameters)
        double energyGained = Math.min(5.0, 100.0 - animalOrganism.getEnergy());

        animalOrganism.setEnergy(animalOrganism.getEnergy() + energyGained);

    }

}
