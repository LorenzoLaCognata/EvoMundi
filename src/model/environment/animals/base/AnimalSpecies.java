package model.environment.animals.base;

import javafx.scene.image.Image;
import model.environment.animals.enums.AnimalAttribute;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Diet;
import model.environment.common.attributes.AttributeValue;
import model.environment.common.base.Species;
import model.environment.common.base.SpeciesTaxonomy;
import model.environment.common.enums.TaxonomySpecies;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AnimalSpecies extends Species {

    private final Diet baseDiet;

    private final Map<AnimalAttribute, AttributeValue> attributes = new EnumMap<>(AnimalAttribute.class);
    private final Map<TaxonomySpecies, PreyAnimalSpecies> basePreyAnimalSpecies = new EnumMap<>(TaxonomySpecies.class);

    private final ArrayList<AnimalOrganism> newbornAnimalOrganisms = new ArrayList<>();
    private final ArrayList<AnimalOrganism> deadAnimalOrganisms = new ArrayList<>();

    public AnimalSpecies(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image, Diet baseDiet) {
        super(speciesTaxonomy, commonName, image);
        this.baseDiet = baseDiet;
    }

    public Diet getBaseDiet() {
        return baseDiet;
    }

    public AttributeValue getAttribute(AnimalAttribute animalAttribute) {
        return attributes.get(animalAttribute);
    }

    public Map<TaxonomySpecies, PreyAnimalSpecies> getBasePreyAnimalSpecies() {
        return basePreyAnimalSpecies;
    }

    public List<AnimalOrganism> getNewbornOrganisms() {
        return newbornAnimalOrganisms;
    }

    public List<AnimalOrganism> getDeadOrganisms() {
        return deadAnimalOrganisms;
    }

    public void addNewbornOrganism(AnimalOrganism organism) {
        newbornAnimalOrganisms.add(organism);
    }

    public int getDeadPopulation() {
        return deadAnimalOrganisms.size();
    }

    public int getDeadPopulation(AnimalOrganismDeathReason organismDeathReason) {

        int count = 0;

        for (AnimalOrganism animalOrganism : deadAnimalOrganisms) {
            if (animalOrganism.getOrganismDeathReason() == organismDeathReason) {
                count++;
            }
        }

        return count;

    }

    public void addAttribute(AnimalAttribute animalAttribute, AttributeValue attributeValue) {
        attributes.put(animalAttribute, attributeValue);
    }

    public void addBasePreyAnimalSpecies(PreyAnimalSpecies preyAnimalSpecies) {
        basePreyAnimalSpecies.put(preyAnimalSpecies.taxonomySpecies(), preyAnimalSpecies);
    }

}