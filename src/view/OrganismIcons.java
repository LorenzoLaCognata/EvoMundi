package view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class OrganismIcons {

    private final ImageView speciesIcon;
    private ImageView impersonatedIcon;
    private final StackPane stackPane;

    public OrganismIcons(ImageView speciesIcon) {
        this.speciesIcon = speciesIcon;
        this.impersonatedIcon = null;
        this.stackPane = new StackPane();
        this.stackPane.getChildren().add(this.speciesIcon);
    }

    public ImageView getSpeciesIcon() {
        return speciesIcon;
    }

    public ImageView getImpersonatedIcon() {
        return impersonatedIcon;
    }

    public void setImpersonatedIcon(ImageView impersonatedIcon) {
        this.impersonatedIcon = impersonatedIcon;
        this.stackPane.getChildren().addLast(this.impersonatedIcon);
    }

    public StackPane getStackPane() {
        return stackPane;
    }

}
