package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.enums.Diet;
import model.environment.common.enums.OrganismStatus;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.enums.PlantAttribute;
import utils.Log;
import view.TileOrganisms;

import java.awt.*;
import java.util.Map;

public class AnimalGrazingSimulation {

    public void animalGraze(Map<Point, TileOrganisms> worldMap) {

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            for (AnimalOrganism animalOrganism : tile.getValue().AnimalOrganisms()) {

                if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                        animalOrganism.getAnimalSpecies().getBaseDiet() == Diet.HERBIVORE) {

                    if (tile.getValue().PlantOrganisms().iterator().hasNext()) {

                        // TODO: selection of the plant species and organism to consume and not always a random one
                        PlantOrganism plantOrganism = tile.getValue().PlantOrganisms().iterator().next();

                        if (plantOrganism.getQuantity() > 0.0) {
                            graze(plantOrganism, animalOrganism);
                        }

                    }
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
        plantOrganism.getPlantSpecies().setOrganismCount(plantOrganism.getPlantSpecies().getOrganismCount() - quantityConsumed);

        double gainSpeciesConstant = animalOrganism.getOrganismAttributes().animalNutritionAttributes().energyGain();
        double energyGained = Math.min(quantityConsumed * gainSpeciesConstant, 1.0 - animalOrganism.getEnergy());

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " recovers " + (energyGained*100) + " % of energy");
        }

        animalOrganism.setEnergy(animalOrganism.getEnergy() + energyGained);

    }

}