package model.simulation.base;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.base.Ecosystem;
import model.environment.common.enums.OrganismStatus;
import model.simulation.animals.*;
import model.simulation.plants.PlantGrowthSimulation;
import utils.Log;
import view.TileOrganisms;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

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

        ecosystem.initializePlantSpecies();
        ecosystem.initializeAnimalSpecies();

    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    public PlantGrowthSimulation getPlantGrowthSimulation() {
        return plantGrowthSimulation;
    }

    public AnimalGrazingSimulation getAnimalGrazingSimulation() {
        return animalGrazingSimulation;
    }

    public AnimalMovementSimulation getAnimalMovementSimulation() {
        return animalMovementSimulation;
    }

    public AnimalAgingSimulation getAnimalAgingSimulation() {
        return animalAgingSimulation;
    }

    public AnimalReproductionSimulation getAnimalReproductionSimulation() {
        return animalReproductionSimulation;
    }

    public AnimalHuntingSimulation getAnimalHuntingSimulation() {
        return animalHuntingSimulation;
    }

    private void buryDead() {

        for (Map.Entry<Point, TileOrganisms> tile : ecosystem.getWorldMap().entrySet()) {

            Iterator<AnimalOrganism> iterator = tile.getValue().AnimalOrganisms().iterator();

            while (iterator.hasNext()) {
                AnimalOrganism animalOrganism = iterator.next();
                AnimalSpecies animalSpecies = animalOrganism.getAnimalSpecies();

                if (animalOrganism.getOrganismStatus() == OrganismStatus.DEAD) {
                    animalSpecies.getDeadOrganisms().add(animalOrganism);
                    animalSpecies.getImageGroup().getChildren().remove(animalOrganism.getOrganismIcons().getStackPane());
                    iterator.remove();
                    animalSpecies.setOrganismCount(animalSpecies.getOrganismCount() - 1);
                }
            }

        }
    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.SIMULATION_SPEED_WEEKS);
        Log.log5("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        plantGrowthSimulation.plantRegeneration(ecosystem.getWorldMap());

        animalAgingSimulation.animalAge(ecosystem.getWorldMap());

        animalGrazingSimulation.animalGraze(ecosystem.getWorldMap());

        animalHuntingSimulation.animalHunt(ecosystem.getWorldMap(), ecosystem.getAnimalSpeciesMap());

        animalReproductionSimulation.animalReproduction(ecosystem.getWorldMap());

        buryDead();

        if (Log.getLogger().getLevel().intValue() >= Level.FINER.intValue()) {

            ecosystem.printImpersonatedOrganism();

            if (Log.getLogger().getLevel().intValue() >= Level.FINEST.intValue()) {
                ecosystem.printSpeciesDistribution();
            }

        }
    }

}