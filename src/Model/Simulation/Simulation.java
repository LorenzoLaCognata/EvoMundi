package Model.Simulation;

import Model.Enums.*;
import Model.Entities.SpeciesAttributeValue;
import Model.Entities.Organism;
import Model.Entities.PreySpeciesType;
import Model.Entities.Species;
import Model.Environment.Ecosystem;
import Utils.Logger;
import Utils.RandomGenerator;

import java.util.ArrayList;

public class Simulation {

    private final Ecosystem ecosystem;

    public Simulation() {
        ecosystem = new Ecosystem();
    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    public void initializeSpecies() {

        Species whiteTailedDeer = new Species(SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", "Mammalia", "Artiodactyla", "Cervidae", "Odocoileus", Diet.HERBIVORE);
        whiteTailedDeer.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 5000, 5000));
        whiteTailedDeer.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 4.0, 5.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 100.0, 65.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.95, 0.85));
        whiteTailedDeer.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.ENERGY_LOST, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST, 0.30, 0.30));
        whiteTailedDeer.addAttribute(SpeciesAttribute.ENERGY_GAIN, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.PREY_EATEN, new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, 1.5));
        whiteTailedDeer.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, 10.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_SEASON_START, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, 40.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_SEASON_END, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, 52.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN, new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, 38.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.GESTATION_PERIOD, new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, 28.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING, new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, 1.5));
        whiteTailedDeer.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, 0.50));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE, new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, 0.70));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, 3.0));
        ecosystem.addSpecies(SpeciesType.WHITE_TAILED_DEER, whiteTailedDeer);

        Species moose = new Species(SpeciesType.MOOSE, "Moose", "Alces alces", "Mammalia", "Artiodactyla", "Cervidae", "Alces", Diet.HERBIVORE);
        moose.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 100, 100));
        moose.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 13.0, 16.0));
        moose.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 520.0, 410.0));
        moose.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 1.80, 1.70));
        moose.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.ENERGY_LOST, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST, 0.25, 0.25));
        moose.addAttribute(SpeciesAttribute.ENERGY_GAIN, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.PREY_EATEN, new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, 2.0));
        moose.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, 12.0));
        moose.addAttribute(SpeciesAttribute.MATING_SEASON_START, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, 36.0));
        moose.addAttribute(SpeciesAttribute.MATING_SEASON_END, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, 44.0));
        moose.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN, new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, 42.0));
        moose.addAttribute(SpeciesAttribute.GESTATION_PERIOD, new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, 36.0));
        moose.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING, new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, 1.0));
        moose.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, 0.70));
        moose.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE, new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, 0.65));
        moose.addAttribute(SpeciesAttribute.MATING_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, 2.0));
        ecosystem.addSpecies(SpeciesType.MOOSE, moose);

        Species grayWolf = new Species(SpeciesType.GRAY_WOLF, "Gray Wolf", "Canis lupus", "Mammalia", "Carnivora", "Canidae", "Canis", Diet.CARNIVORE);
        grayWolf.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 100, 100));
        grayWolf.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 7.0, 8.0));
        grayWolf.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 40.0, 35.0));
        grayWolf.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.80, 0.70));
        grayWolf.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 5.0,  5.0));
        grayWolf.addAttribute(SpeciesAttribute.ENERGY_LOST, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST, 0.30, 0.30));
        grayWolf.addAttribute(SpeciesAttribute.ENERGY_GAIN, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.02, 0.022));
        grayWolf.addAttribute(SpeciesAttribute.PREY_EATEN, new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 50.0, 45.0));
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, 0.75));
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.MOOSE, 0.25));
        grayWolf.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, 2.0));
        grayWolf.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, 10.0));
        grayWolf.addAttribute(SpeciesAttribute.MATING_SEASON_START, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, 1.0));
        grayWolf.addAttribute(SpeciesAttribute.MATING_SEASON_END, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, 13.0));
        grayWolf.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN, new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, 38.0));
        grayWolf.addAttribute(SpeciesAttribute.GESTATION_PERIOD, new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, 20.0));
        grayWolf.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING, new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, 3.0));
        grayWolf.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, 0.60));
        grayWolf.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE, new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, 0.80));
        grayWolf.addAttribute(SpeciesAttribute.MATING_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, 2.0));
        ecosystem.addSpecies(SpeciesType.GRAY_WOLF, grayWolf);

        Species snowshoeHare = new Species(SpeciesType.SNOWSHOE_HARE, "Snowshoe Hare", "Lepus americanus", "Mammalia", "Lagomorpha", "Leporidae", "Lepus", Diet.HERBIVORE);
        snowshoeHare.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 10000, 10000));
        snowshoeHare.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 5.0, 5.0));
        snowshoeHare.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 1.2, 1.4));
        snowshoeHare.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.35, 0.40));
        snowshoeHare.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.ENERGY_LOST, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST, 0.40, 0.40));
        snowshoeHare.addAttribute(SpeciesAttribute.ENERGY_GAIN, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.PREY_EATEN, new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, 1.0));
        snowshoeHare.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, 5.0));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_SEASON_START, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, 5.0));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_SEASON_END, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, 30.0));
        snowshoeHare.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN, new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, 5.0));
        snowshoeHare.addAttribute(SpeciesAttribute.GESTATION_PERIOD, new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, 5.0));
        snowshoeHare.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING, new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, 4.5));
        snowshoeHare.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, 0.40));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE, new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, 0.85));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, 4.0));
        //ecosystem.addSpecies(SpeciesType.SNOWSHOE_HARE, snowshoeHare);

        Species europeanBeaver = new Species(SpeciesType.EUROPEAN_BEAVER, "European Beaver", "Castor fiber", "Mammalia", "Rodentia", "Castoridae", "Castor", Diet.HERBIVORE);
        europeanBeaver.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 1000, 1000));
        europeanBeaver.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 12.0, 12.0));
        europeanBeaver.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 18, 16));
        europeanBeaver.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.35, 0.32));
        europeanBeaver.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        europeanBeaver.addAttribute(SpeciesAttribute.ENERGY_LOST, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST, 0.25, 0.25));
        europeanBeaver.addAttribute(SpeciesAttribute.ENERGY_GAIN, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        europeanBeaver.addAttribute(SpeciesAttribute.PREY_EATEN, new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        europeanBeaver.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, 1.0));
        europeanBeaver.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, 12.0));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_SEASON_START, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, 1.0));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_SEASON_END, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, 9.0));
        europeanBeaver.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN, new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, 42.0));
        europeanBeaver.addAttribute(SpeciesAttribute.GESTATION_PERIOD, new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, 9.0));
        europeanBeaver.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING, new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, 2.0));
        europeanBeaver.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, 0.45));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE, new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, 0.75));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, 2.0));
        ecosystem.addSpecies(SpeciesType.EUROPEAN_BEAVER, europeanBeaver);

        Species bobcat = new Species(SpeciesType.BOBCAT, "Bobcat", "Lynx rufus", "Mammalia", "Carnivora", "Felidae", "Lynx", Diet.CARNIVORE);
        bobcat.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 100, 100));
        bobcat.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 12.0, 12.0));
        bobcat.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 13.0, 10.0));
        bobcat.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.45, 0.40));
        bobcat.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 6.0,  6.0));
        bobcat.addAttribute(SpeciesAttribute.ENERGY_LOST, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST, 0.25, 0.25));
        bobcat.addAttribute(SpeciesAttribute.ENERGY_GAIN, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.10, 0.10));
        bobcat.addAttribute(SpeciesAttribute.PREY_EATEN, new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 10.0, 10.0));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.SNOWSHOE_HARE, 0.70));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.EUROPEAN_BEAVER, 0.20));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, 0.10));
        bobcat.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, 2.0));
        bobcat.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END, new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, 10.0));
        bobcat.addAttribute(SpeciesAttribute.MATING_SEASON_START, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, 5.0));
        bobcat.addAttribute(SpeciesAttribute.MATING_SEASON_END, new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, 13.0));
        bobcat.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN, new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, 42.0));
        bobcat.addAttribute(SpeciesAttribute.GESTATION_PERIOD, new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, 9.0));
        bobcat.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING, new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, 5.0));
        bobcat.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, 0.55));
        bobcat.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE, new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, 0.80));
        bobcat.addAttribute(SpeciesAttribute.MATING_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, 2.0));
        ecosystem.addSpecies(SpeciesType.BOBCAT, bobcat);

    }

    public void initialize() {

        initializeSpecies();
        Logger.logln("");
        Logger.logln("--------");
        Logger.logln("ECOSYSTEM");
        Logger.logln("");
        ecosystem.printSpeciesDetails(LogStatus.INACTIVE);
        Logger.logln("--------");

    }

    public Species preySpeciesChoice(Organism predatorOrganism) {

        if (!predatorOrganism.getPreySpecies().isEmpty()) {

            double preySpeciesTypeSelected = RandomGenerator.random.nextDouble();
            double preySpeciesTypeSelectorIndex = 0.0;

            for (PreySpeciesType preySpeciesType : predatorOrganism.getPreySpecies().values()) {

                if (preySpeciesTypeSelected >= preySpeciesTypeSelectorIndex && preySpeciesTypeSelected < preySpeciesTypeSelectorIndex + preySpeciesType.getPreferenceRate()) {
                    return ecosystem.getSpecies(preySpeciesType.getSpeciesType());
                }

                else {
                    preySpeciesTypeSelectorIndex = preySpeciesTypeSelectorIndex + preySpeciesType.getPreferenceRate();
                }

            }

        }

        return null;

    }

    public void simulateHunt(Species predatorSpecies) {

        for (Organism predatorOrganism : predatorSpecies.getAliveOrganisms()) {

            if (!predatorOrganism.getPreySpecies().isEmpty()) {

                int huntingAttemptNumber = 0;

                while (predatorOrganism.getEnergy() < 1.0 && huntingAttemptNumber < predatorOrganism.getHuntAttempts())  {

                    Species preySpecies = preySpeciesChoice(predatorOrganism);

                    if (preySpecies != null) {

                        ArrayList<Organism> preyOrganisms = preySpecies.getAliveOrganisms();
                        int preySpeciesPopulation = preySpecies.getPopulation();

                        if (preySpeciesPopulation > 0) {

                            int preyOrganismSelected = RandomGenerator.random.nextInt(0, preySpeciesPopulation);

                            Organism preyOrganism = preyOrganisms.get(preyOrganismSelected);
                            double huntSuccessRate = RandomGenerator.generateGaussian(predatorOrganism.calculateHuntSuccessRate(preySpecies, preyOrganism), 0.2);

                            if (RandomGenerator.random.nextDouble() <= huntSuccessRate) {

                                if (predatorOrganism.isImpersonatedOrganism()) {
                                    Logger.logln(predatorOrganism.getSpeciesType() + " " + predatorOrganism.getGender() + " hunts a " + preyOrganism.getSpeciesType());
                                }

                                preyOrganism.setOrganismStatus(OrganismStatus.DEAD);
                                preyOrganism.setOrganismDeathReason(OrganismDeathReason.PREDATION);

                                if (preyOrganism.isImpersonatedOrganism()) {
                                    SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
                                    Logger.logln(preyOrganism.getSpeciesType() + " " + preyOrganism.getGender() + " is hunted by a " + predatorOrganism.getSpeciesType());
                                }

                                double preyKgEaten = Math.max(preyOrganism.getWeight(), predatorOrganism.getPreyEaten());
                                double baseEnergyGain = predatorOrganism.getEnergyGain() * preyKgEaten;
                                double energyGain = Math.min(baseEnergyGain, 1.0 - predatorOrganism.getEnergy());
                                predatorOrganism.setEnergy(predatorOrganism.getEnergy() + energyGain);

                            }
                        }
                    }

                    huntingAttemptNumber++;

                }

            }
        }
    }

    public void simulateAging(Species species) {

        for (Organism organism : species.getAliveOrganisms()) {
            organism.setAge(organism.getAge() + (SimulationSettings.getSimulationSpeedWeeks() / 52.0));

            if (organism.getAge() >= organism.getLifeSpan()) {
                organism.setOrganismStatus(OrganismStatus.DEAD);
                organism.setOrganismDeathReason(OrganismDeathReason.AGE);

                if (organism.isImpersonatedOrganism()) {
                    SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
                    Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " dies by age");
                }

            }
            else {

                if (organism.getGender() == Gender.FEMALE && organism.getReproductionStatus() == ReproductionStatus.MATURE && organism.getAge() >= organism.getSexualMaturityEnd()) {
                    organism.setReproductionStatus(ReproductionStatus.MENOPAUSE);

                    if (organism.isImpersonatedOrganism()) {
                        Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters menopause");
                    }

                }

                if (organism.getReproductionStatus() == ReproductionStatus.NOT_MATURE && organism.getAge() >= organism.getSexualMaturityStart()) {
                    organism.setReproductionStatus(ReproductionStatus.MATURE);

                    if (organism.isImpersonatedOrganism()) {
                        Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters sexual maturity");
                    }

                }

                // WAITING TO IMPLEMENT PLANTS AND NUTRITION FOR HERBIVORES, ASSUMING NO ENERGY LOSS FOR THEM
                if (organism.getDiet() != Diet.HERBIVORE) {
                    organism.setEnergy(organism.getEnergy() - organism.getEnergyLost());

                    if (organism.getEnergy() <= 0.0) {
                        organism.setOrganismStatus(OrganismStatus.DEAD);
                        organism.setOrganismDeathReason(OrganismDeathReason.STARVATION);

                        if (organism.isImpersonatedOrganism()) {
                            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
                            Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " dies by starvation");
                        }

                    }
                }
            }

        }

    }

    public Organism generateInheritance(Organism male, Organism female) {

        Gender gender = Gender.FEMALE;
        if (RandomGenerator.random.nextDouble() < 0.50) {
            gender = Gender.MALE;
        }

        Organism offspring = new Organism(
            female.getSpeciesType(),
            gender,
            female.getDiet(),
            RandomGenerator.generateGaussian(female.getWeight(), male.getWeight(), 0.2),
            RandomGenerator.generateGaussian(female.getHeight(), male.getHeight(), 0.2),
            RandomGenerator.generateGaussian(female.getLifeSpan(), male.getLifeSpan(), 0.2),
            SimulationSettings.getCurrentWeek(),
            0.0,
            RandomGenerator.generateGaussian(gender, female.getHuntAttempts(), male.getHuntAttempts(), 0.0),
            RandomGenerator.generateGaussian(gender, female.getEnergyLost(), male.getEnergyLost(), 0.2),
            RandomGenerator.generateGaussian(gender, female.getEnergyGain(), male.getEnergyGain(), 0.2),
            RandomGenerator.generateGaussian(gender, female.getPreyEaten(), male.getPreyEaten(), 0.2),
            female.getSexualMaturityStart(),
            female.getSexualMaturityEnd(),
            female.getMatingSeasonStart(),
            female.getMatingSeasonEnd(),
            female.getPregnancyCooldown(),
            female.getGestationPeriod(),
            female.getAverageOffspring(),
            female.getJuvenileSurvivalRate(),
            female.getMatingSuccessRate(),
            female.getMatingAttempts()
        );

        offspring.getPreySpecies().putAll(female.getPreySpecies());

        return offspring;

    }

    public void simulateReproduction(Species species) {

        for (Organism organism : species.getAliveOrganisms()) {

            if (organism.getGender() == Gender.FEMALE) {

                if (organism.getReproductionStatus() == ReproductionStatus.COOLDOWN) {
                    organism.setCooldownWeek(organism.getCooldownWeek() + 1);

                    if (organism.getCooldownWeek() >= organism.getPregnancyCooldown()) {

                        organism.setReproductionStatus(ReproductionStatus.MATURE);
                        organism.setCooldownWeek(0.0);

                        if (organism.isImpersonatedOrganism()) {
                            Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " finishes reproduction cooldown");
                        }

                    }

                }

                if (organism.getReproductionStatus() == ReproductionStatus.PREGNANT) {
                    organism.setGestationWeek(organism.getGestationWeek() + 1);

                    if (organism.getGestationWeek() >= organism.getGestationPeriod()) {

                        double offspringCount = Math.round(RandomGenerator.generateGaussian(organism.getAverageOffspring(), 0.2));

                        if (organism.isImpersonatedOrganism()) {
                            Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to " + offspringCount + " offsprings");
                        }

                        for (int i=0; i < (int) offspringCount; i++) {

                            Organism offspring = generateInheritance(organism, organism.getMate());

                            if (RandomGenerator.random.nextDouble() >= organism.getJuvenileSurvivalRate()) {
                                offspring.setOrganismStatus(OrganismStatus.DEAD);
                                offspring.setOrganismDeathReason(OrganismDeathReason.JUVENILE_DEATH);

                                if (organism.isImpersonatedOrganism()) {
                                    Logger.logln("The offspring of " + organism.getSpeciesType() + " " + organism.getGender() + " suffers a juvenile death");
                                }

                                if (offspring.isImpersonatedOrganism()) {
                                    SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
                                    Logger.logln(offspring.getSpeciesType() + " " + offspring.getGender() + " suffers a juvenile death");
                                }

                            }

                            else {

                                if (organism.isImpersonatedOrganism()) {
                                    Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to a " + offspring.getSpeciesType() + " " + offspring.getGender());
                                }

                            }

                            species.getOrganisms().add(offspring);

                        }

                        organism.setReproductionStatus(ReproductionStatus.COOLDOWN);
                        organism.setGestationWeek(0.0);
                        organism.setMate(null);

                        if (organism.isImpersonatedOrganism()) {
                            Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters reproduction cooldown");
                        }

                    }

                }

                if (organism.getReproductionStatus() == ReproductionStatus.MATURE) {

                    boolean foundMate = false;

                    for (Organism mate : species.getAliveOrganisms()) {

                        if (!foundMate && mate.getGender() == Gender.MALE) {

                            if (mate.getReproductionStatus() != ReproductionStatus.NOT_MATURE ) {

                                foundMate = true;

                                if (organism.isImpersonatedOrganism()) {
                                    Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " finds a mate");
                                }

                                organism.setReproductionStatus(ReproductionStatus.PREGNANT);
                                organism.setMate(mate);

                                if (organism.isImpersonatedOrganism()) {
                                    Logger.logln(organism.getSpeciesType() + " " + organism.getGender() + " starts pregnancy");
                                }

                            }

                        }
                    }
                }

            }
        }

    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.getSimulationSpeedWeeks());
        Logger.logln("");
        Logger.logln("--------");
        Logger.logln("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        Logger.logln("");

        for (Species species : ecosystem.getSpeciesMap().values()) {
            simulateAging(species);
        }

        for (Species species : ecosystem.getSpeciesMap().values()) {
            simulateHunt(species);
        }

        for (Species species : ecosystem.getSpeciesMap().values()) {
            simulateReproduction(species);
        }

        ecosystem.printSpeciesDistribution(LogStatus.ACTIVE);

    }

}