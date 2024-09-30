package model.environment.common.base;

import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.common.enums.TaxonomySpecies;
import model.environment.plants.base.PlantOrganism;
import model.environment.plants.base.PlantSpecies;
import model.environment.plants.enums.PlantAttribute;
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
            String s = plantSpecies.getCommonName();
            s = s + " ".repeat(20 - plantSpecies.getCommonName().length());
            s = s + " | " + " ".repeat(10 - Log.formatNumber(plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue()).length());
            s = s + Log.formatNumber(plantSpecies.getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue()) + " CAP";
            s = s + " | " + " ".repeat(10 - Log.formatNumber(plantSpecies.getQuantity()).length());
            s = s + Log.formatNumber(plantSpecies.getQuantity()) + " QTY";
            Log.log4(s);
        }

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            String s = animalSpecies.getCommonName();
            s = s + " ".repeat(20 - animalSpecies.getCommonName().length());
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getAttribute(AnimalAttribute.CARRYING_CAPACITY).getAverageValue()).length());
            s = s + Log.formatNumber(animalSpecies.getAttribute(AnimalAttribute.CARRYING_CAPACITY).getAverageValue()) + " CAP";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getPopulation()).length());
            s = s + Log.formatNumber(animalSpecies.getPopulation()) + " ALIVE";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getPopulation(Gender.MALE)).length());
            s = s + Log.formatNumber(animalSpecies.getPopulation(Gender.MALE)) + " M";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getPopulation(Gender.FEMALE)).length());
            s = s + Log.formatNumber(animalSpecies.getPopulation(Gender.FEMALE)) + " F";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getDeadPopulation()).length());
            s = s + Log.formatNumber(animalSpecies.getDeadPopulation()) + " DEAD";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.PREDATION)).length());
            s = s + Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.PREDATION)) + " PRE";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.STARVATION)).length());
            s = s + Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.STARVATION)) + " STA";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.AGE)).length());
            s = s + Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.AGE)) + " AGE";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.JUVENILE_DEATH)).length());
            s = s + Log.formatNumber(animalSpecies.getDeadPopulation(AnimalOrganismDeathReason.JUVENILE_DEATH)) + " JUV";
            Log.log7(s);
        }

    }

}