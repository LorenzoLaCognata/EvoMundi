package Model.Entities;

import Model.Simulation.SimulationSettings;
import Model.Enums.*;
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
    public Species(SpeciesType speciesType, String commonName, String scientificName, String taxonomyClass, String taxonomyOrder, String taxonomyFamily, String taxonomyGenus, Diet baseDiet) {
        this.speciesType = speciesType;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.taxonomyClass = taxonomyClass;
        this.taxonomyOrder = taxonomyOrder;
        this.taxonomyFamily = taxonomyFamily;
        this.taxonomyGenus = taxonomyGenus;
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

    public double getAverageWeight() {

        double total = 0.0;

        for (Organism organism : getAliveOrganisms()) {
            total += organism.getWeight();
        }

        return total / getAliveOrganisms().size();
    }

    public void addAttribute(SpeciesAttribute speciesAttribute, SpeciesAttributeValue speciesAttributeValue) {
        attributes.put(speciesAttribute, speciesAttributeValue);
    }

    public void addBasePreySpecies(PreySpeciesType preySpeciesType) {
        basePreySpecies.put(preySpeciesType.getSpeciesType(), preySpeciesType);
    }

//    public void removeBasePreySpecies(PreySpeciesType preySpeciesType) {
//        basePreySpecies.remove(preySpeciesType.getSpeciesType());
//    }

    @Override
    public String toString() {
        return commonName;
    }

    public void initializeOrganisms() {

        boolean impersonatingOrganismFound = false;

        int carryingCapacity = (int) getAttribute(SpeciesAttribute.CARRYING_CAPACITY).getValue();

        for (int i=0; i<carryingCapacity; i++) {

            Gender gender = Gender.FEMALE;
            if (RandomGenerator.random.nextDouble() < 0.50) {
                gender = Gender.MALE;
            }

            double lifeSpan = RandomGenerator.generateGaussian(gender, getAttribute(SpeciesAttribute.LIFESPAN).getValue(Gender.FEMALE), getAttribute(SpeciesAttribute.LIFESPAN).getValue(Gender.MALE), 0.2);
            double age = RandomGenerator.random.nextDouble() * lifeSpan;
            Organism organism = new Organism(
                    speciesType,
                    gender,
                    baseDiet,
                    RandomGenerator.generateGaussian(getAttribute(SpeciesAttribute.WEIGHT).getValue(Gender.FEMALE), getAttribute(SpeciesAttribute.WEIGHT).getValue(Gender.MALE), 0.2),
                    RandomGenerator.generateGaussian(getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.FEMALE), getAttribute(SpeciesAttribute.HEIGHT).getValue(Gender.MALE), 0.2),
                    lifeSpan,
                   0,
                    age,
                    getAttribute(SpeciesAttribute.HUNT_ATTEMPTS).getValue(gender),
                    getAttribute(SpeciesAttribute.ENERGY_LOST).getValue(gender),
                    getAttribute(SpeciesAttribute.ENERGY_GAIN).getValue(gender),
                    getAttribute(SpeciesAttribute.PREY_EATEN).getValue(gender),
                    getAttribute(SpeciesAttribute.SEXUAL_MATURITY_START).getValue(gender),
                    getAttribute(SpeciesAttribute.SEXUAL_MATURITY_END).getValue(gender),
                    getAttribute(SpeciesAttribute.MATING_SEASON_START).getValue(gender),
                    getAttribute(SpeciesAttribute.MATING_SEASON_END).getValue(gender),
                    getAttribute(SpeciesAttribute.PREGNANCY_COOLDOWN).getValue(gender),
                    getAttribute(SpeciesAttribute.GESTATION_PERIOD).getValue(gender),
                    getAttribute(SpeciesAttribute.AVERAGE_OFFSPRING).getValue(gender),
                    getAttribute(SpeciesAttribute.JUVENILE_SURVIVAL_RATE).getValue(gender),
                    getAttribute(SpeciesAttribute.MATING_SUCCESS_RATE).getValue(gender),
                    getAttribute(SpeciesAttribute.MATING_ATTEMPTS).getValue(gender)
            );
            organism.getPreySpecies().putAll(basePreySpecies);

            if (!impersonatingOrganismFound && gender == SimulationSettings.getImpersonatingGender() && speciesType == SimulationSettings.getImpersonatingSpeciesType() ) {
                organism.setImpersonatedOrganism(true);
                impersonatingOrganismFound = true;
            }

            organisms.add(organism);
        }
    }

}