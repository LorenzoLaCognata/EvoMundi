package model.environment.common.base;


import javafx.scene.image.Image;
import view.ToolbarSection;

public class Species {

    protected final SpeciesTaxonomy speciesTaxonomy;
    protected final String commonName;
    protected final Image image;
    private final ToolbarSection toolbarSection;

    protected double organismCount;

    public Species(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image) {
        this.speciesTaxonomy = speciesTaxonomy;
        this.commonName = commonName;
        this.image = image;
        this.toolbarSection = new ToolbarSection(this.commonName, this.getImage());
    }

    public SpeciesTaxonomy getSpeciesTaxonomy() {
        return speciesTaxonomy;
    }

    public String getCommonName() {
        return commonName;
    }

    public double getOrganismCount() {
        return organismCount;
    }

    public void setOrganismCount(double organismCount) {
        this.organismCount = organismCount;
    }
    
    public Image getImage() {
        return image;
    }

    public ToolbarSection getToolbarSection() {
        return toolbarSection;
    }

    @Override
    public String toString() {
        return commonName;
    }

}