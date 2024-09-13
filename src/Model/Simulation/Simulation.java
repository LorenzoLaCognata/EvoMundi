package Model.Simulation;

import Model.Animals.Species;
import Model.Enums.BiomassType;
import Model.Enums.LogStatus;
import Model.Enums.SpeciesType;
import Model.Environment.Biomass;
import Model.Environment.Ecosystem;
import Utils.Log;

import java.util.Map;

public class Simulation {

    private final Ecosystem ecosystem;

    private final BiomassGrowthSimulation biomassGrowthSimulation;
    private final GrazingSimulation grazingSimulation;

    private final AgingSimulation agingSimulation;
    private final ReproductionSimulation reproductionSimulation;
    private final HuntingSimulation huntingSimulation;

    public Simulation() {

        ecosystem = new Ecosystem();

        biomassGrowthSimulation = new BiomassGrowthSimulation();
        grazingSimulation = new GrazingSimulation();

        agingSimulation = new AgingSimulation();
        reproductionSimulation = new ReproductionSimulation();
        huntingSimulation = new HuntingSimulation();

        ecosystem.getBiomassMap().putAll(Biomass.initializeBiomass());
        ecosystem.getSpeciesMap().putAll(Species.initializeSpecies());
        Log.logln("");
        Log.logln("ECOSYSTEM");
        Log.logln("");
        ecosystem.printSpeciesDetails(LogStatus.INACTIVE);
        Log.logln("--------");

    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.SIMULATION_SPEED_WEEKS);
        Log.logln("--------");
        Log.logln("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        Log.logln("");

        Map<BiomassType, Biomass> biomassMap = ecosystem.getBiomassMap();

        for (Biomass biomass : biomassMap.values()) {
            biomassGrowthSimulation.biomassRegenerate(biomass);
        }

        Map<SpeciesType, Species> speciesMap = ecosystem.getSpeciesMap();

        for (Species species : speciesMap.values()) {
            agingSimulation.speciesAge(species);
        }

        for (Species species : speciesMap.values()) {
            grazingSimulation.speciesGraze(biomassMap, species);
        }

        for (Species species : speciesMap.values()) {
            huntingSimulation.speciesHunt(speciesMap, species);
        }

        for (Species species : speciesMap.values()) {
            reproductionSimulation.speciesReproduction(species);
        }

        ecosystem.printSpeciesDistribution(LogStatus.ACTIVE);

    }

}