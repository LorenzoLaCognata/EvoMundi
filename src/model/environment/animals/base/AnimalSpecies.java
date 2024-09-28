package model.environment.animals.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.View;
import model.environment.animals.enums.AnimalSpeciesAttribute;
import model.environment.animals.SpeciesAttributeValue;
import model.environment.animals.constants.LynxRufus;
import model.environment.animals.constants.CanisLupus;
import model.environment.animals.attributes.*;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Diet;
import model.environment.animals.enums.Gender;
import model.environment.base.*;
import model.simulation.SimulationSettings;
import utils.Log;
import utils.RandomGenerator;
import view.ToolbarSection;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AnimalSpecies extends Species {

    private final Diet baseDiet;
    private final ToolbarSection toolbarSection;

    private final Map<AnimalSpeciesAttribute, SpeciesAttributeValue> attributes = new EnumMap<>(AnimalSpeciesAttribute.class);

    private final Map<TaxonomySpecies, PreyAnimalSpecies> basePreyAnimalSpecies = new EnumMap<>(TaxonomySpecies.class);
    private final ArrayList<AnimalOrganism> animalOrganisms = new ArrayList<>();
    private final ArrayList<AnimalOrganism> deadAnimalOrganisms = new ArrayList<>();

    public AnimalSpecies(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image, Diet baseDiet) {
        super(speciesTaxonomy, commonName, image);
        this.baseDiet = baseDiet;
        this.toolbarSection = new ToolbarSection(this.commonName, this.getImage());
    }


    public Diet getBaseDiet() {
        return baseDiet;
    }

    public SpeciesAttributeValue getAttribute(AnimalSpeciesAttribute speciesAttribute) {
        return attributes.get(speciesAttribute);
    }

    public Map<TaxonomySpecies, PreyAnimalSpecies> getBasePreyAnimalSpecies() {
        return basePreyAnimalSpecies;
    }

    public List<AnimalOrganism> getOrganisms() {
        return animalOrganisms;
    }

    public List<AnimalOrganism> getDeadOrganisms() {
        return deadAnimalOrganisms;
    }

    public int getDeadPopulation() {
        return deadAnimalOrganisms.size();
    }

    public int getDeadPopulation(AnimalOrganismDeathReason organismDeathReason) {

        int count = 0;

        for (AnimalOrganism animalOrganism : deadAnimalOrganisms) {
            if (animalOrganism.getOrganismDeathReason() == organismDeathReason) {
                count++;
            }
        }

        return count;

    }

    public int getPopulation() {
        return animalOrganisms.size();
    }

    public int getPopulation(Gender gender) {

        int count = 0;

        for (AnimalOrganism animalOrganism : animalOrganisms) {
            if (animalOrganism.getGender() == gender) {
                count++;
            }
        }

        return count;

    }

    public void addAttribute(AnimalSpeciesAttribute speciesAttribute, SpeciesAttributeValue speciesAttributeValue) {
        attributes.put(speciesAttribute, speciesAttributeValue);
    }

    public void addBasePreyAnimalSpecies(PreyAnimalSpecies preyAnimalSpecies) {
        basePreyAnimalSpecies.put(preyAnimalSpecies.taxonomySpecies(), preyAnimalSpecies);
    }

    public ToolbarSection getToolbarSection() {
        return toolbarSection;
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

    private static SpeciesAttributeValue speciesConstants(AnimalSpecies animalSpecies, AnimalSpeciesAttribute speciesAttribute) {

        String speciesName = animalSpecies.getSpeciesTaxonomy().taxonomySpecies().name();
        String speciesClassName = "model.environment.animals.constants." + Log.titleCase(speciesName.replace("_", " ")).replace(" ", "");
        String speciesAttributeConstantName = speciesAttribute.name();
        String maleConstantName = "MALE_" + speciesAttributeConstantName;
        String femaleConstantName = "FEMALE_" + speciesAttributeConstantName;

        return new SpeciesAttributeValue(
                speciesAttribute,
                classDoubleConstant(speciesClassName, maleConstantName),
                classDoubleConstant(speciesClassName, femaleConstantName)
        );

    }

    private static void speciesAttribute(AnimalSpecies animalSpecies) {
        animalSpecies.addAttribute(AnimalSpeciesAttribute.CARRYING_CAPACITY, speciesConstants(animalSpecies, AnimalSpeciesAttribute.CARRYING_CAPACITY));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.LIFESPAN, speciesConstants(animalSpecies, AnimalSpeciesAttribute.LIFESPAN));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.WEIGHT, speciesConstants(animalSpecies, AnimalSpeciesAttribute.WEIGHT));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.HEIGHT, speciesConstants(animalSpecies, AnimalSpeciesAttribute.HEIGHT));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.HUNT_ATTEMPTS, speciesConstants(animalSpecies, AnimalSpeciesAttribute.HUNT_ATTEMPTS));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.ENERGY_GAIN, speciesConstants(animalSpecies, AnimalSpeciesAttribute.ENERGY_GAIN));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.ENERGY_LOSS, speciesConstants(animalSpecies, AnimalSpeciesAttribute.ENERGY_LOSS));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.PREY_EATEN, speciesConstants(animalSpecies, AnimalSpeciesAttribute.PREY_EATEN));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.SEXUAL_MATURITY_START, speciesConstants(animalSpecies, AnimalSpeciesAttribute.SEXUAL_MATURITY_START));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.SEXUAL_MATURITY_END, speciesConstants(animalSpecies, AnimalSpeciesAttribute.SEXUAL_MATURITY_END));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.MATING_SEASON_START, speciesConstants(animalSpecies, AnimalSpeciesAttribute.MATING_SEASON_START));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.MATING_SEASON_END, speciesConstants(animalSpecies, AnimalSpeciesAttribute.MATING_SEASON_END));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.PREGNANCY_COOLDOWN, speciesConstants(animalSpecies, AnimalSpeciesAttribute.PREGNANCY_COOLDOWN));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.GESTATION_PERIOD, speciesConstants(animalSpecies, AnimalSpeciesAttribute.GESTATION_PERIOD));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.AVERAGE_OFFSPRING, speciesConstants(animalSpecies, AnimalSpeciesAttribute.AVERAGE_OFFSPRING));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.JUVENILE_SURVIVAL_RATE, speciesConstants(animalSpecies, AnimalSpeciesAttribute.JUVENILE_SURVIVAL_RATE));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.MATING_SUCCESS_RATE, speciesConstants(animalSpecies, AnimalSpeciesAttribute.MATING_SUCCESS_RATE));
        animalSpecies.addAttribute(AnimalSpeciesAttribute.MATING_ATTEMPTS, speciesConstants(animalSpecies, AnimalSpeciesAttribute.MATING_ATTEMPTS));
    }

    private static void bobcatAttributes(AnimalSpecies bobcat) {
        speciesAttribute(bobcat);
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.LEPUS_AMERICANUS, LynxRufus.PREFERENCE_SNOWSHOE_HARE));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.CASTOR_FIBER, LynxRufus.PREFERENCE_EUROPEAN_BEAVER));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, LynxRufus.PREFERENCE_WHITETAIL_DEER));
    }

    private static void europeanBeaverAttributes(AnimalSpecies europeanBeaver) {
        speciesAttribute(europeanBeaver);
    }

    private static void snowshoeHareAttributes(AnimalSpecies snowshoeHare) {
        speciesAttribute(snowshoeHare);
    }

    private static void grayWolfAttributes(AnimalSpecies grayWolf) {
        speciesAttribute(grayWolf);
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, CanisLupus.PREFERENCE_WHITETAIL_DEER));
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ALCES_ALCES, CanisLupus.PREFERENCE_MOOSE));
    }

    private static void mooseAttributes(AnimalSpecies moose) {
        speciesAttribute(moose);
    }

    private static void whiteTailedDeerAttributes(AnimalSpecies whiteTailedDeer) {
        speciesAttribute(whiteTailedDeer);
    }

    public static Map<TaxonomySpecies, AnimalSpecies> initializeSpecies() {

        Map<TaxonomySpecies, AnimalSpecies> speciesMap = new EnumMap<>(TaxonomySpecies.class);

        SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ODOCOILEUS, TaxonomySpecies.ODOCOILEUS_VIRGINIANUS);

        AnimalSpecies whiteTailedDeer = new AnimalSpecies(odocoileusTaxonomy, "White-Tailed Deer", new Image("resources/images/whitetaildeer_64x64.png"), Diet.HERBIVORE);
        whiteTailedDeerAttributes(whiteTailedDeer);
        whiteTailedDeer.initializeOrganisms();
        speciesMap.put(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, whiteTailedDeer);

        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ALCES, TaxonomySpecies.ALCES_ALCES);
        AnimalSpecies moose = new AnimalSpecies(alcesTaxonomy, "Moose", new Image("resources/images/moose_64x64.png"), Diet.HERBIVORE);
        mooseAttributes(moose);
        moose.initializeOrganisms();
        speciesMap.put(TaxonomySpecies.ALCES_ALCES, moose);

        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.CANIDAE, TaxonomyGenus.CANIS, TaxonomySpecies.CANIS_LUPUS);
        AnimalSpecies grayWolf = new AnimalSpecies(canisTaxonomy, "Gray Wolf", new Image("resources/images/graywolf_64x64.png"), Diet.CARNIVORE);
        grayWolfAttributes(grayWolf);
        grayWolf.initializeOrganisms();
        speciesMap.put(TaxonomySpecies.CANIS_LUPUS, grayWolf);

        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.LAGOMORPHA, TaxonomyFamily.LEPORIDAE, TaxonomyGenus.LEPUS, TaxonomySpecies.LEPUS_AMERICANUS);
        AnimalSpecies snowshoeHare = new AnimalSpecies(lepusTaxonomy, "Snowshoe Hare", new Image("resources/images/snowshoehare_64x64.png"), Diet.HERBIVORE);
        snowshoeHareAttributes(snowshoeHare);
        snowshoeHare.initializeOrganisms();
        speciesMap.put(TaxonomySpecies.LEPUS_AMERICANUS, snowshoeHare);

        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.RODENTIA, TaxonomyFamily.CASTORIDAE, TaxonomyGenus.CASTOR, TaxonomySpecies.CASTOR_FIBER);
        AnimalSpecies europeanBeaver = new AnimalSpecies(castorTaxonomy, "European Beaver", new Image("resources/images/europeanbeaver_64x64.png"), Diet.HERBIVORE);
        europeanBeaverAttributes(europeanBeaver);
        europeanBeaver.initializeOrganisms();
        speciesMap.put(TaxonomySpecies.CASTOR_FIBER, europeanBeaver);

        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.FELIDAE, TaxonomyGenus.LYNX, TaxonomySpecies.LYNX_RUFUS);
        AnimalSpecies bobcat = new AnimalSpecies(lynxTaxonomy, "Bobcat", new Image("resources/images/bobcat_64x64.png"), Diet.CARNIVORE);
        bobcatAttributes(bobcat);
        bobcat.initializeOrganisms();
        speciesMap.put(TaxonomySpecies.LYNX_RUFUS, bobcat);

        return speciesMap;

    }

    public boolean isImpersonatedOrganism(Gender gender, TaxonomySpecies taxonomySpecies) {
        return (gender == SimulationSettings.getImpersonatingGender() && taxonomySpecies == SimulationSettings.getImpersonatingTaxonomySpecies());
    }

    private void initializeOrganisms() {

        boolean impersonatingOrganismFound = false;

        int carryingCapacity = (int) getAttribute(AnimalSpeciesAttribute.CARRYING_CAPACITY).getValue();

        for (int i=0; i<carryingCapacity; i++) {

            Gender gender = Gender.FEMALE;
            if (RandomGenerator.random.nextDouble() < 0.50) {
                gender = Gender.MALE;
            }

            double femaleLifeSpan = getAttribute(AnimalSpeciesAttribute.LIFESPAN).getValue(Gender.FEMALE);
            double maleLifeSpan = getAttribute(AnimalSpeciesAttribute.LIFESPAN).getValue(Gender.MALE);
            double lifeSpan = RandomGenerator.generateGaussian(gender, femaleLifeSpan, maleLifeSpan, RandomGenerator.GAUSSIAN_VARIANCE);
            double femaleWeight = getAttribute(AnimalSpeciesAttribute.WEIGHT).getValue(Gender.FEMALE);
            double maleWeight = getAttribute(AnimalSpeciesAttribute.WEIGHT).getValue(Gender.MALE);
            double femaleHeight = getAttribute(AnimalSpeciesAttribute.HEIGHT).getValue(Gender.FEMALE);
            double maleHeight = getAttribute(AnimalSpeciesAttribute.HEIGHT).getValue(Gender.MALE);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            double posX = RandomGenerator.random.nextDouble() * View.SCENE_WIDTH;
            double posY = RandomGenerator.random.nextDouble() * View.SCENE_HEIGHT;

            VitalsAttributes vitalsAttributes = new VitalsAttributes(
                RandomGenerator.generateGaussian(femaleWeight, maleWeight, RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(femaleHeight, maleHeight, RandomGenerator.GAUSSIAN_VARIANCE),
                lifeSpan,
                0,
                getAttribute(AnimalSpeciesAttribute.ENERGY_LOSS).getValue(gender)
            );

            MovementAttributes movementAttributes = new MovementAttributes(
                posX,
                posY,
                0.0,
                0.0,
                0.0
            );

            HuntingAttributes huntingAttributes = new HuntingAttributes(
                getAttribute(AnimalSpeciesAttribute.HUNT_ATTEMPTS).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.ENERGY_GAIN).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.PREY_EATEN).getValue(gender)
            );

            ReproductionAttributes reproductionAttributes = new ReproductionAttributes(
                getAttribute(AnimalSpeciesAttribute.SEXUAL_MATURITY_START).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.SEXUAL_MATURITY_END).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.MATING_SEASON_START).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.MATING_SEASON_END).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.PREGNANCY_COOLDOWN).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.GESTATION_PERIOD).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.AVERAGE_OFFSPRING).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.JUVENILE_SURVIVAL_RATE).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.MATING_SUCCESS_RATE).getValue(gender),
                getAttribute(AnimalSpeciesAttribute.MATING_ATTEMPTS).getValue(gender)
            );

            OrganismAttributes organismAttributes = new OrganismAttributes(
                vitalsAttributes,
                movementAttributes,
                huntingAttributes,
                reproductionAttributes
            );

            AnimalOrganism animalOrganism = new AnimalOrganism(
                    this,
                    gender,
                    baseDiet,
                    age,
                    new ImageView(image),
                    organismAttributes
            );
            animalOrganism.getPreyAnimalSpecies().putAll(basePreyAnimalSpecies);

            if (!impersonatingOrganismFound && isImpersonatedOrganism(gender, speciesTaxonomy.taxonomySpecies())) {
                animalOrganism.setImpersonatedOrganism(true);
                impersonatingOrganismFound = true;
            }

            animalOrganisms.add(animalOrganism);
        }
    }

}