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
import utils.LogStatus;
import utils.TriConsumer;

import java.util.function.Predicate;

// TODO: check starvation that seems too much frequent e.g. for Moose

public class AnimalGrazingSimulation {

    public static final LogStatus logStatus = LogStatus.INACTIVE;

    private final Predicate<AnimalOrganism> animalOrganismIsAliveHerbivore =
        animalOrganism ->
            animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
            animalOrganism.getAnimalSpecies().getBaseDiet() == Diet.HERBIVORE;

    private final TriConsumer<PlantOrganism, PlantSpecies, AnimalOrganism> animalOrganismGrazeConsumer =
        (plantOrganism, ignored, animalOrganism) -> {
            if (animalOrganism.getEnergy() < 1.0 && plantOrganism.getQuantity() > 0.0) {
                animalOrganismGraze(animalOrganism, plantOrganism);
            }
        };

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y, Z> void ecosystemGraze(Ecosystem ecosystem) {
        ecosystem.getIterationManager().iteratePlantOrganismsPerEachAnimalOrganism(
                (Predicate<X>) animalOrganismIsAliveHerbivore, (TriConsumer<X, Y, Z>) animalOrganismGrazeConsumer,
                ecosystem);
    }

    public void animalOrganismGraze(AnimalOrganism animalOrganism, PlantOrganism plantOrganism) {
        double quantity = grazeQuantityConsumed(animalOrganism, plantOrganism);
        plantOrganismGrazeConsumption(plantOrganism, quantity);
        animalOrganismGrazeEnergyGain(animalOrganism, quantity);
    }

    private static void animalOrganismGrazeEnergyGain(AnimalOrganism animalOrganism, double quantityConsumed) {
        double gainSpeciesConstant = animalOrganism.getOrganismAttributes().animalNutritionAttributes().energyGain();
        double energyGained = Math.min(quantityConsumed * gainSpeciesConstant, 1.0 - animalOrganism.getEnergy());

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " recovers " + (energyGained*100) + " % of energy");
        }

        animalOrganism.setEnergy(animalOrganism.getEnergy() + energyGained);
    }

    private static double grazeQuantityConsumed(AnimalOrganism animalOrganism, PlantOrganism plantOrganism) {
        double consumptionSpeciesConstant = animalOrganism.getOrganismAttributes().animalNutritionAttributes().plantConsumption();
        double baseConsumptionQuantity = consumptionSpeciesConstant * animalOrganism.getOrganismAttributes().animalVitalsAttributes().weight();
        double consumptionQuantity = baseConsumptionQuantity * (plantOrganism.getQuantity() / plantOrganism.getPlantSpecies().getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue());
        double quantity = Math.min(consumptionQuantity, plantOrganism.getQuantity());

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " graze " + quantity + " KGs of " + plantOrganism.getPlantSpecies());
        }
        return quantity;
    }

    private static void plantOrganismGrazeConsumption(PlantOrganism plantOrganism, double quantityConsumed) {
        PlantSpecies plantSpecies = plantOrganism.getPlantSpecies();
        plantOrganism.setQuantity(plantOrganism.getQuantity() - quantityConsumed);
        plantSpecies.setOrganismCount(plantOrganism.getPlantSpecies().getOrganismCount() - quantityConsumed);

        if (logStatus == LogStatus.ACTIVE) {
            Log.log7(plantSpecies + " " + plantOrganism.getId() + " is consumed by " + Log.formatNumber(quantityConsumed) + " to " + Log.formatNumber(plantOrganism.getQuantity()));
        }
    }

}