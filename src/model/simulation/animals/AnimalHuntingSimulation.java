package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.base.PreyAnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.common.enums.OrganismStatus;
import model.environment.common.enums.TaxonomySpecies;
import utils.Log;
import utils.RandomGenerator;

import java.util.List;
import java.util.Map;

public class AnimalHuntingSimulation {

    public AnimalSpecies choosePreySpecies(Map<TaxonomySpecies, AnimalSpecies> speciesMap, AnimalOrganism predatorAnimalOrganism) {

    if (!predatorAnimalOrganism.getPreyAnimalSpecies().isEmpty()) {

        double preySpeciesTypeSelected = RandomGenerator.random.nextDouble();
        double preySpeciesTypeSelectorIndex = 0.0;

        for (PreyAnimalSpecies preyAnimalSpecies : predatorAnimalOrganism.getPreyAnimalSpecies().values()) {

            if (preySpeciesTypeSelected >= preySpeciesTypeSelectorIndex &&
                    preySpeciesTypeSelected < preySpeciesTypeSelectorIndex + preyAnimalSpecies.preferenceRate()) {
                return speciesMap.get(preyAnimalSpecies.taxonomySpecies());
            }

            else {
                preySpeciesTypeSelectorIndex = preySpeciesTypeSelectorIndex + preyAnimalSpecies.preferenceRate();
            }

        }

    }

    return null;

}

    public void speciesHunt(Map<TaxonomySpecies, AnimalSpecies> speciesMap, AnimalSpecies predatorAnimalSpecies) {

        for (int i = 0; i < predatorAnimalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism predatorAnimalOrganism = predatorAnimalSpecies.getOrganisms().get(i);

            if (predatorAnimalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                !predatorAnimalOrganism.getPreyAnimalSpecies().isEmpty()) {

                int huntingAttemptNumber = 0;

                while (predatorAnimalOrganism.getEnergy() < 1.0 &&
                        huntingAttemptNumber < predatorAnimalOrganism.getOrganismAttributes().animalNutritionAttributes().huntAttempts()) {

                    huntingAttempt(speciesMap, predatorAnimalOrganism);

                    huntingAttemptNumber++;

                }

            }

        }
    }

    public void huntingAttempt(Map<TaxonomySpecies, AnimalSpecies> speciesMap, AnimalOrganism predatorAnimalOrganism) {
        AnimalSpecies preyAnimalSpecies = choosePreySpecies(speciesMap, predatorAnimalOrganism);

        if (preyAnimalSpecies != null) {

            List<AnimalOrganism> preyAnimalOrganisms = preyAnimalSpecies.getOrganisms();
            int preySpeciesPopulation = preyAnimalSpecies.getPopulation();

            if (preySpeciesPopulation > 0) {

                int preyOrganismSelected = RandomGenerator.random.nextInt(0, preySpeciesPopulation);

                AnimalOrganism preyAnimalOrganism = preyAnimalOrganisms.get(preyOrganismSelected);
                double baseSuccessRate = predatorAnimalOrganism.calculateHuntSuccessRate(preyAnimalSpecies, preyAnimalOrganism);
                double huntSuccessRate = RandomGenerator.generateGaussian(baseSuccessRate, RandomGenerator.GAUSSIAN_VARIANCE);

                if (RandomGenerator.random.nextDouble() <= huntSuccessRate) {
                    huntingSuccess(predatorAnimalOrganism, preyAnimalOrganism);
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
            preyAnimalOrganism.getAnimalSpecies().chooseImpersonatingOrganism();
        }

        double preyKgEaten = Math.max(preyAnimalOrganism.getOrganismAttributes().animalVitalsAttributes().weight(), predatorAnimalOrganism.getOrganismAttributes().animalNutritionAttributes().preyEaten());
        double baseEnergyGain = predatorAnimalOrganism.getOrganismAttributes().animalNutritionAttributes().energyGain() * preyKgEaten;
        double energyGain = Math.min(baseEnergyGain, 1.0 - predatorAnimalOrganism.getEnergy());
        predatorAnimalOrganism.setEnergy(predatorAnimalOrganism.getEnergy() + energyGain);
    }

}