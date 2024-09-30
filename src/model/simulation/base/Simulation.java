package model.simulation.base;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.enums.OrganismStatus;
import model.environment.common.enums.TaxonomySpecies;
import model.environment.common.base.Ecosystem;
import model.environment.plants.base.PlantSpecies;
import model.simulation.plants.PlantGrowthSimulation;
import model.simulation.animals.*;
import utils.Log;

import java.util.Iterator;
import java.util.Map;

public class Simulation {

    private final Ecosystem ecosystem;

    private final PlantGrowthSimulation plantGrowthSimulation;
    private final AnimalGrazingSimulation animalGrazingSimulation;

    private final AnimalMovementSimulation animalMovementSimulation;
    private final AnimalAgingSimulation animalAgingSimulation;
    private final AnimalReproductionSimulation animalReproductionSimulation;
    private final AnimalHuntingSimulation animalHuntingSimulation;

    public Simulation() {

        ecosystem = new Ecosystem();

        plantGrowthSimulation = new PlantGrowthSimulation();
        animalGrazingSimulation = new AnimalGrazingSimulation();

        animalMovementSimulation = new AnimalMovementSimulation();
        animalAgingSimulation = new AnimalAgingSimulation();
        animalReproductionSimulation = new AnimalReproductionSimulation();
        animalHuntingSimulation = new AnimalHuntingSimulation();

        ecosystem.getPlantSpeciesMap().putAll(PlantSpecies.initializePlantSpecies());
        ecosystem.getAnimalSpeciesMap().putAll(AnimalSpecies.initializeAnimalSpecies());

    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    private static void buryDead(AnimalSpecies animalSpecies) {

        Iterator<AnimalOrganism> iterator = animalSpecies.getOrganisms().iterator();
        while (iterator.hasNext()) {
            AnimalOrganism animalOrganism = iterator.next();
            if (animalOrganism.getOrganismStatus() == OrganismStatus.DEAD) {
                animalSpecies.getDeadOrganisms().add(animalOrganism);
                iterator.remove();
            }
        }

    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.SIMULATION_SPEED_WEEKS);
        Log.log5("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap = ecosystem.getPlantSpeciesMap();

        for (PlantSpecies plantSpecies : plantSpeciesMap.values()) {
            plantGrowthSimulation.plantSpeciesRegeneration(plantSpecies);
        }

        Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap = ecosystem.getAnimalSpeciesMap();

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            animalMovementSimulation.speciesMove(animalSpecies);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            animalAgingSimulation.speciesAge(animalSpecies);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            animalGrazingSimulation.speciesGraze(plantSpeciesMap, animalSpecies);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            animalHuntingSimulation.speciesHunt(animalSpeciesMap, animalSpecies);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            animalReproductionSimulation.speciesReproduction(animalSpecies);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            buryDead(animalSpecies);
        }

        ecosystem.printSpeciesDistribution();

    }


}