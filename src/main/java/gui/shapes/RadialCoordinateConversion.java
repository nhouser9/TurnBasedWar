/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.shapes;

/**
 * Class which converts radial co-ordinates to cartesian co-ordinates.
 *
 * @author Nick Houser
 */
public class RadialCoordinateConversion {

    //the amount to divide by to find the reference angle
    private static final int REFERENCE_DEGREES = 90;

    //the cartesian x and y
    private double x;
    private double y;

    /**
     * Constructor which performs the conversion.
     *
     * @param angle the angle of the radial co-ordinate
     * @param radius the radius of the radial co-ordinate
     */
    public RadialCoordinateConversion(int angle, double radius) {
        int reference = angle % REFERENCE_DEGREES;
        int quadrant = Math.floorDiv(angle, REFERENCE_DEGREES) + 1;

        x = radius * Math.cos(Math.toRadians(reference));
        y = radius * Math.sin(Math.toRadians(reference));

        double swap;
        switch (quadrant) {
            case 2:
                swap = x;
                x = -1 * y;
                y = swap;
                break;
            case 3:
                x = -1 * x;
                y = -1 * y;
                break;
            case 4:
                swap = y;
                y = -1 * x;
                x = swap;
                break;
        }
    }

    /**
     * Gets the converted cartesian x co-ordinate.
     *
     * @return the converted cartesian x co-ordinate
     */
    public double x() {
        return x;
    }

    /**
     * Gets the converted cartesian y co-ordinate.
     *
     * @return the converted cartesian y co-ordinate
     */
    public double y() {
        return y;
    }
}
