package model.simulation;

import model.animals.*;
import model.enums.*;
import utils.Log;
import utils.RandomGenerator;

import javafx.scene.image.ImageView;

public class ReproductionSimulation {

    public Organism birthing(Organism male, Organism female) {

        Gender gender = Gender.FEMALE;
        if (RandomGenerator.random.nextDouble() < 0.50) {
            gender = Gender.MALE;
        }

        VitalsAttributes femaleVitals = female.getVitalsAttributes();
        VitalsAttributes maleVitals = male.getVitalsAttributes();

        HuntingAttributes femaleHunting = female.getHuntingAttributes();
        HuntingAttributes maleHunting = male.getHuntingAttributes();

        Organism offspring = new Organism(
            female.getSpeciesType(),
            gender,
            female.getDiet(),
            0.0,
            new ImageView(female.getImageView().getImage()),
            new VitalsAttributes(
                RandomGenerator.generateGaussian(femaleVitals.weight(), maleVitals.weight(), RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(femaleVitals.height(), maleVitals.height(), RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(femaleVitals.lifeSpan(), maleVitals.lifeSpan(), RandomGenerator.GAUSSIAN_VARIANCE),
                SimulationSettings.getCurrentWeek(),
                RandomGenerator.generateGaussian(gender, femaleVitals.energyLoss(), maleVitals.energyLoss(), RandomGenerator.GAUSSIAN_VARIANCE)
            ),
            new MovementAttributes(
                female.getMovementAttributes().getPosX(),
                female.getMovementAttributes().getPosY(),
                0.0,
                0.0,
                0.0
            ),
            new HuntingAttributes(
                RandomGenerator.generateGaussian(gender, femaleHunting.huntAttempts(), maleHunting.huntAttempts(), 0.0),
                RandomGenerator.generateGaussian(gender, femaleHunting.energyGain(), maleHunting.energyGain(), RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(gender, femaleHunting.preyEaten(), maleHunting.preyEaten(), RandomGenerator.GAUSSIAN_VARIANCE)
            ),
            new ReproductionAttributes(
                female.getReproductionAttributes().sexualMaturityStart(),
                female.getReproductionAttributes().sexualMaturityEnd(),
                female.getReproductionAttributes().matingSeasonStart(),
                female.getReproductionAttributes().matingSeasonEnd(),
                female.getReproductionAttributes().matingCooldown(),
                female.getReproductionAttributes().gestationPeriod(),
                female.getReproductionAttributes().averageOffspring(),
                female.getReproductionAttributes().juvenileSurvivalRate(),
                female.getReproductionAttributes().matingSuccessRate(),
                female.getReproductionAttributes().matingAttempts()
            )
        );

        offspring.getPreySpecies().putAll(female.getPreySpecies());

        return offspring;

    }

    public void gestation(Species species, Organism organism) {

        organism.setGestationWeek(organism.getGestationWeek() + 1);

        ReproductionAttributes organismReproduction = organism.getReproductionAttributes();

        if (organism.getGestationWeek() >= organism.getReproductionAttributes().gestationPeriod()) {

            double baseOffspringCount = RandomGenerator.generateGaussian(organismReproduction.averageOffspring(), RandomGenerator.GAUSSIAN_VARIANCE);
            double offspringCount = Math.round(baseOffspringCount);

            if (organism.isImpersonatedOrganism()) {
                Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to " + offspringCount + " offsprings");
            }

            for (int i=0; i < (int) offspringCount; i++) {

                Organism offspring = birthing(organism, organism.getMate());

                if (RandomGenerator.random.nextDouble() >= organism.getReproductionAttributes().juvenileSurvivalRate()) {
                    juvenileDeath(organism, offspring);
                }

                else {
                    if (organism.isImpersonatedOrganism()) {
                        Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to a " + offspring.getSpeciesType() + " " + offspring.getGender());
                    }
                }

                species.getOrganisms().add(offspring);

            }

            organism.setReproductionStatus(ReproductionStatus.COOLDOWN);
            organism.setGestationWeek(0.0);
            organism.setMate(null);

            if (organism.isImpersonatedOrganism()) {
                Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " enters reproduction cooldown");
            }

        }

    }

    public void juvenileDeath(Organism organism, Organism offspring) {
        offspring.setOrganismStatus(OrganismStatus.DEAD);
        offspring.setOrganismDeathReason(OrganismDeathReason.JUVENILE_DEATH);

        if (organism.isImpersonatedOrganism()) {
            Log.log7("The offspring of " + organism.getSpeciesType() + " " + organism.getGender() + " suffers a juvenile death");
        }

        if (offspring.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.log7(offspring.getSpeciesType() + " " + offspring.getGender() + " suffers a juvenile death");
        }
    }

    public void findMate(Species species, Organism organism) {

        for (int i = 0; i < species.getOrganisms().size(); i++) {

            Organism mate = species.getOrganisms().get(i);

            if (mate.getGender() == Gender.MALE && mate.getReproductionStatus() != ReproductionStatus.NOT_MATURE ) {

                if (organism.isImpersonatedOrganism()) {
                    Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " finds a mate");
                }

                organism.setReproductionStatus(ReproductionStatus.PREGNANT);
                organism.setMate(mate);

                if (organism.isImpersonatedOrganism()) {
                    Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " starts pregnancy");
                }

                break;

            }
        }

    }

    public void matingCooldown(Organism organism) {

        organism.setCooldownWeek(organism.getCooldownWeek() + 1);

        if (organism.getCooldownWeek() >= organism.getReproductionAttributes().matingCooldown()) {

            organism.setReproductionStatus(ReproductionStatus.MATURE);
            organism.setCooldownWeek(0.0);

            if (organism.isImpersonatedOrganism()) {
                Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " finishes reproduction cooldown");
            }

        }

    }

    public void speciesReproduction(Species species) {

        for (int i = 0; i < species.getOrganisms().size(); i++) {

            Organism organism = species.getOrganisms().get(i);

            if (organism.getGender() == Gender.FEMALE) {

                if (organism.getReproductionStatus() == ReproductionStatus.COOLDOWN) {
                    matingCooldown(organism);
                }

                if (organism.getReproductionStatus() == ReproductionStatus.PREGNANT) {
                    gestation(species, organism);
                }

                if (organism.getReproductionStatus() == ReproductionStatus.MATURE) {
                    findMate(species, organism);
                }

            }
        }

    }

}