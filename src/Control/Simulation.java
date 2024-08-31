package Control;

import Entities.SpeciesAttributeValue;
import Enums.*;
import Entities.Organism;
import Entities.PreySpeciesType;
import Entities.Species;
import Environment.Ecosystem;
import Utils.Logger;
import Utils.RandomGenerator;

import java.util.ArrayList;

public class Simulation {

    private final Ecosystem ecosystem;
    private final SimulationSettings simulationSettings;

    public Simulation() {
        ecosystem = new Ecosystem();
        simulationSettings = new SimulationSettings();
    }

    public void initializeSpecies() {

        Species whiteTailedDeer = new Species(SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", "Mammalia", "Artiodactyla", "Cervidae", "Odocoileus", 5000, Diet.HERBIVORE);
        whiteTailedDeer.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 4.0, 5.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 100.0, 65.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.95, 0.85));
        whiteTailedDeer.addAttribute(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.ENERGY_LOST_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST_PER_WEEK, 0.30, 0.30));
        whiteTailedDeer.addAttribute(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, 0.0, 0.0));
        ecosystem.addSpecies(SpeciesType.WHITE_TAILED_DEER, whiteTailedDeer);

        Species moose = new Species(SpeciesType.MOOSE, "Moose", "Alces alces", "Mammalia", "Artiodactyla", "Cervidae", "Alces", 100, Diet.HERBIVORE);
        moose.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 13.0, 16.0));
        moose.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 520.0, 410.0));
        moose.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 1.80, 1.70));
        moose.addAttribute(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.ENERGY_LOST_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST_PER_WEEK, 0.25, 0.25));
        moose.addAttribute(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, 0.0, 0.0));
        ecosystem.addSpecies(SpeciesType.MOOSE, moose);

        Species grayWolf = new Species(SpeciesType.GRAY_WOLF, "Gray Wolf", "Canis lupus", "Mammalia", "Carnivora", "Canidae", "Canis", 100, Diet.CARNIVORE);
        grayWolf.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 7.0, 8.0));
        grayWolf.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 40.0, 35.0));
        grayWolf.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.80, 0.70));
        grayWolf.addAttribute(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, 5.0,  5.0));
        grayWolf.addAttribute(SpeciesAttribute.ENERGY_LOST_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST_PER_WEEK, 0.30, 0.30));
        grayWolf.addAttribute(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, 0.02, 0.022));
        grayWolf.addAttribute(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, 50.0, 45.0));
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, 0.75));
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.MOOSE, 0.25));
        ecosystem.addSpecies(SpeciesType.GRAY_WOLF, grayWolf);

        Species snowshoeHare = new Species(SpeciesType.SNOWSHOE_HARE, "Snowshoe Hare", "Lepus americanus", "Mammalia", "Lagomorpha", "Leporidae", "Lepus", 10000, Diet.HERBIVORE);
        snowshoeHare.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 5.0, 5.0));
        snowshoeHare.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 1.2, 1.4));
        snowshoeHare.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.35, 0.40));
        snowshoeHare.addAttribute(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.ENERGY_LOST_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST_PER_WEEK, 0.40, 0.40));
        snowshoeHare.addAttribute(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, 0.0, 0.0));
        ecosystem.addSpecies(SpeciesType.SNOWSHOE_HARE, snowshoeHare);

        Species beaver = new Species(SpeciesType.BEAVER, "Beaver", "Castor canadensis", "Mammalia", "Rodentia", "Castoridae", "Castor", 1000, Diet.HERBIVORE);
        beaver.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 12.0, 12.0));
        beaver.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 18, 16));
        beaver.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.35, 0.32));
        beaver.addAttribute(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, 0.0, 0.0));
        beaver.addAttribute(SpeciesAttribute.ENERGY_LOST_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST_PER_WEEK, 0.25, 0.25));
        beaver.addAttribute(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, 0.0, 0.0));
        beaver.addAttribute(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, 0.0, 0.0));
        ecosystem.addSpecies(SpeciesType.BEAVER, beaver);

        Species bobcat = new Species(SpeciesType.BOBCAT, "Bobcat", "Lynx rufus", "Mammalia", "Carnivora", "Felidae", "Lynx", 100, Diet.CARNIVORE);
        bobcat.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 12.0, 12.0));
        bobcat.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 13.0, 10.0));
        bobcat.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.45, 0.40));
        bobcat.addAttribute(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK, 6.0,  6.0));
        bobcat.addAttribute(SpeciesAttribute.ENERGY_LOST_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOST_PER_WEEK, 0.25, 0.25));
        bobcat.addAttribute(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG, 0.10, 0.10));
        bobcat.addAttribute(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, new SpeciesAttributeValue(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK, 10.0, 10.0));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.SNOWSHOE_HARE, 0.70));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.BEAVER, 0.20));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, 0.10));
        ecosystem.addSpecies(SpeciesType.BOBCAT, bobcat);

    }

    public void initialize() {

        initializeSpecies();
        Logger.logln("");
        Logger.logln("--------");
        Logger.logln("ECOSYSTEM");
        Logger.logln("");
        ecosystem.printSpeciesDetails();
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

                while (predatorOrganism.getEnergy() < 1.0 && huntingAttemptNumber < predatorOrganism.getHuntingAttemptsPerWeek())  {

                    Species preySpecies = preySpeciesChoice(predatorOrganism);

                    if (preySpecies != null) {

                        ArrayList<Organism> preyOrganisms = preySpecies.getAliveOrganisms();
                        int preySpeciesPopulation = preySpecies.getPopulation();

                        if (preySpeciesPopulation > 0) {

                            int preyOrganismSelected = RandomGenerator.random.nextInt(0, preySpeciesPopulation);

                            Organism preyOrganism = preyOrganisms.get(preyOrganismSelected);
                            double baseHuntSuccessRate = predatorOrganism.calculateHuntSuccessRate(preySpecies, preyOrganism);
                            double gaussian = RandomGenerator.random.nextGaussian();
                            double huntSuccessRate = baseHuntSuccessRate + (gaussian * baseHuntSuccessRate * 0.2);

                            if (RandomGenerator.random.nextDouble() <= huntSuccessRate) {

                                preyOrganism.setOrganismStatus(OrganismStatus.DEAD);
                                preyOrganism.setOrganismDeathReason(OrganismDeathReason.PREDATION);

                                double preyKgEaten = Math.max(preyOrganism.getWeight(), predatorOrganism.getMaxPreyKgEaten());
                                double baseEnergyGain = predatorOrganism.getEnergyGainPerPreyKg() * preyKgEaten;
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
            organism.setAge(organism.getAge() + (simulationSettings.getSimulationSpeedWeeks() / 52.0));

            if (organism.getAge() >= organism.getLifeSpan()) {
                organism.setOrganismStatus(OrganismStatus.DEAD);
                organism.setOrganismDeathReason(OrganismDeathReason.AGE);
            }
            else {

                // WAITING TO IMPLEMENT PLANTS AND NUTRITION FOR HERBIVORES, ASSUMING NO ENERGY LOSS FOR THEM
                if (organism.getDiet() != Diet.HERBIVORE) {
                    organism.setEnergy(organism.getEnergy() - organism.getEnergyLostPerWeek());

                    if (organism.getEnergy() <= 0.0) {
                        organism.setOrganismStatus(OrganismStatus.DEAD);
                        organism.setOrganismDeathReason(OrganismDeathReason.STARVATION);
                    }
                }
            }

        }

    }

    public void simulate() {

        simulationSettings.setCurrentWeek(simulationSettings.getCurrentWeek() + simulationSettings.getSimulationSpeedWeeks());
        Logger.logln("");
        Logger.logln("--------");
        Logger.logln("YEAR #" + simulationSettings.getYear() + " - WEEK #" + simulationSettings.getWeek());

        Logger.logln("");

        for (Species species : ecosystem.getSpeciesMap().values()) {
            simulateAging(species);
        }

        for (Species species : ecosystem.getSpeciesMap().values()) {
            simulateHunt(species);
        }

        ecosystem.printSpeciesDistribution();

    }

    public void run() {

        initialize();

        for (int w = 0; w < simulationSettings.getSimulationLengthWeeks(); w++) {
            simulate();
        }
    }

}