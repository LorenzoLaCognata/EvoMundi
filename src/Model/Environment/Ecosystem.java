package Model.Environment;

import Model.Entities.Species;
import Model.Enums.*;
import Utils.Log;

import java.util.EnumMap;
import java.util.Map;

public class Ecosystem {

    private final Map<SpeciesType, Species> speciesMap = new EnumMap<>(SpeciesType.class);

    public Map<SpeciesType, Species> getSpeciesMap() {
        return speciesMap;
    }

    public Species getSpecies(SpeciesType speciesType) {
        return speciesMap.get(speciesType);
    }

    public void addSpecies(SpeciesType speciesType, Species species) {
        species.initializeOrganisms();
        speciesMap.put(speciesType, species);
    }

    public void printSpeciesDetails(LogStatus logStatus) {
        for (Species species : speciesMap.values()) {
            Log.logln(logStatus, species.getCommonName() + ": " + Log.formatNumber(species.getPopulation()) + " ALIVE");
            Log.logln(logStatus, "\tScientific Name: " + species.getScientificName());
            Log.logln(logStatus, "\tTaxonomy: " + species.getSpeciesTaxonomy().taxonomyClass() + " > " +  species.getSpeciesTaxonomy().taxonomyOrder() + " > " + species.getSpeciesTaxonomy().taxonomyFamily() + " > " + species.getSpeciesTaxonomy().taxonomyGenus());
            Log.logln(logStatus, "\tDiet: " + species.getBaseDiet());
            Log.logln(logStatus, "\tPrey Species: " + species.getBasePreySpecies().keySet());
        }
    }

    public void printSpeciesDistribution(LogStatus logStatus) {

        for (Species species : speciesMap.values()) {
            Log.log(logStatus, species.getCommonName());
            Log.log(logStatus, " ".repeat(20 - species.getCommonName().length()));
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue()).length()) + Log.formatNumber(species.getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue()) + " CAP");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getOrganisms().size()).length()) + Log.formatNumber(species.getOrganisms().size()) + " TOT");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation()).length()) + Log.formatNumber(species.getPopulation()) + " ALIVE");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(Gender.MALE)).length()) + Log.formatNumber(species.getPopulation(Gender.MALE)) + " M");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(Gender.FEMALE)).length()) + Log.formatNumber(species.getPopulation(Gender.FEMALE)) + " F");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(OrganismStatus.DEAD)).length()) + Log.formatNumber(species.getPopulation(OrganismStatus.DEAD)) + " DEAD");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.PREDATION)).length()) + Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.PREDATION)) + " PRE");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.STARVATION)).length()) + Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.STARVATION)) + " STA");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.AGE)).length()) + Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.AGE)) + " AGE");
            Log.log(logStatus, " | " + " ".repeat(7 - Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.JUVENILE_DEATH)).length()) + Log.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.JUVENILE_DEATH)) + " JUV");
            Log.logln(logStatus, "");
        }

    }

}