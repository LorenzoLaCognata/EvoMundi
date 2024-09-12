package Model.Simulation;

import Model.Entities.*;
import Model.Enums.*;
import Model.Environment.Ecosystem;
import Utils.Log;
import Utils.RandomGenerator;

import java.util.List;

public class Simulation {

    private final Ecosystem ecosystem;

    public Simulation() {
        ecosystem = new Ecosystem();
    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    public void initializeSpecies() {

        TaxonomyClass mammalia = new TaxonomyClass("Mammalia");
        TaxonomyOrder artiodactyla = new TaxonomyOrder("Artiodactyla");
        TaxonomyFamily cervidae = new TaxonomyFamily("Cervidae");
        TaxonomyGenus odocoileus = new TaxonomyGenus("Odocoileus");
        SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, odocoileus);

        Species whiteTailedDeer = new Species(odocoileusTaxonomy, SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", Diet.HERBIVORE);
        whiteTailedDeerAttributes(whiteTailedDeer);
        ecosystem.addSpecies(SpeciesType.WHITE_TAILED_DEER, whiteTailedDeer);

        TaxonomyGenus alces = new TaxonomyGenus("Alces");
        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, alces);
        Species moose = new Species(alcesTaxonomy, SpeciesType.MOOSE, "Moose", "Alces alces", Diet.HERBIVORE);
        mooseAttributes(moose);
        ecosystem.addSpecies(SpeciesType.MOOSE, moose);

        TaxonomyOrder carnivora = new TaxonomyOrder("Carnivora");
        TaxonomyFamily canidae = new TaxonomyFamily("Canidae");
        TaxonomyGenus canis = new TaxonomyGenus("Canis");
        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, canidae, canis);
        Species grayWolf = new Species(canisTaxonomy, SpeciesType.GRAY_WOLF, "Gray Wolf", "Canis lupus", Diet.CARNIVORE);
        grayWolfAttributes(grayWolf);
        ecosystem.addSpecies(SpeciesType.GRAY_WOLF, grayWolf);

        TaxonomyOrder lagomorpha = new TaxonomyOrder("Lagomorpha");
        TaxonomyFamily leporidae = new TaxonomyFamily("Leporidae");
        TaxonomyGenus lepus = new TaxonomyGenus("Lepus");
        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(mammalia, lagomorpha, leporidae, lepus);
        Species snowshoeHare = new Species(lepusTaxonomy, SpeciesType.SNOWSHOE_HARE, "Snowshoe Hare", "Lepus americanus", Diet.HERBIVORE);
        snowshoeHareAttributes(snowshoeHare);
        // TODO: ecosystem.addSpecies(SpeciesType.SNOWSHOE_HARE, snowshoeHare);

        TaxonomyOrder rodentia = new TaxonomyOrder("Rodentia");
        TaxonomyFamily castoridae = new TaxonomyFamily("Castoridae");
        TaxonomyGenus castor = new TaxonomyGenus("Castor");
        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(mammalia, rodentia, castoridae, castor);
        Species europeanBeaver = new Species(castorTaxonomy, SpeciesType.EUROPEAN_BEAVER, "European Beaver", "Castor fiber", Diet.HERBIVORE);
        europeanBeaverAttributes(europeanBeaver);
        ecosystem.addSpecies(SpeciesType.EUROPEAN_BEAVER, europeanBeaver);

