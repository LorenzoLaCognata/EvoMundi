package view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.simulation.base.SimulationSettings;

public class CenterPaneManager {

    private final Pane centerPane;

    public CenterPaneManager() {
        centerPane = createCenterPane();
    }

    public Pane getCenterPane() {
        return centerPane;
    }

    private Pane createCenterPane() {
        Pane pane = new Pane();

        double rows = Math.abs(SimulationSettings.MAX_LATITUDE - SimulationSettings.MIN_LATITUDE) / SimulationSettings.DEGREES_PER_TILE;
        double cols = Math.abs(SimulationSettings.MAX_LONGITUDE - SimulationSettings.MIN_LONGITUDE) / SimulationSettings.DEGREES_PER_TILE;

        for (int row = 0; row <= rows; row++) {
            Line line = new Line(0, row * SimulationSettings.PIXELS_PER_TILE, cols * SimulationSettings.PIXELS_PER_TILE, row * SimulationSettings.PIXELS_PER_TILE);
            line.setStroke(Color.GRAY);
            pane.getChildren().add(line);
        }

        for (int col = 0; col <= cols; col++) {
            Line line = new Line(col * SimulationSettings.PIXELS_PER_TILE, 0, col * SimulationSettings.PIXELS_PER_TILE, rows * SimulationSettings.PIXELS_PER_TILE);
            line.setStroke(Color.GRAY);
            pane.getChildren().add(line);
        }

        return pane;
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
