package Model.Entities;

import Model.Enums.SpeciesType;

public class PreySpeciesType {

    private final SpeciesType speciesType;
    private double preferenceRate;

    public PreySpeciesType(SpeciesType speciesType, double preferenceRate) {
        this.speciesType = speciesType;
        this.preferenceRate = preferenceRate;
    }

    public SpeciesType getSpeciesType() {
        return speciesType;
    }

    public double getPreferenceRate() {
        return preferenceRate;
    }

    public void setPreferenceRate(double preferenceRate) {
        this.preferenceRate = preferenceRate;
    }
}
