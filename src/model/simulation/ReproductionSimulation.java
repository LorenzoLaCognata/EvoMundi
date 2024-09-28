package model.simulation;

import javafx.scene.image.ImageView;
import model.environment.animals.attributes.*;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.ReproductionStatus;
import model.environment.base.OrganismStatus;
import utils.Log;
import utils.RandomGenerator;

public class ReproductionSimulation {

    public AnimalOrganism birthing(AnimalOrganism male, AnimalOrganism female) {

        Gender gender = Gender.FEMALE;
        if (RandomGenerator.random.nextDouble() < 0.50) {
            gender = Gender.MALE;
        }

        VitalsAttributes femaleVitals = female.getOrganismAttributes().vitalsAttributes();
        VitalsAttributes maleVitals = male.getOrganismAttributes().vitalsAttributes();

        HuntingAttributes femaleHunting = female.getOrganismAttributes().huntingAttributes();
        HuntingAttributes maleHunting = male.getOrganismAttributes().huntingAttributes();

        VitalsAttributes offspringVitalsAttributes = new VitalsAttributes(
            RandomGenerator.generateGaussian(femaleVitals.weight(), maleVitals.weight(), RandomGenerator.GAUSSIAN_VARIANCE),
            RandomGenerator.generateGaussian(femaleVitals.height(), maleVitals.height(), RandomGenerator.GAUSSIAN_VARIANCE),
            RandomGenerator.generateGaussian(femaleVitals.lifeSpan(), maleVitals.lifeSpan(), RandomGenerator.GAUSSIAN_VARIANCE),
            SimulationSettings.getCurrentWeek(),
            RandomGenerator.generateGaussian(gender, femaleVitals.energyLoss(), maleVitals.energyLoss(), RandomGenerator.GAUSSIAN_VARIANCE)
        );

        MovementAttributes offspringMovementAttributes = new MovementAttributes(
            female.getOrganismAttributes().movementAttributes().getPosX(),
            female.getOrganismAttributes().movementAttributes().getPosY(),
            0.0,
            0.0,
            0.0
        );

        HuntingAttributes offspringHuntingAttributes = new HuntingAttributes(
            RandomGenerator.generateGaussian(gender, femaleHunting.huntAttempts(), maleHunting.huntAttempts(), 0.0),
            RandomGenerator.generateGaussian(gender, femaleHunting.energyGain(), maleHunting.energyGain(), RandomGenerator.GAUSSIAN_VARIANCE),
            RandomGenerator.generateGaussian(gender, femaleHunting.preyEaten(), maleHunting.preyEaten(), RandomGenerator.GAUSSIAN_VARIANCE)
        );

        ReproductionAttributes offspringReproductionAttributes = new ReproductionAttributes(
            female.getOrganismAttributes().reproductionAttributes().sexualMaturityStart(),
            female.getOrganismAttributes().reproductionAttributes().sexualMaturityEnd(),
            female.getOrganismAttributes().reproductionAttributes().matingSeasonStart(),
            female.getOrganismAttributes().reproductionAttributes().matingSeasonEnd(),
            female.getOrganismAttributes().reproductionAttributes().matingCooldown(),
            female.getOrganismAttributes().reproductionAttributes().gestationPeriod(),
            female.getOrganismAttributes().reproductionAttributes().averageOffspring(),
            female.getOrganismAttributes().reproductionAttributes().juvenileSurvivalRate(),
            female.getOrganismAttributes().reproductionAttributes().matingSuccessRate(),
            female.getOrganismAttributes().reproductionAttributes().matingAttempts()
        );

        OrganismAttributes offspringOrganismAttributes = new OrganismAttributes(
            offspringVitalsAttributes,
            offspringMovementAttributes,
            offspringHuntingAttributes,
            offspringReproductionAttributes
        );

        AnimalOrganism offspring = new AnimalOrganism(
            female.getAnimalSpecies(),
            gender,
            female.getDiet(),
            0.0,
            new ImageView(female.getImageView().getImage()),
            offspringOrganismAttributes
        );

        offspring.getPreyAnimalSpecies().putAll(female.getPreyAnimalSpecies());

        return offspring;

    }

    public void gestation(AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        animalOrganism.setGestationWeek(animalOrganism.getGestationWeek() + 1);

        ReproductionAttributes organismReproduction = animalOrganism.getOrganismAttributes().reproductionAttributes();

        if (animalOrganism.getGestationWeek() >= animalOrganism.getOrganismAttributes().reproductionAttributes().gestationPeriod()) {

            double baseOffspringCount = RandomGenerator.generateGaussian(organismReproduction.averageOffspring(), RandomGenerator.GAUSSIAN_VARIANCE);
            double offspringCount = Math.round(baseOffspringCount);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " gives birth to " + offspringCount + " offsprings");
            }

            for (int i=0; i < (int) offspringCount; i++) {

                AnimalOrganism offspring = birthing(animalOrganism, animalOrganism.getMate());

                if (RandomGenerator.random.nextDouble() >= animalOrganism.getOrganismAttributes().reproductionAttributes().juvenileSurvivalRate()) {
                    juvenileDeath(animalOrganism, offspring);
                }

                else {
                    if (animalOrganism.isImpersonatedOrganism()) {
                        Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " gives birth to a " + offspring.getAnimalSpecies() + " " + offspring.getGender());
                    }
                }

                animalSpecies.getOrganisms().add(offspring);

            }

            animalOrganism.setReproductionStatus(ReproductionStatus.COOLDOWN);
            animalOrganism.setGestationWeek(0.0);
            animalOrganism.setMate(null);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " enters reproduction cooldown");
            }

        }

    }

    public void juvenileDeath(AnimalOrganism animalOrganism, AnimalOrganism offspring) {
        offspring.setOrganismStatus(OrganismStatus.DEAD);
        offspring.setOrganismDeathReason(AnimalOrganismDeathReason.JUVENILE_DEATH);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7("The offspring of " + animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " suffers a juvenile death");
        }

        if (offspring.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.log7(offspring.getAnimalSpecies() + " " + offspring.getGender() + " suffers a juvenile death");
        }
    }

    public void findMate(AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism mate = animalSpecies.getOrganisms().get(i);

            if (mate.getGender() == Gender.MALE && mate.getReproductionStatus() != ReproductionStatus.NOT_MATURE ) {

                if (animalOrganism.isImpersonatedOrganism()) {
                    Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " finds a mate");
                }

                animalOrganism.setReproductionStatus(ReproductionStatus.PREGNANT);
                animalOrganism.setMate(mate);

                if (animalOrganism.isImpersonatedOrganism()) {
                    Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " starts pregnancy");
                }

                break;

            }
        }

    }

    public void matingCooldown(AnimalOrganism animalOrganism) {

        animalOrganism.setCooldownWeek(animalOrganism.getCooldownWeek() + 1);

        if (animalOrganism.getCooldownWeek() >= animalOrganism.getOrganismAttributes().reproductionAttributes().matingCooldown()) {

            animalOrganism.setReproductionStatus(ReproductionStatus.MATURE);
            animalOrganism.setCooldownWeek(0.0);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " finishes reproduction cooldown");
            }

        }

    }

    public void speciesReproduction(AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            if (animalOrganism.getGender() == Gender.FEMALE) {

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