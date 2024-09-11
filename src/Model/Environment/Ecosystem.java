package Model.Environment;

import Model.Entities.Species;
import Model.Enums.*;
import Utils.Logger;

import java.util.HashMap;

public class Ecosystem {

    private final HashMap<SpeciesType, Species> speciesMap = new HashMap<>();

    public HashMap<SpeciesType, Species> getSpeciesMap() {
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
            Logger.logln(logStatus, species.getCommonName() + ": " + Logger.formatNumber(species.getPopulation()) + " ALIVE");
            Logger.logln(logStatus, "\tScientific Name: " + species.getScientificName());
            Logger.logln(logStatus, "\tTaxonomy: " + species.getTaxonomyClass() + " > " +  species.getTaxonomyOrder() + " > " + species.getTaxonomyFamily() + " > " + species.getTaxonomyGenus());
            Logger.logln(logStatus, "\tDiet: " + species.getBaseDiet());
            Logger.logln(logStatus, "\tPrey Species: " + species.getBasePreySpecies().keySet());
        }
    }

    public void printSpeciesDistribution(LogStatus logStatus) {

        for (Species species : speciesMap.values()) {
            Logger.log(logStatus, species.getCommonName());
            Logger.log(logStatus, " ".repeat(20 - species.getCommonName().length()));
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue()).length()) + Logger.formatNumber(species.getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue()) + " CAP");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getOrganisms().size()).length()) + Logger.formatNumber(species.getOrganisms().size()) + " TOT");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation()).length()) + Logger.formatNumber(species.getPopulation()) + " ALIVE");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation(Gender.MALE)).length()) + Logger.formatNumber(species.getPopulation(Gender.MALE)) + " M");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation(Gender.FEMALE)).length()) + Logger.formatNumber(species.getPopulation(Gender.FEMALE)) + " F");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD)) + " DEAD");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.PREDATION)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.PREDATION)) + " PRE");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.STARVATION)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.STARVATION)) + " STA");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.AGE)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.AGE)) + " AGE");
            Logger.log(logStatus, " | " + " ".repeat(7 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.JUVENILE_DEATH)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.JUVENILE_DEATH)) + " JUV");
            Logger.logln(logStatus, "");
        }

    }

}