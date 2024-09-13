package Model.Animals;

import Model.Enums.*;
import Model.Simulation.SimulationSettings;
import Utils.RandomGenerator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Species {

    private final SpeciesTaxonomy speciesTaxonomy;
    private final SpeciesType speciesType;
    private final String commonName;
    private final String scientificName;

    private final Diet baseDiet;

    private final Map<SpeciesAttribute, SpeciesAttributeValue> attributes = new EnumMap<>(SpeciesAttribute.class);

    private final Map<SpeciesType, PreySpeciesType> basePreySpecies = new EnumMap<>(SpeciesType.class);
    private final ArrayList<Organism> organisms = new ArrayList<>();

    public Species(SpeciesTaxonomy speciesTaxonomy, SpeciesType speciesType, String commonName, String scientificName, Diet baseDiet) {

        this.speciesTaxonomy = speciesTaxonomy;
        this.speciesType = speciesType;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.baseDiet = baseDiet;

    }

    public SpeciesType getSpeciesType() {
        return speciesType;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public SpeciesTaxonomy getSpeciesTaxonomy() {
        return speciesTaxonomy;
    }

    public Diet getBaseDiet() {
        return baseDiet;
    }

    public SpeciesAttributeValue getAttribute(SpeciesAttribute speciesAttribute) {
        return attributes.get(speciesAttribute);
    }

    public Map<SpeciesType, PreySpeciesType> getBasePreySpecies() {
        return basePreySpecies;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public List<Organism> getOrganisms(OrganismStatus organismStatus) {

        ArrayList<Organism> statusOrganisms = new ArrayList<>();

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == organismStatus) {
                statusOrganisms.add(organism);
            }
        }

        return statusOrganisms;

    }

    public List<Organism> getAliveOrganisms() {
        return getOrganisms(OrganismStatus.ALIVE);
    }

    public int getPopulation(OrganismStatus organismStatus, OrganismDeathReason organismDeathReason) {

        int count = 0;

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == organismStatus && organism.getOrganismDeathReason() == organismDeathReason) {
                count++;
            }
        }

        return count;

    }

    public int getPopulation(OrganismStatus organismStatus) {

        int count = 0;

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == organismStatus) {
                count++;
            }
        }

        return count;

    }

    public int getPopulation() {
        return getPopulation(OrganismStatus.ALIVE);
    }

    public int getPopulation(Gender gender) {

        int count = 0;

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == OrganismStatus.ALIVE && organism.getGender() == gender) {
                count++;
            }
        }

        return count;

    }

    public void addAttribute(SpeciesAttribute speciesAttribute, SpeciesAttributeValue speciesAttributeValue) {
        attributes.put(speciesAttribute, speciesAttributeValue);
    }

    public void addBasePreySpecies(PreySpeciesType preySpeciesType) {
        basePreySpecies.put(preySpeciesType.speciesType(), preySpeciesType);
    }

    @Override
    public String toString() {
        return commonName;
    }

    // TODO: review energy loss for herbivores

    private static void bobcatAttributes(Species bobcat) {
        bobcat.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 100, 100));
        bobcat.addAttribute(SpeciesAttribute.LIFESPAN, new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, 12.0, 12.0));
        bobcat.addAttribute(SpeciesAttribute.WEIGHT, new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, 13.0, 10.0));
        bobcat.addAttribute(SpeciesAttribute.HEIGHT, new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, 0.45, 0.40));
        bobcat.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 6.0,  6.0));
        bobcat.addAttribute(SpeciesAttribute.ENERGY_LOSS, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, 0.25, 0.25));
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
        europeanBeaver.addAttribute(SpeciesAttribute.ENERGY_LOSS, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, 0.25, 0.25));
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
        snowshoeHare.addAttribute(SpeciesAttribute.ENERGY_LOSS, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, 0.40, 0.40));
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
        grayWolf.addAttribute(SpeciesAttribute.ENERGY_LOSS, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, 0.30, 0.30));
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
        moose.addAttribute(SpeciesAttribute.ENERGY_LOSS, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, 0.25, 0.25));
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
        whiteTailedDeer.addAttribute(SpeciesAttribute.ENERGY_LOSS, new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, 0.30, 0.30));
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

    public static Map<SpeciesType, Species> initializeSpecies() {

        Map<SpeciesType, Species> speciesMap = new EnumMap<>(SpeciesType.class);

        TaxonomyClass mammalia = new TaxonomyClass("Mammalia");
        TaxonomyOrder artiodactyla = new TaxonomyOrder("Artiodactyla");
        TaxonomyFamily cervidae = new TaxonomyFamily("Cervidae");
        TaxonomyGenus odocoileus = new TaxonomyGenus("Odocoileus");
        SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, odocoileus);

        Species whiteTailedDeer = new Species(odocoileusTaxonomy, SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", Diet.HERBIVORE);
        whiteTailedDeerAttributes(whiteTailedDeer);
        whiteTailedDeer.initializeOrganisms();
        speciesMap.put(SpeciesType.WHITE_TAILED_DEER, whiteTailedDeer);

        TaxonomyGenus alces = new TaxonomyGenus("Alces");
        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, alces);
        Species moose = new Species(alcesTaxonomy, SpeciesType.MOOSE, "Moose", "Alces alces", Diet.HERBIVORE);
        mooseAttributes(moose);
        moose.initializeOrganisms();
        speciesMap.put(SpeciesType.MOOSE, moose);

        TaxonomyOrder carnivora = new TaxonomyOrder("Carnivora");
        TaxonomyFamily canidae = new TaxonomyFamily("Canidae");
        TaxonomyGenus canis = new TaxonomyGenus("Canis");
        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, canidae, canis);
        Species grayWolf = new Species(canisTaxonomy, SpeciesType.GRAY_WOLF, "Gray Wolf", "Canis lupus", Diet.CARNIVORE);
        grayWolfAttributes(grayWolf);
        grayWolf.initializeOrganisms();
        speciesMap.put(SpeciesType.GRAY_WOLF, grayWolf);

        TaxonomyOrder lagomorpha = new TaxonomyOrder("Lagomorpha");
        TaxonomyFamily leporidae = new TaxonomyFamily("Leporidae");
        TaxonomyGenus lepus = new TaxonomyGenus("Lepus");
        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(mammalia, lagomorpha, leporidae, lepus);
        Species snowshoeHare = new Species(lepusTaxonomy, SpeciesType.SNOWSHOE_HARE, "Snowshoe Hare", "Lepus americanus", Diet.HERBIVORE);
        snowshoeHareAttributes(snowshoeHare);
        snowshoeHare.initializeOrganisms();
        speciesMap.put(SpeciesType.SNOWSHOE_HARE, snowshoeHare);

        TaxonomyOrder rodentia = new TaxonomyOrder("Rodentia");
        TaxonomyFamily castoridae = new TaxonomyFamily("Castoridae");
        TaxonomyGenus castor = new TaxonomyGenus("Castor");
        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(mammalia, rodentia, castoridae, castor);
        Species europeanBeaver = new Species(castorTaxonomy, SpeciesType.EUROPEAN_BEAVER, "European Beaver", "Castor fiber", Diet.HERBIVORE);
        europeanBeaverAttributes(europeanBeaver);
        europeanBeaver.initializeOrganisms();
        speciesMap.put(SpeciesType.EUROPEAN_BEAVER, europeanBeaver);

        TaxonomyFamily felidae = new TaxonomyFamily("Felidae");
        TaxonomyGenus lynx = new TaxonomyGenus("Lynx");
        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, felidae, lynx);
        Species bobcat = new Species(lynxTaxonomy, SpeciesType.BOBCAT, "Bobcat", "Lynx rufus", Diet.CARNIVORE);
        bobcatAttributes(bobcat);
        bobcat.initializeOrganisms();
        speciesMap.put(SpeciesType.BOBCAT, bobcat);

        return speciesMap;

    }

    private void initializeOrganisms() {

        boolean impersonatingOrganismFound = false;

        int carryingCapacity = (int) getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue();

        for (int i=0; i<carryingCapacity; i++) {

            Gender gender = Gender.FEMALE;
            if (RandomGenerator.random.nextDouble() < 0.50) {
                gender = Gender.MALE;
            }

            double lifeSpan = RandomGenerator.generateGaussian(gender, getAttribute(SpeciesAttribute.LIFESPAN).getValue(Gender.FEMALE), getAttribute(SpeciesAttribute.LIFESPAN).getValue(Gender.MALE), 0.2);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            Organism organism = new Organism(
                    speciesType,
                    gender,
                    baseDiet,
                    age,
                    new VitalsAttributes(
                        RandomGenerator.generateGaussian(getAttribute(SpeciesAttribute.WEIGHT).getValue(Gender.FEMALE), getAttribute(SpeciesAttribute.WEIGHT).getValue(Gender.MALE), 0.2),
                        RandomGenerator.generateGaussian(getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.FEMALE), getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.MALE), 0.2),
                        lifeSpan,
                        0,
                        getAttribute(SpeciesAttribute.ENERGY_LOSS).getValue(gender)
                    ),
                    new HuntingAttributes(
                        getAttribute(SpeciesAttribute.HUNT_ATTEMPTS).getValue(gender),
                        getAttribute(SpeciesAttribute.ENERGY_GAIN).getValue(gender),
                        getAttribute(SpeciesAttribute.PREY_EATEN).getValue(gender)
                    ),
                    new ReproductionAttributes(
                        getAttribute(SpeciesAttribute.SEXUAL_MATURITY_START).getValue(gender),
                        getAttribute(SpeciesAttribute.SEXUAL_MATURITY_END).getValue(gender),
                        getAttribute(SpeciesAttribute.MATING_SEASON_START).getValue(gender),
                        getAttribute(SpeciesAttribute.MATING_SEASON_END).getValue(gender),
                        getAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN).getValue(gender),
                        getAttribute(SpeciesAttribute.GESTATION_PERIOD).getValue(gender),
                        getAttribute(SpeciesAttribute.AVERAGE_OFFSPRING).getValue(gender),
                        getAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE).getValue(gender),
                        getAttribute(SpeciesAttribute.MATING_SUCCESS_RATE).getValue(gender),
                        getAttribute(SpeciesAttribute.MATING_ATTEMPTS).getValue(gender)
                    )
            );
            organism.getPreySpecies().putAll(basePreySpecies);

            if (!impersonatingOrganismFound && gender == SimulationSettings.getImpersonatingGender() && speciesType == SimulationSettings.getImpersonatingSpeciesType() ) {
                organism.setImpersonatedOrganism(true);
                impersonatingOrganismFound = true;
            }

            organisms.add(organism);
        }
    }

}