package Entities;

import Enums.*;

import java.util.HashMap;

public class Organism {

    private final SpeciesType speciesType;
    private final Gender gender;

    private final Diet diet;

    private final double weight;
    private final double height;
    private final double lifeSpan;
    private final double birthWeek;
    private double age;

    private final double huntAttempts;
    private final double energyLost;
    private final double energyGain;
    private final double preyEaten;
    private double energy = 1.0;

    private final double sexualMaturityStart;
    private final double sexualMaturityEnd;
    private final double matingSeasonStart;
    private final double matingSeasonEnd;
    private final double pregnancyCooldown;
    private final double gestationPeriod;
    private final double averageOffspring;
    private final double juvenileSurvivalRate;
    private final double matingSuccessRate;
    private final double matingAttempts;

    private ReproductionStatus reproductionStatus = ReproductionStatus.NOT_MATURE;
    private double gestationWeek = 0.0;
    private double cooldownWeek = 0.0;
    private Organism mate;

    private OrganismStatus organismStatus = OrganismStatus.ALIVE;
    private OrganismDeathReason organismDeathReason = OrganismDeathReason.NA;

    private boolean impersonatedOrganism = false;

    private final HashMap<SpeciesType, PreySpeciesType> preySpecies = new HashMap<>();

    public Organism(SpeciesType speciesType, Gender gender, Diet diet, double weight, double height, double lifeSpan, double birthWeek, double age, double huntAttempts, double energyLost, double energyGain, double preyEaten, double sexualMaturityStart, double sexualMaturityEnd, double matingSeasonStart, double matingSeasonEnd, double pregnancyCooldown, double gestationPeriod, double averageOffspring, double juvenileSurvivalRate, double matingSuccessRate, double matingAttempts) {
        this.speciesType = speciesType;
        this.gender = gender;
        this.diet = diet;
        this.weight = weight;
        this.height = height;
        this.lifeSpan = lifeSpan;
        this.birthWeek = birthWeek;
        this.age = age;
        this.huntAttempts = huntAttempts;
        this.energyLost = energyLost;
        this.energyGain = energyGain;
        this.preyEaten = preyEaten;
        this.sexualMaturityStart = sexualMaturityStart;
        this.sexualMaturityEnd = sexualMaturityEnd;
        this.matingSeasonStart = matingSeasonStart;
        this.matingSeasonEnd = matingSeasonEnd;
        this.pregnancyCooldown = pregnancyCooldown;
        this.gestationPeriod = gestationPeriod;
        this.averageOffspring = averageOffspring;
        this.juvenileSurvivalRate = juvenileSurvivalRate;
        this.matingSuccessRate = matingSuccessRate;
        this.matingAttempts = matingAttempts;
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

    public double getHuntAttempts() {
        return huntAttempts;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getLifeSpan() {
        return lifeSpan;
    }

    public double getEnergyLost() {
        return energyLost;
    }

    public double getEnergyGain() {
        return energyGain;
    }

    public double getPreyEaten() {
        return preyEaten;
    }

    public double getAge() {
        return age;
    }

    public double getBirthWeek() {
        return birthWeek;
    }

    public double getEnergy() {
        return energy;
    }

    public double getSexualMaturityStart() {
        return sexualMaturityStart;
    }

    public double getSexualMaturityEnd() {
        return sexualMaturityEnd;
    }

    public double getMatingSeasonStart() {
        return matingSeasonStart;
    }

    public double getMatingSeasonEnd() {
        return matingSeasonEnd;
    }

    public double getPregnancyCooldown() {
        return pregnancyCooldown;
    }

    public double getGestationPeriod() {
        return gestationPeriod;
    }

    public double getAverageOffspring() {
        return averageOffspring;
    }

    public double getJuvenileSurvivalRate() {
        return juvenileSurvivalRate;
    }

    public double getMatingSuccessRate() {
        return matingSuccessRate;
    }

    public double getMatingAttempts() {
        return matingAttempts;
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

    public boolean isImpersonatedOrganism(Gender gender) {
        if (this.gender == gender) {
            return impersonatedOrganism;
        }
        else return false;
    }

    public HashMap<SpeciesType, PreySpeciesType> getPreySpecies() {
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

        double baseSuccessRate = 0.0;

        switch (speciesType) {
            case SpeciesType.GRAY_WOLF:
                switch (prey.getSpeciesType()) {
                    case SpeciesType.WHITE_TAILED_DEER -> baseSuccessRate = 0.30;
                    case SpeciesType.MOOSE -> baseSuccessRate = 0.15;
                }
            case SpeciesType.BOBCAT:
                switch (prey.getSpeciesType()) {
                    case SpeciesType.SNOWSHOE_HARE -> baseSuccessRate = 0.45;
                    case SpeciesType.BEAVER -> baseSuccessRate = 0.10;
                    case SpeciesType.WHITE_TAILED_DEER -> baseSuccessRate = 0.05;
                }
        }

        return baseSuccessRate * ((double) preySpecies.getPopulation() / preySpecies.getAttribute (SpeciesAttribute.CARRYING_CAPACITY).getValue());

    }

}