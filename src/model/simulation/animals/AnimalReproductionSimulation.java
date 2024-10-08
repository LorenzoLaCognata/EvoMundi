package model.simulation.animals;

import javafx.scene.image.ImageView;
import model.environment.animals.attributes.*;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.ReproductionStatus;
import model.environment.common.enums.OrganismStatus;
import model.simulation.base.SimulationSettings;
import utils.Log;
import utils.RandomGenerator;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.Map;

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
            female.getOrganismAttributes().animalPositionAttributes().getLatitude(),
            female.getOrganismAttributes().animalPositionAttributes().getLongitude()
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
            new ImageView(female.getOrganismIcons().getSpeciesIcon().getImage()),
            offspringAnimalOrganismAttributes
        );

        offspring.getPreyAnimalSpecies().putAll(female.getPreyAnimalSpecies());

        offspring.getAnimalSpecies().getImageGroup().getChildren().add(offspring.getOrganismIcons().getStackPane());

        return offspring;

    }

    public void gestation(Map<Point, TileOrganisms> worldMap, AnimalOrganism animalOrganism) {

        animalOrganism.setGestationWeek(animalOrganism.getGestationWeek() + 1);

        AnimalReproductionAttributes organismReproduction = animalOrganism.getOrganismAttributes().animalReproductionAttributes();

        if (animalOrganism.getGestationWeek() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().gestationPeriod()) {

            double baseOffspringCount = RandomGenerator.generateGaussian(organismReproduction.averageOffspring(), RandomGenerator.GAUSSIAN_VARIANCE);
            double offspringCount = Math.round(baseOffspringCount);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " gives birth to " + offspringCount + " offsprings");
            }

            for (int i=0; i < (int) offspringCount; i++) {

                AnimalOrganism offspring = birthing(animalOrganism, animalOrganism.getMate());

                if (RandomGenerator.random.nextDouble() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().juvenileSurvivalRate()) {
                    juvenileDeath(animalOrganism, offspring);
                }

                else {
                    if (animalOrganism.isImpersonatedOrganism()) {
                        Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " gives birth to a " + offspring.getGender() + " " + offspring.getAnimalSpecies() + " " + offspring.getId());
                    }
                }



                AnimalPositionAttributes offspringPositionAttributes = offspring.getOrganismAttributes().animalPositionAttributes();

                Point currentTile = Geography.calculateTile(offspringPositionAttributes.getLatitude(), offspringPositionAttributes.getLongitude());

                worldMap.get(currentTile).AnimalOrganisms().add(offspring);
                offspring.getAnimalSpecies().setOrganismCount(offspring.getAnimalSpecies().getOrganismCount() + 1);

            }

            animalOrganism.setReproductionStatus(ReproductionStatus.COOLDOWN);
            animalOrganism.setGestationWeek(0.0);
            animalOrganism.setMate(null);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " enters reproduction cooldown");
            }

        }

    }

    public void juvenileDeath(AnimalOrganism animalOrganism, AnimalOrganism offspring) {
        offspring.setOrganismStatus(OrganismStatus.DEAD);
        offspring.setOrganismDeathReason(AnimalOrganismDeathReason.JUVENILE_DEATH);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6("The offspring of " + animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " suffers a juvenile death");
        }

        if (offspring.isImpersonatedOrganism()) {
            Log.log6(offspring.getAnimalSpecies() + " " + offspring.getId() + " suffers a juvenile death");
            offspring.setImpersonatedOrganism(false);
        }
    }

    public void findMate(Map<Point, TileOrganisms> worldMap, AnimalOrganism animalOrganism) {

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            for (AnimalOrganism mate : tile.getValue().AnimalOrganisms()) {

                if (mate.getAnimalSpecies() == animalOrganism.getAnimalSpecies()) {

                    if (mate.getGender() == Gender.MALE && mate.getReproductionStatus() != ReproductionStatus.NOT_MATURE ) {

                        if (animalOrganism.isImpersonatedOrganism()) {
                            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " finds a mate");
                        }

                        animalOrganism.setReproductionStatus(ReproductionStatus.PREGNANT);
                        animalOrganism.setMate(mate);

                        if (animalOrganism.isImpersonatedOrganism()) {
                            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " starts pregnancy");
                        }

                        return;

                    }
                }
            }
        }

    }

    public void matingCooldown(AnimalOrganism animalOrganism) {

        animalOrganism.setCooldownWeek(animalOrganism.getCooldownWeek() + 1);

        if (animalOrganism.getCooldownWeek() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().matingCooldown()) {

            animalOrganism.setReproductionStatus(ReproductionStatus.MATURE);
            animalOrganism.setCooldownWeek(0.0);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " finishes reproduction cooldown");
            }

        }

    }

    public void animalReproduction(Map<Point, TileOrganisms> worldMap) {

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            for (AnimalOrganism animalOrganism : tile.getValue().AnimalOrganisms()) {

                if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                    animalOrganism.getGender() == Gender.FEMALE) {

                    if (animalOrganism.getReproductionStatus() == ReproductionStatus.COOLDOWN) {
                        matingCooldown(animalOrganism);
                    }

                    if (animalOrganism.getReproductionStatus() == ReproductionStatus.PREGNANT) {
                        gestation(worldMap, animalOrganism);
                    }

                    if (animalOrganism.getReproductionStatus() == ReproductionStatus.MATURE) {
                        findMate(worldMap, animalOrganism);
                    }

                }
            }
        }

    }

}