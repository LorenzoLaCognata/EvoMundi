package Model.Entities;

import Model.Enums.*;
import Utils.Log;

import java.util.EnumMap;
import java.util.Map;

public class Organism {

    private final SpeciesType speciesType;
    private final Gender gender;
    private final Diet diet;

    private final VitalsAttributes vitalsAttributes;
    private final HuntingAttributes huntingAttributes;
    private final ReproductionAttributes reproductionAttributes;

    private double age;
    private double energy = 1.0;

    private ReproductionStatus reproductionStatus = ReproductionStatus.NOT_MATURE;
    private double gestationWeek = 0.0;
    private double cooldownWeek = 0.0;
    private Organism mate;

    private OrganismStatus organismStatus = OrganismStatus.ALIVE;
    private OrganismDeathReason organismDeathReason = OrganismDeathReason.NA;

    private boolean impersonatedOrganism = false;

    private final Map<SpeciesType, PreySpeciesType> preySpecies = new EnumMap<>(SpeciesType.class);

    public Organism(SpeciesType speciesType, Gender gender, Diet diet, double age, VitalsAttributes vitalsAttributes, HuntingAttributes huntingAttributes, ReproductionAttributes reproductionAttributes) {
        this.speciesType = speciesType;
        this.gender = gender;
        this.diet = diet;
        this.age = age;
        this.vitalsAttributes = vitalsAttributes;
        this.huntingAttributes = huntingAttributes;
        this.reproductionAttributes = reproductionAttributes;
    }

    public SpeciesType getSpeciesType() {
        return speciesType;
    }

    public Gender getGender() {
        return gender;
    }

    public Diet getDiet() {
        return diet;
    }

    public double getAge() {
        return age;
    }

    public double getEnergy() {
        return energy;
    }

    public VitalsAttributes getVitalsAttributes() {
        return vitalsAttributes;
    }

    public HuntingAttributes getHuntingAttributes() {
        return huntingAttributes;
    }

    public ReproductionAttributes getReproductionAttributes() {
        return reproductionAttributes;
    }

    public ReproductionStatus getReproductionStatus() {
        return reproductionStatus;
    }

    public double getGestationWeek() {
        return gestationWeek;
    }

    public double getCooldownWeek() {
        return cooldownWeek;
    }

    public Organism getMate() {
        return mate;
    }

    public OrganismStatus getOrganismStatus() {
        return organismStatus;
    }

    public OrganismDeathReason getOrganismDeathReason() {
        return organismDeathReason;
    }

    public boolean isImpersonatedOrganism() {
        return impersonatedOrganism;
    }

    public Map<SpeciesType, PreySpeciesType> getPreySpecies() {
        return preySpecies;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setReproductionStatus(ReproductionStatus reproductionStatus) {
        this.reproductionStatus = reproductionStatus;
    }

    public void setGestationWeek(double gestationWeek) {
        this.gestationWeek = gestationWeek;
    }

    public void setCooldownWeek(double cooldownWeek) {
        this.cooldownWeek = cooldownWeek;
    }

    public void setMate(Organism mate) {
        this.mate = mate;
    }

    public void setOrganismStatus(OrganismStatus organismStatus) {
        this.organismStatus = organismStatus;
    }

    public void setOrganismDeathReason(OrganismDeathReason organismDeathReason) {
        this.organismDeathReason = organismDeathReason;
    }

    public void setImpersonatedOrganism(boolean impersonatedOrganism) {
        this.impersonatedOrganism = impersonatedOrganism;
    }

    public double calculateHuntSuccessRate(Species preySpecies, Organism prey) {

        double baseSuccessRate;

        switch (speciesType) {
            case SpeciesType.GRAY_WOLF:
                switch (prey.getSpeciesType()) {
                    case SpeciesType.WHITE_TAILED_DEER -> baseSuccessRate = 0.30;
                    case SpeciesType.MOOSE -> baseSuccessRate = 0.15;
                    default -> throw new IllegalStateException(Log.UNEXPECTED_VALUE_MESSAGE + prey.getSpeciesType());
                }
                break;
            case SpeciesType.BOBCAT:
                switch (prey.getSpeciesType()) {
                    case SpeciesType.SNOWSHOE_HARE -> baseSuccessRate = 0.45;
                    case SpeciesType.EUROPEAN_BEAVER -> baseSuccessRate = 0.10;
                    case SpeciesType.WHITE_TAILED_DEER -> baseSuccessRate = 0.05;
                    default -> throw new IllegalStateException("Unexpected value: " + prey.getSpeciesType());
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + speciesType);
        }

        return baseSuccessRate * (preySpecies.getPopulation() / preySpecies.getAttribute (SpeciesAttribute.CARRYING_CAPACITY).getValue());

    }

}