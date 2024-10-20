package model.environment.common.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.environment.animals.attributes.*;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.animals.enums.Diet;
import model.environment.animals.enums.Gender;
import model.environment.common.enums.*;
import model.environment.plants.attributes.PlantOrganismAttributes;
import model.environment.plants.attributes.PlantPositionAttributes;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import model.simulation.base.SimulationSettings;
import utils.Constants;
import utils.RandomGenerator;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.List;
import java.util.*;

public class InitializationManager {

    private List<PlantOrganism> initializePlantOrganisms(PlantSpecies plantSpecies) {

        List<PlantOrganism> plantOrganisms = new ArrayList<>();

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

                plantOrganisms.add(plantOrganism);

            }

        }

        return plantOrganisms;

    }

    private List<AnimalOrganism> initializeAnimalOrganisms(AnimalSpecies animalSpecies) {

        List<AnimalOrganism> animalOrganisms = new ArrayList<>();

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

            animalOrganisms.add(animalOrganism);

        }

        return animalOrganisms;

    }

    public void addPlantOrganism(Map<Point, TileOrganisms> worldMap, Point tile, PlantSpecies plantSpecies, PlantOrganism plantOrganism) {

        TileOrganisms tileOrganisms = worldMap.computeIfAbsent(tile, ignored -> new TileOrganisms(new HashMap<>(), new HashMap<>()));

        tileOrganisms.plantOrganisms()
                .computeIfAbsent(plantSpecies, ignored -> Collections.synchronizedList(new ArrayList<>()))
                .add(plantOrganism);

        worldMap.put(tile, tileOrganisms);
        plantSpecies.setOrganismCount(plantSpecies.getOrganismCount() + plantOrganism.getQuantity());

    }

    public void addAnimalOrganism(Map<Point, TileOrganisms> worldMap, Point tile, AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        TileOrganisms tileOrganisms = worldMap.computeIfAbsent(tile, ignored -> new TileOrganisms(new HashMap<>(), new HashMap<>()));

        tileOrganisms.animalOrganisms()
                .computeIfAbsent(animalSpecies, ignored -> Collections.synchronizedList(new ArrayList<>()))
                .add(animalOrganism);

        worldMap.put(tile, tileOrganisms);
        animalSpecies.setOrganismCount(animalSpecies.getOrganismCount() + 1);

    }

    private void addPlantSpeciesOrganisms(Map<Point, TileOrganisms> worldMap, List<PlantOrganism> plantOrganisms, PlantSpecies plantSpecies) {
        for (PlantOrganism plantOrganism : plantOrganisms) {
            PlantPositionAttributes plantPositionAttributes = plantOrganism.getPlantOrganismAttributes().plantPositionAttributes();
            Point tile = Geography.calculateTile(plantPositionAttributes.getLatitude(), plantPositionAttributes.getLongitude());
            addPlantOrganism(worldMap, tile, plantSpecies, plantOrganism);
        }
    }

    private void addAnimalSpeciesOrganisms(Map<Point, TileOrganisms> worldMap, List<AnimalOrganism> animalOrganisms, AnimalSpecies animalSpecies) {
        for (AnimalOrganism animalOrganism : animalOrganisms) {
            AnimalPositionAttributes animalPositionAttributes = animalOrganism.getOrganismAttributes().animalPositionAttributes();
            Point tile = Geography.calculateTile(animalPositionAttributes.getLatitude(), animalPositionAttributes.getLongitude());
            addAnimalOrganism(worldMap, tile, animalSpecies, animalOrganism);
        }
    }

    public Map<TaxonomySpecies, PlantSpecies>  initializePlantSpecies(Map<Point, TileOrganisms> worldMap) {

        EnumMap<TaxonomySpecies, PlantSpecies> plantSpeciesMap = new EnumMap<>(TaxonomySpecies.class);

        SpeciesTaxonomy salixAlbaTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAGNOLIOPSIDA, TaxonomyOrder.MALPIGHIALES, TaxonomyFamily.SALICACEAE, TaxonomyGenus.SALIX, TaxonomySpecies.SALIX_ALBA);

        PlantSpecies whiteWillow = new PlantSpecies(salixAlbaTaxonomy, "White Willow", new Image("images/whiteWillow.png"));
        Constants.whiteWillowAttributes(whiteWillow);
        plantSpeciesMap.put(TaxonomySpecies.SALIX_ALBA, whiteWillow);

        List<PlantOrganism> whiteWillowOrganisms = initializePlantOrganisms(whiteWillow);
        addPlantSpeciesOrganisms(worldMap, whiteWillowOrganisms, whiteWillow);

        return plantSpeciesMap;

    }

    public Map<TaxonomySpecies, AnimalSpecies> initializeAnimalSpecies(Map<Point, TileOrganisms> worldMap) {

        EnumMap<TaxonomySpecies, AnimalSpecies> animalSpeciesMap = new EnumMap<>(TaxonomySpecies.class);

        SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ODOCOILEUS, TaxonomySpecies.ODOCOILEUS_VIRGINIANUS);

        AnimalSpecies whiteTailedDeer = new AnimalSpecies(odocoileusTaxonomy, "White-Tailed Deer", new Image("images/whiteTailedDeer.png", 64, 64, false, false), Diet.HERBIVORE);
        Constants.whiteTailedDeerAttributes(whiteTailedDeer);
        animalSpeciesMap.put(TaxonomySpecies.ODOCOILEUS_VIRGINIANUS, whiteTailedDeer);

        List<AnimalOrganism> whiteTailedDeerOrganisms = initializeAnimalOrganisms(whiteTailedDeer);
        addAnimalSpeciesOrganisms(worldMap, whiteTailedDeerOrganisms, whiteTailedDeer);

        SpeciesTaxonomy alcesTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.ARTIODACTYLA, TaxonomyFamily.CERVIDAE, TaxonomyGenus.ALCES, TaxonomySpecies.ALCES_ALCES);
        AnimalSpecies moose = new AnimalSpecies(alcesTaxonomy, "Moose", new Image("images/moose.png", 64, 64, false, false), Diet.HERBIVORE);
        Constants.mooseAttributes(moose);
        animalSpeciesMap.put(TaxonomySpecies.ALCES_ALCES, moose);

        List<AnimalOrganism> mooseOrganisms = initializeAnimalOrganisms(moose);
        addAnimalSpeciesOrganisms(worldMap, mooseOrganisms, moose);

        SpeciesTaxonomy canisTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.CANIDAE, TaxonomyGenus.CANIS, TaxonomySpecies.CANIS_LUPUS);
        AnimalSpecies grayWolf = new AnimalSpecies(canisTaxonomy, "Gray Wolf", new Image("images/grayWolf.png", 64, 64, false, false), Diet.CARNIVORE);
        Constants.grayWolfAttributes(grayWolf);
        animalSpeciesMap.put(TaxonomySpecies.CANIS_LUPUS, grayWolf);

        List<AnimalOrganism> grayWolfOrganisms = initializeAnimalOrganisms(grayWolf);
        addAnimalSpeciesOrganisms(worldMap, grayWolfOrganisms, grayWolf);

        SpeciesTaxonomy lepusTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.LAGOMORPHA, TaxonomyFamily.LEPORIDAE, TaxonomyGenus.LEPUS, TaxonomySpecies.LEPUS_AMERICANUS);
        AnimalSpecies snowshoeHare = new AnimalSpecies(lepusTaxonomy, "Snowshoe Hare", new Image("images/snowshoeHare.png", 64, 64, false, false), Diet.HERBIVORE);
        Constants.snowshoeHareAttributes(snowshoeHare);
        animalSpeciesMap.put(TaxonomySpecies.LEPUS_AMERICANUS, snowshoeHare);

        List<AnimalOrganism> snowshoeHareOrganisms = initializeAnimalOrganisms(snowshoeHare);
        addAnimalSpeciesOrganisms(worldMap, snowshoeHareOrganisms, snowshoeHare);

        SpeciesTaxonomy castorTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.RODENTIA, TaxonomyFamily.CASTORIDAE, TaxonomyGenus.CASTOR, TaxonomySpecies.CASTOR_FIBER);
        AnimalSpecies europeanBeaver = new AnimalSpecies(castorTaxonomy, "European Beaver", new Image("images/europeanBeaver.png", 64, 64, false, false), Diet.HERBIVORE);
        Constants.europeanBeaverAttributes(europeanBeaver);
        animalSpeciesMap.put(TaxonomySpecies.CASTOR_FIBER, europeanBeaver);

        List<AnimalOrganism> europeanBeaverOrganisms = initializeAnimalOrganisms(europeanBeaver);
        addAnimalSpeciesOrganisms(worldMap, europeanBeaverOrganisms, europeanBeaver);

        SpeciesTaxonomy lynxTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAMMALIA, TaxonomyOrder.CARNIVORA, TaxonomyFamily.FELIDAE, TaxonomyGenus.LYNX, TaxonomySpecies.LYNX_RUFUS);
        AnimalSpecies bobcat = new AnimalSpecies(lynxTaxonomy, "Bobcat", new Image("images/bobcat.png", 64, 64, false, false), Diet.CARNIVORE);
        Constants.bobcatAttributes(bobcat);
        animalSpeciesMap.put(TaxonomySpecies.LYNX_RUFUS, bobcat);

        List<AnimalOrganism> bobcatOrganisms = initializeAnimalOrganisms(bobcat);
        addAnimalSpeciesOrganisms(worldMap, bobcatOrganisms, bobcat);

        return animalSpeciesMap;

    }

    public void addPlantOrganismImage(PlantOrganism plantOrganism, PlantSpecies plantSpecies) {
        plantSpecies.getImageGroup().getChildren().add(plantOrganism.getOrganismIcons().getStackPane());
    }

    public void addAnimalOrganismImage(AnimalOrganism animalOrganism, AnimalSpecies animalSpecies) {
        animalSpecies.getImageGroup().getChildren().add(animalOrganism.getOrganismIcons().getStackPane());
    }

}
