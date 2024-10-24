package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class OrganismIcons {

    private final ImageView speciesIcon;
    private ImageView impersonatedIcon;

    private final Label label;
    private final StackPane stackPane;

    public OrganismIcons(ImageView speciesIcon, Label label) {
        this.speciesIcon = speciesIcon;
        this.label = label;
        this.impersonatedIcon = null;
        this.stackPane = new StackPane();
        this.stackPane.getChildren().addAll(this.speciesIcon, this.label);
        StackPane.setAlignment(label,Pos.BOTTOM_RIGHT);
    }

    public ImageView getSpeciesIcon() {
        return speciesIcon;
    }

    public Label getLabel() {
        return label;
    }

    public void setImpersonatedIcon(ImageView impersonatedIcon) {
        this.impersonatedIcon = impersonatedIcon;
        this.stackPane.getChildren().addLast(this.impersonatedIcon);
    }

    public StackPane getStackPane() {
        return stackPane;
    }

}
