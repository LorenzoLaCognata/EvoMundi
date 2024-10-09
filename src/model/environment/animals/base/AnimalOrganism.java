package model.environment.animals.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.environment.animals.attributes.AnimalOrganismAttributes;
import model.environment.animals.enums.*;
import model.environment.common.base.Organism;
import model.environment.common.enums.OrganismStatus;
import model.environment.common.enums.TaxonomySpecies;
import utils.Log;

import java.util.EnumMap;
import java.util.Map;

public class AnimalOrganism extends Organism {

    private final AnimalSpecies animalSpecies;
    private final Gender gender;
    private final Diet diet;
    private final AnimalOrganismAttributes animalOrganismAttributes;

    private double age;
    private double energy = 1.0;

    private ReproductionStatus reproductionStatus = ReproductionStatus.NOT_MATURE;
    private double gestationWeek = 0.0;
    private double cooldownWeek = 0.0;
    private AnimalOrganism mate;

    private OrganismStatus organismStatus = OrganismStatus.ALIVE;
    private AnimalOrganismDeathReason organismDeathReason = AnimalOrganismDeathReason.NA;

    private boolean impersonatedOrganism = false;

    private final Map<TaxonomySpecies, PreyAnimalSpecies> preyAnimalSpecies = new EnumMap<>(TaxonomySpecies.class);

    public AnimalOrganism(AnimalSpecies animalSpecies, Gender gender, Diet diet, double age, ImageView imageView, AnimalOrganismAttributes animalOrganismAttributes) {
        super(imageView);
        this.animalSpecies = animalSpecies;
        this.gender = gender;
        this.diet = diet;
        this.age = age;
        this.animalOrganismAttributes = animalOrganismAttributes;
    }

    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
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

    public AnimalOrganismAttributes getOrganismAttributes() {
        return animalOrganismAttributes;
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

    public AnimalOrganism getMate() {
        return mate;
    }

    public OrganismStatus getOrganismStatus() {
        return organismStatus;
    }

    public AnimalOrganismDeathReason getOrganismDeathReason() {
        return organismDeathReason;
    }

    public boolean isImpersonatedOrganism() {
        return impersonatedOrganism;
    }

    public Map<TaxonomySpecies, PreyAnimalSpecies> getPreyAnimalSpecies() {
        return preyAnimalSpecies;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
        this.setOrganismIconLabel(Log.formatPercentage(energy));
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

    public void setMate(AnimalOrganism mate) {
        this.mate = mate;
    }

    public void setOrganismStatus(OrganismStatus organismStatus) {
        this.organismStatus = organismStatus;
    }

    public void setOrganismDeathReason(AnimalOrganismDeathReason organismDeathReason) {
        this.organismDeathReason = organismDeathReason;
    }

    public void setImpersonatedOrganism(boolean impersonatedOrganism) {

        this.impersonatedOrganism = impersonatedOrganism;

        if (impersonatedOrganism) {
            ImageView imageView = new ImageView(new Image("resources/images/impersonated.png", 64, 64, false, false));
            this.getOrganismIcons().setImpersonatedIcon(imageView);
        }

    }

    public double calculateHuntSuccessRate(AnimalSpecies preyAnimalSpecies, AnimalOrganism preyOrganism) {

        double baseSuccessRate;

        switch (this.animalSpecies.getSpeciesTaxonomy().taxonomySpecies()) {
            case TaxonomySpecies.CANIS_LUPUS:
                switch (preyOrganism.getAnimalSpecies().getSpeciesTaxonomy().taxonomySpecies()) {
                    case TaxonomySpecies.ODOCOILEUS_VIRGINIANUS -> baseSuccessRate = 0.30;
                    case TaxonomySpecies.ALCES_ALCES -> baseSuccessRate = 0.15;
                    default -> throw new IllegalStateException(Log.UNEXPECTED_PREY_MESSAGE + preyOrganism.getAnimalSpecies().getSpeciesTaxonomy().taxonomySpecies());
                }
                break;
            case TaxonomySpecies.LYNX_RUFUS:
                switch (preyOrganism.getAnimalSpecies().getSpeciesTaxonomy().taxonomySpecies()) {
                    case TaxonomySpecies.LEPUS_AMERICANUS -> baseSuccessRate = 0.45;
                    case TaxonomySpecies.CASTOR_FIBER -> baseSuccessRate = 0.10;
                    case TaxonomySpecies.ODOCOILEUS_VIRGINIANUS -> baseSuccessRate = 0.05;
                    default -> throw new IllegalStateException(Log.UNEXPECTED_PREY_MESSAGE + preyOrganism.getAnimalSpecies().getSpeciesTaxonomy().taxonomySpecies());
                }
                break;
            default:
                throw new IllegalStateException(Log.UNEXPECTED_PREDATOR_MESSAGE + this.animalSpecies.getSpeciesTaxonomy().taxonomySpecies());
        }

        return baseSuccessRate * (preyAnimalSpecies.getOrganismCount() / preyAnimalSpecies.getAttribute (AnimalAttribute.CARRYING_CAPACITY).getAverageValue());

    }

}