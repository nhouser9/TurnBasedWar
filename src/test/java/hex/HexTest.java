package hex;

import hexoccupants.Constructs;
import hexoccupants.pieces.Unit;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HexTest {

    @Test
    public void construct_returnsNone_initially() {
        HexGrid<Hex> fakeGrid = (HexGrid<Hex>) mock(HexGrid.class);
        Hex test = new Hex(fakeGrid, 0, 0);
        assertTrue(test.construct() == Constructs.NONE);
    }

    @Test
    public void setConstruct_setsTheConstruct() {
        HexGrid<Hex> fakeGrid = (HexGrid<Hex>) mock(HexGrid.class);
        Hex test = new Hex(fakeGrid, 0, 0);
        test.setConstruct(Constructs.FORT);
        assertTrue(test.construct() == Constructs.FORT);
    }

    @Test
    public void occupant_returnsNull_initially() {
        HexGrid<Hex> fakeGrid = (HexGrid<Hex>) mock(HexGrid.class);
        Hex test = new Hex(fakeGrid, 0, 0);
        assertTrue(test.occupant() == null);
    }

    @Test
    public void setOccupant_setsTheOccupant() {
        HexGrid<Hex> fakeGrid = (HexGrid<Hex>) mock(HexGrid.class);
        Hex test = new Hex(fakeGrid, 0, 0);
        Unit fakeUnit = mock(Unit.class);
        test.setOccupant(fakeUnit);
        assertTrue(test.occupant() == fakeUnit);
    }

    @Test
    public void constructor_setsThePosition() {
        HexGrid<Hex> fakeGrid = (HexGrid<Hex>) mock(HexGrid.class);
        int expectedX = -1;
        int expectedY = 1;

        Hex test = new Hex(fakeGrid, expectedX, expectedY);
        assertTrue(test.x() == expectedX);
        assertTrue(test.y() == expectedY);
    }

    @Test
    public void constructor_addsTheHexToAGrid() {
        HexGrid<Hex> fakeGrid = (HexGrid<Hex>) mock(HexGrid.class);
        int expectedX = -1;
        int expectedY = 1;

        Hex test = new Hex(fakeGrid, expectedX, expectedY);
        verify(fakeGrid).set(expectedX, expectedY, test);
    }
}
