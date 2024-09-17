package Model.Animals;

import Main.View;
import Model.Enums.*;
import Model.Simulation.SimulationSettings;
import Model.SpeciesConstants.*;
import Utils.RandomGenerator;
import View.ToolbarSection;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    // TODO: create parameters or relative folders
    private final Image image;
    private final Group imageGroup = new Group();
    private final ToolbarSection toolbarSection;

    private final Map<SpeciesAttribute, SpeciesAttributeValue> attributes = new EnumMap<>(SpeciesAttribute.class);

    private final Map<SpeciesType, PreySpeciesType> basePreySpecies = new EnumMap<>(SpeciesType.class);
    private final ArrayList<Organism> organisms = new ArrayList<>();
    private final ArrayList<Organism> deadOrganisms = new ArrayList<>();

    public Species(SpeciesTaxonomy speciesTaxonomy, SpeciesType speciesType, String commonName, String scientificName, Diet baseDiet, Image image) {
        this.speciesTaxonomy = speciesTaxonomy;
        this.speciesType = speciesType;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.baseDiet = baseDiet;
        this.image = image;
        this.toolbarSection = new ToolbarSection(this.commonName, this.getImage());
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

    public Image getImage() {
        return image;
    }

    public Group getImageGroup() {
        return imageGroup;
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

    public List<Organism> getDeadOrganisms() {
        return deadOrganisms;
    }

    public int getDeadPopulation() {
        return deadOrganisms.size();
    }

    public int getDeadPopulation(OrganismDeathReason organismDeathReason) {

        int count = 0;

        for (Organism organism : deadOrganisms) {
            if (organism.getOrganismDeathReason() == organismDeathReason) {
                count++;
            }
        }

        return count;

    }

    public int getPopulation() {
        return organisms.size();
    }

    public int getPopulation(Gender gender) {

        int count = 0;

        for (Organism organism : organisms) {
            if (organism.getGender() == gender) {
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

    public ToolbarSection getToolbarSection() {
        return toolbarSection;
    }

    @Override
    public String toString() {
        return commonName;
    }

    // TODO: review energy loss for herbivores

    private static void bobcatAttributes(Species bobcat) {
        bobcat.addAttribute(SpeciesAttribute.CARRYING_CAPACITY,
                new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, Bobcat.CARRYING_CAPACITY, Bobcat.CARRYING_CAPACITY));
        bobcat.addAttribute(SpeciesAttribute.LIFESPAN,
                new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, Bobcat.MALE_LIFESPAN, Bobcat.FEMALE_LIFESPAN));
        bobcat.addAttribute(SpeciesAttribute.WEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, Bobcat.MALE_WEIGHT, Bobcat.FEMALE_WEIGHT));
        bobcat.addAttribute(SpeciesAttribute.HEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, Bobcat.MALE_HEIGHT, Bobcat.FEMALE_HEIGHT));
        bobcat.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, Bobcat.MALE_HUNT_ATTEMPTS, Bobcat.FEMALE_HUNT_ATTEMPTS));
        bobcat.addAttribute(SpeciesAttribute.ENERGY_LOSS,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, Bobcat.MALE_ENERGY_LOSS, Bobcat.FEMALE_ENERGY_LOSS));
        bobcat.addAttribute(SpeciesAttribute.ENERGY_GAIN,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, Bobcat.MALE_ENERGY_GAIN, Bobcat.FEMALE_ENERGY_GAIN));
        bobcat.addAttribute(SpeciesAttribute.PREY_EATEN,
                new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, Bobcat.MALE_PREY_EATEN, Bobcat.FEMALE_PREY_EATEN));
        bobcat.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, Bobcat.SEXUAL_MATURITY_START));
        bobcat.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, Bobcat.SEXUAL_MATURITY_END));
        bobcat.addAttribute(SpeciesAttribute.MATING_SEASON_START,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, Bobcat.MATING_SEASON_START));
        bobcat.addAttribute(SpeciesAttribute.MATING_SEASON_END,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, Bobcat.MATING_SEASON_END));
        bobcat.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN,
                new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, Bobcat.PREGNANCY_COOLDOWN));
        bobcat.addAttribute(SpeciesAttribute.GESTATION_PERIOD,
                new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, Bobcat.GESTATION_PERIOD));
        bobcat.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING,
                new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, Bobcat.AVERAGE_OFFSPRING));
        bobcat.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, Bobcat.JUVENILE_SURVIVAL_RATE));
        bobcat.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, Bobcat.MATING_SUCCESS_RATE));
        bobcat.addAttribute(SpeciesAttribute.MATING_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, Bobcat.MATING_ATTEMPTS));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.SNOWSHOE_HARE, Bobcat.PREFERENCE_SNOWSHOE_HARE));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.EUROPEAN_BEAVER, Bobcat.PREFERENCE_EUROPEAN_BEAVER));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, Bobcat.PREFERENCE_WHITETAIL_DEER));
    }

    private static void europeanBeaverAttributes(Species europeanBeaver) {
        europeanBeaver.addAttribute(SpeciesAttribute.CARRYING_CAPACITY,
                new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, EuropeanBeaver.CARRYING_CAPACITY, EuropeanBeaver.CARRYING_CAPACITY));
        europeanBeaver.addAttribute(SpeciesAttribute.LIFESPAN,
                new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, EuropeanBeaver.MALE_LIFESPAN, EuropeanBeaver.FEMALE_LIFESPAN));
        europeanBeaver.addAttribute(SpeciesAttribute.WEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, EuropeanBeaver.MALE_WEIGHT, EuropeanBeaver.FEMALE_WEIGHT));
        europeanBeaver.addAttribute(SpeciesAttribute.HEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, EuropeanBeaver.MALE_HEIGHT, EuropeanBeaver.FEMALE_HEIGHT));
        europeanBeaver.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        europeanBeaver.addAttribute(SpeciesAttribute.ENERGY_LOSS,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, EuropeanBeaver.MALE_ENERGY_LOSS, EuropeanBeaver.FEMALE_ENERGY_LOSS));
        europeanBeaver.addAttribute(SpeciesAttribute.ENERGY_GAIN,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        europeanBeaver.addAttribute(SpeciesAttribute.PREY_EATEN,
                new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        europeanBeaver.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, EuropeanBeaver.SEXUAL_MATURITY_START));
        europeanBeaver.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, EuropeanBeaver.SEXUAL_MATURITY_END));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_SEASON_START,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, EuropeanBeaver.MATING_SEASON_START));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_SEASON_END,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, EuropeanBeaver.MATING_SEASON_END));
        europeanBeaver.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN,
                new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, EuropeanBeaver.PREGNANCY_COOLDOWN));
        europeanBeaver.addAttribute(SpeciesAttribute.GESTATION_PERIOD,
                new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, EuropeanBeaver.GESTATION_PERIOD));
        europeanBeaver.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING,
                new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, EuropeanBeaver.AVERAGE_OFFSPRING));
        europeanBeaver.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, EuropeanBeaver.JUVENILE_SURVIVAL_RATE));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, EuropeanBeaver.MATING_SUCCESS_RATE));
        europeanBeaver.addAttribute(SpeciesAttribute.MATING_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, EuropeanBeaver.MATING_ATTEMPTS));
    }

    private static void snowshoeHareAttributes(Species snowshoeHare) {
        snowshoeHare.addAttribute(SpeciesAttribute.CARRYING_CAPACITY,
                new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, SnowshoeHare.CARRYING_CAPACITY, SnowshoeHare.CARRYING_CAPACITY));
        snowshoeHare.addAttribute(SpeciesAttribute.LIFESPAN,
                new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, SnowshoeHare.MALE_LIFESPAN, SnowshoeHare.FEMALE_LIFESPAN));
        snowshoeHare.addAttribute(SpeciesAttribute.WEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, SnowshoeHare.MALE_WEIGHT, SnowshoeHare.FEMALE_WEIGHT));
        snowshoeHare.addAttribute(SpeciesAttribute.HEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, SnowshoeHare.MALE_HEIGHT, SnowshoeHare.FEMALE_HEIGHT));
        snowshoeHare.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.ENERGY_LOSS,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, SnowshoeHare.MALE_ENERGY_LOSS, SnowshoeHare.FEMALE_ENERGY_LOSS));
        snowshoeHare.addAttribute(SpeciesAttribute.ENERGY_GAIN,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.PREY_EATEN,
                new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        snowshoeHare.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, SnowshoeHare.SEXUAL_MATURITY_START));
        snowshoeHare.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, SnowshoeHare.SEXUAL_MATURITY_END));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_SEASON_START,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, SnowshoeHare.MATING_SEASON_START));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_SEASON_END,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, SnowshoeHare.MATING_SEASON_END));
        snowshoeHare.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN,
                new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, SnowshoeHare.PREGNANCY_COOLDOWN));
        snowshoeHare.addAttribute(SpeciesAttribute.GESTATION_PERIOD,
                new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, SnowshoeHare.GESTATION_PERIOD));
        snowshoeHare.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING,
                new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, SnowshoeHare.AVERAGE_OFFSPRING));
        snowshoeHare.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, SnowshoeHare.JUVENILE_SURVIVAL_RATE));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, SnowshoeHare.MATING_SUCCESS_RATE));
        snowshoeHare.addAttribute(SpeciesAttribute.MATING_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, SnowshoeHare.MATING_ATTEMPTS));
    }

    private static void grayWolfAttributes(Species grayWolf) {
        grayWolf.addAttribute(SpeciesAttribute.CARRYING_CAPACITY,
                new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, GrayWolf.CARRYING_CAPACITY, GrayWolf.CARRYING_CAPACITY));
        grayWolf.addAttribute(SpeciesAttribute.LIFESPAN,
                new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, GrayWolf.MALE_LIFESPAN, GrayWolf.FEMALE_LIFESPAN));
        grayWolf.addAttribute(SpeciesAttribute.WEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, GrayWolf.MALE_WEIGHT, GrayWolf.FEMALE_WEIGHT));
        grayWolf.addAttribute(SpeciesAttribute.HEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, GrayWolf.MALE_HEIGHT, GrayWolf.FEMALE_HEIGHT));
        grayWolf.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, GrayWolf.MALE_HUNT_ATTEMPTS, GrayWolf.FEMALE_HUNT_ATTEMPTS));
        grayWolf.addAttribute(SpeciesAttribute.ENERGY_LOSS,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, GrayWolf.MALE_ENERGY_LOSS, GrayWolf.FEMALE_ENERGY_LOSS));
        grayWolf.addAttribute(SpeciesAttribute.ENERGY_GAIN,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, GrayWolf.MALE_ENERGY_GAIN, GrayWolf.FEMALE_ENERGY_GAIN));
        grayWolf.addAttribute(SpeciesAttribute.PREY_EATEN,
                new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, GrayWolf.MALE_PREY_EATEN, GrayWolf.FEMALE_PREY_EATEN));
        grayWolf.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, GrayWolf.SEXUAL_MATURITY_START));
        grayWolf.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, GrayWolf.SEXUAL_MATURITY_END));
        grayWolf.addAttribute(SpeciesAttribute.MATING_SEASON_START,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, GrayWolf.MATING_SEASON_START));
        grayWolf.addAttribute(SpeciesAttribute.MATING_SEASON_END,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, GrayWolf.MATING_SEASON_END));
        grayWolf.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN,
                new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, GrayWolf.PREGNANCY_COOLDOWN));
        grayWolf.addAttribute(SpeciesAttribute.GESTATION_PERIOD,
                new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, GrayWolf.GESTATION_PERIOD));
        grayWolf.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING,
                new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, GrayWolf.AVERAGE_OFFSPRING));
        grayWolf.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, GrayWolf.JUVENILE_SURVIVAL_RATE));
        grayWolf.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, GrayWolf.MATING_SUCCESS_RATE));
        grayWolf.addAttribute(SpeciesAttribute.MATING_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, GrayWolf.MATING_ATTEMPTS));
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, GrayWolf.PREFERENCE_WHITETAIL_DEER));
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.MOOSE, GrayWolf.PREFERENCE_MOOSE));
    }

    private static void mooseAttributes(Species moose) {
        moose.addAttribute(SpeciesAttribute.CARRYING_CAPACITY,
                new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, Moose.CARRYING_CAPACITY, Moose.CARRYING_CAPACITY));
        moose.addAttribute(SpeciesAttribute.LIFESPAN,
                new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, Moose.MALE_LIFESPAN, Moose.FEMALE_LIFESPAN));
        moose.addAttribute(SpeciesAttribute.WEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, Moose.MALE_WEIGHT, Moose.FEMALE_WEIGHT));
        moose.addAttribute(SpeciesAttribute.HEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, Moose.MALE_HEIGHT, Moose.FEMALE_HEIGHT));
        moose.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.ENERGY_LOSS,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, Moose.MALE_ENERGY_LOSS, Moose.FEMALE_ENERGY_LOSS));
        moose.addAttribute(SpeciesAttribute.ENERGY_GAIN,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.PREY_EATEN,
                new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        moose.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, Moose.SEXUAL_MATURITY_START));
        moose.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, Moose.SEXUAL_MATURITY_END));
        moose.addAttribute(SpeciesAttribute.MATING_SEASON_START,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, Moose.MATING_SEASON_START));
        moose.addAttribute(SpeciesAttribute.MATING_SEASON_END,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, Moose.MATING_SEASON_END));
        moose.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN,
                new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, Moose.PREGNANCY_COOLDOWN));
        moose.addAttribute(SpeciesAttribute.GESTATION_PERIOD,
                new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, Moose.GESTATION_PERIOD));
        moose.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING,
                new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, Moose.AVERAGE_OFFSPRING));
        moose.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, Moose.JUVENILE_SURVIVAL_RATE));
        moose.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, Moose.MATING_SUCCESS_RATE));
        moose.addAttribute(SpeciesAttribute.MATING_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, Moose.MATING_ATTEMPTS));
    }

    private static void whiteTailedDeerAttributes(Species whiteTailedDeer) {
        whiteTailedDeer.addAttribute(SpeciesAttribute.CARRYING_CAPACITY,
                new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, WhiteTailedDeer.CARRYING_CAPACITY, WhiteTailedDeer.CARRYING_CAPACITY));
        whiteTailedDeer.addAttribute(SpeciesAttribute.LIFESPAN,
                new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, WhiteTailedDeer.MALE_LIFESPAN, WhiteTailedDeer.FEMALE_LIFESPAN));
        whiteTailedDeer.addAttribute(SpeciesAttribute.WEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, WhiteTailedDeer.MALE_WEIGHT, WhiteTailedDeer.FEMALE_WEIGHT));
        whiteTailedDeer.addAttribute(SpeciesAttribute.HEIGHT,
                new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, WhiteTailedDeer.MALE_HEIGHT, WhiteTailedDeer.FEMALE_HEIGHT));
        whiteTailedDeer.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.ENERGY_LOSS,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, WhiteTailedDeer.MALE_ENERGY_LOSS, WhiteTailedDeer.FEMALE_ENERGY_LOSS));
        whiteTailedDeer.addAttribute(SpeciesAttribute.ENERGY_GAIN,
                new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.PREY_EATEN,
                new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, 0.0, 0.0));
        whiteTailedDeer.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, WhiteTailedDeer.SEXUAL_MATURITY_START));
        whiteTailedDeer.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END,
                new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, WhiteTailedDeer.SEXUAL_MATURITY_END));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_SEASON_START,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, WhiteTailedDeer.MATING_SEASON_START));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_SEASON_END,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, WhiteTailedDeer.MATING_SEASON_END));
        whiteTailedDeer.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN,
                new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, WhiteTailedDeer.PREGNANCY_COOLDOWN));
        whiteTailedDeer.addAttribute(SpeciesAttribute.GESTATION_PERIOD,
                new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, WhiteTailedDeer.GESTATION_PERIOD));
        whiteTailedDeer.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING,
                new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, WhiteTailedDeer.AVERAGE_OFFSPRING));
        whiteTailedDeer.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, WhiteTailedDeer.JUVENILE_SURVIVAL_RATE));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, WhiteTailedDeer.MATING_SUCCESS_RATE));
        whiteTailedDeer.addAttribute(SpeciesAttribute.MATING_ATTEMPTS,
                new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, WhiteTailedDeer.MATING_ATTEMPTS));
    }

    public static Map<SpeciesType, Species> initializeSpecies() {

        Map<SpeciesType, Species> speciesMap = new EnumMap<>(SpeciesType.class);

        TaxonomyClass mammalia = new TaxonomyClass("Mammalia");
        TaxonomyOrder artiodactyla = new TaxonomyOrder("Artiodactyla");
        TaxonomyFamily cervidae = new TaxonomyFamily("Cervidae");
        TaxonomyGenus odocoileus = new TaxonomyGenus("Odocoileus");
        SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, odocoileus);

        Species whiteTailedDeer = new Species(odocoileusTaxonomy, SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", Diet.HERBIVORE, new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\whitetaildeer_64x64.png"));
        whiteTailedDeerAttributes(whiteTailedDeer);
        whiteTailedDeer.initializeOrganisms();
        speciesMap.put(SpeciesType.WHITE_TAILED_DEER, whiteTailedDeer);

        TaxonomyGenus alces = new TaxonomyGenus("Alces");
        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, alces);
        Species moose = new Species(alcesTaxonomy, SpeciesType.MOOSE, "Moose", "Alces alces", Diet.HERBIVORE, new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\moose_64x64.png"));
        mooseAttributes(moose);
        moose.initializeOrganisms();
        speciesMap.put(SpeciesType.MOOSE, moose);

        TaxonomyOrder carnivora = new TaxonomyOrder("Carnivora");
        TaxonomyFamily canidae = new TaxonomyFamily("Canidae");
        TaxonomyGenus canis = new TaxonomyGenus("Canis");
        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, canidae, canis);
        Species grayWolf = new Species(canisTaxonomy, SpeciesType.GRAY_WOLF, "Gray Wolf", "Canis lupus", Diet.CARNIVORE, new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\graywolf_64x64.png"));
        grayWolfAttributes(grayWolf);
        grayWolf.initializeOrganisms();
        speciesMap.put(SpeciesType.GRAY_WOLF, grayWolf);

        TaxonomyOrder lagomorpha = new TaxonomyOrder("Lagomorpha");
        TaxonomyFamily leporidae = new TaxonomyFamily("Leporidae");
        TaxonomyGenus lepus = new TaxonomyGenus("Lepus");
        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(mammalia, lagomorpha, leporidae, lepus);
        Species snowshoeHare = new Species(lepusTaxonomy, SpeciesType.SNOWSHOE_HARE, "Snowshoe Hare", "Lepus americanus", Diet.HERBIVORE, new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\snowshoehare_64x64.png"));
        snowshoeHareAttributes(snowshoeHare);
        //snowshoeHare.initializeOrganisms();
        speciesMap.put(SpeciesType.SNOWSHOE_HARE, snowshoeHare);

        TaxonomyOrder rodentia = new TaxonomyOrder("Rodentia");
        TaxonomyFamily castoridae = new TaxonomyFamily("Castoridae");
        TaxonomyGenus castor = new TaxonomyGenus("Castor");
        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(mammalia, rodentia, castoridae, castor);
        Species europeanBeaver = new Species(castorTaxonomy, SpeciesType.EUROPEAN_BEAVER, "European Beaver", "Castor fiber", Diet.HERBIVORE, new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\europeanbeaver_64x64.png"));
        europeanBeaverAttributes(europeanBeaver);
        europeanBeaver.initializeOrganisms();
        speciesMap.put(SpeciesType.EUROPEAN_BEAVER, europeanBeaver);

        TaxonomyFamily felidae = new TaxonomyFamily("Felidae");
        TaxonomyGenus lynx = new TaxonomyGenus("Lynx");
        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, felidae, lynx);
        Species bobcat = new Species(lynxTaxonomy, SpeciesType.BOBCAT, "Bobcat", "Lynx rufus", Diet.CARNIVORE, new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\bobcat_64x64.png"));
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

            double femaleLifeSpan = getAttribute(SpeciesAttribute.LIFESPAN).getValue(Gender.FEMALE);
            double maleLifeSpan = getAttribute(SpeciesAttribute.LIFESPAN).getValue(Gender.MALE);
            double lifeSpan = RandomGenerator.generateGaussian(gender, femaleLifeSpan, maleLifeSpan, RandomGenerator.GAUSSIAN_VARIANCE);
            double femaleWeight = getAttribute(SpeciesAttribute.WEIGHT).getValue(Gender.FEMALE);
            double maleWeight = getAttribute(SpeciesAttribute.WEIGHT).getValue(Gender.MALE);
            double femaleHeigth = getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.FEMALE);
            double maleHeigth = getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.MALE);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            double posX = RandomGenerator.random.nextDouble() * View.SCENE_WIDTH;
            double posY = RandomGenerator.random.nextDouble() * View.SCENE_HEIGHT;
            Organism organism = new Organism(
                    speciesType,
                    gender,
                    baseDiet,
                    age,
                    new ImageView(image),
                    new VitalsAttributes(
                        RandomGenerator.generateGaussian(femaleWeight, maleWeight, RandomGenerator.GAUSSIAN_VARIANCE),
                        RandomGenerator.generateGaussian(femaleHeigth, maleHeigth, RandomGenerator.GAUSSIAN_VARIANCE),
                        lifeSpan,
                        0,
                        getAttribute(SpeciesAttribute.ENERGY_LOSS).getValue(gender)
                    ),
                    new MovementAttributes(
                        posX,
                        posY,
                        0.0,
                        0.0,
                        0.0
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