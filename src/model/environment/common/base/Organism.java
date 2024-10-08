package model.environment.common.base;

import javafx.scene.image.ImageView;
import view.OrganismIcons;

import java.awt.*;
import java.util.UUID;

public class Organism {

    private final String id;
    private final OrganismIcons organismIcons;

    public Organism(ImageView imageView) {
        this.id = UUID.randomUUID().toString();
        this.organismIcons = new OrganismIcons(imageView, new Label(id));
    }

    public String getId() {
        return id;
    }

    public OrganismIcons getOrganismIcons() {
        return organismIcons;
    }

}
