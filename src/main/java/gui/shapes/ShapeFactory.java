package gui.shapes;

import hexoccupants.Constructs;
import javafx.scene.shape.Shape;

/**
 * Factory for the shapes of Hexes displayed on the board.
 *
 * @author Nick Houser
 */
public class ShapeFactory {

    /**
     * Factory method which creates the shape to display for a GUI Hex based on
     * the construct (or lack thereof) on that Hex.
     *
     * @param construct the contruct on the Hex for which the GUI shape is being
     * constructed
     * @param xCenter the x co-ordinate of the center of the Shape
     * @param yCenter the y co-ordinate of the center of the Shape
     * @param radius the radius of the Shape
     * @param gap the gap between Shapes on the board
     * @return a Shape that meets the requested criteria
     */
    public Shape create(Constructs construct, double xCenter, double yCenter, double radius, int gap) {
        switch (construct) {
            case FORT:
                return new IndentHexagon(xCenter, yCenter, radius, gap);
            case ROAD:
                return new ExtrudeHexagon(xCenter, yCenter, radius, gap);
            case OBJECTIVE_CENTER:
                return new Hexagon(xCenter, yCenter, radius);
            case OBJECTIVE_EDGE:
                return new Hexagon(xCenter, yCenter, radius);
            case NONE:
                return new Hexagon(xCenter, yCenter, radius);
        }
        return null;
    }
}
