package model.environment.common.base;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.common.enums.OrganismStatus;
import model.environment.common.enums.TaxonomySpecies;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import model.simulation.base.SimulationSettings;
import utils.Log;
import view.Tile;
import view.TileSpecies;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ecosystem {

    private final Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap;
    private final Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap;
    private final Map<Point, Tile> worldMap = new ConcurrentHashMap<>();

    private final InitializationManager initializationManager = new InitializationManager();
    private final IterationManager iterationManager = new IterationManager();

    public Ecosystem() {
        plantSpeciesMap = initializationManager.initializePlantSpecies(worldMap);
        initializePlantOrganismImages();
        animalSpeciesMap = initializationManager.initializeAnimalSpecies(worldMap);
        initializeAnimalOrganismImages();
        chooseImpersonatingOrganism();
    }

    public Map<TaxonomySpecies, PlantSpecies> getPlantSpeciesMap() {
        return plantSpeciesMap;
    }

    public Map<TaxonomySpecies, AnimalSpecies> getAnimalSpeciesMap() {
        return animalSpeciesMap;
    }

    public IterationManager getIterationManager() {
        return iterationManager;
    }

    public InitializationManager getInitializationManager() {
        return initializationManager;
    }

    public Map<Point, Tile> getWorldMap() {
        return worldMap;
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

        for (Map.Entry<Point, Tile> entry : worldMap.entrySet()) {

            Map<AnimalSpecies, TileSpecies> tileSpecies = entry.getValue().animalTileSpecies();

            for (AnimalSpecies animalSpecies : entry.getValue().animalTileSpecies().keySet()) {

                if (canBeImpersonated(animalSpecies.getSpeciesTaxonomy().taxonomySpecies())) {

                    List<Organism> animalOrganisms = tileSpecies.get(animalSpecies).organisms();

                    if (chooseOrganismToImpersonateSelectedGender(animalSpecies, animalOrganisms)) return true;
                }
            }
        }
        return false;
    }

    private boolean chooseOrganismToImpersonateSelectedGender(AnimalSpecies animalSpecies, List<Organism> animalOrganisms) {

        synchronized (animalOrganisms) {

            for (Organism organism : animalOrganisms) {

                AnimalOrganism animalOrganism = (AnimalOrganism) organism;

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

        for (Map.Entry<Point, Tile> entry : worldMap.entrySet()) {

            Map<AnimalSpecies, TileSpecies> tileSpecies = entry.getValue().animalTileSpecies();

            for (AnimalSpecies animalSpecies : entry.getValue().animalTileSpecies().keySet()) {

                if (canBeImpersonated(animalSpecies.getSpeciesTaxonomy().taxonomySpecies())) {

                    List<Organism> animalOrganisms = tileSpecies.get(animalSpecies).organisms();

                    if (chooseOrganismToImpersonateAnyGender(animalOrganisms)) return;
                }
            }
        }

    }

    private boolean chooseOrganismToImpersonateAnyGender(List<Organism> animalOrganisms) {
        synchronized (animalOrganisms) {
            for (Organism organism : animalOrganisms) {

                if (organism instanceof AnimalOrganism animalOrganism) {

                    if (animalOrganism.getOrganismStatus() == OrganismStatus.ALIVE) {
                        animalOrganism.setImpersonatedOrganism(true);
                        Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " is impersonated now");

                        return true;

                    }
                }
            }
        }
        return false;
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

        for (Map.Entry<Point, Tile> entry : worldMap.entrySet()) {

            Map<AnimalSpecies, TileSpecies> tileSpecies = entry.getValue().animalTileSpecies();

            for (AnimalSpecies animalSpecies : entry.getValue().animalTileSpecies().keySet()) {

                if (animalSpecies.getSpeciesTaxonomy().taxonomySpecies() == SimulationSettings.getImpersonatingTaxonomySpecies()) {

                    List<Organism> animalOrganisms = tileSpecies.get(animalSpecies).organisms();

                    for (Organism organism : animalOrganisms) {
                        if (organism instanceof AnimalOrganism) {
                            ((AnimalOrganism) organism).logEnergy();
                        }
                    }
                }
            }
        }

    }













    public void initializePlantOrganismImages() {

        for (Map.Entry<Point, Tile> entry : worldMap.entrySet()) {
            Tile tile = entry.getValue();

            for (Map.Entry<PlantSpecies, TileSpecies> tileSpeciesEntry : tile.plantTileSpecies().entrySet()) {
                Organism plantOrganism = tileSpeciesEntry.getValue().organisms().getFirst();
                tileSpeciesEntry.getValue().addOrganismImage(plantOrganism.getOrganismIcons().getStackPane());
            }

        }

    }

    public void initializeAnimalOrganismImages() {

        for (Map.Entry<Point, Tile> entry : worldMap.entrySet()) {
            Tile tile = entry.getValue();

            for (Map.Entry<AnimalSpecies, TileSpecies> tileSpeciesEntry : tile.animalTileSpecies().entrySet()) {
                Organism animalOrganism = tileSpeciesEntry.getValue().organisms().getFirst();
                tileSpeciesEntry.getValue().addOrganismImage(animalOrganism.getOrganismIcons().getStackPane());
            }

        }

    }


}