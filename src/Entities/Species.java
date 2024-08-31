package Entities;

import Enums.*;
import Utils.RandomGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class Species {

    private final SpeciesType speciesType;
    private final String commonName;
    private final String scientificName;

    private final String taxonomyClass;
    private final String taxonomyOrder;
    private final String taxonomyFamily;
    private final String taxonomyGenus;

    private final Diet baseDiet;
    private int carryingCapacityPer1000Km2;

    private final HashMap<SpeciesAttribute, SpeciesAttributeValue> attributes = new HashMap<>();

    private final HashMap<SpeciesType, PreySpeciesType> basePreySpecies = new HashMap<>();
    private final ArrayList<Organism> organisms = new ArrayList<>();

    /*
    MatingSeason matingSeason;
    int gestationDays;
    int minOffspring;
    int maxOffspring;
    SocialStructure socialStructure;
    boolean isTerritorial;
    ActivityPattern activityPattern;
    String range;
    Habitat[] habitats;
    int movementRadius;
    int minDensity;
    int maxDensity;
    double fawnMortalityRate;
    int carryingCapacity;
    int maxSpeed;
    boolean hasAntlers;
*/
    public Species(SpeciesType speciesType, String commonName, String scientificName, String taxonomyClass, String taxonomyOrder, String taxonomyFamily, String taxonomyGenus, int carryingCapacityPer1000Km2, Diet baseDiet) {
        this.speciesType = speciesType;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.taxonomyClass = taxonomyClass;
        this.taxonomyOrder = taxonomyOrder;
        this.taxonomyFamily = taxonomyFamily;
        this.taxonomyGenus = taxonomyGenus;
        this.carryingCapacityPer1000Km2 = carryingCapacityPer1000Km2;
        this.baseDiet = baseDiet;
    }

    public SpeciesType getSpeciesType() {
        return speciesType;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getTaxonomyClass() {
        return taxonomyClass;
    }

    public String getTaxonomyOrder() {
        return taxonomyOrder;
    }

    public String getTaxonomyFamily() {
        return taxonomyFamily;
    }

    public String getTaxonomyGenus() {
        return taxonomyGenus;
    }

    public int getCarryingCapacityPer1000Km2() {
        return carryingCapacityPer1000Km2;
    }

    public Diet getBaseDiet() {
        return baseDiet;
    }

    public HashMap<SpeciesAttribute, SpeciesAttributeValue> getAttributes() {
        return attributes;
    }

    public SpeciesAttributeValue getAttribute(SpeciesAttribute speciesAttribute) {
        return attributes.get(speciesAttribute);
    }

    public HashMap<SpeciesType, PreySpeciesType> getBasePreySpecies() {
        return basePreySpecies;
    }

    public ArrayList<Organism> getOrganisms() {
        return organisms;
    }

    public ArrayList<Organism> getOrganisms(OrganismStatus organismStatus) {

        ArrayList<Organism> statusOrganisms = new ArrayList<>();

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == organismStatus) {
                statusOrganisms.add(organism);
            }
        }

        return statusOrganisms;

    }

    public ArrayList<Organism> getAliveOrganisms() {
        return getOrganisms(OrganismStatus.ALIVE);
    }

    public int getPopulation(OrganismStatus organismStatus, OrganismDeathReason organismDeathReason) {

        int count = 0;

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == organismStatus && organism.getOrganismDeathReason() == organismDeathReason) {
                count++;
            }
        }

        return count;

    }

    public int getPopulation(OrganismStatus organismStatus) {

        int count = 0;

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == organismStatus) {
                count++;
            }
        }

        return count;

    }

    public int getPopulation() {
        return getPopulation(OrganismStatus.ALIVE);
    }

    public int getPopulation(Gender gender) {

        int count = 0;

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == OrganismStatus.ALIVE && organism.getGender() == gender) {
                count++;
            }
        }

        return count;

    }

    public double getAverageAge() {

        double total = 0.0;

        for (Organism organism : getAliveOrganisms()) {
            total += organism.getAge();
        }

        return total / getAliveOrganisms().size();
    }

    public double getAverageEnergy() {

        double total = 0.0;

        for (Organism organism : getAliveOrganisms()) {
            total += organism.getEnergy();
        }

        return total / getAliveOrganisms().size();
    }

    public void setCarryingCapacityPer1000Km2(int carryingCapacityPer1000Km2) {
        this.carryingCapacityPer1000Km2 = carryingCapacityPer1000Km2;
    }

    public void addAttribute(SpeciesAttribute speciesAttribute, SpeciesAttributeValue speciesAttributeValue) {
        attributes.put(speciesAttribute, speciesAttributeValue);
    }

    public void addBasePreySpecies(PreySpeciesType preySpeciesType) {
        basePreySpecies.put(preySpeciesType.getSpeciesType(), preySpeciesType);
    }

    public void removeBasePreySpecies(PreySpeciesType preySpeciesType) {
        basePreySpecies.remove(preySpeciesType.getSpeciesType());
    }

    @Override
    public String toString() {
        return commonName;
    }

    public void initializeOrganisms() {
        for (int i=0; i<carryingCapacityPer1000Km2; i++) {

            Gender gender = Gender.FEMALE;
            if (RandomGenerator.random.nextDouble() < 0.50) {
                gender = Gender.MALE;
            }

            double lifeSpanGaussian = RandomGenerator.random.nextGaussian();
            double lifeSpan = getAttribute(SpeciesAttribute.LIFESPAN).getValue(gender) + (lifeSpanGaussian * getAttribute(SpeciesAttribute.LIFESPAN).getValue(gender) * 0.2);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            Organism organism = new Organism(
                    speciesType,
                    gender,
                    baseDiet,
                    getAttribute(SpeciesAttribute.WEIGHT).getValue(gender),
                    getAttribute(SpeciesAttribute.HEIGHT).getValue(gender),
                    lifeSpan,
                    age,
                    getAttribute(SpeciesAttribute.HUNTING_ATTEMPTS_PER_WEEK).getValue(gender),
                    getAttribute(SpeciesAttribute.ENERGY_LOST_PER_WEEK).getValue(gender),
                    getAttribute(SpeciesAttribute.ENERGY_GAIN_PER_PREY_KG).getValue(gender),
                    getAttribute(SpeciesAttribute.MAX_PREY_KG_EATEN_PER_WEEK).getValue(gender)
            );
            organism.getPreySpecies().putAll(basePreySpecies);
            organisms.add(organism);
        }
    }

}