package model.environment.common.base;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.common.enums.TaxonomySpecies;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
import model.simulation.base.SimulationSettings;
import utils.Log;

import java.util.EnumMap;
import java.util.Map;

public class Ecosystem {

    private final Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap = new EnumMap<>(TaxonomySpecies.class);
    private final Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap = new EnumMap<>(TaxonomySpecies.class);


    public Map<TaxonomySpecies, PlantSpecies> getPlantSpeciesMap() {
        return plantSpeciesMap;
    }

    public Map<TaxonomySpecies, AnimalSpecies> getAnimalSpeciesMap() {
        return animalSpeciesMap;
    }

    public void printSpeciesDetails() {
        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            Log.log5(animalSpecies.getCommonName() + ": " + Log.formatNumber(animalSpecies.getPopulation()) + " ALIVE");
            Log.log5("\tTaxonomy: " + animalSpecies.getSpeciesTaxonomy().taxonomyClass() + " > " +  animalSpecies.getSpeciesTaxonomy().taxonomyOrder() + " > " + animalSpecies.getSpeciesTaxonomy().taxonomyFamily() + " > " + animalSpecies.getSpeciesTaxonomy().taxonomyGenus() + " > " + animalSpecies.getSpeciesTaxonomy().taxonomySpecies());
            Log.log5("\tDiet: " + animalSpecies.getBaseDiet());
            Log.log5("\tPrey Species: " + animalSpecies.getBasePreyAnimalSpecies().keySet());
        }
    }

    public void printSpeciesDistribution() {

        for (PlantSpecies plantSpecies : plantSpeciesMap.values()) {
            String s = Log.padRight(16, plantSpecies.getCommonName());
            s = s + " | " + Log.padLeft(24, Log.formatNumber(plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue()) + " CAP");
            s = s + " | " + Log.padLeft(24, Log.formatNumber(plantSpecies.getQuantity()) + " QTY");
            Log.log7(s);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            String s = Log.padRight(16, animalSpecies.getCommonName());
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getAttribute(AnimalAttribute.CARRYING_CAPACITY).getAverageValue()) + " CAP");
            s = s + " | " + Log.padLeft(14, Log.formatNumber(animalSpecies.getPopulation()) + " ALIVE");
            s = s + " | " + Log.padLeft(10, Log.formatNumber(animalSpecies.getPopulation(Gender.MALE)) + " M");
            s = s + " | " + Log.padLeft(10, Log.formatNumber(animalSpecies.getPopulation(Gender.FEMALE)) + " F");
            s = s + " | " + Log.padLeft(13, Log.formatNumber(animalSpecies.getDeadPopulation()) + " DEAD");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.PREDATION)) + " PRE");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.STARVATION)) + " STA");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.AGE)) + " AGE");
            s = s + " | " + Log.padLeft(12, Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.JUVENILE_DEATH)) + " JUV");
            Log.log7(s);
        }

    }

    public void printImpersonatedOrganism() {

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {

            if (animalSpecies.getSpeciesTaxonomy().taxonomySpecies() == SimulationSettings.getImpersonatingTaxonomySpecies()) {
                for (AnimalOrganism animalOrganism : animalSpecies.getOrganisms()) {

                    if (animalOrganism.isImpersonatedOrganism()) {
                        Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + ": " + Log.formatNumber(animalOrganism.getEnergy()));
                    }

                }
            }
        }

    }

}