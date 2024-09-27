package model.simulation;

import javafx.scene.image.ImageView;
import model.animals.*;
import model.enums.*;
import utils.Log;
import utils.RandomGenerator;

public class ReproductionSimulation {

    public Organism birthing(Organism male, Organism female) {

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

        Organism offspring = new Organism(
            female.getSpeciesType(),
            gender,
            female.getDiet(),
            0.0,
            new ImageView(female.getImageView().getImage()),
            offspringOrganismAttributes
        );

        offspring.getPreySpecies().putAll(female.getPreySpecies());

        return offspring;

    }

    public void gestation(Species species, Organism organism) {

        organism.setGestationWeek(organism.getGestationWeek() + 1);

        ReproductionAttributes organismReproduction = organism.getOrganismAttributes().reproductionAttributes();

        if (organism.getGestationWeek() >= organism.getOrganismAttributes().reproductionAttributes().gestationPeriod()) {

            double baseOffspringCount = RandomGenerator.generateGaussian(organismReproduction.averageOffspring(), RandomGenerator.GAUSSIAN_VARIANCE);
            double offspringCount = Math.round(baseOffspringCount);

            if (organism.isImpersonatedOrganism()) {
                Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to " + offspringCount + " offsprings");
            }

            for (int i=0; i < (int) offspringCount; i++) {

                Organism offspring = birthing(organism, organism.getMate());

                if (RandomGenerator.random.nextDouble() >= organism.getOrganismAttributes().reproductionAttributes().juvenileSurvivalRate()) {
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

        if (organism.getCooldownWeek() >= organism.getOrganismAttributes().reproductionAttributes().matingCooldown()) {

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