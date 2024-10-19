package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.common.base.Ecosystem;
import model.simulation.base.SimulationSettings;
import utils.RandomGenerator;
import utils.TriConsumer;
import view.Geography;
import view.TileOrganisms;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnimalMovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    private final TriConsumer<AnimalOrganism, AnimalSpecies, Map<Point, ArrayList<AnimalMovementPoint>>> animalMoveOrganismConsumer =
            (animalOrganism, ignored, movementChanges) -> animalMoveOrganism(animalOrganism, movementChanges);

    private void removeAnimalOrganismFromTile(Ecosystem ecosystem, Point point, AnimalOrganism animalOrganism) {

        Map<Point, TileOrganisms> worldMap = ecosystem.getWorldMap();
        TileOrganisms tileOrganisms = worldMap.get(point);
        AnimalSpecies animalSpecies = animalOrganism.getAnimalSpecies();
        ArrayList<AnimalOrganism> animalSpeciesOrganisms = tileOrganisms.animalOrganisms().get(animalSpecies);

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
                .computeIfAbsent(animalSpecies, ignored -> new ArrayList<>())
                .add(animalOrganism);

    }

    public void animalMoveOrganism(AnimalOrganism animalOrganism, Map<Point, ArrayList<AnimalMovementPoint>> movementChanges) {

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

        AnimalSpecies animalSpecies = animalOrganism.getAnimalSpecies();

        if (currentTile != newTile) {
            movementChanges.computeIfAbsent(currentTile, ignored ->
                new ArrayList<>()).add(new AnimalMovementPoint(animalSpecies, animalOrganism, newTile));
        }

        double screenX = animalOrganism.getOrganismAttributes().animalPositionAttributes().getScreenX();
        double screenY = animalOrganism.getOrganismAttributes().animalPositionAttributes().getScreenY();

        animalOrganism.getOrganismIcons().getStackPane().setLayoutX(screenX);
        animalOrganism.getOrganismIcons().getStackPane().setLayoutY(screenY);

        animalOrganism.getOrganismAttributes().animalPositionAttributes().setAnimationStep(animalOrganism.getOrganismAttributes().animalPositionAttributes().getAnimationStep() + 1);

    }

    public void animalMove(Ecosystem ecosystem) {

        Map<Point, ArrayList<AnimalMovementPoint>> movementChanges = new HashMap<>();

        ecosystem.iterateAnimalOrganismsTriConsumer(Ecosystem.animalTruePredicate, animalMoveOrganismConsumer, movementChanges);

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