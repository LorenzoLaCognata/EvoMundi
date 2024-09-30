package model.environment.common.base;

import javafx.scene.Group;
import javafx.scene.image.Image;
import view.ToolbarSection;

public class Species {

    protected final SpeciesTaxonomy speciesTaxonomy;
    protected final String commonName;

    protected final Image image;
    protected final Group imageGroup = new Group();

    private final ToolbarSection toolbarSection;

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

    public Image getImage() {
        return image;
    }

    public Group getImageGroup() {
        return imageGroup;
    }

    public ToolbarSection getToolbarSection() {
        return toolbarSection;
    }

    @Override
    public String toString() {
        return commonName;
    }

    // TODO: review energy loss for herbivores
    public static double classDoubleConstant(String className, String constantName) {

        Class<?> speciesClass;
        try {
            speciesClass = Class.forName(className);
            return speciesClass.getField(constantName).getDouble(null);

        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}