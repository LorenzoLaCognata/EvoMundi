package model.animals;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.View;
import model.constants.Bobcat;
import model.constants.GrayWolf;
import model.enums.*;
import model.simulation.SimulationSettings;
import utils.Log;
import utils.RandomGenerator;
import view.ToolbarSection;

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

    public static double classDoubleConstant(String className, String constantName) {

        Class<?> speciesClass;
        try {
            speciesClass = Class.forName(className);
            return speciesClass.getField(constantName).getDouble(null);

        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    private static SpeciesAttributeValue speciesConstants(Species species, SpeciesAttribute speciesAttribute) {

        String speciesName = species.speciesType.name();
        String speciesClassName = "model.constants." + Log.titleCase(speciesName.replace("_", " ")).replace(" ", "");
        String speciesAttributeConstantName = speciesAttribute.name();
        String maleConstantName = "MALE_" + speciesAttributeConstantName;
        String femaleConstantName = "FEMALE_" + speciesAttributeConstantName;

        return new SpeciesAttributeValue(
                speciesAttribute,
                classDoubleConstant(speciesClassName, maleConstantName),
                classDoubleConstant(speciesClassName, femaleConstantName)
        );

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

        Species whiteTailedDeer = new Species(odocoileusTaxonomy, SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", Diet.HERBIVORE, new Image("resources/images/whitetaildeer_64x64.png"));
        whiteTailedDeerAttributes(whiteTailedDeer);
        whiteTailedDeer.initializeOrganisms();
        speciesMap.put(SpeciesType.WHITE_TAILED_DEER, whiteTailedDeer);

        TaxonomyGenus alces = new TaxonomyGenus("Alces");
        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, alces);
        Species moose = new Species(alcesTaxonomy, SpeciesType.MOOSE, "Moose", "Alces alces", Diet.HERBIVORE, new Image("resources/images/moose_64x64.png"));
        mooseAttributes(moose);
        moose.initializeOrganisms();
        speciesMap.put(SpeciesType.MOOSE, moose);

        TaxonomyOrder carnivora = new TaxonomyOrder("Carnivora");
        TaxonomyFamily canidae = new TaxonomyFamily("Canidae");
        TaxonomyGenus canis = new TaxonomyGenus("Canis");
        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, canidae, canis);
        Species grayWolf = new Species(canisTaxonomy, SpeciesType.GRAY_WOLF, "Gray Wolf", "Canis lupus", Diet.CARNIVORE, new Image("resources/images/graywolf_64x64.png"));
        grayWolfAttributes(grayWolf);
        grayWolf.initializeOrganisms();
        speciesMap.put(SpeciesType.GRAY_WOLF, grayWolf);

        TaxonomyOrder lagomorpha = new TaxonomyOrder("Lagomorpha");
        TaxonomyFamily leporidae = new TaxonomyFamily("Leporidae");
        TaxonomyGenus lepus = new TaxonomyGenus("Lepus");
        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(mammalia, lagomorpha, leporidae, lepus);
        Species snowshoeHare = new Species(lepusTaxonomy, SpeciesType.SNOWSHOE_HARE, "Snowshoe Hare", "Lepus americanus", Diet.HERBIVORE, new Image("resources/images/snowshoehare_64x64.png"));
        snowshoeHareAttributes(snowshoeHare);
        snowshoeHare.initializeOrganisms();
        speciesMap.put(SpeciesType.SNOWSHOE_HARE, snowshoeHare);

        TaxonomyOrder rodentia = new TaxonomyOrder("Rodentia");
        TaxonomyFamily castoridae = new TaxonomyFamily("Castoridae");
        TaxonomyGenus castor = new TaxonomyGenus("Castor");
        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(mammalia, rodentia, castoridae, castor);
        Species europeanBeaver = new Species(castorTaxonomy, SpeciesType.EUROPEAN_BEAVER, "European Beaver", "Castor fiber", Diet.HERBIVORE, new Image("resources/images/europeanbeaver_64x64.png"));
        europeanBeaverAttributes(europeanBeaver);
        europeanBeaver.initializeOrganisms();
        speciesMap.put(SpeciesType.EUROPEAN_BEAVER, europeanBeaver);

        TaxonomyFamily felidae = new TaxonomyFamily("Felidae");
        TaxonomyGenus lynx = new TaxonomyGenus("Lynx");
        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(mammalia, carnivora, felidae, lynx);
        Species bobcat = new Species(lynxTaxonomy, SpeciesType.BOBCAT, "Bobcat", "Lynx rufus", Diet.CARNIVORE, new Image("resources/images/bobcat_64x64.png"));
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
            double femaleHeight = getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.FEMALE);
            double maleHeight = getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.MALE);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            double posX = RandomGenerator.random.nextDouble() * View.SCENE_WIDTH;
            double posY = RandomGenerator.random.nextDouble() * View.SCENE_HEIGHT;

            VitalsAttributes vitalsAttributes = new VitalsAttributes(
                RandomGenerator.generateGaussian(femaleWeight, maleWeight, RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(femaleHeight, maleHeight, RandomGenerator.GAUSSIAN_VARIANCE),
                lifeSpan,
                0,
                getAttribute(SpeciesAttribute.ENERGY_LOSS).getValue(gender)
            );

            MovementAttributes movementAttributes = new MovementAttributes(
                posX,
                posY,
                0.0,
                0.0,
                0.0
            );

            HuntingAttributes huntingAttributes = new HuntingAttributes(
                getAttribute(SpeciesAttribute.HUNT_ATTEMPTS).getValue(gender),
                getAttribute(SpeciesAttribute.ENERGY_GAIN).getValue(gender),
                getAttribute(SpeciesAttribute.PREY_EATEN).getValue(gender)
            );

            ReproductionAttributes reproductionAttributes = new ReproductionAttributes(
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
            );

            OrganismAttributes organismAttributes = new OrganismAttributes(
                vitalsAttributes,
                movementAttributes,
                huntingAttributes,
                reproductionAttributes
            );

            Organism organism = new Organism(
                    speciesType,
                    gender,
                    baseDiet,
                    age,
                    new ImageView(image),
                    organismAttributes
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