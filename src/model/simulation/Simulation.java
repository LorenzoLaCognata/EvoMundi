package model.simulation;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.base.OrganismStatus;
import model.environment.base.TaxonomySpecies;
import model.environment.plants.base.PlantPatch;
import model.environment.base.Ecosystem;
import utils.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Simulation {

    private final Ecosystem ecosystem;

    private final PlantGrowthSimulation plantGrowthSimulation;
    private final GrazingSimulation grazingSimulation;

    private final MovementSimulation movementSimulation;
    private final AgingSimulation agingSimulation;
    private final ReproductionSimulation reproductionSimulation;
    private final HuntingSimulation huntingSimulation;

    public Simulation() {

        ecosystem = new Ecosystem();

        plantGrowthSimulation = new PlantGrowthSimulation();
        grazingSimulation = new GrazingSimulation();

        movementSimulation = new MovementSimulation();
        agingSimulation = new AgingSimulation();
        reproductionSimulation = new ReproductionSimulation();
        huntingSimulation = new HuntingSimulation();

        ecosystem.getPlantPatchSet().addAll(PlantPatch.initializePlantPatchSet());
        ecosystem.getAnimalSpeciesMap().putAll(AnimalSpecies.initializeSpecies());

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

        Set<PlantPatch> plantPatchSet = ecosystem.getPlantPatchSet();

        for (PlantPatch plantPatch : plantPatchSet) {
            plantGrowthSimulation.plantPatchRegeneration(plantPatch);
        }

        Map<TaxonomySpecies, AnimalSpecies> speciesMap = ecosystem.getAnimalSpeciesMap();

        for (AnimalSpecies animalSpecies : speciesMap.values()) {
            movementSimulation.speciesMove(animalSpecies);
        }

        for (AnimalSpecies animalSpecies : speciesMap.values()) {
            agingSimulation.speciesAge(animalSpecies);
        }

        for (AnimalSpecies animalSpecies : speciesMap.values()) {
            grazingSimulation.speciesGraze(plantPatchSet, animalSpecies);
        }

        for (AnimalSpecies animalSpecies : speciesMap.values()) {
            huntingSimulation.speciesHunt(speciesMap, animalSpecies);
        }

        for (AnimalSpecies animalSpecies : speciesMap.values()) {
            reproductionSimulation.speciesReproduction(animalSpecies);
        }

        for (AnimalSpecies animalSpecies : speciesMap.values()) {
            buryDead(animalSpecies);
        }

        ecosystem.printSpeciesDistribution();

    }


}