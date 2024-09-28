package model.environment.animals;

import model.environment.animals.enums.AnimalSpeciesAttribute;
import model.environment.animals.enums.Gender;

public class SpeciesAttributeValue {

    private final AnimalSpeciesAttribute animalSpeciesAttribute;
    private final double valueMale;
    private final double valueFemale;

    public SpeciesAttributeValue(AnimalSpeciesAttribute animalSpeciesAttribute, double valueMale, double valueFemale) {
        this.animalSpeciesAttribute = animalSpeciesAttribute;
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