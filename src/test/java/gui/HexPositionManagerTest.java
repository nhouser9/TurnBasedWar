package gui;

import org.junit.Test;
import static org.junit.Assert.*;

public class HexPositionManagerTest {

    @Test
    public void constructor_setsRadiusToMinimumTimesIncrement() {
        int size = 2;
        int increment = 10;
        int minimum = 40;
        HexPositionManager test = new HexPositionManager(size, increment, minimum);
        assertTrue(test.radius() == minimum * increment);
    }

    @Test
    public void zoomIn_addsIncrementToRadius() {
        int size = 2;
        int increment = 10;
        int minimum = 40;
        HexPositionManager test = new HexPositionManager(size, increment, minimum);
        double beforeZoom = test.radius();
        test.zoomIn();
        assertTrue(test.radius() == beforeZoom + increment);
    }

    @Test
    public void zoomOut_subtractsIncrementFromRadius_whenNotAtMinimumZoom() {
        int size = 2;
        int increment = 10;
        int minimum = 40;
        HexPositionManager test = new HexPositionManager(size, increment, minimum);
        test.zoomIn();
        double beforeZoom = test.radius();
        test.zoomOut();
        assertTrue(test.radius() == beforeZoom - increment);
    }

    @Test
    public void zoomOut_doesNotSubtractIncrementFromRadius_whenAtMinimumZoom() {
        int size = 2;
        int increment = 10;
        int minimum = 40;
        HexPositionManager test = new HexPositionManager(size, increment, minimum);
        double beforeZoom = test.radius();
        test.zoomOut();
        assertTrue(test.radius() == beforeZoom);
    }

    @Test
    public void verticalGap_returnsMinimumZoom_initially() {
        int size = 2;
        int increment = 10;
        int minimum = 40;
        HexPositionManager test = new HexPositionManager(size, increment, minimum);
        assertTrue(test.gap() == minimum);
    }

    @Test
    public void verticalGap_incrementsByOne_afterZoomIn() {
        int size = 2;
        int increment = 10;
        int minimum = 40;
        HexPositionManager test = new HexPositionManager(size, increment, minimum);
        test.zoomIn();
        assertTrue(test.gap() == minimum + 1);
    }
}
