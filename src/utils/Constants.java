package utils;

import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.base.PreyAnimalSpecies;
import model.environment.animals.constants.CanisLupus;
import model.environment.animals.constants.LynxRufus;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.animals.enums.Gender;
import model.environment.common.attributes.AttributeValue;
import model.environment.common.enums.TaxonomySpecies;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;

import java.util.EnumMap;
import java.util.Map;

public class Constants {

    private Constants() {
    }

    private static double classDoubleConstant(String className, String constantName) {

        Class<?> speciesClass;
        try {
            speciesClass = Class.forName(className);
            return speciesClass.getField(constantName).getDouble(null);

        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    private static AttributeValue plantSpeciesConstants(PlantSpecies plantSpecies, PlantAttribute plantAttribute) {

        String speciesName = plantSpecies.getSpeciesTaxonomy().taxonomySpecies().name();
        String speciesClassName = "model.environment.plants.constants." + Log.titleCase(speciesName.replace("_", " ")).replace(" ", "");
        String constantName = plantAttribute.name();

        Map<Gender, Double> values = new EnumMap<>(Gender.class);
        values.put(Gender.NA, Constants.classDoubleConstant(speciesClassName, constantName));
        return new AttributeValue(values);

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

    private static void initializePlantSpeciesAttributes(PlantSpecies plantSpecies) {
        plantSpecies.addAttribute(PlantAttribute.CARRYING_CAPACITY, Constants.plantSpeciesConstants(plantSpecies, PlantAttribute.CARRYING_CAPACITY));
        plantSpecies.addAttribute(PlantAttribute.GROWTH_RATE, Constants.plantSpeciesConstants(plantSpecies, PlantAttribute.GROWTH_RATE));
    }

    public static void whiteWillowAttributes(PlantSpecies whiteWillow) {
        initializePlantSpeciesAttributes(whiteWillow);
    }

    private static void initializeAnimalSpeciesAttributes(AnimalSpecies animalSpecies) {
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

    public static void bobcatAttributes(AnimalSpecies bobcat) {
        initializeAnimalSpeciesAttributes(bobcat);
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.LEPUS_AMERICANUS, LynxRufus.PREFERENCE_SNOWSHOE_HARE));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.CASTOR_FIBER, LynxRufus.PREFERENCE_EUROPEAN_BEAVER));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, LynxRufus.PREFERENCE_WHITETAIL_DEER));
    }

    public static void europeanBeaverAttributes(AnimalSpecies europeanBeaver) {
        initializeAnimalSpeciesAttributes(europeanBeaver);
    }

    public static void snowshoeHareAttributes(AnimalSpecies snowshoeHare) {
        initializeAnimalSpeciesAttributes(snowshoeHare);
    }

    public static void grayWolfAttributes(AnimalSpecies grayWolf) {
        initializeAnimalSpeciesAttributes(grayWolf);
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, CanisLupus.PREFERENCE_WHITETAIL_DEER));
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ALCES_ALCES, CanisLupus.PREFERENCE_MOOSE));
    }

    public static void mooseAttributes(AnimalSpecies moose) {
        initializeAnimalSpeciesAttributes(moose);
    }

    public static void whiteTailedDeerAttributes(AnimalSpecies whiteTailedDeer) {
        initializeAnimalSpeciesAttributes(whiteTailedDeer);
    }

}
