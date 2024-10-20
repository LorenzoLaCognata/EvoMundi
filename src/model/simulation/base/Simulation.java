package model.simulation.base;

import model.environment.animals.attributes.AnimalPositionAttributes;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.base.Ecosystem;
import model.environment.common.enums.OrganismStatus;
import model.simulation.animals.*;
import model.simulation.plants.PlantGrowthSimulation;
import utils.Log;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.List;
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

    public AnimalMovementSimulation getAnimalMovementSimulation() {
        return animalMovementSimulation;
    }

    private void buryDead() {

        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {

            TileOrganisms tileOrganisms = entry.getValue();

            for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {

                List<AnimalOrganism> animalOrganisms = animalEntry.getValue();

                Iterator<AnimalOrganism> iterator = animalOrganisms.iterator();

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
    }

    // TODO: fix negative plants quantity

    private void newbornSurvival() {

        for (AnimalSpecies animalSpecies : ecosystem.getAnimalSpeciesMap().values()) {

            for (AnimalOrganism animalOrganism : animalSpecies.getNewbornOrganisms()) {

                animalOrganism.setOrganismStatus(OrganismStatus.ALIVE);

                AnimalPositionAttributes animalPositionAttributes = animalOrganism.getOrganismAttributes().animalPositionAttributes();
                Point tile = Geography.calculateTile(animalPositionAttributes.getLatitude(), animalPositionAttributes.getLongitude());

                ecosystem.addAnimalOrganism(tile, animalSpecies, animalOrganism);

                animalSpecies.setOrganismCount(animalSpecies.getOrganismCount() + 1);
                animalSpecies.getImageGroup().getChildren().add(animalOrganism.getOrganismIcons().getStackPane());
            }

            animalSpecies.getNewbornOrganisms().clear();
        }

    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.SIMULATION_SPEED_WEEKS);
        Log.log5("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());


        plantGrowthSimulation.plantRegeneration(ecosystem);

        animalAgingSimulation.animalAge(ecosystem);

        animalGrazingSimulation.animalGraze(ecosystem);

        animalHuntingSimulation.animalHunt(ecosystem);

        animalReproductionSimulation.animalReproduction(ecosystem);

        buryDead();

        newbornSurvival();

        if (Log.getLogger().getLevel().intValue() >= Level.FINER.intValue()) {

            ecosystem.printImpersonatedOrganism();

            if (Log.getLogger().getLevel().intValue() >= Level.FINEST.intValue()) {
                ecosystem.printSpeciesDistribution();
            }

        }
    }

}