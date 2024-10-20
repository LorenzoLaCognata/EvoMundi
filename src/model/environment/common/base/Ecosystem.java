package model.environment.common.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.environment.animals.attributes.*;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.base.PreyAnimalSpecies;
import model.environment.animals.constants.CanisLupus;
import model.environment.animals.constants.LynxRufus;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Diet;
import model.environment.animals.enums.Gender;
import model.environment.common.attributes.AttributeValue;
import model.environment.common.enums.*;
import model.environment.plants.attributes.PlantOrganismAttributes;
import model.environment.plants.attributes.PlantPositionAttributes;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import model.simulation.base.SimulationSettings;
import utils.Log;
import utils.RandomGenerator;
import utils.TriConsumer;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Ecosystem {

    private final Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap = new EnumMap<>(TaxonomySpecies.class);
    private final Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap = new EnumMap<>(TaxonomySpecies.class);
    private final Map<Point, TileOrganisms> worldMap = new ConcurrentHashMap<>();

    public static final Predicate<AnimalOrganism> animalTruePredicate =
        ignored -> true;

    public static final Predicate<PlantOrganism> plantTruePredicate =
        ignored -> true;

    public Map<TaxonomySpecies, PlantSpecies> getPlantSpeciesMap() {
        return plantSpeciesMap;
    }

    public Map<TaxonomySpecies, AnimalSpecies> getAnimalSpeciesMap() {
        return animalSpeciesMap;
    }

    public Map<Point, TileOrganisms> getWorldMap() {
        return worldMap;
    }

    public void addPlantOrganism(Point tile, PlantSpecies plantSpecies, PlantOrganism plantOrganism) {

        TileOrganisms tileOrganisms = worldMap.computeIfAbsent(tile, ignored -> new TileOrganisms(new HashMap<>(), new HashMap<>()));

        tileOrganisms.plantOrganisms()
                .computeIfAbsent(plantSpecies, ignored -> Collections.synchronizedList(new ArrayList<>()))
                .add(plantOrganism);

        worldMap.put(tile, tileOrganisms);
    }

    public void addAnimalOrganism(Point tile, AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        TileOrganisms tileOrganisms = worldMap.computeIfAbsent(tile, ignored -> new TileOrganisms(new HashMap<>(), new HashMap<>()));

        tileOrganisms.animalOrganisms()
                .computeIfAbsent(animalSpecies, ignored -> Collections.synchronizedList(new ArrayList<>()))
                .add(animalOrganism);

        worldMap.put(tile, tileOrganisms);
    }

    public void addPlantOrganismImage(PlantOrganism plantOrganism, PlantSpecies plantSpecies) {
        plantSpecies.getImageGroup().getChildren().add(plantOrganism.getOrganismIcons().getStackPane());
    }

    public void initializePlantOrganismImages(PlantSpecies selectedPlantSpecies) {

        iteratePlantOrganismsPerPlantSpecies(selectedPlantSpecies, (plantOrganism, plantSpecies) ->
                addPlantOrganismImage((PlantOrganism) plantOrganism, (PlantSpecies) plantSpecies)
        );

    }

    public void initializeAnimalOrganismImages(AnimalSpecies animalSpecies) {

        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {

            Map<AnimalSpecies, List<AnimalOrganism>> tileSpecies = entry.getValue().animalOrganisms();

            if (!tileSpecies.isEmpty()) {

                List<AnimalOrganism> animalOrganisms = tileSpecies.get(animalSpecies);

                if (animalOrganisms != null) {
                    for (AnimalOrganism animalOrganism : animalOrganisms) {
                        animalSpecies.getImageGroup().getChildren().add(animalOrganism.getOrganismIcons().getStackPane());
                    }
                }

            }

        }
    }

    private void initializePlantOrganisms(PlantSpecies plantSpecies) {

        int carryingCapacity = (int) plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue();

        int horizontalTiles = (int) Math.floor(SimulationSettings.SCENE_WIDTH / SimulationSettings.PIXELS_PER_TILE);
        int verticalTiles = (int) Math.floor(SimulationSettings.SCENE_HEIGHT / SimulationSettings.PIXELS_PER_TILE);

        for (int i=0; i<horizontalTiles; i++) {

            for (int j=0; j<verticalTiles; j++) {

                double latitudeSpan = SimulationSettings.DEGREES_PER_TILE;
                double baseLatitude = SimulationSettings.MIN_LATITUDE + (latitudeSpan * j);
                double latitude = baseLatitude + (latitudeSpan * (RandomGenerator.random.nextDouble() - 0.5));

                double longitudeSpan = SimulationSettings.DEGREES_PER_TILE;
                double baseLongitude = SimulationSettings.MIN_LONGITUDE + (longitudeSpan * i);
                double longitude = baseLongitude + (longitudeSpan * (RandomGenerator.random.nextDouble() - 0.5));

                PlantOrganism plantOrganism = new PlantOrganism(
                        plantSpecies,
                        new ImageView(plantSpecies.getImage()),
                        new PlantOrganismAttributes(new PlantPositionAttributes(latitude, longitude)),
                        carryingCapacity
                );

                PlantPositionAttributes plantPositionAttributes = plantOrganism.getPlantOrganismAttributes().plantPositionAttributes();

                double screenX = plantPositionAttributes.getScreenX();
                double screenY = plantPositionAttributes.getScreenY();

                plantOrganism.getOrganismIcons().getStackPane().setLayoutX(screenX);
                plantOrganism.getOrganismIcons().getStackPane().setLayoutY(screenY);

                Point tile = Geography.calculateTile(plantPositionAttributes.getLatitude(), plantPositionAttributes.getLongitude());

                addPlantOrganism(tile, plantSpecies, plantOrganism);
                plantSpecies.setOrganismCount(plantSpecies.getOrganismCount() + plantOrganism.getQuantity());

            }
        }

        initializePlantOrganismImages(plantSpecies);

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

    private static AttributeValue plantSpeciesConstants(PlantSpecies plantSpecies, PlantAttribute plantAttribute) {

        String speciesName = plantSpecies.getSpeciesTaxonomy().taxonomySpecies().name();
        String speciesClassName = "model.environment.plants.constants." + Log.titleCase(speciesName.replace("_", " ")).replace(" ", "");
        String constantName = plantAttribute.name();

        Map<Gender, Double> values = new EnumMap<>(Gender.class);
        values.put(Gender.NA, classDoubleConstant(speciesClassName, constantName));
        return new AttributeValue(values);

    }

    private void initializePlantSpeciesAttributes(PlantSpecies plantSpecies) {
        plantSpecies.addAttribute(PlantAttribute.CARRYING_CAPACITY, plantSpeciesConstants(plantSpecies, PlantAttribute.CARRYING_CAPACITY));
        plantSpecies.addAttribute(PlantAttribute.GROWTH_RATE, plantSpeciesConstants(plantSpecies, PlantAttribute.GROWTH_RATE));
    }

    public void initializePlantSpecies() {

        SpeciesTaxonomy salixAlbaTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAGNOLIOPSIDA, TaxonomyOrder.MALPIGHIALES, TaxonomyFamily.SALICACEAE, TaxonomyGenus.SALIX, TaxonomySpecies.SALIX_ALBA);

        PlantSpecies whiteWillow = new PlantSpecies(salixAlbaTaxonomy, "White Willow", new Image("images/whiteWillow.png"));
        initializePlantSpeciesAttributes(whiteWillow);
        initializePlantOrganisms(whiteWillow);
        plantSpeciesMap.put(TaxonomySpecies.SALIX_ALBA, whiteWillow);

    }

    public boolean canBeImpersonated(TaxonomySpecies taxonomySpecies) {
        return (taxonomySpecies == SimulationSettings.getImpersonatingTaxonomySpecies());
    }

    public boolean canBeImpersonated(Gender gender, TaxonomySpecies taxonomySpecies) {
        return (gender == SimulationSettings.getImpersonatingGender() && taxonomySpecies == SimulationSettings.getImpersonatingTaxonomySpecies());
    }

    public void chooseImpersonatingOrganism() {

        if (chooseImpersonatingOrganismSelectedGender()) {
            return;
        }

        chooseImpersonatingOrganismAnyGender();

    }

    private boolean chooseImpersonatingOrganismSelectedGender() {

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            Map<AnimalSpecies, List<AnimalOrganism>> tileSpecies = tile.getValue().animalOrganisms();

            for (AnimalSpecies animalSpecies : tile.getValue().animalOrganisms().keySet()) {

                if (canBeImpersonated(animalSpecies.getSpeciesTaxonomy().taxonomySpecies())) {

                    List<AnimalOrganism> animalOrganisms = tileSpecies.get(animalSpecies);

                    if (chooseOrganismToImpersonateSelectedGender(animalSpecies, animalOrganisms)) return true;
                }
            }
        }
        return false;
    }

    private boolean chooseOrganismToImpersonateSelectedGender(AnimalSpecies animalSpecies, List<AnimalOrganism> animalOrganisms) {
        synchronized (animalOrganisms) {
            for (AnimalOrganism animalOrganism : animalOrganisms) {

                if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE &&
                        canBeImpersonated(animalOrganism.getGender(), animalSpecies.getSpeciesTaxonomy().taxonomySpecies())) {
                    animalOrganism.setImpersonatedOrganism(true);
                    Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " is impersonated now");

                    return true;

                }
            }
        }
        return false;
    }

    private void chooseImpersonatingOrganismAnyGender() {

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            Map<AnimalSpecies, List<AnimalOrganism>> tileSpecies = tile.getValue().animalOrganisms();

            for (AnimalSpecies animalSpecies : tile.getValue().animalOrganisms().keySet()) {

                if (canBeImpersonated(animalSpecies.getSpeciesTaxonomy().taxonomySpecies())) {

                    List<AnimalOrganism> animalOrganisms = tileSpecies.get(animalSpecies);

                    if (chooseOrganismToImpersonateAnyGender(animalOrganisms)) return;
                }
            }
        }

    }

    private static boolean chooseOrganismToImpersonateAnyGender(List<AnimalOrganism> animalOrganisms) {
        synchronized (animalOrganisms) {
            for (AnimalOrganism animalOrganism : animalOrganisms) {

                if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE) {
                    animalOrganism.setImpersonatedOrganism(true);
                    Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " is impersonated now");

                    return true;

                }
            }
        }
        return false;
    }

    private void initializeAnimalOrganisms(AnimalSpecies animalSpecies) {

        int carryingCapacity = (int) animalSpecies.getAttribute(AnimalAttribute.CARRYING_CAPACITY).getAverageValue();

        for (int i=0; i<carryingCapacity; i++) {

            Gender gender = Gender.FEMALE;
            if (RandomGenerator.random.nextDouble() < 0.50) {
                gender = Gender.MALE;
            }

            double femaleLifeSpan = animalSpecies.getAttribute(AnimalAttribute.LIFESPAN).getValue(Gender.FEMALE);
            double maleLifeSpan = animalSpecies.getAttribute(AnimalAttribute.LIFESPAN).getValue(Gender.MALE);
            double lifeSpan = RandomGenerator.generateGaussian(gender, femaleLifeSpan, maleLifeSpan, RandomGenerator.GAUSSIAN_VARIANCE);
            double femaleWeight = animalSpecies.getAttribute(AnimalAttribute.WEIGHT).getValue(Gender.FEMALE);
            double maleWeight = animalSpecies.getAttribute(AnimalAttribute.WEIGHT).getValue(Gender.MALE);
            double femaleHeight = animalSpecies.getAttribute(AnimalAttribute.HEIGHT).getValue(Gender.FEMALE);
            double maleHeight = animalSpecies.getAttribute(AnimalAttribute.HEIGHT).getValue(Gender.MALE);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            double latitudeSpan = SimulationSettings.MAX_LATITUDE - SimulationSettings.MIN_LATITUDE;
            double latitude = SimulationSettings.MIN_LATITUDE + (latitudeSpan * RandomGenerator.random.nextDouble());
            double longitudeSpan = SimulationSettings.MAX_LONGITUDE - SimulationSettings.MIN_LONGITUDE;
            double longitude = SimulationSettings.MIN_LONGITUDE + (longitudeSpan * RandomGenerator.random.nextDouble());

            AnimalVitalsAttributes animalVitalsAttributes = new AnimalVitalsAttributes(
                    RandomGenerator.generateGaussian(femaleWeight, maleWeight, RandomGenerator.GAUSSIAN_VARIANCE),
                    RandomGenerator.generateGaussian(femaleHeight, maleHeight, RandomGenerator.GAUSSIAN_VARIANCE),
                    lifeSpan,
                    0,
                    animalSpecies.getAttribute(AnimalAttribute.ENERGY_LOSS).getValue(gender)
            );

            AnimalPositionAttributes animalPositionAttributes = new AnimalPositionAttributes(
                    latitude,
                    longitude
            );

            AnimalNutritionAttributes animalNutritionAttributes = new AnimalNutritionAttributes(
                    animalSpecies.getAttribute(AnimalAttribute.HUNT_ATTEMPTS).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.ENERGY_GAIN).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.PREY_EATEN).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.PLANT_CONSUMPTION_RATE).getValue(gender)
            );

            AnimalReproductionAttributes animalReproductionAttributes = new AnimalReproductionAttributes(
                    animalSpecies.getAttribute(AnimalAttribute.SEXUAL_MATURITY_START).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.SEXUAL_MATURITY_END).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.MATING_SEASON_START).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.MATING_SEASON_END).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.PREGNANCY_COOLDOWN).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.GESTATION_PERIOD).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.AVERAGE_OFFSPRING).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.JUVENILE_SURVIVAL_RATE).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.MATING_SUCCESS_RATE).getValue(gender),
                    animalSpecies.getAttribute(AnimalAttribute.MATING_ATTEMPTS).getValue(gender)
            );

            AnimalOrganismAttributes animalOrganismAttributes = new AnimalOrganismAttributes(
                    animalVitalsAttributes,
                    animalPositionAttributes,
                    animalNutritionAttributes,
                    animalReproductionAttributes
            );

            AnimalOrganism animalOrganism = new AnimalOrganism(
                    animalSpecies,
                    gender,
                    animalSpecies.getBaseDiet(),
                    OrganismStatus.ALIVE,
                    age,
                    new ImageView(animalSpecies.getImage()),
                    animalOrganismAttributes
            );
            animalOrganism.getPreyAnimalSpecies().putAll(animalSpecies.getBasePreyAnimalSpecies());

            Point tile = Geography.calculateTile(animalPositionAttributes.getLatitude(), animalPositionAttributes.getLongitude());

            addAnimalOrganism(tile, animalSpecies, animalOrganism);
            animalSpecies.setOrganismCount(animalSpecies.getOrganismCount() + 1);

        }

        initializeAnimalOrganismImages(animalSpecies);

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

    private static void bobcatAttributes(AnimalSpecies bobcat) {
        initializeAnimalSpeciesAttributes(bobcat);
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.LEPUS_AMERICANUS, LynxRufus.PREFERENCE_SNOWSHOE_HARE));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.CASTOR_FIBER, LynxRufus.PREFERENCE_EUROPEAN_BEAVER));
        bobcat.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, LynxRufus.PREFERENCE_WHITETAIL_DEER));
    }

    private static void europeanBeaverAttributes(AnimalSpecies europeanBeaver) {
        initializeAnimalSpeciesAttributes(europeanBeaver);
    }

    private static void snowshoeHareAttributes(AnimalSpecies snowshoeHare) {
        initializeAnimalSpeciesAttributes(snowshoeHare);
    }

    private static void grayWolfAttributes(AnimalSpecies grayWolf) {
        initializeAnimalSpeciesAttributes(grayWolf);
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, CanisLupus.PREFERENCE_WHITETAIL_DEER));
        grayWolf.addBasePreyAnimalSpecies(new PreyAnimalSpecies(TaxonomySpecies.ALCES_ALCES, CanisLupus.PREFERENCE_MOOSE));
    }

    private static void mooseAttributes(AnimalSpecies moose) {
        initializeAnimalSpeciesAttributes(moose);
    }

    private static void whiteTailedDeerAttributes(AnimalSpecies whiteTailedDeer) {
        initializeAnimalSpeciesAttributes(whiteTailedDeer);
    }

    public void initializeAnimalSpecies() {

        SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ODOCOILEUS, TaxonomySpecies.ODOCOILEUS_VIRGINIANUS);

        AnimalSpecies whiteTailedDeer = new AnimalSpecies(odocoileusTaxonomy, "White-Tailed Deer", new Image("images/whiteTailedDeer.png", 64, 64, false, false), Diet.HERBIVORE);
        whiteTailedDeerAttributes(whiteTailedDeer);
        initializeAnimalOrganisms(whiteTailedDeer);

        animalSpeciesMap.put(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, whiteTailedDeer);

        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ALCES, TaxonomySpecies.ALCES_ALCES);
        AnimalSpecies moose = new AnimalSpecies(alcesTaxonomy, "Moose", new Image("images/moose.png", 64, 64, false, false), Diet.HERBIVORE);
        mooseAttributes(moose);
        initializeAnimalOrganisms(moose);
        animalSpeciesMap.put(TaxonomySpecies.ALCES_ALCES, moose);

        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.CANIDAE, TaxonomyGenus.CANIS, TaxonomySpecies.CANIS_LUPUS);
        AnimalSpecies grayWolf = new AnimalSpecies(canisTaxonomy, "Gray Wolf", new Image("images/grayWolf.png", 64, 64, false, false), Diet.CARNIVORE);
        grayWolfAttributes(grayWolf);
        initializeAnimalOrganisms(grayWolf);
        animalSpeciesMap.put(TaxonomySpecies.CANIS_LUPUS, grayWolf);

        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.LAGOMORPHA, TaxonomyFamily.LEPORIDAE, TaxonomyGenus.LEPUS, TaxonomySpecies.LEPUS_AMERICANUS);
        AnimalSpecies snowshoeHare = new AnimalSpecies(lepusTaxonomy, "Snowshoe Hare", new Image("images/snowshoeHare.png", 64, 64, false, false), Diet.HERBIVORE);
        snowshoeHareAttributes(snowshoeHare);
        initializeAnimalOrganisms(snowshoeHare);
        animalSpeciesMap.put(TaxonomySpecies.LEPUS_AMERICANUS, snowshoeHare);

        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.RODENTIA, TaxonomyFamily.CASTORIDAE, TaxonomyGenus.CASTOR, TaxonomySpecies.CASTOR_FIBER);
        AnimalSpecies europeanBeaver = new AnimalSpecies(castorTaxonomy, "European Beaver", new Image("images/europeanBeaver.png", 64, 64, false, false), Diet.HERBIVORE);
        europeanBeaverAttributes(europeanBeaver);
        initializeAnimalOrganisms(europeanBeaver);
        animalSpeciesMap.put(TaxonomySpecies.CASTOR_FIBER, europeanBeaver);

        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.FELIDAE, TaxonomyGenus.LYNX, TaxonomySpecies.LYNX_RUFUS);
        AnimalSpecies bobcat = new AnimalSpecies(lynxTaxonomy, "Bobcat", new Image("images/bobcat.png", 64, 64, false, false), Diet.CARNIVORE);
        bobcatAttributes(bobcat);
        initializeAnimalOrganisms(bobcat);
        animalSpeciesMap.put(TaxonomySpecies.LYNX_RUFUS, bobcat);

        chooseImpersonatingOrganism();

    }

    public void printSpeciesDistribution() {

        for (PlantSpecies plantSpecies : plantSpeciesMap.values()) {
            String s = Log.padRight(16, plantSpecies.getCommonName());
            s = s + " | " + Log.padLeft(24, Log.formatNumber(plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue()) + " CAP");
            s = s + " | " + Log.padLeft(24, Log.formatNumber(plantSpecies.getOrganismCount()) + " QTY");
            Log.log7(s);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            String s = Log.padRight(16, animalSpecies.getCommonName());
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getAttribute(AnimalAttribute.CARRYING_CAPACITY).getAverageValue()) + " CAP");
            s = s + " | " + Log.padLeft(14, Log.formatNumber(animalSpecies.getOrganismCount()) + " ALIVE");
            s = s + " | " + Log.padLeft(13, Log.formatNumber(animalSpecies.getDeadPopulation()) + " DEAD");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.PREDATION)) + " PRE");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.STARVATION)) + " STA");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.AGE)) + " AGE");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.JUVENILE_DEATH)) + " JUV");
            Log.log7(s);
        }

    }

    public void printImpersonatedOrganism() {

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            Map<AnimalSpecies, List<AnimalOrganism>> tileSpecies = tile.getValue().animalOrganisms();

            for (AnimalSpecies animalSpecies : tile.getValue().animalOrganisms().keySet()) {

                if (animalSpecies.getSpeciesTaxonomy().taxonomySpecies() == SimulationSettings.getImpersonatingTaxonomySpecies()) {

                    List<AnimalOrganism> animalOrganisms = tileSpecies.get(animalSpecies);

                    for (AnimalOrganism animalOrganism : animalOrganisms) {
                        if (animalOrganism.isImpersonatedOrganism()) {
                            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + ": " + Log.formatPercentage(animalOrganism.getEnergy()));
                        }

                    }
                }
            }
        }

    }

    // TODO: move iterate methods to dedicated class

    @SuppressWarnings("unused")
    private <X extends Organism> void iterateOrganismConsumer(Predicate<X> predicate, Consumer<X> action, List<X> organisms) {
        if (predicate.equals(animalTruePredicate) || predicate.equals(plantTruePredicate) ) {
            synchronized (organisms) {
                for (X organism : organisms) {
                    action.accept(organism);
                }
            }
        }
        else {
            synchronized (organisms) {
                for (X organism : organisms) {
                    if (predicate.test(organism)) {
                        action.accept(organism);
                    }
                }
            }
        }
    }

    private <X extends Organism, Y> void iterateOrganismBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action, List<X> organisms, Y y) {
        if (predicate.equals(animalTruePredicate) || predicate.equals(plantTruePredicate) ) {
            synchronized (organisms) {
                for (X organism : organisms) {
                    action.accept(organism, y);
                }
            }
        }
        else {
            synchronized (organisms) {
                for (X organism : organisms) {
                    if (predicate.test(organism)) {
                        action.accept(organism, y);
                    }
                }
            }
        }
    }

    private <X extends Organism, Y, Z> void iterateOrganismTriConsumer(Predicate<X> predicate, TriConsumer<X, Y, Z> action, List<X> organisms, Y y, Z z) {
        if (predicate.equals(animalTruePredicate) || predicate.equals(plantTruePredicate) ) {
            synchronized (organisms) {
                for (X organism : organisms) {
                    action.accept(organism, y, z);
                }
            }
        }
        else {
            synchronized (organisms) {
                for (X organism : organisms) {
                    if (predicate.test(organism)) {
                        action.accept(organism, y, z);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <X extends  Organism, Y> void iterateTilePlantSpeciesBiConsumer(BiConsumer<X, Y> action, TileOrganisms tileOrganisms) {

        for (Map.Entry<PlantSpecies, List<PlantOrganism>> plantEntry : tileOrganisms.plantOrganisms().entrySet()) {
            PlantSpecies plantSpecies = plantEntry.getKey();
            List<PlantOrganism> plantOrganisms = plantEntry.getValue();
            iterateOrganismBiConsumer((Predicate<X>) plantTruePredicate, action, (List<X>) plantOrganisms, (Y) plantSpecies);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y, Z> void iterateTilePlantSpeciesTriConsumer(TriConsumer<X, Y, Z> action, TileOrganisms tileOrganisms, Z z) {

        for (Map.Entry<PlantSpecies, List<PlantOrganism>> plantEntry : tileOrganisms.plantOrganisms().entrySet()) {
            PlantSpecies plantSpecies = plantEntry.getKey();
            List<PlantOrganism> plantOrganisms = plantEntry.getValue();
            iterateOrganismTriConsumer((Predicate<X>) plantTruePredicate, action, (List<X>) plantOrganisms, (Y) plantSpecies, z);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism> void iterateTileAnimalSpeciesConsumer(Predicate<X> predicate, Consumer<X> action, TileOrganisms tileOrganisms) {

        for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {
            List<AnimalOrganism> animalOrganisms = animalEntry.getValue();
            iterateOrganismConsumer(predicate, action, (List<X>) animalOrganisms);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y> void iterateTileAnimalSpeciesBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action, TileOrganisms tileOrganisms) {

        for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {
            AnimalSpecies animalSpecies = animalEntry.getKey();
            List<AnimalOrganism> animalOrganisms = animalEntry.getValue();
            iterateOrganismBiConsumer(predicate, action, (List<X>) animalOrganisms, (Y) animalSpecies);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y, Z> void iterateTileAnimalSpeciesTriConsumer(Predicate<X> predicate, TriConsumer<X, Y, Z> action, TileOrganisms tileOrganisms, Z z) {

        for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {
            AnimalSpecies animalSpecies = animalEntry.getKey();
            List<AnimalOrganism> animalOrganisms = animalEntry.getValue();
            iterateOrganismTriConsumer(predicate, action, (List<X>) animalOrganisms, (Y) animalSpecies, z);
        }

    }

    public void iteratePlantOrganisms(BiConsumer<PlantOrganism, PlantSpecies> action) {
        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTilePlantSpeciesBiConsumer(action, tileOrganisms);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iteratePlantOrganismsPerPlantSpecies(PlantSpecies plantSpecies, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            List<PlantOrganism> plantOrganisms = tileOrganisms.plantOrganisms().get(plantSpecies);
            iterateOrganismBiConsumer((Predicate<X>) plantTruePredicate, action, (List<X>) plantOrganisms, (Y) plantSpecies);
        }
    }

    public <X extends Organism> void iterateAnimalOrganismsConsumer(Predicate<X> predicate, Consumer<X> action) {
        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTileAnimalSpeciesConsumer(predicate, action, tileOrganisms);
        }
    }

    @SuppressWarnings("unused")
    public <X extends Organism, Y> void iterateAnimalOrganismsBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTileAnimalSpeciesBiConsumer(predicate, action, tileOrganisms);
        }
    }

    public <X extends Organism, Y, Z> void iterateAnimalOrganismsTriConsumer(Predicate<X> predicate, TriConsumer<X, Y, Z> action, Z z) {
        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTileAnimalSpeciesTriConsumer(predicate, action, tileOrganisms, z);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iterateAnimalOrganismsPerEachAnimalOrganism(Predicate<X> predicate, BiConsumer<X, Y> action) {

        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {

            TileOrganisms tileOrganisms = entry.getValue();

            for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {

                List<AnimalOrganism> animalOrganisms = animalEntry.getValue();

                synchronized (animalOrganisms) {
                    for (AnimalOrganism animalOrganism : animalOrganisms) {
                        if (predicate.test((X) animalOrganism)) {
                            iterateOrganismBiConsumer((Predicate<X>) animalTruePredicate, action, (List<X>) animalOrganisms, (Y) animalOrganism);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y, Z> void iteratePlantOrganismsPerEachAnimalOrganism(Predicate<X> predicate, TriConsumer<X, Y, Z> action) {

        for (Map.Entry<Point, TileOrganisms> entry : worldMap.entrySet()) {

            TileOrganisms tileOrganisms = entry.getValue();

            for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {

                List<AnimalOrganism> animalOrganisms = animalEntry.getValue();

                Consumer<X> innerAction = (X animalOrganism) -> iterateTilePlantSpeciesTriConsumer(action, tileOrganisms, (Z) animalOrganism);

                iterateOrganismConsumer(predicate, innerAction, (List<X>) animalOrganisms);


            }
        }
    }

}