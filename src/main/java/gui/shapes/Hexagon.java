package gui.shapes;

import javafx.scene.shape.Polygon;

/**
 * Extension of the Polygon class from JavaFX which is a hexagon where all of
 * the sides are equal.
 *
 * @author Nick Houser
 */
public class Hexagon extends Polygon {

    //the lengths of the sides of the internal triangles of this hexagon
    private final double longSide;
    private final double shortSide;
    private final double radius;

    /**
     * Constructor which initializes the vertices.
     *
     * @param xCenter the x co-ordinate of the center of the Hexagon
     * @param yCenter the y co-ordinate of the center of the Hexagon
     * @param radius the radius of the Hexagon
     */
    public Hexagon(double xCenter, double yCenter, double radius) {
        this.radius = radius;
        longSide = (Math.sqrt(3) / 2) * radius;
        shortSide = radius / 2;

        Double[] points = new Double[]{
            xCenter - radius, yCenter,
            xCenter - shortSide, yCenter + longSide,
            xCenter + shortSide, yCenter + longSide,
            xCenter + radius, yCenter,
            xCenter + shortSide, yCenter - longSide,
            xCenter - shortSide, yCenter - longSide
        };

        getPoints().addAll(points);
    }

    /**
     * Gets the length of the long side of one of the internal triangles
     * comprising the Hexagon. This is equivalent to the y-offset of the four
     * points of the Hexagon which are not along the x axis.
     *
     * @return the length of the long side of one of the internal triangles
     */
    public double longSide() {
        return longSide;
    }

    /**
     * Gets the length of the short side of one of the internal triangles
     * comprising the Hexagon. This is equivalent to the x-offset of the four
     * points of the Hexagon which are not along the x axis.
     *
     * @return the length of the short side of one of the internal triangles
     */
    public double shortSide() {
        return shortSide;
    }

    /**
     * Gets the radius of the Hexagon.
     *
     * @return the radius of the Hexagon
     */
    public double radius() {
        return radius;
    }
}
