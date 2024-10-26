package model.environment.common.base;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import utils.TriConsumer;
import view.Tile;
import view.TileSpecies;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class IterationManager {

    public static final Predicate<AnimalOrganism> animalTruePredicate =
            ignored -> true;

    public static final Predicate<PlantOrganism> plantTruePredicate =
            ignored -> true;


    @SuppressWarnings("unused")
    private <X extends Organism> void iterateOrganismConsumer(Predicate<X> predicate, Consumer<X> action, List<X> organisms) {
        if (predicate.equals(animalTruePredicate) || predicate.equals(plantTruePredicate) ) {
            synchronized (organisms) {
                for (X organism : organisms) {
                    action.accept(organism);
                }
            }
        }
        else {
            synchronized (organisms) {
                for (X organism : organisms) {
                    if (predicate.test(organism)) {
                        action.accept(organism);
                    }
                }
            }
        }
    }

    private <X extends Organism, Y> void iterateOrganismBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action, List<X> organisms, Y y) {
        if (predicate.equals(animalTruePredicate) || predicate.equals(plantTruePredicate) ) {
            synchronized (organisms) {
                for (X organism : organisms) {
                    action.accept(organism, y);
                }
            }
        }
        else {
            synchronized (organisms) {
                for (X organism : organisms) {
                    if (predicate.test(organism)) {
                        action.accept(organism, y);
                    }
                }
            }
        }
    }

    private <X extends Organism, Y, Z> void iterateOrganismTriConsumer(Predicate<X> predicate, TriConsumer<X, Y, Z> action, List<X> organisms, Y y, Z z) {
        if (predicate.equals(animalTruePredicate) || predicate.equals(plantTruePredicate) ) {
            synchronized (organisms) {
                for (X organism : organisms) {
                    action.accept(organism, y, z);
                }
            }
        }
        else {
            synchronized (organisms) {
                for (X organism : organisms) {
                    if (predicate.test(organism)) {
                        action.accept(organism, y, z);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <X extends  Organism, Y> void iterateTilePlantSpeciesBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action, Tile tile) {

        for (Map.Entry<PlantSpecies, TileSpecies> entry : tile.plantTileSpecies().entrySet()) {
            PlantSpecies plantSpecies = entry.getKey();
            List<Organism> plantOrganisms = entry.getValue().organisms();
            iterateOrganismBiConsumer(predicate, action, (List<X>) plantOrganisms, (Y) plantSpecies);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y, Z> void iterateTilePlantSpeciesTriConsumer(TriConsumer<X, Y, Z> action, Tile tile, Z z) {

        for (Map.Entry<PlantSpecies, TileSpecies> entry : tile.plantTileSpecies().entrySet()) {
            PlantSpecies plantSpecies = entry.getKey();
            List<Organism> plantOrganisms = entry.getValue().organisms();
            iterateOrganismTriConsumer((Predicate<X>) plantTruePredicate, action, (List<X>) plantOrganisms, (Y) plantSpecies, z);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism> void iterateTileAnimalSpeciesConsumer(Predicate<X> predicate, Consumer<X> action, Tile tile) {

        for (Map.Entry<AnimalSpecies, TileSpecies> entry : tile.animalTileSpecies().entrySet()) {
            List<Organism> animalOrganisms = entry.getValue().organisms();
            iterateOrganismConsumer(predicate, action, (List<X>) animalOrganisms);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y> void iterateTileAnimalSpeciesBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action, Tile tile) {

        for (Map.Entry<AnimalSpecies, TileSpecies> entry : tile.animalTileSpecies().entrySet()) {
            AnimalSpecies animalSpecies = entry.getKey();
            List<Organism> animalOrganisms = entry.getValue().organisms();
            iterateOrganismBiConsumer(predicate, action, (List<X>) animalOrganisms, (Y) animalSpecies);
        }


    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y, Z> void iterateTileAnimalSpeciesTriConsumer(Predicate<X> predicate, TriConsumer<X, Y, Z> action, Tile tile, Z z) {

        for (Map.Entry<AnimalSpecies, TileSpecies> entry : tile.animalTileSpecies().entrySet()) {
            AnimalSpecies animalSpecies = entry.getKey();
            List<Organism> animalOrganisms = entry.getValue().organisms();
            iterateOrganismTriConsumer(predicate, action, (List<X>) animalOrganisms, (Y) animalSpecies, z);
        }

    }

    public <X extends Organism, Y> void iteratePlantOrganisms(Ecosystem ecosystem, Predicate<X> predicate, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {
            Tile tile = entry.getValue();
            iterateTilePlantSpeciesBiConsumer(predicate, action, tile);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iteratePlantOrganismsPerPlantSpecies(Ecosystem ecosystem, PlantSpecies plantSpecies, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {
            Tile tile = entry.getValue();
            List<Organism> plantOrganisms = tile.plantTileSpecies().get(plantSpecies).organisms();
            iterateOrganismBiConsumer((Predicate<X>) plantTruePredicate, action, (List<X>) plantOrganisms, (Y) plantSpecies);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iterateAnimalOrganismsPerAnimalSpecies(Ecosystem ecosystem, AnimalSpecies animalSpecies, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {
            Tile tile = entry.getValue();
            List<Organism> animalOrganisms = tile.animalTileSpecies().get(animalSpecies).organisms();
            iterateOrganismBiConsumer((Predicate<X>) animalTruePredicate, action, (List<X>) animalOrganisms, (Y) animalSpecies);
        }
    }

    public <X extends Organism> void iterateAnimalOrganismsConsumer(Ecosystem ecosystem, Predicate<X> predicate, Consumer<X> action) {
        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {
            Tile tile = entry.getValue();
            iterateTileAnimalSpeciesConsumer(predicate, action, tile);
        }
    }

    @SuppressWarnings("unused")
    public <X extends Organism, Y> void iterateAnimalOrganismsBiConsumer(Ecosystem ecosystem, Predicate<X> predicate, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {
            Tile tile = entry.getValue();
            iterateTileAnimalSpeciesBiConsumer(predicate, action, tile);
        }
    }

    public <X extends Organism, Y, Z> void iterateAnimalOrganismsTriConsumer(Ecosystem ecosystem, Predicate<X> predicate, TriConsumer<X, Y, Z> action, Z z) {
        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {
            Tile tile = entry.getValue();
            iterateTileAnimalSpeciesTriConsumer(predicate, action, tile, z);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iterateAnimalOrganismsPerEachAnimalOrganism(Ecosystem ecosystem, Predicate<X> predicate, BiConsumer<X, Y> action) {

        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {

            Tile tile = entry.getValue();

            for (Map.Entry<AnimalSpecies, TileSpecies> animalEntry : tile.animalTileSpecies().entrySet()) {

                List<Organism> animalOrganisms = animalEntry.getValue().organisms();

                synchronized (animalOrganisms) {
                    for (Organism animalOrganism : animalOrganisms) {
                        if (predicate.test((X) animalOrganism)) {
                            iterateOrganismBiConsumer((Predicate<X>) animalTruePredicate, action, (List<X>) animalOrganisms, (Y) animalOrganism);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y, Z> void iteratePlantOrganismsPerEachAnimalOrganism(Predicate<X> predicate, TriConsumer<X, Y, Z> action, Ecosystem ecosystem) {

        for (Map.Entry<Point, Tile> entry : ecosystem.getWorldMap().entrySet()) {

            Tile tile = entry.getValue();

            for (Map.Entry<AnimalSpecies, TileSpecies> animalEntry : tile.animalTileSpecies().entrySet()) {

                List<Organism> animalOrganisms = animalEntry.getValue().organisms();

                Consumer<X> innerAction = (X animalOrganism) -> iterateTilePlantSpeciesTriConsumer(action, tile, (Z) animalOrganism);

                iterateOrganismConsumer(predicate, innerAction, (List<X>) animalOrganisms);


            }
        }
    }
}
