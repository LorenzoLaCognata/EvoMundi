package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.enums.OrganismStatus;
import model.environment.common.enums.TaxonomySpecies;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import utils.Log;

import java.util.Map;

public class AnimalGrazingSimulation {

    public void speciesGraze(Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap, AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE) {
                // TODO: selection of the plant species and organism to consume and not always the first of any species
                PlantOrganism plantOrganism = plantSpeciesMap.entrySet().iterator().next().getValue().getOrganisms().getFirst();

                if (plantOrganism.getQuantity() > 0.0) {
                    graze(plantOrganism, animalOrganism);
                }

            }

        }
    }

    public void graze(PlantOrganism plantOrganism, AnimalOrganism animalOrganism) {

        double consumptionSpeciesConstant = animalOrganism.getOrganismAttributes().animalNutritionAttributes().plantConsumption();
        double baseConsumptionQuantity = consumptionSpeciesConstant * animalOrganism.getOrganismAttributes().animalVitalsAttributes().weight();
        double consumptionQuantity = baseConsumptionQuantity * (plantOrganism.getQuantity() / plantOrganism.getPlantSpecies().getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue());
        double quantityConsumed = Math.min(consumptionQuantity, plantOrganism.getQuantity());

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " graze " + quantityConsumed + " KGs of " + plantOrganism.getPlantSpecies());
        }

        plantOrganism.setQuantity(plantOrganism.getQuantity() - quantityConsumed);

        double gainSpeciesConstant = animalOrganism.getOrganismAttributes().animalNutritionAttributes().energyGain();
        double energyGained = Math.min(quantityConsumed * gainSpeciesConstant, 1.0 - animalOrganism.getEnergy());

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " recovers " + (energyGained*100) + " % of energy");
        }

        animalOrganism.setEnergy(animalOrganism.getEnergy() + energyGained);

    }

}