package Model.Simulation;

import Model.Animals.*;
import Model.Enums.*;
import Utils.Log;
import Utils.RandomGenerator;

public class ReproductionSimulation {

    public Organism birthing(Organism male, Organism female) {

        Gender gender = Gender.FEMALE;
        if (RandomGenerator.random.nextDouble() < 0.50) {
            gender = Gender.MALE;
        }

        Organism offspring = new Organism(
            female.getSpeciesType(),
            gender,
            female.getDiet(),
            0.0,
            new VitalsAttributes(
                RandomGenerator.generateGaussian(female.getVitalsAttributes().weight(), male.getVitalsAttributes().weight(), 0.2),
                RandomGenerator.generateGaussian(female.getVitalsAttributes().height(), male.getVitalsAttributes().height(), 0.2),
                RandomGenerator.generateGaussian(female.getVitalsAttributes().lifeSpan(), male.getVitalsAttributes().lifeSpan(), 0.2),
                SimulationSettings.getCurrentWeek(),
                RandomGenerator.generateGaussian(gender, female.getVitalsAttributes().energyLoss(), male.getVitalsAttributes().energyLoss(), 0.2)
            ),
            new HuntingAttributes(
                RandomGenerator.generateGaussian(gender, female.getHuntingAttributes().huntAttempts(), male.getHuntingAttributes().huntAttempts(), 0.0),
                RandomGenerator.generateGaussian(gender, female.getHuntingAttributes().energyGain(), male.getHuntingAttributes().energyGain(), 0.2),
                RandomGenerator.generateGaussian(gender, female.getHuntingAttributes().preyEaten(), male.getHuntingAttributes().preyEaten(), 0.2)
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

        if (organism.getGestationWeek() >= organism.getReproductionAttributes().gestationPeriod()) {

            double offspringCount = Math.round(RandomGenerator.generateGaussian(organism.getReproductionAttributes().averageOffspring(), 0.2));

            if (organism.isImpersonatedOrganism()) {
                Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to " + offspringCount + " offsprings");
            }

            for (int i=0; i < (int) offspringCount; i++) {

                Organism offspring = birthing(organism, organism.getMate());

                if (RandomGenerator.random.nextDouble() >= organism.getReproductionAttributes().juvenileSurvivalRate()) {
                    juvenileDeath(organism, offspring);
                }

                else {
                    if (organism.isImpersonatedOrganism()) {
                        Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to a " + offspring.getSpeciesType() + " " + offspring.getGender());
                    }
                }

                species.getOrganisms().add(offspring);

            }

            organism.setReproductionStatus(ReproductionStatus.COOLDOWN);
            organism.setGestationWeek(0.0);
            organism.setMate(null);

            if (organism.isImpersonatedOrganism()) {
                Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters reproduction cooldown");
            }

        }

    }

    public void juvenileDeath(Organism organism, Organism offspring) {
        offspring.setOrganismStatus(OrganismStatus.DEAD);
        offspring.setOrganismDeathReason(OrganismDeathReason.JUVENILE_DEATH);

        if (organism.isImpersonatedOrganism()) {
            Log.logln("The offspring of " + organism.getSpeciesType() + " " + organism.getGender() + " suffers a juvenile death");
        }

        if (offspring.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.logln(offspring.getSpeciesType() + " " + offspring.getGender() + " suffers a juvenile death");
        }
    }

    public void findMate(Species species, Organism organism) {

        boolean foundMate = false;

        for (Organism mate : species.getAliveOrganisms()) {

            if (!foundMate && mate.getGender() == Gender.MALE && mate.getReproductionStatus() != ReproductionStatus.NOT_MATURE ) {

                foundMate = true;

                if (organism.isImpersonatedOrganism()) {
                    Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " finds a mate");
                }

                organism.setReproductionStatus(ReproductionStatus.PREGNANT);
                organism.setMate(mate);

                if (organism.isImpersonatedOrganism()) {
                    Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " starts pregnancy");
                }

            }
        }

    }

    public void matingCooldown(Organism organism) {

        organism.setCooldownWeek(organism.getCooldownWeek() + 1);

        if (organism.getCooldownWeek() >= organism.getReproductionAttributes().matingCooldown()) {

            organism.setReproductionStatus(ReproductionStatus.MATURE);
            organism.setCooldownWeek(0.0);

            if (organism.isImpersonatedOrganism()) {
                Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " finishes reproduction cooldown");
            }

        }

    }

    public void speciesReproduction(Species species) {

        for (Organism organism : species.getAliveOrganisms()) {

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