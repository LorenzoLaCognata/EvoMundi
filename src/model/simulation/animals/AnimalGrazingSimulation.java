package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.enums.Diet;
import model.environment.common.base.Ecosystem;
import model.environment.common.base.Organism;
import model.environment.common.enums.OrganismStatus;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import utils.Log;
import utils.TriConsumer;

import java.util.function.Predicate;

public class AnimalGrazingSimulation {

    private final Predicate<AnimalOrganism> animalOrganismIsAliveHerbivore =
        animalOrganism ->
            animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
            animalOrganism.getAnimalSpecies().getBaseDiet() == Diet.HERBIVORE;

    private final TriConsumer<PlantOrganism, PlantSpecies, AnimalOrganism> animalGrazeConsumer =
        (plantOrganism, ignored, animalOrganism) -> {
            if (animalOrganism.getEnergy() < 1.0 && plantOrganism.getQuantity() > 0.0) {
                graze(animalOrganism, plantOrganism);
            }
        };

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y, Z> void animalGraze(Ecosystem ecosystem) {
        ecosystem.iteratePlantOrganismsPerEachAnimalOrganism(
                (Predicate<X>) animalOrganismIsAliveHerbivore, (TriConsumer<X, Y, Z>) animalGrazeConsumer
        );
    }

    public void graze(AnimalOrganism animalOrganism, PlantOrganism plantOrganism) {

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