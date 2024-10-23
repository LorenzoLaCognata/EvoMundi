package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.base.Ecosystem;
import model.environment.common.base.IterationManager;
import model.simulation.base.SimulationSettings;
import utils.RandomGenerator;
import utils.TriConsumer;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.List;
import java.util.*;

public class AnimalMovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    private final TriConsumer<AnimalOrganism, AnimalSpecies, Map<Point, ArrayList<AnimalMovementPoint>>> animalOrganismMoveConsumer =
            (animalOrganism, ignored, movementChanges) -> animalOrganismMove(animalOrganism, movementChanges);

    private void removeAnimalOrganismFromTile(Ecosystem ecosystem, Point point, AnimalOrganism animalOrganism) {

        Map<Point, TileOrganisms> worldMap = ecosystem.getWorldMap();
        TileOrganisms tileOrganisms = worldMap.get(point);
        AnimalSpecies animalSpecies = animalOrganism.getAnimalSpecies();
        List<AnimalOrganism> animalSpeciesOrganisms = tileOrganisms.animalOrganisms().get(animalSpecies);

        if (animalSpeciesOrganisms != null) {
            animalSpeciesOrganisms.remove(animalOrganism);

            if (animalSpeciesOrganisms.isEmpty()) {
                tileOrganisms.animalOrganisms().remove(animalSpecies);
            }
        }

    }

    private void addAnimalOrganismToTile(Ecosystem ecosystem, Point point, AnimalSpecies animalSpecies, AnimalOrganism animalOrganism) {

        Map<Point, TileOrganisms> worldMap = ecosystem.getWorldMap();
        TileOrganisms tileOrganisms = worldMap.computeIfAbsent(point, ignored -> new TileOrganisms(new HashMap<>(), new HashMap<>()));

        tileOrganisms.animalOrganisms()
                .computeIfAbsent(animalSpecies, ignored -> Collections.synchronizedList(new ArrayList<>()))
                .add(animalOrganism);

    }

    public void animalOrganismMove(AnimalOrganism animalOrganism, Map<Point, ArrayList<AnimalMovementPoint>> movementChanges) {
        animalOrganismUpdateSpeed(animalOrganism);
        animalOrganismCalculateTileMove(animalOrganism, movementChanges);
        animalOrganism.updateLayout();
        animalOrganismUpdateAnimationStep(animalOrganism);
    }

    private static void animalOrganismCalculateTileMove(AnimalOrganism animalOrganism, Map<Point, ArrayList<AnimalMovementPoint>> movementChanges) {

        Point currentTile = animalOrganismCurrentTile(animalOrganism);
        animalOrganismUpdateCoordinates(animalOrganism);
        Point newTile = animalOrganismCurrentTile(animalOrganism);

        if (currentTile != newTile) {
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

    private static Point animalOrganismCurrentTile(AnimalOrganism animalOrganism) {
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
                removeAnimalOrganismFromTile(ecosystem, currentTile, move.animalOrganism());
                addAnimalOrganismToTile(ecosystem, newTile, move.animalOrganism().getAnimalSpecies(), move.animalOrganism());
            }
        }

    }

}