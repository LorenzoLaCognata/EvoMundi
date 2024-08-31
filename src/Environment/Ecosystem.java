package Environment;

import Enums.Gender;
import Enums.OrganismDeathReason;
import Enums.OrganismStatus;
import Enums.SpeciesType;
import Entities.Species;
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

    public void removeOrganism(SpeciesType speciesType) {
        speciesMap.remove(speciesType);
    }

    public void printSpeciesDetails() {
        for (Species species : speciesMap.values()) {
            if (species.getPopulation() > 0) {
                Logger.logln(species.getCommonName() + ": " + Logger.formatNumber(species.getPopulation()) + " ALIVE");
                Logger.logln("\tScientific Name: " + species.getScientificName());
                Logger.logln("\tTaxonomy: " + species.getTaxonomyClass() + " > " +  species.getTaxonomyOrder() + " > " + species.getTaxonomyFamily() + " > " + species.getTaxonomyGenus());
                Logger.logln("\tDiet: " + species.getBaseDiet());
                Logger.logln("\tPrey Species: " + species.getBasePreySpecies().keySet());
            }
            else {
                Logger.logln(species.getCommonName() + ": EXTINCT");
            }
        }
    }

    public void printSpeciesDistribution() {

        for (Species species : speciesMap.values()) {
            Logger.log(species.getCommonName());
            Logger.log(" ".repeat(25 - species.getCommonName().length()));
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getCarryingCapacityPer1000Km2()).length()) + Logger.formatNumber(species.getCarryingCapacityPer1000Km2()) + " CAPACITY");
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getPopulation()).length()) + Logger.formatNumber(species.getPopulation()) + " ALIVE");
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getPopulation(Gender.MALE)).length()) + Logger.formatNumber(species.getPopulation(Gender.MALE)) + " M");
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getPopulation(Gender.FEMALE)).length()) + Logger.formatNumber(species.getPopulation(Gender.FEMALE)) + " F");
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD)) + " DEAD");
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.PREDATION)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.PREDATION)) + " PRE");
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.STARVATION)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.STARVATION)) + " STA");
            Logger.log("\t|\t" + " ".repeat(6 - Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.AGE)).length()) + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.AGE)) + " AGE");
            Logger.logln("");
        }

    }

}