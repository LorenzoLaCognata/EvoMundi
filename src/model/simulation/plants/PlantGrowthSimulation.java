package model.simulation.plants;

import model.environment.common.base.Ecosystem;
import model.environment.common.base.IterationManager;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import utils.Log;
import utils.LogStatus;

public class PlantGrowthSimulation {

    public static final LogStatus logStatus = LogStatus.INACTIVE;
    private static void plantRegenerationOrganism(PlantOrganism plantOrganism, PlantSpecies plantSpecies) {

        double growthRate = plantSpecies.getAttribute(PlantAttribute.GROWTH_RATE).getValue();
        double currentQuantity = plantOrganism.getQuantity();
        double carryingCapacity = plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue();
        double growthQuantity = currentQuantity * growthRate * (1.0 - (currentQuantity / carryingCapacity));

        plantOrganism.setQuantity(currentQuantity + growthQuantity);
        plantSpecies.setOrganismCount(plantSpecies.getOrganismCount() + growthQuantity);

        if (logStatus == LogStatus.ACTIVE) {
            Log.log7(plantSpecies + " " + plantOrganism.getId() + " grows by " + Log.formatNumber(growthQuantity) + " to " + Log.formatNumber(plantOrganism.getQuantity()));
        }

    }

    public void plantRegeneration(Ecosystem ecosystem) {
         ecosystem.getIterationManager().iteratePlantOrganisms(ecosystem, IterationManager.plantTruePredicate, PlantGrowthSimulation::plantRegenerationOrganism);
    }

}
