package model.simulation.animals;

import javafx.scene.Group;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.base.Ecosystem;
import model.environment.common.base.IterationManager;
import model.environment.common.base.Organism;
import model.simulation.base.SimulationSettings;
import utils.RandomGenerator;
import utils.TriConsumer;
import view.Geography;
import view.Tile;
import view.TileSpecies;

import java.awt.*;
import java.util.List;
import java.util.*;

public class AnimalMovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    private final TriConsumer<AnimalOrganism, AnimalSpecies, Map<Point, ArrayList<AnimalMovementPoint>>> animalOrganismMoveConsumer =
            (animalOrganism, ignored, movementChanges) -> animalOrganismMove(animalOrganism, movementChanges);

    private void removeMovingAnimalOrganismFromTile(Ecosystem ecosystem, Point point, AnimalOrganism animalOrganism) {

        Map<Point, Tile> worldMap = ecosystem.getWorldMap();
        Tile tile = worldMap.get(point);
        AnimalSpecies animalSpecies = animalOrganism.getAnimalSpecies();
        TileSpecies tileSpecies = tile.animalTileSpecies().get(animalSpecies);
        List<Organism> animalSpeciesOrganisms = tileSpecies.organisms();

        if (animalSpeciesOrganisms != null) {
            animalSpeciesOrganisms.remove(animalOrganism);

            if (!tileSpecies.getOrganismsImages().getChildren().isEmpty()) {
                if (tileSpecies.containsOrganismImage(animalOrganism.getOrganismIcons().getStackPane())) {
                    tileSpecies.removeOrganismImage(animalOrganism.getOrganismIcons().getStackPane());
                }
            }

            if (animalSpeciesOrganisms.isEmpty()) {
                tile.animalTileSpecies().remove(animalSpecies);
            }
        }

    }

    private void addMovingAnimalOrganismToTile(Ecosystem ecosystem, Point point, AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        Map<Point, Tile> worldMap = ecosystem.getWorldMap();
        Tile tile = worldMap.computeIfAbsent(point, ignored -> new Tile(new HashMap<>(), new HashMap<>()));

        tile.animalTileSpecies()
                .computeIfAbsent(animalSpecies, ignored -> new TileSpecies(Collections.synchronizedList(new ArrayList<>()), new Group()))
                .addOrganism(animalOrganism);

        TileSpecies tileSpecies = tile.animalTileSpecies().get(animalSpecies);

        if (tileSpecies.organismImages().getChildren().isEmpty()) {
            tileSpecies.addOrganismImage(animalOrganism.getOrganismIcons().getStackPane());
        }

    }

    public void animalOrganismMove(AnimalOrganism animalOrganism, Map<Point, ArrayList<AnimalMovementPoint>> movementChanges) {
        animalOrganismUpdateSpeed(animalOrganism);
        animalOrganismCalculateTileMove(animalOrganism, movementChanges);
        animalOrganism.updateLayout();
        animalOrganismUpdateAnimationStep(animalOrganism);
    }

    private static void animalOrganismCalculateTileMove(AnimalOrganism animalOrganism, Map<Point, ArrayList<AnimalMovementPoint>> movementChanges) {

        Point currentTile = animalOrganismTile(animalOrganism);
        animalOrganismUpdateCoordinates(animalOrganism);
        Point newTile = animalOrganismTile(animalOrganism);

        if (currentTile.getX() != newTile.getX() || currentTile.getY() != newTile.getY()) {
            movementChanges.computeIfAbsent(currentTile, ignored ->
                new ArrayList<>()).add(new AnimalMovementPoint(animalOrganism.getAnimalSpecies(), animalOrganism, newTile));
        }
    }

    private static void animalOrganismUpdateCoordinates(AnimalOrganism animalOrganism) {
        double newLatitude = animalOrganismNewLatitude(animalOrganism);
        double newLongitude = animalOrganismNewLongitude(animalOrganism);
        animalOrganism.getOrganismAttributes().animalPositionAttributes().setLatitude(newLatitude);
        animalOrganism.getOrganismAttributes().animalPositionAttributes().setLongitude(newLongitude);
    }

    private static Point animalOrganismTile(AnimalOrganism animalOrganism) {
        return Geography.calculateTile(
                animalOrganism.getOrganismAttributes().animalPositionAttributes().getLatitude(),
                animalOrganism.getOrganismAttributes().animalPositionAttributes().getLongitude()
        );
    }

    private static void animalOrganismUpdateAnimationStep(AnimalOrganism animalOrganism) {
        animalOrganism.getOrganismAttributes().animalPositionAttributes().setAnimationStep(animalOrganism.getOrganismAttributes().animalPositionAttributes().getAnimationStep() + 1);
    }

    private static void animalOrganismUpdateSpeed(AnimalOrganism animalOrganism) {
        if (animalOrganism.getOrganismAttributes().animalPositionAttributes().getAnimationStep() % STEPS_PER_DIRECTION == 0) {
            double latitudeSpeed = SimulationSettings.MOVEMENT_SPEED_PER_FRAME * RandomGenerator.random.nextDouble(-1.0, 1.0);
            double longitudeSpeed = SimulationSettings.MOVEMENT_SPEED_PER_FRAME * RandomGenerator.random.nextDouble(-1.0, 1.0);
            animalOrganism.getOrganismAttributes().animalPositionAttributes().setLatitudeSpeed(latitudeSpeed);
            animalOrganism.getOrganismAttributes().animalPositionAttributes().setLongitudeSpeed(longitudeSpeed);
        }
    }

    private static double animalOrganismNewLongitude(AnimalOrganism animalOrganism) {
        double baseNewLongitude = animalOrganism.getOrganismAttributes().animalPositionAttributes().getLongitude() + animalOrganism.getOrganismAttributes().animalPositionAttributes().getLongitudeSpeed();
        return Math.clamp(baseNewLongitude, SimulationSettings.MIN_LONGITUDE, SimulationSettings.MAX_LONGITUDE);
    }

    private static double animalOrganismNewLatitude(AnimalOrganism animalOrganism) {
        double baseNewLatitude = animalOrganism.getOrganismAttributes().animalPositionAttributes().getLatitude() + animalOrganism.getOrganismAttributes().animalPositionAttributes().getLatitudeSpeed();
        return Math.clamp(baseNewLatitude, SimulationSettings.MIN_LATITUDE, SimulationSettings.MAX_LATITUDE);
    }

    public void ecosystemMove(Ecosystem ecosystem) {

        Map<Point, ArrayList<AnimalMovementPoint>> movementChanges = new HashMap<>();

        ecosystem.getIterationManager().iterateAnimalOrganismsTriConsumer(ecosystem, IterationManager.animalTruePredicate, animalOrganismMoveConsumer, movementChanges);

        for (Map.Entry<Point, ArrayList<AnimalMovementPoint>> entry : movementChanges.entrySet()) {
            Point currentTile = entry.getKey();

            for (AnimalMovementPoint move : entry.getValue()) {
                Point newTile = move.point();
                removeMovingAnimalOrganismFromTile(ecosystem, currentTile, move.animalOrganism());
                addMovingAnimalOrganismToTile(ecosystem, newTile, move.animalOrganism().getAnimalSpecies(), move.animalOrganism());
            }
        }

    }

}