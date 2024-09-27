package model.environment;

import model.animals.Species;
import model.enums.*;
import utils.Log;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Ecosystem {

    private final Map<BiomassType, Biomass> biomassMap = new HashMap<>();
    private final Map<SpeciesType, Species> speciesMap = new EnumMap<>(SpeciesType.class);

    public Map<BiomassType, Biomass> getBiomassMap() {
        return biomassMap;
    }

    public Map<SpeciesType, Species> getSpeciesMap() {
        return speciesMap;
    }

    public void printSpeciesDetails() {
        for (Species species : speciesMap.values()) {
            Log.log5(species.getCommonName() + ": " + Log.formatNumber(species.getPopulation()) + " ALIVE");
            Log.log5("\tScientific Name: " + species.getScientificName());
            Log.log5("\tTaxonomy: " + species.getSpeciesTaxonomy().taxonomyClass() + " > " +  species.getSpeciesTaxonomy().taxonomyOrder() + " > " + species.getSpeciesTaxonomy().taxonomyFamily() + " > " + species.getSpeciesTaxonomy().taxonomyGenus());
            Log.log5("\tDiet: " + species.getBaseDiet());
            Log.log5("\tPrey Species: " + species.getBasePreySpecies().keySet());
        }
    }

    public void printSpeciesDistribution() {

        for (Species species : speciesMap.values()) {
            String s = species.getCommonName();
            s = s + " ".repeat(20 - species.getCommonName().length());
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue()).length());
            s = s + Log.formatNumber(species.getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue()) + " CAP";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation()).length());
            s = s + Log.formatNumber(species.getPopulation()) + " ALIVE";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(Gender.MALE)).length());
            s = s + Log.formatNumber(species.getPopulation(Gender.MALE)) + " M";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(Gender.FEMALE)).length());
            s = s + Log.formatNumber(species.getPopulation(Gender.FEMALE)) + " F";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getDeadPopulation()).length());
            s = s + Log.formatNumber(species.getDeadPopulation()) + " DEAD";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.PREDATION)).length());
            s = s + Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.PREDATION)) + " PRE";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.STARVATION)).length());
            s = s + Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.STARVATION)) + " STA";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.AGE)).length());
            s = s + Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.AGE)) + " AGE";
            s = s + " | " + " ".repeat(7 - Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.JUVENILE_DEATH)).length());
            s = s + Log.formatNumber(species.getDeadPopulation(OrganismDeathReason.JUVENILE_DEATH)) + " JUV";
            Log.log6(s);
        }

    }

}