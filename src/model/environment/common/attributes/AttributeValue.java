package model.environment.common.attributes;

import model.environment.animals.enums.Gender;

import java.util.EnumMap;
import java.util.Map;

public class AttributeValue {

    private final Map<Gender, Double> values = new EnumMap<>(Gender.class);

    public AttributeValue(Map<Gender, Double> values) {
        this.values.putAll(values);
    }

    public double getValue(Gender gender) {
        return values.get(gender);
    }

    public double getValue() {
        return getValue(Gender.NA);
    }

    public double getAverageValue() {
        return (values.get(Gender.FEMALE) + values.get(Gender.MALE)) / 2.0;
    }

}