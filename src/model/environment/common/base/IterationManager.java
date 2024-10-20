package model.environment.common.base;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import utils.TriConsumer;
import view.TileOrganisms;

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
    private <X extends  Organism, Y> void iterateTilePlantSpeciesBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action, TileOrganisms tileOrganisms) {

        for (Map.Entry<PlantSpecies, List<PlantOrganism>> plantEntry : tileOrganisms.plantOrganisms().entrySet()) {
            PlantSpecies plantSpecies = plantEntry.getKey();
            List<PlantOrganism> plantOrganisms = plantEntry.getValue();
            iterateOrganismBiConsumer(predicate, action, (List<X>) plantOrganisms, (Y) plantSpecies);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y, Z> void iterateTilePlantSpeciesTriConsumer(TriConsumer<X, Y, Z> action, TileOrganisms tileOrganisms, Z z) {

        for (Map.Entry<PlantSpecies, List<PlantOrganism>> plantEntry : tileOrganisms.plantOrganisms().entrySet()) {
            PlantSpecies plantSpecies = plantEntry.getKey();
            List<PlantOrganism> plantOrganisms = plantEntry.getValue();
            iterateOrganismTriConsumer((Predicate<X>) plantTruePredicate, action, (List<X>) plantOrganisms, (Y) plantSpecies, z);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism> void iterateTileAnimalSpeciesConsumer(Predicate<X> predicate, Consumer<X> action, TileOrganisms tileOrganisms) {

        for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {
            List<AnimalOrganism> animalOrganisms = animalEntry.getValue();
            iterateOrganismConsumer(predicate, action, (List<X>) animalOrganisms);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y> void iterateTileAnimalSpeciesBiConsumer(Predicate<X> predicate, BiConsumer<X, Y> action, TileOrganisms tileOrganisms) {

        for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {
            AnimalSpecies animalSpecies = animalEntry.getKey();
            List<AnimalOrganism> animalOrganisms = animalEntry.getValue();
            iterateOrganismBiConsumer(predicate, action, (List<X>) animalOrganisms, (Y) animalSpecies);
        }

    }

    @SuppressWarnings("unchecked")
    private <X extends Organism, Y, Z> void iterateTileAnimalSpeciesTriConsumer(Predicate<X> predicate, TriConsumer<X, Y, Z> action, TileOrganisms tileOrganisms, Z z) {

        for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {
            AnimalSpecies animalSpecies = animalEntry.getKey();
            List<AnimalOrganism> animalOrganisms = animalEntry.getValue();
            iterateOrganismTriConsumer(predicate, action, (List<X>) animalOrganisms, (Y) animalSpecies, z);
        }

    }

    public <X extends Organism, Y> void iteratePlantOrganisms(Ecosystem ecosystem, Predicate<X> predicate, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTilePlantSpeciesBiConsumer(predicate, action, tileOrganisms);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iteratePlantOrganismsPerPlantSpecies(Ecosystem ecosystem, PlantSpecies plantSpecies, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            List<PlantOrganism> plantOrganisms = tileOrganisms.plantOrganisms().get(plantSpecies);
            iterateOrganismBiConsumer((Predicate<X>) plantTruePredicate, action, (List<X>) plantOrganisms, (Y) plantSpecies);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iterateAnimalOrganismsPerAnimalSpecies(Ecosystem ecosystem, AnimalSpecies animalSpecies, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            List<AnimalOrganism> animalOrganisms = tileOrganisms.animalOrganisms().get(animalSpecies);
            iterateOrganismBiConsumer((Predicate<X>) animalTruePredicate, action, (List<X>) animalOrganisms, (Y) animalSpecies);
        }
    }

    public <X extends Organism> void iterateAnimalOrganismsConsumer(Ecosystem ecosystem, Predicate<X> predicate, Consumer<X> action) {
        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTileAnimalSpeciesConsumer(predicate, action, tileOrganisms);
        }
    }

    @SuppressWarnings("unused")
    public <X extends Organism, Y> void iterateAnimalOrganismsBiConsumer(Ecosystem ecosystem, Predicate<X> predicate, BiConsumer<X, Y> action) {
        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTileAnimalSpeciesBiConsumer(predicate, action, tileOrganisms);
        }
    }

    public <X extends Organism, Y, Z> void iterateAnimalOrganismsTriConsumer(Ecosystem ecosystem, Predicate<X> predicate, TriConsumer<X, Y, Z> action, Z z) {
        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {
            TileOrganisms tileOrganisms = entry.getValue();
            iterateTileAnimalSpeciesTriConsumer(predicate, action, tileOrganisms, z);
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Organism, Y> void iterateAnimalOrganismsPerEachAnimalOrganism(Ecosystem ecosystem, Predicate<X> predicate, BiConsumer<X, Y> action) {

        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {

            TileOrganisms tileOrganisms = entry.getValue();

            for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {

                List<AnimalOrganism> animalOrganisms = animalEntry.getValue();

                synchronized (animalOrganisms) {
                    for (AnimalOrganism animalOrganism : animalOrganisms) {
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

        for (Map.Entry<Point, TileOrganisms> entry : ecosystem.getWorldMap().entrySet()) {

            TileOrganisms tileOrganisms = entry.getValue();

            for (Map.Entry<AnimalSpecies, java.util.List<AnimalOrganism>> animalEntry : tileOrganisms.animalOrganisms().entrySet()) {

                java.util.List<AnimalOrganism> animalOrganisms = animalEntry.getValue();

                Consumer<X> innerAction = (X animalOrganism) -> iterateTilePlantSpeciesTriConsumer(action, tileOrganisms, (Z) animalOrganism);

                iterateOrganismConsumer(predicate, innerAction, (List<X>) animalOrganisms);


            }
        }
    }
}
