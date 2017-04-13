package gui.shapes;

import org.junit.Test;
import static org.junit.Assert.*;

public class RadialCoordinateConversionTest {

    private final static double EPSILON = 0.00001;

    @Test
    public void constructor_initializesCorrectly_whenAngleInFirstQuadrant() {
        RadialCoordinateConversion test = new RadialCoordinateConversion(0, 2.0);
        assertTrue(test.x() == 2.0);
        assertTrue(test.y() == 0.0);
    }

    @Test
    public void constructor_initializesCorrectly_whenAngleInSecondQuadrant() {
        RadialCoordinateConversion test = new RadialCoordinateConversion(150, 10.0);
        double expectedX = (((-10.0) * Math.sqrt(3)) / 2);
        double expectedY = (10.0 / 2.0);
        assertTrue(test.x() <= expectedX + EPSILON && test.x() >= expectedX - EPSILON);
        assertTrue(test.y() <= expectedY + EPSILON && test.y() >= expectedY - EPSILON);
    }

    @Test
    public void constructor_initializesCorrectly_whenAngleInThirdQuadrant() {
        RadialCoordinateConversion test = new RadialCoordinateConversion(190, 10.0);
        double expectedX = (-10) * Math.cos(Math.toRadians(10));
        double expectedY = (-10) * Math.sin(Math.toRadians(10));
        assertTrue(test.x() <= expectedX + EPSILON && test.x() >= expectedX - EPSILON);
        assertTrue(test.y() <= expectedY + EPSILON && test.y() >= expectedY - EPSILON);
    }

    @Test
    public void constructor_initializesCorrectly_whenAngleInFourthQuadrant() {
        RadialCoordinateConversion test = new RadialCoordinateConversion(315, 1.0);
        double expected = 1.0 / Math.sqrt(2);
        double expectedX = expected;
        double expectedY = (-1.0) * expected;
        assertTrue(test.x() <= expectedX + EPSILON && test.x() >= expectedX - EPSILON);
        assertTrue(test.y() <= expectedY + EPSILON && test.y() >= expectedY - EPSILON);
    }
}
