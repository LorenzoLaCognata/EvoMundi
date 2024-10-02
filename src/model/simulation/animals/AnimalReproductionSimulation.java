package model.simulation.animals;

import javafx.scene.image.ImageView;
import model.environment.animals.attributes.*;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.ReproductionStatus;
import model.environment.common.enums.OrganismStatus;
import model.simulation.base.SimulationSettings;
import model.simulation.base.SimulationStatus;
import utils.Log;
import utils.RandomGenerator;

public class AnimalReproductionSimulation {

    public AnimalOrganism birthing(AnimalOrganism male, AnimalOrganism female) {

        Gender gender = Gender.FEMALE;
        if (RandomGenerator.random.nextDouble() < 0.50) {
            gender = Gender.MALE;
        }

        AnimalVitalsAttributes femaleVitals = female.getOrganismAttributes().animalVitalsAttributes();
        AnimalVitalsAttributes maleVitals = male.getOrganismAttributes().animalVitalsAttributes();

        AnimalNutritionAttributes femaleNutrition = female.getOrganismAttributes().animalNutritionAttributes();
        AnimalNutritionAttributes maleNutrition = male.getOrganismAttributes().animalNutritionAttributes();

        AnimalVitalsAttributes offspringAnimalVitalsAttributes = new AnimalVitalsAttributes(
            RandomGenerator.generateGaussian(femaleVitals.weight(), maleVitals.weight(), RandomGenerator.GAUSSIAN_VARIANCE),
            RandomGenerator.generateGaussian(femaleVitals.height(), maleVitals.height(), RandomGenerator.GAUSSIAN_VARIANCE),
            RandomGenerator.generateGaussian(femaleVitals.lifeSpan(), maleVitals.lifeSpan(), RandomGenerator.GAUSSIAN_VARIANCE),
            SimulationSettings.getCurrentWeek(),
            RandomGenerator.generateGaussian(gender, femaleVitals.energyLoss(), maleVitals.energyLoss(), RandomGenerator.GAUSSIAN_VARIANCE)
        );

        AnimalPositionAttributes offspringAnimalPositionAttributes = new AnimalPositionAttributes(
            female.getOrganismAttributes().animalPositionAttributes().getPosX(),
            female.getOrganismAttributes().animalPositionAttributes().getPosY(),
            0.0,
            0.0,
            0.0
        );

        AnimalNutritionAttributes offspringAnimalNutritionAttributes = new AnimalNutritionAttributes(
            RandomGenerator.generateGaussian(gender, femaleNutrition.huntAttempts(), maleNutrition.huntAttempts(), 0.0),
            RandomGenerator.generateGaussian(gender, femaleNutrition.energyGain(), maleNutrition.energyGain(), RandomGenerator.GAUSSIAN_VARIANCE),
            RandomGenerator.generateGaussian(gender, femaleNutrition.preyEaten(), maleNutrition.preyEaten(), RandomGenerator.GAUSSIAN_VARIANCE),
            RandomGenerator.generateGaussian(gender, femaleNutrition.plantConsumption(), maleNutrition.plantConsumption(), RandomGenerator.GAUSSIAN_VARIANCE)
        );

        AnimalReproductionAttributes offspringAnimalReproductionAttributes = new AnimalReproductionAttributes(
            female.getOrganismAttributes().animalReproductionAttributes().sexualMaturityStart(),
            female.getOrganismAttributes().animalReproductionAttributes().sexualMaturityEnd(),
            female.getOrganismAttributes().animalReproductionAttributes().matingSeasonStart(),
            female.getOrganismAttributes().animalReproductionAttributes().matingSeasonEnd(),
            female.getOrganismAttributes().animalReproductionAttributes().matingCooldown(),
            female.getOrganismAttributes().animalReproductionAttributes().gestationPeriod(),
            female.getOrganismAttributes().animalReproductionAttributes().averageOffspring(),
            female.getOrganismAttributes().animalReproductionAttributes().juvenileSurvivalRate(),
            female.getOrganismAttributes().animalReproductionAttributes().matingSuccessRate(),
            female.getOrganismAttributes().animalReproductionAttributes().matingAttempts()
        );

        AnimalOrganismAttributes offspringAnimalOrganismAttributes = new AnimalOrganismAttributes(
                offspringAnimalVitalsAttributes,
                offspringAnimalPositionAttributes,
                offspringAnimalNutritionAttributes,
                offspringAnimalReproductionAttributes
        );

        AnimalOrganism offspring = new AnimalOrganism(
            female.getAnimalSpecies(),
            gender,
            female.getDiet(),
            0.0,
            new ImageView(female.getImageView().getImage()),
                offspringAnimalOrganismAttributes
        );

        offspring.getPreyAnimalSpecies().putAll(female.getPreyAnimalSpecies());

        return offspring;

    }

