package model.environment.animals.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.View;
import model.environment.animals.attributes.*;
import model.environment.animals.constants.CanisLupus;
import model.environment.animals.constants.LynxRufus;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Diet;
import model.environment.animals.enums.Gender;
import model.environment.common.attributes.AttributeValue;
import model.environment.common.base.Species;
import model.environment.common.base.SpeciesTaxonomy;
import model.environment.common.enums.*;
import model.simulation.base.SimulationSettings;
import utils.Log;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AnimalSpecies extends Species {

    private final Diet baseDiet;

    private final Map<AnimalAttribute, AttributeValue> attributes = new EnumMap<>(AnimalAttribute.class);

    private final Map<TaxonomySpecies, PreyAnimalSpecies> basePreyAnimalSpecies = new EnumMap<>(TaxonomySpecies.class);
    private final ArrayList<AnimalOrganism> animalOrganisms = new ArrayList<>();
    private final ArrayList<AnimalOrganism> deadAnimalOrganisms = new ArrayList<>();

    public AnimalSpecies(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image, Diet baseDiet) {
        super(speciesTaxonomy, commonName, image);
        this.baseDiet = baseDiet;
    }

    public Diet getBaseDiet() {
        return baseDiet;
    }

    public AttributeValue getAttribute(AnimalAttribute animalAttribute) {
        return attributes.get(animalAttribute);
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

    public void addAttribute(AnimalAttribute animalAttribute, AttributeValue attributeValue) {
        attributes.put(animalAttribute, attributeValue);
    }

    public void addBasePreyAnimalSpecies(PreyAnimalSpecies preyAnimalSpecies) {
        basePreyAnimalSpecies.put(preyAnimalSpecies.taxonomySpecies(), preyAnimalSpecies);
    }

    private static AttributeValue animalSpeciesConstants(AnimalSpecies animalSpecies, AnimalAttribute animalAttribute) {

        String speciesName = animalSpecies.getSpeciesTaxonomy().taxonomySpecies().name();
        String speciesClassName = "model.environment.animals.constants." + Log.titleCase(speciesName.replace("_", " ")).replace(" ", "");
        String speciesAttributeConstantName = animalAttribute.name();
        String maleConstantName = "MALE_" + speciesAttributeConstantName;
        String femaleConstantName = "FEMALE_" + speciesAttributeConstantName;

        Map<Gender, Double> values = new EnumMap<>(Gender.class);
        values.put(Gender.MALE, classDoubleConstant(speciesClassName, maleConstantName));
        values.put(Gender.FEMALE, classDoubleConstant(speciesClassName, femaleConstantName));
        return new AttributeValue(values);

    }

    private static void animalSpeciesAttribute(AnimalSpecies animalSpecies) {
        animalSpecies.addAttribute(AnimalAttribute.CARRYING_CAPACITY, animalSpeciesConstants(animalSpecies, AnimalAttribute.CARRYING_CAPACITY));
        animalSpecies.addAttribute(AnimalAttribute.LIFESPAN, animalSpeciesConstants(animalSpecies, AnimalAttribute.LIFESPAN));
        animalSpecies.addAttribute(AnimalAttribute.WEIGHT, animalSpeciesConstants(animalSpecies, AnimalAttribute.WEIGHT));
        animalSpecies.addAttribute(AnimalAttribute.HEIGHT, animalSpeciesConstants(animalSpecies, AnimalAttribute.HEIGHT));

        animalSpecies.addAttribute(AnimalAttribute.HUNT_ATTEMPTS, animalSpeciesConstants(animalSpecies, AnimalAttribute.HUNT_ATTEMPTS));
        animalSpecies.addAttribute(AnimalAttribute.ENERGY_GAIN, animalSpeciesConstants(animalSpecies, AnimalAttribute.ENERGY_GAIN));
        animalSpecies.addAttribute(AnimalAttribute.ENERGY_LOSS, animalSpeciesConstants(animalSpecies, AnimalAttribute.ENERGY_LOSS));
        animalSpecies.addAttribute(AnimalAttribute.PREY_EATEN, animalSpeciesConstants(animalSpecies, AnimalAttribute.PREY_EATEN));
        animalSpecies.addAttribute(AnimalAttribute.PLANT_CONSUMPTION_RATE, animalSpeciesConstants(animalSpecies, AnimalAttribute.PLANT_CONSUMPTION_RATE));

        animalSpecies.addAttribute(AnimalAttribute.SEXUAL_MATURITY_START, animalSpeciesConstants(animalSpecies, AnimalAttribute.SEXUAL_MATURITY_START));
        animalSpecies.addAttribute(AnimalAttribute.SEXUAL_MATURITY_END, animalSpeciesConstants(animalSpecies, AnimalAttribute.SEXUAL_MATURITY_END));
        animalSpecies.addAttribute(AnimalAttribute.MATING_SEASON_START, animalSpeciesConstants(animalSpecies, AnimalAttribute.MATING_SEASON_START));
        animalSpecies.addAttribute(AnimalAttribute.MATING_SEASON_END, animalSpeciesConstants(animalSpecies, AnimalAttribute.MATING_SEASON_END));
        animalSpecies.addAttribute(AnimalAttribute.PREGNANCY_COOLDOWN, animalSpeciesConstants(animalSpecies, AnimalAttribute.PREGNANCY_COOLDOWN));
        animalSpecies.addAttribute(AnimalAttribute.GESTATION_PERIOD, animalSpeciesConstants(animalSpecies, AnimalAttribute.GESTATION_PERIOD));
        animalSpecies.addAttribute(AnimalAttribute.AVERAGE_OFFSPRING, animalSpeciesConstants(animalSpecies, AnimalAttribute.AVERAGE_OFFSPRING));
        animalSpecies.addAttribute(AnimalAttribute.JUVENILE_SURVIVAL_RATE, animalSpeciesConstants(animalSpecies, AnimalAttribute.JUVENILE_SURVIVAL_RATE));
        animalSpecies.addAttribute(AnimalAttribute.MATING_SUCCESS_RATE, animalSpeciesConstants(animalSpecies, AnimalAttribute.MATING_SUCCESS_RATE));
        animalSpecies.addAttribute(AnimalAttribute.MATING_ATTEMPTS, animalSpeciesConstants(animalSpecies, AnimalAttribute.MATING_ATTEMPTS));
    }

    private static void bobcatAttributes(AnimalSpecies bobcat) {
        animalSpeciesAttribute(bobcat);
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.LEPUS_AMERICANUS, LynxRufus.PREFERENCE_SNOWSHOE_HARE));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.CASTOR_FIBER, LynxRufus.PREFERENCE_EUROPEAN_BEAVER));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, LynxRufus.PREFERENCE_WHITETAIL_DEER));
    }

    private static void europeanBeaverAttributes(AnimalSpecies europeanBeaver) {
        animalSpeciesAttribute(europeanBeaver);
    }

    private static void snowshoeHareAttributes(AnimalSpecies snowshoeHare) {
        animalSpeciesAttribute(snowshoeHare);
    }

    private static void grayWolfAttributes(AnimalSpecies grayWolf) {
        animalSpeciesAttribute(grayWolf);
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, CanisLupus.PREFERENCE_WHITETAIL_DEER));
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ALCES_ALCES, CanisLupus.PREFERENCE_MOOSE));
    }

    private static void mooseAttributes(AnimalSpecies moose) {
        animalSpeciesAttribute(moose);
    }

    private static void whiteTailedDeerAttributes(AnimalSpecies whiteTailedDeer) {
        animalSpeciesAttribute(whiteTailedDeer);
    }

    public static Map<TaxonomySpecies, AnimalSpecies> initializeAnimalSpecies() {

        Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap = new EnumMap<>(TaxonomySpecies.class);

        SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ODOCOILEUS, TaxonomySpecies.ODOCOILEUS_VIRGINIANUS);

        AnimalSpecies whiteTailedDeer = new AnimalSpecies(odocoileusTaxonomy, "White-Tailed Deer", new Image("resources/images/whiteTailedDeer.png", 64, 64, false, false), Diet.HERBIVORE);
        whiteTailedDeerAttributes(whiteTailedDeer);
        whiteTailedDeer.initializeAnimalOrganisms();
        animalSpeciesMap.put(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, whiteTailedDeer);

        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ALCES, TaxonomySpecies.ALCES_ALCES);
        AnimalSpecies moose = new AnimalSpecies(alcesTaxonomy, "Moose", new Image("resources/images/moose.png", 64, 64, false, false), Diet.HERBIVORE);
        mooseAttributes(moose);
        moose.initializeAnimalOrganisms();
        animalSpeciesMap.put(TaxonomySpecies.ALCES_ALCES, moose);

        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.CANIDAE, TaxonomyGenus.CANIS, TaxonomySpecies.CANIS_LUPUS);
        AnimalSpecies grayWolf = new AnimalSpecies(canisTaxonomy, "Gray Wolf", new Image("resources/images/grayWolf.png", 64, 64, false, false), Diet.CARNIVORE);
        grayWolfAttributes(grayWolf);
        grayWolf.initializeAnimalOrganisms();
        animalSpeciesMap.put(TaxonomySpecies.CANIS_LUPUS, grayWolf);

        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.LAGOMORPHA, TaxonomyFamily.LEPORIDAE, TaxonomyGenus.LEPUS, TaxonomySpecies.LEPUS_AMERICANUS);
        AnimalSpecies snowshoeHare = new AnimalSpecies(lepusTaxonomy, "Snowshoe Hare", new Image("resources/images/snowshoeHare.png", 64, 64, false, false), Diet.HERBIVORE);
        snowshoeHareAttributes(snowshoeHare);
        snowshoeHare.initializeAnimalOrganisms();
        animalSpeciesMap.put(TaxonomySpecies.LEPUS_AMERICANUS, snowshoeHare);

        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.RODENTIA, TaxonomyFamily.CASTORIDAE, TaxonomyGenus.CASTOR, TaxonomySpecies.CASTOR_FIBER);
        AnimalSpecies europeanBeaver = new AnimalSpecies(castorTaxonomy, "European Beaver", new Image("resources/images/europeanBeaver.png", 64, 64, false, false), Diet.HERBIVORE);
        europeanBeaverAttributes(europeanBeaver);
        europeanBeaver.initializeAnimalOrganisms();
        animalSpeciesMap.put(TaxonomySpecies.CASTOR_FIBER, europeanBeaver);

        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.FELIDAE, TaxonomyGenus.LYNX, TaxonomySpecies.LYNX_RUFUS);
        AnimalSpecies bobcat = new AnimalSpecies(lynxTaxonomy, "Bobcat", new Image("resources/images/bobcat.png", 64, 64, false, false), Diet.CARNIVORE);
        bobcatAttributes(bobcat);
        bobcat.initializeAnimalOrganisms();
        animalSpeciesMap.put(TaxonomySpecies.LYNX_RUFUS, bobcat);

        return animalSpeciesMap;

    }

    public boolean organismCanBeImpersonated(TaxonomySpecies taxonomySpecies) {
        return (taxonomySpecies == SimulationSettings.getImpersonatingTaxonomySpecies());
    }

    public boolean organismCanBeImpersonated(Gender gender, TaxonomySpecies taxonomySpecies) {
        return (gender == SimulationSettings.getImpersonatingGender() && taxonomySpecies == SimulationSettings.getImpersonatingTaxonomySpecies());
    }

    public void chooseImpersonatingOrganism() {

        boolean impersonatingOrganismFound = false;

        for (AnimalOrganism animalOrganism : animalOrganisms) {

            if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                    organismCanBeImpersonated(animalOrganism.getGender(), speciesTaxonomy.taxonomySpecies())) {
                animalOrganism.setImpersonatedOrganism(true);
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " is impersonated now");

                return;
            }

        }

        for (AnimalOrganism animalOrganism : animalOrganisms) {

            if (!impersonatingOrganismFound &&
                    animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                    organismCanBeImpersonated(speciesTaxonomy.taxonomySpecies())) {
                animalOrganism.setImpersonatedOrganism(true);
                impersonatingOrganismFound = true;
                Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " is impersonated now");

            }

        }

    }

    private void initializeAnimalOrganisms() {

        int carryingCapacity = (int) getAttribute(AnimalAttribute.CARRYING_CAPACITY).getAverageValue();

        for (int i=0; i<carryingCapacity; i++) {

            Gender gender = Gender.FEMALE;
            if (RandomGenerator.random.nextDouble() < 0.50) {
                gender = Gender.MALE;
            }

            double femaleLifeSpan = getAttribute(AnimalAttribute.LIFESPAN).getValue(Gender.FEMALE);
            double maleLifeSpan = getAttribute(AnimalAttribute.LIFESPAN).getValue(Gender.MALE);
            double lifeSpan = RandomGenerator.generateGaussian(gender, femaleLifeSpan, maleLifeSpan, RandomGenerator.GAUSSIAN_VARIANCE);
            double femaleWeight = getAttribute(AnimalAttribute.WEIGHT).getValue(Gender.FEMALE);
            double maleWeight = getAttribute(AnimalAttribute.WEIGHT).getValue(Gender.MALE);
            double femaleHeight = getAttribute(AnimalAttribute.HEIGHT).getValue(Gender.FEMALE);
            double maleHeight = getAttribute(AnimalAttribute.HEIGHT).getValue(Gender.MALE);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            double posX = RandomGenerator.random.nextDouble() * View.SCENE_WIDTH;
            double posY = RandomGenerator.random.nextDouble() * View.SCENE_HEIGHT;

            AnimalVitalsAttributes animalVitalsAttributes = new AnimalVitalsAttributes(
                RandomGenerator.generateGaussian(femaleWeight, maleWeight, RandomGenerator.GAUSSIAN_VARIANCE),
                RandomGenerator.generateGaussian(femaleHeight, maleHeight, RandomGenerator.GAUSSIAN_VARIANCE),
                lifeSpan,
                0,
                getAttribute(AnimalAttribute.ENERGY_LOSS).getValue(gender)
            );

            AnimalPositionAttributes animalPositionAttributes = new AnimalPositionAttributes(
                posX,
                posY,
                0.0,
                0.0,
                0.0
            );

            AnimalNutritionAttributes animalNutritionAttributes = new AnimalNutritionAttributes(
                getAttribute(AnimalAttribute.HUNT_ATTEMPTS).getValue(gender),
                getAttribute(AnimalAttribute.ENERGY_GAIN).getValue(gender),
                getAttribute(AnimalAttribute.PREY_EATEN).getValue(gender),
                getAttribute(AnimalAttribute.PLANT_CONSUMPTION_RATE).getValue(gender)
            );

            AnimalReproductionAttributes animalReproductionAttributes = new AnimalReproductionAttributes(
                getAttribute(AnimalAttribute.SEXUAL_MATURITY_START).getValue(gender),
                getAttribute(AnimalAttribute.SEXUAL_MATURITY_END).getValue(gender),
                getAttribute(AnimalAttribute.MATING_SEASON_START).getValue(gender),
                getAttribute(AnimalAttribute.MATING_SEASON_END).getValue(gender),
                getAttribute(AnimalAttribute.PREGNANCY_COOLDOWN).getValue(gender),
                getAttribute(AnimalAttribute.GESTATION_PERIOD).getValue(gender),
                getAttribute(AnimalAttribute.AVERAGE_OFFSPRING).getValue(gender),
                getAttribute(AnimalAttribute.JUVENILE_SURVIVAL_RATE).getValue(gender),
                getAttribute(AnimalAttribute.MATING_SUCCESS_RATE).getValue(gender),
                getAttribute(AnimalAttribute.MATING_ATTEMPTS).getValue(gender)
            );

            AnimalOrganismAttributes animalOrganismAttributes = new AnimalOrganismAttributes(
                    animalVitalsAttributes,
                    animalPositionAttributes,
                    animalNutritionAttributes,
                    animalReproductionAttributes
            );

            AnimalOrganism animalOrganism = new AnimalOrganism(
                    this,
                    gender,
                    baseDiet,
                    age,
                    new ImageView(image),
                    animalOrganismAttributes
            );
            animalOrganism.getPreyAnimalSpecies().putAll(basePreyAnimalSpecies);

            animalOrganisms.add(animalOrganism);
        }

        chooseImpersonatingOrganism();

    }

}