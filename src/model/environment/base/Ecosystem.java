package model.environment.base;

import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.AnimalSpeciesAttribute;
import model.environment.plants.base.PlantPatch;
import utils.Log;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Ecosystem {

    private final Set<PlantPatch> plantPatchSet = new HashSet<>();
    private final Map<TaxonomySpecies, AnimalSpecies> animalSpeciesMap = new EnumMap<>(TaxonomySpecies.class);

    public Set<PlantPatch> getPlantPatchSet() {
        return plantPatchSet;
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

        for (AnimalSpecies animalSpecies : animalSpeciesMap.values()) {
            String s = animalSpecies.getCommonName();
            s = s + " ".repeat(20 - animalSpecies.getCommonName().length());
            s = s + " | " + " ".repeat(7 - Log.formatNumber(animalSpecies.getAttribute(AnimalSpeciesAttribute.CARRYING_CAPACITY).getValue()).length());
            s = s + Log.formatNumber(animalSpecies.getAttribute(AnimalSpeciesAttribute.CARRYING_CAPACITY).getValue()) + " CAP";
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
            Log.log6(s);
        }

    }

}