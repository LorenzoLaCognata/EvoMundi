package view;

import javafx.scene.Group;
import javafx.scene.Node;
import model.environment.common.base.Organism;

import java.util.Collections;
import java.util.List;

public record TileSpecies(List<Organism> organisms, Group organismImages) {

    public TileSpecies(List<Organism> organisms, Group organismImages) {
        this.organisms = Collections.synchronizedList(organisms);
        this.organismImages = organismImages;
    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public Group getOrganismsImages() {
        return organismImages;
    }

    public void addOrganismImage(Node node) {
        organismImages.getChildren().add(node);
    }

    public void removeOrganismImage(Node node) {
        organismImages.getChildren().remove(node);
    }

    public boolean containsOrganismImage(Node node) {
        return organismImages.getChildren().contains(node);
    }

}