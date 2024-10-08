package view;

import model.simulation.base.SimulationSettings;

import java.awt.*;
import java.awt.geom.Point2D;

public class Geography {

    private Geography()  {
    }

    public static Point2D.Double geographyToScreen(double latitude, double longitude) {

        double xNum = Math.abs(longitude - SimulationSettings.MIN_LONGITUDE) * SimulationSettings.SCENE_WIDTH;
        double xDen = SimulationSettings.MAX_LONGITUDE - SimulationSettings.MIN_LONGITUDE;
        double x = xNum / xDen;

        double yNum = Math.abs(latitude - SimulationSettings.MIN_LATITUDE) * SimulationSettings.SCENE_HEIGHT;
        double yDen = SimulationSettings.MAX_LATITUDE - SimulationSettings.MIN_LATITUDE;
        double y = yNum / yDen;

        return new Point2D.Double(x, y);
    }

    public static Point calculateTile(double latitude, double longitude) {

        int cellX = (int) Math.floor((longitude - SimulationSettings.MIN_LONGITUDE) / SimulationSettings.DEGREES_PER_TILE);
        int cellY = (int) Math.floor((latitude - SimulationSettings.MIN_LATITUDE) / SimulationSettings.DEGREES_PER_TILE);

        return new Point(cellX, cellY);
    }

}
