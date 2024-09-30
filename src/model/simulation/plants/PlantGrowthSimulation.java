package model.simulation.plants;

import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;

public class PlantGrowthSimulation {

    public void plantSpeciesRegeneration(PlantSpecies plantSpecies) {

        for (int i = 0; i < plantSpecies.getOrganisms().size(); i++) {

            PlantOrganism plantOrganism = plantSpecies.getOrganisms().get(i);

            double growthRate = plantSpecies.getAttribute(PlantAttribute.GROWTH_RATE).getValue();
            double currentQuantity = plantOrganism.getQuantity();
            double carryingCapacity = plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue();
            double growthQuantity = currentQuantity * growthRate * (1.0 - (currentQuantity / carryingCapacity) );

            plantOrganism.setQuantity(currentQuantity + growthQuantity);

        }

    }

}
