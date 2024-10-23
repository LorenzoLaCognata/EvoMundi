package model.environment.common.base;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.environment.animals.base.AnimalOrganism;
import view.OrganismIcons;

import java.util.UUID;

public class Organism {

    protected final String id;
    protected final OrganismIcons organismIcons;

    public Organism(ImageView imageView) {
        this.id = UUID.randomUUID().toString();
        this.organismIcons = new OrganismIcons(imageView, new Label(""));
    }

    public String getId() {
        return id;
    }

    public OrganismIcons getOrganismIcons() {
        return organismIcons;
    }

    public void setOrganismIconLabel(String string) {
        this.organismIcons.getLabel().setText(string);
    }

}
