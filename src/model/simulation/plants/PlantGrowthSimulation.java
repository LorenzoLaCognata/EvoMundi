package model.simulation.plants;

import model.environment.common.base.Ecosystem;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;

public class PlantGrowthSimulation {

    private static void plantRegenerationOrganism(PlantOrganism plantOrganism, PlantSpecies plantSpecies) {

        double growthRate = plantSpecies.getAttribute(PlantAttribute.GROWTH_RATE).getValue();
        double currentQuantity = plantOrganism.getQuantity();
        double carryingCapacity = plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue();
        double growthQuantity = currentQuantity * growthRate * (1.0 - (currentQuantity / carryingCapacity));

        plantOrganism.setQuantity(currentQuantity + growthQuantity);
        plantSpecies.setOrganismCount(plantSpecies.getOrganismCount() + growthQuantity);

    }

    public void plantRegeneration(Ecosystem ecosystem) {
         ecosystem.iteratePlantOrganisms(PlantGrowthSimulation::plantRegenerationOrganism);
    }

}
