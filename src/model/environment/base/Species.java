package model.environment.base;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class Species {

    protected final SpeciesTaxonomy speciesTaxonomy;
    protected final String commonName;

    protected final Image image;
    protected final Group imageGroup = new Group();

    public Species(SpeciesTaxonomy speciesTaxonomy, String commonName, Image image) {
        this.speciesTaxonomy = speciesTaxonomy;
        this.commonName = commonName;
        this.image = image;
    }

    public SpeciesTaxonomy getSpeciesTaxonomy() {
        return speciesTaxonomy;
    }

    public String getCommonName() {
        return commonName;
    }

    public Image getImage() {
        return image;
    }

    public Group getImageGroup() {
        return imageGroup;
    }

    @Override
    public String toString() {
        return commonName;
    }

}