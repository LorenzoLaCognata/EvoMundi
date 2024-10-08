package model.simulation.animals;

import javafx.util.Pair;
import model.environment.animals.base.AnimalOrganism;
import model.simulation.base.SimulationSettings;
import utils.RandomGenerator;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AnimalMovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    private void removeAnimalOrganismFromTile(Map<Point, TileOrganisms> worldMap, Point point, AnimalOrganism animalOrganism) {

        TileOrganisms tileOrganisms = worldMap.get(point);

        if (tileOrganisms != null) {
            tileOrganisms.AnimalOrganisms().remove(animalOrganism);
        }

    }

    private void addAnimalOrganismToTile(Map<Point, TileOrganisms> worldMap, Point point, AnimalOrganism animalOrganism) {
        worldMap.computeIfAbsent(point, k -> new TileOrganisms(new HashSet<>(), new HashSet<>())).AnimalOrganisms().add(animalOrganism);
    }

    public void animalMove(Map<Point, TileOrganisms> worldMap) {

        Map<Point, ArrayList<Pair<AnimalOrganism, Point>>> movementChanges = new HashMap<>();

        for (Map.Entry<Point, TileOrganisms> tile : worldMap.entrySet()) {

            for (AnimalOrganism animalOrganism : tile.getValue().AnimalOrganisms()) {

                if (animalOrganism.getOrganismAttributes().animalPositionAttributes().getAnimationStep() % STEPS_PER_DIRECTION == 0) {
                    double latitudeSpeed = SimulationSettings.MOVEMENT_SPEED_PER_FRAME * RandomGenerator.random.nextDouble(-1.0, 1.0);
                    double longitudeSpeed = SimulationSettings.MOVEMENT_SPEED_PER_FRAME * RandomGenerator.random.nextDouble(-1.0, 1.0);
                    animalOrganism.getOrganismAttributes().animalPositionAttributes().setLatitudeSpeed(latitudeSpeed);
                    animalOrganism.getOrganismAttributes().animalPositionAttributes().setLongitudeSpeed(longitudeSpeed);
                }

                double baseNewLatitude = animalOrganism.getOrganismAttributes().animalPositionAttributes().getLatitude() + animalOrganism.getOrganismAttributes().animalPositionAttributes().getLatitudeSpeed();
                double newLatitude = Math.clamp(baseNewLatitude, SimulationSettings.MIN_LATITUDE, SimulationSettings.MAX_LATITUDE);

                double baseNewLongitude = animalOrganism.getOrganismAttributes().animalPositionAttributes().getLongitude() + animalOrganism.getOrganismAttributes().animalPositionAttributes().getLongitudeSpeed();
                double newLongitude = Math.clamp(baseNewLongitude, SimulationSettings.MIN_LONGITUDE, SimulationSettings.MAX_LONGITUDE);

                Point currentTile = Geography.calculateTile(
                        animalOrganism.getOrganismAttributes().animalPositionAttributes().getLatitude(),
                        animalOrganism.getOrganismAttributes().animalPositionAttributes().getLongitude()
                );

                animalOrganism.getOrganismAttributes().animalPositionAttributes().setLatitude(newLatitude);
                animalOrganism.getOrganismAttributes().animalPositionAttributes().setLongitude(newLongitude);

                Point newTile = Geography.calculateTile(
                        animalOrganism.getOrganismAttributes().animalPositionAttributes().getLatitude(),
                        animalOrganism.getOrganismAttributes().animalPositionAttributes().getLongitude()
                );

                if (currentTile != newTile) {
                    movementChanges.computeIfAbsent(currentTile, k -> new ArrayList<>()).add(new Pair<>(animalOrganism, newTile));
                }

                double screenX = animalOrganism.getOrganismAttributes().animalPositionAttributes().getScreenX();
                double screenY = animalOrganism.getOrganismAttributes().animalPositionAttributes().getScreenY();

                animalOrganism.getOrganismIcons().getStackPane().setLayoutX(screenX);
                animalOrganism.getOrganismIcons().getStackPane().setLayoutY(screenY);

                animalOrganism.getOrganismAttributes().animalPositionAttributes().setAnimationStep(animalOrganism.getOrganismAttributes().animalPositionAttributes().getAnimationStep() + 1);

            }
        }

        for (Map.Entry<Point, ArrayList<Pair<AnimalOrganism, Point>>> entry : movementChanges.entrySet()) {
            Point currentTile = entry.getKey();
            ArrayList<Pair<AnimalOrganism, Point>> animalOrganismMovements = entry.getValue();

            for (Pair<AnimalOrganism, Point> move : animalOrganismMovements) {
                AnimalOrganism animalOrganism = move.getKey();
                Point newTile = move.getValue();

                removeAnimalOrganismFromTile(worldMap, currentTile, animalOrganism);
                addAnimalOrganismToTile(worldMap, newTile, animalOrganism);

            }
        }

    }

}