        TaxonomyFamily felidae = new TaxonomyFamily("Felidae");
        TaxonomyGenus lynx = new TaxonomyGenus("Lynx");
        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, felidae, lynx);
        Species bobcat = new Species(lynxTaxonomy, SpeciesType.BOBCAT, "Bobcat", "Lynx rufus", Diet.CARNIVORE);
        bobcatAttributes(bobcat);
        ecosystem.addSpecies(SpeciesType.BOBCAT, bobcat);

    }

    private static void bobcatAttributes(Species bobcat) {
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
    }

    private static void europeanBeaverAttributes(Species europeanBeaver) {
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
    }

    private static void snowshoeHareAttributes(Species snowshoeHare) {
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
    }

    private static void grayWolfAttributes(Species grayWolf) {
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
    }

    private static void mooseAttributes(Species moose) {
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
    }

    private static void whiteTailedDeerAttributes(Species whiteTailedDeer) {
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
    }

    public void initialize() {

        initializeSpecies();
        Log.logln("");
        Log.logln("ECOSYSTEM");
        Log.logln("");
        ecosystem.printSpeciesDetails(LogStatus.INACTIVE);
        Log.logln("--------");

    }

    public Species preySpeciesChoice(Organism predatorOrganism) {

        if (!predatorOrganism.getPreySpecies().isEmpty()) {

            double preySpeciesTypeSelected = RandomGenerator.random.nextDouble();
            double preySpeciesTypeSelectorIndex = 0.0;

            for (PreySpeciesType preySpeciesType : predatorOrganism.getPreySpecies().values()) {

                if (preySpeciesTypeSelected >= preySpeciesTypeSelectorIndex && preySpeciesTypeSelected < preySpeciesTypeSelectorIndex + preySpeciesType.preferenceRate()) {
                    return ecosystem.getSpecies(preySpeciesType.speciesType());
                }

                else {
                    preySpeciesTypeSelectorIndex = preySpeciesTypeSelectorIndex + preySpeciesType.preferenceRate();
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

                    huntingAttempt(predatorOrganism);

                    huntingAttemptNumber++;

                }

            }
        }
    }

    private void huntingAttempt(Organism predatorOrganism) {
        Species preySpecies = preySpeciesChoice(predatorOrganism);

        if (preySpecies != null) {

            List<Organism> preyOrganisms = preySpecies.getAliveOrganisms();
            int preySpeciesPopulation = preySpecies.getPopulation();

            if (preySpeciesPopulation > 0) {

                int preyOrganismSelected = RandomGenerator.random.nextInt(0, preySpeciesPopulation);

                Organism preyOrganism = preyOrganisms.get(preyOrganismSelected);
                double huntSuccessRate = RandomGenerator.generateGaussian(predatorOrganism.calculateHuntSuccessRate(preySpecies, preyOrganism), 0.2);

                if (RandomGenerator.random.nextDouble() <= huntSuccessRate) {
                    huntSuccessful(predatorOrganism, preyOrganism);
                }
            }
        }
    }

    private static void huntSuccessful(Organism predatorOrganism, Organism preyOrganism) {
        if (predatorOrganism.isImpersonatedOrganism()) {
            Log.logln(predatorOrganism.getSpeciesType() + " " + predatorOrganism.getGender() + " hunts a " + preyOrganism.getSpeciesType());
        }

        preyOrganism.setOrganismStatus(OrganismStatus.DEAD);
        preyOrganism.setOrganismDeathReason(OrganismDeathReason.PREDATION);

        if (preyOrganism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.logln(preyOrganism.getSpeciesType() + " " + preyOrganism.getGender() + " is hunted by a " + predatorOrganism.getSpeciesType());
        }

        double preyKgEaten = Math.max(preyOrganism.getWeight(), predatorOrganism.getPreyEaten());
        double baseEnergyGain = predatorOrganism.getEnergyGain() * preyKgEaten;
        double energyGain = Math.min(baseEnergyGain, 1.0 - predatorOrganism.getEnergy());
        predatorOrganism.setEnergy(predatorOrganism.getEnergy() + energyGain);
    }

    public void simulateAging(Species species) {

        for (Organism organism : species.getAliveOrganisms()) {
            organism.setAge(organism.getAge() + (SimulationSettings.SIMULATION_SPEED_WEEKS / 52.0));

            if (organism.getAge() >= organism.getLifeSpan()) {
                organismDeathByAge(organism);
            }

            else {

                if (organism.getGender() == Gender.FEMALE && organism.getReproductionStatus() == ReproductionStatus.MATURE && organism.getAge() >= organism.getSexualMaturityEnd()) {
                    organismMenopause(organism);
                }

                if (organism.getReproductionStatus() == ReproductionStatus.NOT_MATURE && organism.getAge() >= organism.getSexualMaturityStart()) {
                    organismSexualMaturityStart(organism);
                }

                // TODO: waiting to implement plants and nutrition for herbivores, assuming no energy loss for them
                if (organism.getDiet() != Diet.HERBIVORE) {
                    organismEnergyLoss(organism);
                }
            }

        }

    }

    private static void organismEnergyLoss(Organism organism) {
        organism.setEnergy(organism.getEnergy() - organism.getEnergyLost());

        if (organism.getEnergy() <= 0.0) {
            organismDeathByStarvation(organism);
        }
    }

    private static void organismDeathByStarvation(Organism organism) {

        organism.setOrganismStatus(OrganismStatus.DEAD);
        organism.setOrganismDeathReason(OrganismDeathReason.STARVATION);

        if (organism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " dies by starvation");
        }

    }

    private static void organismSexualMaturityStart(Organism organism) {
        organism.setReproductionStatus(ReproductionStatus.MATURE);

        if (organism.isImpersonatedOrganism()) {
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters sexual maturity");
        }
    }

    private static void organismMenopause(Organism organism) {

        organism.setReproductionStatus(ReproductionStatus.MENOPAUSE);

        if (organism.isImpersonatedOrganism()) {
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters menopause");
        }

    }

    private static void organismDeathByAge(Organism organism) {

        organism.setOrganismStatus(OrganismStatus.DEAD);
        organism.setOrganismDeathReason(OrganismDeathReason.AGE);

        if (organism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " dies by age");
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

    public void simulatePregnancy(Species species, Organism organism) {

        organism.setGestationWeek(organism.getGestationWeek() + 1);

        if (organism.getGestationWeek() >= organism.getGestationPeriod()) {

            double offspringCount = Math.round(RandomGenerator.generateGaussian(organism.getAverageOffspring(), 0.2));

            if (organism.isImpersonatedOrganism()) {
                Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " gives birth to " + offspringCount + " offsprings");
            }

            for (int i=0; i < (int) offspringCount; i++) {

                Organism offspring = generateInheritance(organism, organism.getMate());

                if (RandomGenerator.random.nextDouble() >= organism.getJuvenileSurvivalRate()) {
                    organismJuvenileDeath(organism, offspring);
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

    private static void organismJuvenileDeath(Organism organism, Organism offspring) {
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

    public void simulateCooldown(Organism organism) {

        organism.setCooldownWeek(organism.getCooldownWeek() + 1);

        if (organism.getCooldownWeek() >= organism.getPregnancyCooldown()) {

            organism.setReproductionStatus(ReproductionStatus.MATURE);
            organism.setCooldownWeek(0.0);

            if (organism.isImpersonatedOrganism()) {
                Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " finishes reproduction cooldown");
            }

        }

    }

    public void simulateReproduction(Species species) {

        for (Organism organism : species.getAliveOrganisms()) {

            if (organism.getGender() == Gender.FEMALE) {

                if (organism.getReproductionStatus() == ReproductionStatus.COOLDOWN) {
                    simulateCooldown(organism);
                }

                if (organism.getReproductionStatus() == ReproductionStatus.PREGNANT) {
                    simulatePregnancy(species, organism);
                }

                if (organism.getReproductionStatus() == ReproductionStatus.MATURE) {
                    findMate(species, organism);
                }

            }
        }

    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.SIMULATION_SPEED_WEEKS);
        Log.logln("--------");
        Log.logln("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        Log.logln("");

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