    public void gestation(AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        animalOrganism.setGestationWeek(animalOrganism.getGestationWeek() + 1);

        AnimalReproductionAttributes organismReproduction = animalOrganism.getOrganismAttributes().animalReproductionAttributes();

        if (animalOrganism.getGestationWeek() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().gestationPeriod()) {

            double baseOffspringCount = RandomGenerator.generateGaussian(organismReproduction.averageOffspring(), RandomGenerator.GAUSSIAN_VARIANCE);
            double offspringCount = Math.round(baseOffspringCount);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " gives birth to " + offspringCount + " offsprings");
            }

            for (int i=0; i < (int) offspringCount; i++) {

                AnimalOrganism offspring = birthing(animalOrganism, animalOrganism.getMate());

                if (RandomGenerator.random.nextDouble() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().juvenileSurvivalRate()) {
                    juvenileDeath(animalOrganism, offspring);
                }

                else {
                    if (animalOrganism.isImpersonatedOrganism()) {
                        Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " gives birth to a " + offspring.getAnimalSpecies() + " " + offspring.getGender());
                    }
                }

                animalSpecies.getOrganisms().add(offspring);

            }

            animalOrganism.setReproductionStatus(ReproductionStatus.COOLDOWN);
            animalOrganism.setGestationWeek(0.0);
            animalOrganism.setMate(null);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " enters reproduction cooldown");
            }

        }

    }

    public void juvenileDeath(AnimalOrganism animalOrganism, AnimalOrganism offspring) {
        offspring.setOrganismStatus(OrganismStatus.DEAD);
        offspring.setOrganismDeathReason(AnimalOrganismDeathReason.JUVENILE_DEATH);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6("The offspring of " + animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " suffers a juvenile death");
        }

        if (offspring.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.log6(offspring.getAnimalSpecies() + " " + offspring.getGender() + " suffers a juvenile death");
        }
    }

    public void findMate(AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism mate = animalSpecies.getOrganisms().get(i);

            if (mate.getGender() == Gender.MALE && mate.getReproductionStatus() != ReproductionStatus.NOT_MATURE ) {

                if (animalOrganism.isImpersonatedOrganism()) {
                    Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " finds a mate");
                }

                animalOrganism.setReproductionStatus(ReproductionStatus.PREGNANT);
                animalOrganism.setMate(mate);

                if (animalOrganism.isImpersonatedOrganism()) {
                    Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " starts pregnancy");
                }

                break;

            }
        }

    }

    public void matingCooldown(AnimalOrganism animalOrganism) {

        animalOrganism.setCooldownWeek(animalOrganism.getCooldownWeek() + 1);

        if (animalOrganism.getCooldownWeek() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().matingCooldown()) {

            animalOrganism.setReproductionStatus(ReproductionStatus.MATURE);
            animalOrganism.setCooldownWeek(0.0);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " finishes reproduction cooldown");
            }

        }

    }

    public void speciesReproduction(AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                animalOrganism.getGender() == Gender.FEMALE) {

                if (animalOrganism.getReproductionStatus() == ReproductionStatus.COOLDOWN) {
                    matingCooldown(animalOrganism);
                }

                if (animalOrganism.getReproductionStatus() == ReproductionStatus.PREGNANT) {
                    gestation(animalSpecies, animalOrganism);
                }

                if (animalOrganism.getReproductionStatus() == ReproductionStatus.MATURE) {
                    findMate(animalSpecies, animalOrganism);
                }

            }
        }

    }

}