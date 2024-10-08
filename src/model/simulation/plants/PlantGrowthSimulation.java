package model.simulation.plants;

import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import view.TileOrganisms;

import java.awt.*;
import java.util.Map;

public class PlantGrowthSimulation {

    public void plantRegeneration(Map<Point, TileOrganisms> worldMap) {

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            for (PlantOrganism plantOrganism : tile.getValue().PlantOrganisms()) {

                PlantSpecies plantSpecies = plantOrganism.getPlantSpecies();

                double growthRate = plantSpecies.getAttribute(PlantAttribute.GROWTH_RATE).getValue();
                double currentQuantity = plantOrganism.getQuantity();
                double carryingCapacity = plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue();
                double growthQuantity = currentQuantity * growthRate * (1.0 - (currentQuantity / carryingCapacity));

                plantOrganism.setQuantity(currentQuantity + growthQuantity);
                plantSpecies.setOrganismCount(plantSpecies.getOrganismCount() + growthQuantity);

            }
        }

    }

}
