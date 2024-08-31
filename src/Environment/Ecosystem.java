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
            Logger.logln(species.getCommonName());
            Logger.logln("\t" + Logger.formatNumber(species.getPopulation()) + " ALIVE (Avg Energy: " + species.getAverageEnergy() + ")");
            Logger.logln("\t\t" + Logger.formatNumber(species.getPopulation(Gender.MALE)) + " MALE");
            Logger.logln("\t\t" + Logger.formatNumber(species.getPopulation(Gender.FEMALE)) + " FEMALE");
            Logger.logln("\t" + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD)) + " DEAD");
            Logger.logln("\t\t" + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.PREDATION)) + " PREDATION");
            Logger.logln("\t\t" + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.STARVATION)) + " STARVATION");
            Logger.logln("\t\t" + Logger.formatNumber(species.getPopulation(OrganismStatus.DEAD, OrganismDeathReason.AGE)) + " AGE");
        }
    }

}