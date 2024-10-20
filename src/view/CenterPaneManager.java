package view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class CenterPaneManager {

    private final Pane centerPane;

    public CenterPaneManager() {
        centerPane = createCenterPane();
    }

    public Pane getCenterPane() {
        return centerPane;
    }

    private Pane createCenterPane() {
        return new Pane();
    }

    public boolean groupMissingFromCenterPane(Group group) {
        return !centerPane.getChildren().contains(group);
    }

    public void addCenterPaneGroup(Group group) {
        centerPane.getChildren().add(group);
    }

    public void removeCenterPaneGroup(Group group) {
        centerPane.getChildren().remove(group);
    }


}
