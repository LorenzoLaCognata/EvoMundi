package Model.Entities;

import Model.Enums.*;
import Model.Simulation.SimulationSettings;
import Utils.RandomGenerator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Species {

    private final SpeciesTaxonomy speciesTaxonomy;
    private final SpeciesType speciesType;
    private final String commonName;
    private final String scientificName;

    private final Diet baseDiet;

    private final Map<SpeciesAttribute, SpeciesAttributeValue> attributes = new EnumMap<>(SpeciesAttribute.class);

    private final Map<SpeciesType, PreySpeciesType> basePreySpecies = new EnumMap<>(SpeciesType.class);
    private final ArrayList<Organism> organisms = new ArrayList<>();

    // TODO: implement matingSeason
    // TODO: implement socialStructure
    // TODO: implement activityPattern
    // TODO: implement habitat
    // TODO: implement movementRadius
    // TODO: implement hasAntlers

    public Species(SpeciesTaxonomy speciesTaxonomy, SpeciesType speciesType, String commonName, String scientificName, Diet baseDiet) {
        this.speciesTaxonomy = speciesTaxonomy;
        this.speciesType = speciesType;
        this.commonName = commonName;
        this.scientificName = scientificName;
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

    public SpeciesTaxonomy getSpeciesTaxonomy() {
        return speciesTaxonomy;
    }

    public Diet getBaseDiet() {
        return baseDiet;
    }

    public SpeciesAttributeValue getAttribute(SpeciesAttribute speciesAttribute) {
        return attributes.get(speciesAttribute);
    }

    public Map<SpeciesType, PreySpeciesType> getBasePreySpecies() {
        return basePreySpecies;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public List<Organism> getOrganisms(OrganismStatus organismStatus) {

        ArrayList<Organism> statusOrganisms = new ArrayList<>();

        for (Organism organism : organisms) {
            if (organism.getOrganismStatus() == organismStatus) {
                statusOrganisms.add(organism);
            }
        }

        return statusOrganisms;

    }

    public List<Organism> getAliveOrganisms() {
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

    public void addAttribute(SpeciesAttribute speciesAttribute, SpeciesAttributeValue speciesAttributeValue) {
        attributes.put(speciesAttribute, speciesAttributeValue);
    }

    public void addBasePreySpecies(PreySpeciesType preySpeciesType) {
        basePreySpecies.put(preySpeciesType.speciesType(), preySpeciesType);
    }

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