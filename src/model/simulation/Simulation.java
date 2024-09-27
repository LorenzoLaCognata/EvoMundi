package model.simulation;

import model.animals.Organism;
import model.animals.Species;
import model.enums.OrganismStatus;
import model.enums.SpeciesType;
import model.environment.Biomass;
import model.environment.Ecosystem;
import utils.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Simulation {

    private final Ecosystem ecosystem;

    private final BiomassGrowthSimulation biomassGrowthSimulation;
    private final GrazingSimulation grazingSimulation;

    private final MovementSimulation movementSimulation;
    private final AgingSimulation agingSimulation;
    private final ReproductionSimulation reproductionSimulation;
    private final HuntingSimulation huntingSimulation;

    public Simulation() {

        ecosystem = new Ecosystem();

        biomassGrowthSimulation = new BiomassGrowthSimulation();
        grazingSimulation = new GrazingSimulation();

        movementSimulation = new MovementSimulation();
        agingSimulation = new AgingSimulation();
        reproductionSimulation = new ReproductionSimulation();
        huntingSimulation = new HuntingSimulation();

        ecosystem.getBiomassSet().addAll(Biomass.initializeBiomass());
        ecosystem.getSpeciesMap().putAll(Species.initializeSpecies());

    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    private static void buryDead(Species species) {

        Iterator<Organism> iterator = species.getOrganisms().iterator();
        while (iterator.hasNext()) {
            Organism organism = iterator.next();
            if (organism.getOrganismStatus() == OrganismStatus.DEAD) {
                species.getDeadOrganisms().add(organism);
                iterator.remove();
            }
        }

    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.SIMULATION_SPEED_WEEKS);
        Log.log5("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        Set<Biomass> biomassSet = ecosystem.getBiomassSet();

        for (Biomass biomass : biomassSet) {
            biomassGrowthSimulation.biomassRegenerate(biomass);
        }

        Map<SpeciesType, Species> speciesMap = ecosystem.getSpeciesMap();

        for (Species species : speciesMap.values()) {
            movementSimulation.speciesMove(species);
        }

        for (Species species : speciesMap.values()) {
            agingSimulation.speciesAge(species);
        }

        for (Species species : speciesMap.values()) {
            grazingSimulation.speciesGraze(biomassSet, species);
        }

        for (Species species : speciesMap.values()) {
            huntingSimulation.speciesHunt(speciesMap, species);
        }

        for (Species species : speciesMap.values()) {
            reproductionSimulation.speciesReproduction(species);
        }

        for (Species species : speciesMap.values()) {
            buryDead(species);
        }

        ecosystem.printSpeciesDistribution();

    }


}