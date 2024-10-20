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
import view.TileOrganisms;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ecosystem {

    private final Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap;
    private final Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap;
    private final Map<Point, TileOrganisms> worldMap = new ConcurrentHashMap<>();

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

    public Map<Point, TileOrganisms> getWorldMap() {
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

    private boolean chooseOrganismToImpersonateAnyGender(List<AnimalOrganism> animalOrganisms) {
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
                        animalOrganism.logEnergy();

                    }
                }
            }
        }

    }













    public void initializePlantOrganismImages() {
        iterationManager.iteratePlantOrganisms(this, IterationManager.plantTruePredicate, (plantOrganism, plantSpecies) ->
                initializationManager.addPlantOrganismImage(plantOrganism, (PlantSpecies) plantSpecies)
        );
    }

    public void initializeAnimalOrganismImages() {
        iterationManager.iterateAnimalOrganismsBiConsumer(this, IterationManager.animalTruePredicate, (animalOrganism, animalSpecies) ->
                initializationManager.addAnimalOrganismImage(animalOrganism, (AnimalSpecies) animalSpecies)
        );
    }


}