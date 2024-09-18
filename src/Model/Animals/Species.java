package Model.Animals;

import Main.View;
import Model.Enums.*;
import Model.Simulation.SimulationSettings;
import Model.SpeciesConstants.*;
import Utils.Log;
import Utils.RandomGenerator;
import View.ToolbarSection;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class
Species {

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

    private static SpeciesAttributeValue speciesConstants(Species species, SpeciesAttribute speciesAttribute) {

        SpeciesAttributeValue speciesAttributeValue = new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, 0.0, 0.0);
        
        switch (species.speciesType) {
            case SpeciesType.BOBCAT:
                switch (speciesAttribute) {
                    case SpeciesAttribute.CARRYING_CAPACITY -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, Bobcat.CARRYING_CAPACITY, Bobcat.CARRYING_CAPACITY);
                    case SpeciesAttribute.LIFESPAN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, Bobcat.MALE_LIFESPAN, Bobcat.FEMALE_LIFESPAN);
                    case SpeciesAttribute.WEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, Bobcat.MALE_WEIGHT, Bobcat.FEMALE_WEIGHT);
                    case SpeciesAttribute.HEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, Bobcat.MALE_HEIGHT, Bobcat.FEMALE_HEIGHT);
                    case SpeciesAttribute.HUNT_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, Bobcat.MALE_HUNT_ATTEMPTS, Bobcat.FEMALE_HUNT_ATTEMPTS);
                    case SpeciesAttribute.ENERGY_LOSS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, Bobcat.MALE_ENERGY_LOSS, Bobcat.FEMALE_ENERGY_LOSS);
                    case SpeciesAttribute.ENERGY_GAIN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, Bobcat.MALE_ENERGY_GAIN, Bobcat.FEMALE_ENERGY_GAIN);
                    case SpeciesAttribute.PREY_EATEN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, Bobcat.MALE_PREY_EATEN, Bobcat.FEMALE_PREY_EATEN);
                    case SpeciesAttribute.SEXUAL_MATURITY_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, Bobcat.SEXUAL_MATURITY_START);
                    case SpeciesAttribute.SEXUAL_MATURITY_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, Bobcat.SEXUAL_MATURITY_END);
                    case SpeciesAttribute.MATING_SEASON_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, Bobcat.MATING_SEASON_START);
                    case SpeciesAttribute.MATING_SEASON_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, Bobcat.MATING_SEASON_END);
                    case SpeciesAttribute.PREGNANCY_COOLDOWN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, Bobcat.PREGNANCY_COOLDOWN);
                    case SpeciesAttribute.GESTATION_PERIOD -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, Bobcat.GESTATION_PERIOD);
                    case SpeciesAttribute.AVERAGE_OFFSPRING -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, Bobcat.AVERAGE_OFFSPRING);
                    case SpeciesAttribute.JUVENILE_SURVIVAL_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, Bobcat.JUVENILE_SURVIVAL_RATE);
                    case SpeciesAttribute.MATING_SUCCESS_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, Bobcat.MATING_SUCCESS_RATE);
                    case SpeciesAttribute.MATING_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, Bobcat.MATING_ATTEMPTS);
                    default -> throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + " " + species.speciesType + " " + speciesAttribute);
                }
                break;
            case SpeciesType.EUROPEAN_BEAVER:
                switch (speciesAttribute) {
                    case SpeciesAttribute.CARRYING_CAPACITY -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, EuropeanBeaver.CARRYING_CAPACITY, EuropeanBeaver.CARRYING_CAPACITY);
                    case SpeciesAttribute.LIFESPAN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, EuropeanBeaver.MALE_LIFESPAN, EuropeanBeaver.FEMALE_LIFESPAN);
                    case SpeciesAttribute.WEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, EuropeanBeaver.MALE_WEIGHT, EuropeanBeaver.FEMALE_WEIGHT);
                    case SpeciesAttribute.HEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, EuropeanBeaver.MALE_HEIGHT, EuropeanBeaver.FEMALE_HEIGHT);
                    case SpeciesAttribute.HUNT_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, EuropeanBeaver.MALE_HUNT_ATTEMPTS, EuropeanBeaver.FEMALE_HUNT_ATTEMPTS);
                    case SpeciesAttribute.ENERGY_LOSS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, EuropeanBeaver.MALE_ENERGY_LOSS, EuropeanBeaver.FEMALE_ENERGY_LOSS);
                    case SpeciesAttribute.ENERGY_GAIN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, EuropeanBeaver.MALE_ENERGY_GAIN, EuropeanBeaver.FEMALE_ENERGY_GAIN);
                    case SpeciesAttribute.PREY_EATEN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, EuropeanBeaver.MALE_PREY_EATEN, EuropeanBeaver.FEMALE_PREY_EATEN);
                    case SpeciesAttribute.SEXUAL_MATURITY_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, EuropeanBeaver.SEXUAL_MATURITY_START);
                    case SpeciesAttribute.SEXUAL_MATURITY_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, EuropeanBeaver.SEXUAL_MATURITY_END);
                    case SpeciesAttribute.MATING_SEASON_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, EuropeanBeaver.MATING_SEASON_START);
                    case SpeciesAttribute.MATING_SEASON_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, EuropeanBeaver.MATING_SEASON_END);
                    case SpeciesAttribute.PREGNANCY_COOLDOWN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, EuropeanBeaver.PREGNANCY_COOLDOWN);
                    case SpeciesAttribute.GESTATION_PERIOD -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, EuropeanBeaver.GESTATION_PERIOD);
                    case SpeciesAttribute.AVERAGE_OFFSPRING -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, EuropeanBeaver.AVERAGE_OFFSPRING);
                    case SpeciesAttribute.JUVENILE_SURVIVAL_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, EuropeanBeaver.JUVENILE_SURVIVAL_RATE);
                    case SpeciesAttribute.MATING_SUCCESS_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, EuropeanBeaver.MATING_SUCCESS_RATE);
                    case SpeciesAttribute.MATING_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, EuropeanBeaver.MATING_ATTEMPTS);
                    default -> throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + " " + species.speciesType + " " + speciesAttribute);
                }
                break;
            case SpeciesType.GRAY_WOLF:
                switch (speciesAttribute) {
                    case SpeciesAttribute.CARRYING_CAPACITY -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, GrayWolf.CARRYING_CAPACITY, GrayWolf.CARRYING_CAPACITY);
                    case SpeciesAttribute.LIFESPAN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, GrayWolf.MALE_LIFESPAN, GrayWolf.FEMALE_LIFESPAN);
                    case SpeciesAttribute.WEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, GrayWolf.MALE_WEIGHT, GrayWolf.FEMALE_WEIGHT);
                    case SpeciesAttribute.HEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, GrayWolf.MALE_HEIGHT, GrayWolf.FEMALE_HEIGHT);
                    case SpeciesAttribute.HUNT_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, GrayWolf.MALE_HUNT_ATTEMPTS, GrayWolf.FEMALE_HUNT_ATTEMPTS);
                    case SpeciesAttribute.ENERGY_LOSS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, GrayWolf.MALE_ENERGY_LOSS, GrayWolf.FEMALE_ENERGY_LOSS);
                    case SpeciesAttribute.ENERGY_GAIN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, GrayWolf.MALE_ENERGY_GAIN, GrayWolf.FEMALE_ENERGY_GAIN);
                    case SpeciesAttribute.PREY_EATEN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, GrayWolf.MALE_PREY_EATEN, GrayWolf.FEMALE_PREY_EATEN);
                    case SpeciesAttribute.SEXUAL_MATURITY_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, GrayWolf.SEXUAL_MATURITY_START);
                    case SpeciesAttribute.SEXUAL_MATURITY_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, GrayWolf.SEXUAL_MATURITY_END);
                    case SpeciesAttribute.MATING_SEASON_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, GrayWolf.MATING_SEASON_START);
                    case SpeciesAttribute.MATING_SEASON_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, GrayWolf.MATING_SEASON_END);
                    case SpeciesAttribute.PREGNANCY_COOLDOWN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, GrayWolf.PREGNANCY_COOLDOWN);
                    case SpeciesAttribute.GESTATION_PERIOD -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, GrayWolf.GESTATION_PERIOD);
                    case SpeciesAttribute.AVERAGE_OFFSPRING -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, GrayWolf.AVERAGE_OFFSPRING);
                    case SpeciesAttribute.JUVENILE_SURVIVAL_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, GrayWolf.JUVENILE_SURVIVAL_RATE);
                    case SpeciesAttribute.MATING_SUCCESS_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, GrayWolf.MATING_SUCCESS_RATE);
                    case SpeciesAttribute.MATING_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, GrayWolf.MATING_ATTEMPTS);
                    default -> throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + " " + species.speciesType + " " + speciesAttribute);
                }
                break;
            case SpeciesType.MOOSE:
                switch (speciesAttribute) {
                    case SpeciesAttribute.CARRYING_CAPACITY -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, Moose.CARRYING_CAPACITY, Moose.CARRYING_CAPACITY);
                    case SpeciesAttribute.LIFESPAN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, Moose.MALE_LIFESPAN, Moose.FEMALE_LIFESPAN);
                    case SpeciesAttribute.WEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, Moose.MALE_WEIGHT, Moose.FEMALE_WEIGHT);
                    case SpeciesAttribute.HEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, Moose.MALE_HEIGHT, Moose.FEMALE_HEIGHT);
                    case SpeciesAttribute.HUNT_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, Moose.MALE_HUNT_ATTEMPTS, Moose.FEMALE_HUNT_ATTEMPTS);
                    case SpeciesAttribute.ENERGY_LOSS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, Moose.MALE_ENERGY_LOSS, Moose.FEMALE_ENERGY_LOSS);
                    case SpeciesAttribute.ENERGY_GAIN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, Moose.MALE_ENERGY_GAIN, Moose.FEMALE_ENERGY_GAIN);
                    case SpeciesAttribute.PREY_EATEN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, Moose.MALE_PREY_EATEN, Moose.FEMALE_PREY_EATEN);
                    case SpeciesAttribute.SEXUAL_MATURITY_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, Moose.SEXUAL_MATURITY_START);
                    case SpeciesAttribute.SEXUAL_MATURITY_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, Moose.SEXUAL_MATURITY_END);
                    case SpeciesAttribute.MATING_SEASON_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, Moose.MATING_SEASON_START);
                    case SpeciesAttribute.MATING_SEASON_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, Moose.MATING_SEASON_END);
                    case SpeciesAttribute.PREGNANCY_COOLDOWN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, Moose.PREGNANCY_COOLDOWN);
                    case SpeciesAttribute.GESTATION_PERIOD -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, Moose.GESTATION_PERIOD);
                    case SpeciesAttribute.AVERAGE_OFFSPRING -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, Moose.AVERAGE_OFFSPRING);
                    case SpeciesAttribute.JUVENILE_SURVIVAL_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, Moose.JUVENILE_SURVIVAL_RATE);
                    case SpeciesAttribute.MATING_SUCCESS_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, Moose.MATING_SUCCESS_RATE);
                    case SpeciesAttribute.MATING_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, Moose.MATING_ATTEMPTS);
                    default -> throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + " " + species.speciesType + " " + speciesAttribute);
                }
                break;
            case SpeciesType.SNOWSHOE_HARE:
                switch (speciesAttribute) {
                    case SpeciesAttribute.CARRYING_CAPACITY -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, SnowshoeHare.CARRYING_CAPACITY, SnowshoeHare.CARRYING_CAPACITY);
                    case SpeciesAttribute.LIFESPAN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, SnowshoeHare.MALE_LIFESPAN, SnowshoeHare.FEMALE_LIFESPAN);
                    case SpeciesAttribute.WEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, SnowshoeHare.MALE_WEIGHT, SnowshoeHare.FEMALE_WEIGHT);
                    case SpeciesAttribute.HEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, SnowshoeHare.MALE_HEIGHT, SnowshoeHare.FEMALE_HEIGHT);
                    case SpeciesAttribute.HUNT_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, SnowshoeHare.MALE_HUNT_ATTEMPTS, SnowshoeHare.FEMALE_HUNT_ATTEMPTS);
                    case SpeciesAttribute.ENERGY_LOSS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, SnowshoeHare.MALE_ENERGY_LOSS, SnowshoeHare.FEMALE_ENERGY_LOSS);
                    case SpeciesAttribute.ENERGY_GAIN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, SnowshoeHare.MALE_ENERGY_GAIN, SnowshoeHare.FEMALE_ENERGY_GAIN);
                    case SpeciesAttribute.PREY_EATEN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, SnowshoeHare.MALE_PREY_EATEN, SnowshoeHare.FEMALE_PREY_EATEN);
                    case SpeciesAttribute.SEXUAL_MATURITY_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, SnowshoeHare.SEXUAL_MATURITY_START);
                    case SpeciesAttribute.SEXUAL_MATURITY_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, SnowshoeHare.SEXUAL_MATURITY_END);
                    case SpeciesAttribute.MATING_SEASON_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, SnowshoeHare.MATING_SEASON_START);
                    case SpeciesAttribute.MATING_SEASON_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, SnowshoeHare.MATING_SEASON_END);
                    case SpeciesAttribute.PREGNANCY_COOLDOWN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, SnowshoeHare.PREGNANCY_COOLDOWN);
                    case SpeciesAttribute.GESTATION_PERIOD -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, SnowshoeHare.GESTATION_PERIOD);
                    case SpeciesAttribute.AVERAGE_OFFSPRING -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, SnowshoeHare.AVERAGE_OFFSPRING);
                    case SpeciesAttribute.JUVENILE_SURVIVAL_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, SnowshoeHare.JUVENILE_SURVIVAL_RATE);
                    case SpeciesAttribute.MATING_SUCCESS_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, SnowshoeHare.MATING_SUCCESS_RATE);
                    case SpeciesAttribute.MATING_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, SnowshoeHare.MATING_ATTEMPTS);
                    default -> throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + " " + species.speciesType + " " + speciesAttribute);
                }
                break;
            case SpeciesType.WHITE_TAILED_DEER:
                switch (speciesAttribute) {
                    case SpeciesAttribute.CARRYING_CAPACITY -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.CARRYING_CAPACITY, WhiteTailedDeer.CARRYING_CAPACITY, WhiteTailedDeer.CARRYING_CAPACITY);
                    case SpeciesAttribute.LIFESPAN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.LIFESPAN, WhiteTailedDeer.MALE_LIFESPAN, WhiteTailedDeer.FEMALE_LIFESPAN);
                    case SpeciesAttribute.WEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.WEIGHT, WhiteTailedDeer.MALE_WEIGHT, WhiteTailedDeer.FEMALE_WEIGHT);
                    case SpeciesAttribute.HEIGHT -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HEIGHT, WhiteTailedDeer.MALE_HEIGHT, WhiteTailedDeer.FEMALE_HEIGHT);
                    case SpeciesAttribute.HUNT_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.HUNT_ATTEMPTS, WhiteTailedDeer.MALE_HUNT_ATTEMPTS, WhiteTailedDeer.FEMALE_HUNT_ATTEMPTS);
                    case SpeciesAttribute.ENERGY_LOSS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_LOSS, WhiteTailedDeer.MALE_ENERGY_LOSS, WhiteTailedDeer.FEMALE_ENERGY_LOSS);
                    case SpeciesAttribute.ENERGY_GAIN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.ENERGY_GAIN, WhiteTailedDeer.MALE_ENERGY_GAIN, WhiteTailedDeer.FEMALE_ENERGY_GAIN);
                    case SpeciesAttribute.PREY_EATEN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREY_EATEN, WhiteTailedDeer.MALE_PREY_EATEN, WhiteTailedDeer.FEMALE_PREY_EATEN);
                    case SpeciesAttribute.SEXUAL_MATURITY_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_START, 0.0, WhiteTailedDeer.SEXUAL_MATURITY_START);
                    case SpeciesAttribute.SEXUAL_MATURITY_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.SEXUAL_MATURITY_END, 0.0, WhiteTailedDeer.SEXUAL_MATURITY_END);
                    case SpeciesAttribute.MATING_SEASON_START -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_START, 0.0, WhiteTailedDeer.MATING_SEASON_START);
                    case SpeciesAttribute.MATING_SEASON_END -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SEASON_END, 0.0, WhiteTailedDeer.MATING_SEASON_END);
                    case SpeciesAttribute.PREGNANCY_COOLDOWN -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.PREGNANCY_COOLDOWN, 0.0, WhiteTailedDeer.PREGNANCY_COOLDOWN);
                    case SpeciesAttribute.GESTATION_PERIOD -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.GESTATION_PERIOD, 0.0, WhiteTailedDeer.GESTATION_PERIOD);
                    case SpeciesAttribute.AVERAGE_OFFSPRING -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.AVERAGE_OFFSPRING, 0.0, WhiteTailedDeer.AVERAGE_OFFSPRING);
                    case SpeciesAttribute.JUVENILE_SURVIVAL_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, 0.0, WhiteTailedDeer.JUVENILE_SURVIVAL_RATE);
                    case SpeciesAttribute.MATING_SUCCESS_RATE -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_SUCCESS_RATE, 0.0, WhiteTailedDeer.MATING_SUCCESS_RATE);
                    case SpeciesAttribute.MATING_ATTEMPTS -> speciesAttributeValue =
                            new SpeciesAttributeValue(SpeciesAttribute.MATING_ATTEMPTS, 0.0, WhiteTailedDeer.MATING_ATTEMPTS);
                    default -> throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + " " + species.speciesType + " " + speciesAttribute);
                }
                break;
            default:
                throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + " " + species.speciesType + " " + speciesAttribute);
        }

        return speciesAttributeValue;

    }

    private static void speciesAttribute(Species species) {
        species.addAttribute(SpeciesAttribute.CARRYING_CAPACITY, speciesConstants(species, SpeciesAttribute.CARRYING_CAPACITY));
        species.addAttribute(SpeciesAttribute.LIFESPAN, speciesConstants(species, SpeciesAttribute.LIFESPAN));
        species.addAttribute(SpeciesAttribute.WEIGHT, speciesConstants(species, SpeciesAttribute.WEIGHT));
        species.addAttribute(SpeciesAttribute.HEIGHT, speciesConstants(species, SpeciesAttribute.HEIGHT));
        species.addAttribute(SpeciesAttribute.HUNT_ATTEMPTS, speciesConstants(species, SpeciesAttribute.HUNT_ATTEMPTS));
        species.addAttribute(SpeciesAttribute.ENERGY_GAIN, speciesConstants(species, SpeciesAttribute.ENERGY_GAIN));
        species.addAttribute(SpeciesAttribute.ENERGY_LOSS, speciesConstants(species, SpeciesAttribute.ENERGY_LOSS));
        species.addAttribute(SpeciesAttribute.PREY_EATEN, speciesConstants(species, SpeciesAttribute.PREY_EATEN));
        species.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_START, speciesConstants(species, SpeciesAttribute.SEXUAL_MATURITY_START));
        species.addAttribute(SpeciesAttribute.SEXUAL_MATURITY_END, speciesConstants(species, SpeciesAttribute.SEXUAL_MATURITY_END));
        species.addAttribute(SpeciesAttribute.MATING_SEASON_START, speciesConstants(species, SpeciesAttribute.MATING_SEASON_START));
        species.addAttribute(SpeciesAttribute.MATING_SEASON_END, speciesConstants(species, SpeciesAttribute.MATING_SEASON_END));
        species.addAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN, speciesConstants(species, SpeciesAttribute.PREGNANCY_COOLDOWN));
        species.addAttribute(SpeciesAttribute.GESTATION_PERIOD, speciesConstants(species, SpeciesAttribute.GESTATION_PERIOD));
        species.addAttribute(SpeciesAttribute.AVERAGE_OFFSPRING, speciesConstants(species, SpeciesAttribute.AVERAGE_OFFSPRING));
        species.addAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE, speciesConstants(species, SpeciesAttribute.JUVENILE_SURVIVAL_RATE));
        species.addAttribute(SpeciesAttribute.MATING_SUCCESS_RATE, speciesConstants(species, SpeciesAttribute.MATING_SUCCESS_RATE));
        species.addAttribute(SpeciesAttribute.MATING_ATTEMPTS, speciesConstants(species, SpeciesAttribute.MATING_ATTEMPTS));
    }

    private static void bobcatAttributes(Species bobcat) {
        speciesAttribute(bobcat);
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.SNOWSHOE_HARE, Bobcat.PREFERENCE_SNOWSHOE_HARE));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.EUROPEAN_BEAVER, Bobcat.PREFERENCE_EUROPEAN_BEAVER));
        bobcat.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, Bobcat.PREFERENCE_WHITETAIL_DEER));
    }

    private static void europeanBeaverAttributes(Species europeanBeaver) {
        speciesAttribute(europeanBeaver);
    }

    private static void snowshoeHareAttributes(Species snowshoeHare) {
        speciesAttribute(snowshoeHare);
    }

    private static void grayWolfAttributes(Species grayWolf) {
        speciesAttribute(grayWolf);
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.WHITE_TAILED_DEER, GrayWolf.PREFERENCE_WHITETAIL_DEER));
        grayWolf.addBasePreySpecies(new PreySpeciesType(SpeciesType.MOOSE, GrayWolf.PREFERENCE_MOOSE));
    }

    private static void mooseAttributes(Species moose) {
        speciesAttribute(moose);
    }

    private static void whiteTailedDeerAttributes(Species whiteTailedDeer) {
        speciesAttribute(whiteTailedDeer);
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

    public boolean isImpersonatedOrganism(Gender gender, SpeciesType speciesType) {
        return (gender == SimulationSettings.getImpersonatingGender() && speciesType == SimulationSettings.getImpersonatingSpeciesType());
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

            if (!impersonatingOrganismFound && isImpersonatedOrganism(gender, speciesType)) {
                organism.setImpersonatedOrganism(true);
                impersonatingOrganismFound = true;
            }

            organisms.add(organism);
        }
    }

}