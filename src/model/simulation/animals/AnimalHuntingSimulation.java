package model.simulation.animals;

import model.environment.animals.attributes.AnimalPositionAttributes;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.base.PreyAnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Diet;
import model.environment.common.base.Ecosystem;
import model.environment.common.enums.OrganismStatus;
import model.environment.common.enums.TaxonomySpecies;
import utils.Log;
import utils.RandomGenerator;
import utils.TriConsumer;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;

public class AnimalHuntingSimulation {

    private final Predicate<AnimalOrganism> animalOrganismIsAliveCarnivore =
            animalOrganism ->
                    animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                            animalOrganism.getAnimalSpecies().getBaseDiet() == Diet.CARNIVORE;

    private final TriConsumer<AnimalOrganism, AnimalSpecies, Ecosystem> huntConsumer =

            (animalOrganism, ignored, ecosystem) -> {

                int huntingAttemptNumber = 0;
                double huntAttempts = animalOrganism.getOrganismAttributes().animalNutritionAttributes().huntAttempts();

                while (animalOrganism.getEnergy() < 1.0 && huntingAttemptNumber < huntAttempts) {
                    huntingAttempt(animalOrganism, ecosystem);
                    huntingAttemptNumber++;
                }

            };

    // TODO: try refactoring loops using variables below
    /*
    private final TriConsumer<AnimalOrganism, AnimalSpecies, AnimalOrganism> huntingAttemptConsumer =

        (preyAnimalOrganism, preyAnimalSpecies, predatorAnimalOrganism) -> {

            int preySpeciesPopulation = (int) preyAnimalSpecies.getOrganismCount();

            if (preySpeciesPopulation > 0) {

                double baseSuccessRate = predatorAnimalOrganism.calculateHuntSuccessRate(preyAnimalSpecies, preyAnimalOrganism);
                double huntSuccessRate = RandomGenerator.generateGaussian(baseSuccessRate, RandomGenerator.GAUSSIAN_VARIANCE);

                if (RandomGenerator.random.nextDouble() <= huntSuccessRate) {
                    huntingSuccess(predatorAnimalOrganism, preyAnimalOrganism);
                }

            }

        };
     */

    public AnimalSpecies choosePreySpecies(Ecosystem ecosystem, AnimalOrganism predatorAnimalOrganism) {

        Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap = ecosystem.getAnimalSpeciesMap();

        if (!predatorAnimalOrganism.getPreyAnimalSpecies().isEmpty()) {

            double preySpeciesTypeSelected = RandomGenerator.random.nextDouble();
            double preySpeciesTypeSelectorIndex = 0.0;

            for (PreyAnimalSpecies preyAnimalSpecies : predatorAnimalOrganism.getPreyAnimalSpecies().values()) {

                if (preySpeciesTypeSelected >= preySpeciesTypeSelectorIndex &&
                        preySpeciesTypeSelected < preySpeciesTypeSelectorIndex + preyAnimalSpecies.preferenceRate()) {
                    return animalSpeciesMap.get(preyAnimalSpecies.taxonomySpecies());
                }

                else {
                    preySpeciesTypeSelectorIndex = preySpeciesTypeSelectorIndex + preyAnimalSpecies.preferenceRate();
                }

            }

        }

        return null;

    }

    public void animalHunt(Ecosystem ecosystem) {
        ecosystem.iterateAnimalOrganismsTriConsumer(animalOrganismIsAliveCarnivore, huntConsumer, ecosystem);
    }

    public void huntingAttempt(AnimalOrganism predatorAnimalOrganism, Ecosystem ecosystem) {

        Map<Point, TileOrganisms> worldMap = ecosystem.getWorldMap();
        AnimalSpecies preyAnimalSpecies = choosePreySpecies(ecosystem, predatorAnimalOrganism);

        if (preyAnimalSpecies != null) {

            AnimalPositionAttributes animalPositionAttributes = predatorAnimalOrganism.getOrganismAttributes().animalPositionAttributes();
            Point currentTile = Geography.calculateTile(animalPositionAttributes.getLatitude(), animalPositionAttributes.getLongitude());
            TileOrganisms tileOrganisms = worldMap.get(currentTile);

            if (tileOrganisms != null) {

                ArrayList<AnimalOrganism> preyAnimalOrganisms = tileOrganisms.animalOrganisms().get(preyAnimalSpecies);

                for (AnimalOrganism preyAnimalOrganism : preyAnimalOrganisms) {

                    int preySpeciesPopulation = (int) preyAnimalSpecies.getOrganismCount();

                    if (preySpeciesPopulation > 0) {

                        double baseSuccessRate = predatorAnimalOrganism.calculateHuntSuccessRate(preyAnimalSpecies, preyAnimalOrganism);
                        double huntSuccessRate = RandomGenerator.generateGaussian(baseSuccessRate, RandomGenerator.GAUSSIAN_VARIANCE);

                        if (RandomGenerator.random.nextDouble() <= huntSuccessRate) {
                            huntingSuccess(predatorAnimalOrganism, preyAnimalOrganism);
                            return;
                        }

                    }
                }
            }
        }
    }

    public void huntingSuccess(AnimalOrganism predatorAnimalOrganism, AnimalOrganism preyAnimalOrganism) {

        if (predatorAnimalOrganism.isImpersonatedOrganism()) {
            Log.log6(predatorAnimalOrganism.getAnimalSpecies() + " " + predatorAnimalOrganism.getId() + " hunts a " + preyAnimalOrganism.getAnimalSpecies());
        }

        preyAnimalOrganism.setOrganismStatus(OrganismStatus.DEAD);
        preyAnimalOrganism.setOrganismDeathReason(AnimalOrganismDeathReason.PREDATION);

        if (preyAnimalOrganism.isImpersonatedOrganism()) {
            Log.log6(preyAnimalOrganism.getAnimalSpecies() + " " + preyAnimalOrganism.getId() + " is hunted by a " + predatorAnimalOrganism.getAnimalSpecies());
            preyAnimalOrganism.setImpersonatedOrganism(false);
        }

        double preyKgEaten = Math.max(preyAnimalOrganism.getOrganismAttributes().animalVitalsAttributes().weight(), predatorAnimalOrganism.getOrganismAttributes().animalNutritionAttributes().preyEaten());
        double baseEnergyGain = predatorAnimalOrganism.getOrganismAttributes().animalNutritionAttributes().energyGain() * preyKgEaten;
        double energyGain = Math.min(baseEnergyGain, 1.0 - predatorAnimalOrganism.getEnergy());
        predatorAnimalOrganism.setEnergy(predatorAnimalOrganism.getEnergy() + energyGain);
    }

}