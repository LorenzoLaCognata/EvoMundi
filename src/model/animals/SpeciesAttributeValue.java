package model.animals;

import model.enums.Gender;
import model.enums.SpeciesAttribute;

public class SpeciesAttributeValue {

    private final SpeciesAttribute speciesAttribute;
    private final double valueMale;
    private final double valueFemale;

    public SpeciesAttributeValue(SpeciesAttribute speciesAttribute, double valueMale, double valueFemale) {
        this.speciesAttribute = speciesAttribute;
        this.valueMale = valueMale;
        this.valueFemale = valueFemale;
    }

    public double getValue(Gender gender) {
        if (gender == Gender.FEMALE) {
            return valueFemale;
        } else {
            return valueMale;
        }
    }

    public double getValue() {
        return (valueFemale + valueMale) / 2.0;
    }

}