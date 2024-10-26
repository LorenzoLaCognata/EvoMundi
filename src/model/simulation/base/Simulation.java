package model.simulation.base;

import model.environment.animals.attributes.AnimalPositionAttributes;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.base.Ecosystem;
import model.environment.common.base.Organism;
import model.environment.common.enums.OrganismStatus;
import model.simulation.animals.*;
import model.simulation.plants.PlantGrowthSimulation;
import utils.Log;
import view.Geography;
import view.Tile;
import view.TileSpecies;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
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

    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    public AnimalMovementSimulation getAnimalMovementSimulation() {
        return animalMovementSimulation;
    }

    private void buryDead() {

        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {

            Tile tile = entry.getValue();

            for (Map.Entry<AnimalSpecies, TileSpecies> animalEntry : tile.animalTileSpecies().entrySet()) {

                AnimalSpecies animalSpecies = animalEntry.getKey();
                List<Organism> animalOrganisms = animalEntry.getValue().organisms();

                Iterator<Organism> iterator = animalOrganisms.iterator();

                while (iterator.hasNext()) {
                    AnimalOrganism animalOrganism = (AnimalOrganism) iterator.next();

                    if (animalOrganism.getOrganismStatus() == OrganismStatus.DEAD) {
                        animalSpecies.addDeadOrganism(animalOrganism);
                        animalSpecies.setOrganismCount(animalSpecies.getOrganismCount() - 1);
                        iterator.remove();

                        if (!animalEntry.getValue().organismImages().getChildren().isEmpty()) {
                            if (animalEntry.getValue().containsOrganismImage(animalOrganism.getOrganismIcons().getStackPane())) {
                                animalEntry.getValue().removeOrganismImage(animalOrganism.getOrganismIcons().getStackPane());
                            }
                        }

                    }

                }

                for (Organism animalOrganism : animalOrganisms) {
                    if (animalEntry.getValue().organismImages().getChildren().isEmpty()) {
                        animalEntry.getValue().addOrganismImage(animalOrganism.getOrganismIcons().getStackPane());
                    }

                }

            }
        }
    }

    private void newbornSurvival() {

        for (AnimalSpecies animalSpecies : ecosystem.getAnimalSpeciesMap().values()) {

            for (AnimalOrganism animalOrganism : animalSpecies.getNewbornOrganisms()) {

                animalOrganism.setOrganismStatus(OrganismStatus.ALIVE);

                AnimalPositionAttributes animalPositionAttributes = animalOrganism.getOrganismAttributes().animalPositionAttributes();
                Point tile = Geography.calculateTile(animalPositionAttributes.getLatitude(), animalPositionAttributes.getLongitude());

                ecosystem.getInitializationManager().addAnimalOrganism(ecosystem.getWorldMap(), tile, animalSpecies, animalOrganism);

            }

            animalSpecies.getNewbornOrganisms().clear();
        }

    }

    public void simulate() {

        SimulationSettings.setCurrentWeek(SimulationSettings.getCurrentWeek() + SimulationSettings.SIMULATION_SPEED_WEEKS);
        Log.log5("YEAR #" + SimulationSettings.getYear() + " - WEEK #" + SimulationSettings.getWeek());

        plantGrowthSimulation.plantRegeneration(ecosystem);
        animalAgingSimulation.ecosystemAge(ecosystem);
        animalGrazingSimulation.ecosystemGraze(ecosystem);
        animalHuntingSimulation.ecosystemHunt(ecosystem);
        animalReproductionSimulation.ecosystemReproduction(ecosystem);

        buryDead();
        newbornSurvival();

        if (Log.getLogger().getLevel().intValue() <= Level.FINER.intValue()) {
            ecosystem.printImpersonatedOrganism();

            if (Log.getLogger().getLevel().intValue() <= Level.FINEST.intValue()) {
                ecosystem.printSpeciesDistribution();
            }

        }
    }

}