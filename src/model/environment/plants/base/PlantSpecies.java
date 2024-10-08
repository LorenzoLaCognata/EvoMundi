package model.environment.plants.base;

import javafx.scene.image.Image;
import model.environment.common.attributes.AttributeValue;
import model.environment.common.base.Species;
import model.environment.common.base.SpeciesTaxonomy;
import model.environment.plants.enums.PlantAttribute;

import java.util.EnumMap;
import java.util.Map;


public class PlantSpecies extends Species {

    private final Map<PlantAttribute, AttributeValue> attributes = new EnumMap<>(PlantAttribute.class);

    public PlantSpecies(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image) {
        super(speciesTaxonomy, commonName, image);
    }

    public AttributeValue getAttribute(PlantAttribute plantAttribute) {
        return attributes.get(plantAttribute);
    }

    public void addAttribute(PlantAttribute plantAttribute, AttributeValue attributeValue) {
        attributes.put(plantAttribute, attributeValue);
    }

}