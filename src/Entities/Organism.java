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
    private double age;

    private final double huntingAttemptsPerWeek;
    private final double energyLostPerWeek;
    private final double energyGainPerPreyKg;
    private final double maxPreyKgEaten;
    private double energy = 1.0;

    private OrganismStatus organismStatus = OrganismStatus.ALIVE;
    private OrganismDeathReason organismDeathReason = OrganismDeathReason.NA;

    private final HashMap<SpeciesType, PreySpeciesType> preySpecies = new HashMap<>();

    public Organism(SpeciesType speciesType, Gender gender, Diet diet, double weight, double height, double lifeSpan, double age, double huntingAttemptsPerWeek, double energyLostPerWeek, double energyGainPerPreyKg, double maxPreyKgEaten) {
        this.speciesType = speciesType;
        this.gender = gender;
        this.diet = diet;
        this.weight = weight;
        this.height = height;
        this.lifeSpan = lifeSpan;
        this.age = age;
        this.huntingAttemptsPerWeek = huntingAttemptsPerWeek;
        this.energyLostPerWeek = energyLostPerWeek;
        this.energyGainPerPreyKg = energyGainPerPreyKg;
        this.maxPreyKgEaten = maxPreyKgEaten;
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

    public double getHuntingAttemptsPerWeek() {
        return huntingAttemptsPerWeek;
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

    public double getEnergyLostPerWeek() {
        return energyLostPerWeek;
    }

    public double getEnergyGainPerPreyKg() {
        return energyGainPerPreyKg;
    }

    public double getMaxPreyKgEaten() {
        return maxPreyKgEaten;
    }

    public double getAge() {
        return age;
    }

    public double getEnergy() {
        return energy;
    }

    public HashMap<SpeciesType, PreySpeciesType> getPreySpecies() {
        return preySpecies;
    }

    public OrganismStatus getOrganismStatus() {
        return organismStatus;
    }

    public void setOrganismStatus(OrganismStatus organismStatus) {
        this.organismStatus = organismStatus;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public OrganismDeathReason getOrganismDeathReason() {
        return organismDeathReason;
    }

    public void setOrganismDeathReason(OrganismDeathReason organismDeathReason) {
        this.organismDeathReason = organismDeathReason;
    }

    public double calculateHuntSuccessRate(Species preySpecies, Organism prey) {

        double baseSuccessRate = 0.0;

        switch (speciesType) {
            case SpeciesType.GRAY_WOLF:
                switch (prey.getSpeciesType()) {
                    case SpeciesType.WHITE_TAILED_DEER -> baseSuccessRate = 0.35;
                    case SpeciesType.MOOSE -> baseSuccessRate = 0.15;
                }
        }

        double huntSuccessRate = baseSuccessRate * ((double) preySpecies.getPopulation() / (double) preySpecies.getCarryingCapacity());

        return huntSuccessRate;

    }

}