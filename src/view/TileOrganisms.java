package view;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record TileOrganisms(Map<PlantSpecies, List<PlantOrganism>> plantOrganisms,
                            Map<AnimalSpecies, List<AnimalOrganism>> animalOrganisms) {

    public TileOrganisms(Map<PlantSpecies, List<PlantOrganism>> plantOrganisms, Map<AnimalSpecies, List<AnimalOrganism>> animalOrganisms) {
        this.plantOrganisms = wrapPlantsInSynchronizedList(plantOrganisms);
        this.animalOrganisms = wrapAnimalsInSynchronizedList(animalOrganisms);
    }

    private Map<PlantSpecies, List<PlantOrganism>> wrapPlantsInSynchronizedList(Map<PlantSpecies, List<PlantOrganism>> originalMap) {
        for (Map.Entry<PlantSpecies, List<PlantOrganism>> entry : originalMap.entrySet()) {
            entry.setValue(Collections.synchronizedList(entry.getValue()));
        }
        return originalMap;
    }

    private Map<AnimalSpecies, List<AnimalOrganism>> wrapAnimalsInSynchronizedList(Map<AnimalSpecies, List<AnimalOrganism>> originalMap) {
        for (Map.Entry<AnimalSpecies, List<AnimalOrganism>> entry : originalMap.entrySet()) {
            entry.setValue(Collections.synchronizedList(entry.getValue()));
        }
        return originalMap;
    }

}