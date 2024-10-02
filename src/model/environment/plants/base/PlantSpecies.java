package model.environment.plants.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.environment.animals.enums.Gender;
import model.environment.common.attributes.AttributeValue;
import model.environment.common.base.Species;
import model.environment.common.base.SpeciesTaxonomy;
import model.environment.common.enums.*;
import model.environment.plants.attributes.PlantOrganismAttributes;
import model.environment.plants.attributes.PlantPositionAttributes;
import model.environment.plants.enums.PlantAttribute;
import utils.Log;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class PlantSpecies extends Species {

    private final Map<PlantAttribute, AttributeValue> attributes = new EnumMap<>(PlantAttribute.class);

    private final ArrayList<PlantOrganism> plantOrganisms = new ArrayList<>();

    public PlantSpecies(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image) {
        super(speciesTaxonomy, commonName, image);
    }

    public AttributeValue getAttribute(PlantAttribute plantAttribute) {
        return attributes.get(plantAttribute);
    }

    public List<PlantOrganism> getOrganisms() {
        return plantOrganisms;
    }

    public void addAttribute(PlantAttribute plantAttribute, AttributeValue attributeValue) {
        attributes.put(plantAttribute, attributeValue);
    }

    public static Map<TaxonomySpecies, PlantSpecies> initializePlantSpecies() {

        Map<TaxonomySpecies, PlantSpecies> plantSpeciesMap = new EnumMap<>(TaxonomySpecies.class);

        SpeciesTaxonomy salixAlbaTaxonomy = new SpeciesTaxonomy(TaxonomyClass.MAGNOLIOPSIDA, TaxonomyOrder.MALPIGHIALES, TaxonomyFamily.SALICACEAE, TaxonomyGenus.SALIX, TaxonomySpecies.SALIX_ALBA);

        PlantSpecies whiteWillow = new PlantSpecies(salixAlbaTaxonomy, "White Willow", new Image("resources/images/whiteWillow.png"));
        whiteWillowAttributes(whiteWillow);
        whiteWillow.initializePlantOrganisms();
        plantSpeciesMap.put(TaxonomySpecies.SALIX_ALBA, whiteWillow);

        return plantSpeciesMap;

    }

    private void initializePlantOrganisms() {

        int carryingCapacity = (int) getAttribute(PlantAttribute.CARRYING_CAPACITY).getValue(Gender.NA);

        PlantOrganism plantOrganism = new PlantOrganism(
            this,
            new ImageView(image),
            new PlantOrganismAttributes(new PlantPositionAttributes(300.0, 300.0)),
            carryingCapacity
        );

        plantOrganism.getImageView().setX(plantOrganism.getPlantOrganismAttributes().plantPositionAttributes().getPosX());
        plantOrganism.getImageView().setY(plantOrganism.getPlantOrganismAttributes().plantPositionAttributes().getPosY());

        plantOrganisms.add(plantOrganism);

    }

    private static AttributeValue plantSpeciesConstants(PlantSpecies plantSpecies, PlantAttribute plantAttribute) {

        String speciesName = plantSpecies.getSpeciesTaxonomy().taxonomySpecies().name();
        String speciesClassName = "model.environment.plants.constants." + Log.titleCase(speciesName.replace("_", " ")).replace(" ", "");
        String constantName = plantAttribute.name();

        Map<Gender, Double> values = new EnumMap<>(Gender.class);
        values.put(Gender.NA, classDoubleConstant(speciesClassName, constantName));
        return new AttributeValue(values);

    }

    private static void plantSpeciesAttributes(PlantSpecies plantSpecies) {
        plantSpecies.addAttribute(PlantAttribute.CARRYING_CAPACITY, plantSpeciesConstants(plantSpecies, PlantAttribute.CARRYING_CAPACITY));
        plantSpecies.addAttribute(PlantAttribute.GROWTH_RATE, plantSpeciesConstants(plantSpecies, PlantAttribute.GROWTH_RATE));
    }

    private static void whiteWillowAttributes(PlantSpecies whiteWillow) {
        plantSpeciesAttributes(whiteWillow);
    }

    public double getQuantity() {

        double quantity = 0;

        for (PlantOrganism plantOrganism : plantOrganisms) {
            quantity = quantity + plantOrganism.getQuantity();
        }

        return quantity;

    }

}