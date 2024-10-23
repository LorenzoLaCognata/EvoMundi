package model.simulation.animals;

import javafx.scene.image.ImageView;
import model.environment.animals.attributes.*;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.ReproductionStatus;
import model.environment.common.base.Ecosystem;
import model.environment.common.enums.OrganismStatus;
import model.simulation.base.SimulationSettings;
import utils.Log;
import utils.RandomGenerator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AnimalReproductionSimulation {

    private final Predicate<AnimalOrganism> animalOrganismIsAliveFemaleMature =
            animalOrganism ->
                    animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                    animalOrganism.getGender() == Gender.FEMALE &&
                    animalOrganism.getReproductionStatus() == ReproductionStatus.MATURE;

    private final Predicate<AnimalOrganism> animalOrganismIsAliveFemalePregnant =
            animalOrganism ->
                    animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                            animalOrganism.getGender() == Gender.FEMALE &&
                            animalOrganism.getReproductionStatus() == ReproductionStatus.PREGNANT;

    private final Predicate<AnimalOrganism> animalOrganismIsAliveFemaleCooldown =
            animalOrganism ->
                    animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                            animalOrganism.getGender() == Gender.FEMALE &&
                            animalOrganism.getReproductionStatus() == ReproductionStatus.COOLDOWN;

    private final BiConsumer<AnimalOrganism, AnimalOrganism> animalOrganismMateAttemptConsumer = this::animalOrganismMateAttempt;
    private final Consumer<AnimalOrganism> gestationConsumer = this::animalOrganismGestation;
    private final BiConsumer<AnimalOrganism, AnimalSpecies> matingCooldownConsumer = this::animalOrganismMatingCooldown;

    private AnimalVitalsAttributes offspringAnimalVitals(AnimalOrganism female, AnimalOrganism male, Gender gender) {

        AnimalVitalsAttributes femaleVitals = female.getOrganismAttributes().animalVitalsAttributes();
        AnimalVitalsAttributes maleVitals = male.getOrganismAttributes().animalVitalsAttributes();

        return new AnimalVitalsAttributes(
                RandomGenerator.generateGaussian(femaleVitals.weight(), maleVitals.weight(), RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(femaleVitals.height(), maleVitals.height(), RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(femaleVitals.lifeSpan(), maleVitals.lifeSpan(), RandomGenerator.GAUSSIAN_VARIANCE),
                SimulationSettings.getCurrentWeek(),
                RandomGenerator.generateGaussian(gender, femaleVitals.energyLoss(), maleVitals.energyLoss(), RandomGenerator.GAUSSIAN_VARIANCE)
        );
    }

    private AnimalNutritionAttributes offspringAnimalNutritionAttributes(AnimalOrganism female, AnimalOrganism male, Gender gender) {

        AnimalNutritionAttributes femaleNutrition = female.getOrganismAttributes().animalNutritionAttributes();
        AnimalNutritionAttributes maleNutrition = male.getOrganismAttributes().animalNutritionAttributes();

        return new AnimalNutritionAttributes(
                RandomGenerator.generateGaussian(gender, femaleNutrition.huntAttempts(), maleNutrition.huntAttempts(), 0.0),
                RandomGenerator.generateGaussian(gender, femaleNutrition.energyGain(), maleNutrition.energyGain(), RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(gender, femaleNutrition.preyEaten(), maleNutrition.preyEaten(), RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(gender, femaleNutrition.plantConsumption(), maleNutrition.plantConsumption(), RandomGenerator.GAUSSIAN_VARIANCE)
        );

    }

    private static AnimalReproductionAttributes offrspringAnimalReproductionAttributes(AnimalOrganism female) {
        return new AnimalReproductionAttributes(
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
    }

    private static AnimalPositionAttributes offspringAnimalPositionAttributes(AnimalOrganism female) {
        return new AnimalPositionAttributes(
                female.getOrganismAttributes().animalPositionAttributes().getLatitude(),
                female.getOrganismAttributes().animalPositionAttributes().getLongitude()
        );
    }

    private AnimalOrganismAttributes offspringAnimalOrganismAttributes(AnimalOrganism male, AnimalOrganism female, Gender gender) {
        AnimalVitalsAttributes offspringAnimalVitalsAttributes = offspringAnimalVitals(female, male, gender);
        AnimalNutritionAttributes offspringAnimalNutritionAttributes = offspringAnimalNutritionAttributes(female, male, gender);
        AnimalReproductionAttributes offspringAnimalReproductionAttributes = offrspringAnimalReproductionAttributes(female);
        AnimalPositionAttributes offspringAnimalPositionAttributes = offspringAnimalPositionAttributes(female);

        return new AnimalOrganismAttributes(
                offspringAnimalVitalsAttributes,
                offspringAnimalPositionAttributes,
                offspringAnimalNutritionAttributes,
                offspringAnimalReproductionAttributes
        );
    }

    public AnimalOrganism animalOffspringGeneration(AnimalOrganism male, AnimalOrganism female) {

        Gender gender = (RandomGenerator.random.nextDouble() < 0.50) ? Gender.FEMALE : Gender.MALE;

        AnimalOrganismAttributes offspringAnimalOrganismAttributes = offspringAnimalOrganismAttributes(male, female, gender);

        AnimalOrganism offspring = new AnimalOrganism(
            female.getAnimalSpecies(),
            gender,
            female.getDiet(),
            OrganismStatus.NEWBORN,
            0.0,
            new ImageView(female.getOrganismIcons().getSpeciesIcon().getImage()),
            offspringAnimalOrganismAttributes
        );

        offspring.getPreyAnimalSpecies().putAll(female.getPreyAnimalSpecies());

        return offspring;

    }

    // TODO: check juvenile deaths that appear to never happen anymore

    public void animalOrganismGestation(AnimalOrganism animalOrganism) {

        animalOrganism.setGestationWeek(animalOrganism.getGestationWeek() + 1);

        if (animalOrganism.getGestationWeek() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().gestationPeriod()) {
            animalOrganismGiveBirth(animalOrganism);
        }

    }

    private void animalOrganismGiveBirth(AnimalOrganism animalOrganism) {

        AnimalReproductionAttributes organismReproduction = animalOrganism.getOrganismAttributes().animalReproductionAttributes();

        double baseOffspringCount = RandomGenerator.generateGaussian(organismReproduction.averageOffspring(), RandomGenerator.GAUSSIAN_VARIANCE);
        double offspringCount = Math.round(baseOffspringCount);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " gives birth to " + offspringCount + " offsprings");
        }

        for (int i=0; i < (int) offspringCount; i++) {
            animalOrganismOffspringBirth(animalOrganism);
        }

        animalOrganism.setReproductionStatus(ReproductionStatus.COOLDOWN);
        animalOrganism.setGestationWeek(0.0);
        animalOrganism.setMate(null);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " enters reproduction cooldown");
        }
    }

    private void animalOrganismOffspringBirth(AnimalOrganism animalOrganism) {

        AnimalOrganism offspring = animalOffspringGeneration(animalOrganism, animalOrganism.getMate());

        if (RandomGenerator.random.nextDouble() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().juvenileSurvivalRate()) {
            animalOrganismJuvenileDeath(animalOrganism, offspring);
        }

        else {

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " gives birth to a " + offspring.getGender() + " " + offspring.getAnimalSpecies() + " " + offspring.getId());
            }

            offspring.getAnimalSpecies().addNewbornOrganism(offspring);
        }

    }

    public void animalOrganismJuvenileDeath(AnimalOrganism animalOrganism, AnimalOrganism offspring) {
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

    public void animalOrganismMateAttempt(AnimalOrganism mate, AnimalOrganism animalOrganism) {

        if (animalOrganism.getReproductionStatus() != ReproductionStatus.PREGNANT &&
                mate.getGender() == Gender.MALE &&
                mate.getReproductionStatus() != ReproductionStatus.NOT_MATURE) {

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " finds a mate");
            }

            animalOrganism.setReproductionStatus(ReproductionStatus.PREGNANT);
            animalOrganism.setMate(mate);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " starts pregnancy");
            }

        }

    }

    public void animalOrganismMatingCooldown(AnimalOrganism animalOrganism, AnimalSpecies ignored) {

        animalOrganism.setCooldownWeek(animalOrganism.getCooldownWeek() + 1);

        if (animalOrganism.getCooldownWeek() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().matingCooldown()) {

            animalOrganism.setReproductionStatus(ReproductionStatus.MATURE);
            animalOrganism.setCooldownWeek(0.0);

            if (animalOrganism.isImpersonatedOrganism()) {
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " finishes reproduction cooldown");
            }

        }

    }

    public void ecosystemReproduction(Ecosystem ecosystem) {
        ecosystem.getIterationManager().iterateAnimalOrganismsBiConsumer(ecosystem, animalOrganismIsAliveFemaleCooldown, matingCooldownConsumer);
        ecosystem.getIterationManager().iterateAnimalOrganismsConsumer(ecosystem, animalOrganismIsAliveFemalePregnant, gestationConsumer);
        ecosystem.getIterationManager().iterateAnimalOrganismsPerEachAnimalOrganism(ecosystem, animalOrganismIsAliveFemaleMature, animalOrganismMateAttemptConsumer);
    }

}