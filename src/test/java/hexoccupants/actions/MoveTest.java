package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Unit;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class MoveTest {

    private Unit fakeActor;
    private Move test;
    private Hex fakeTarget;
    private HexGrid<Hex> fakeBoard;

    @Before
    public void beforeTest() {
        fakeActor = mock(Unit.class);
        when(fakeActor.location()).thenReturn(mock(Hex.class));
        when(fakeActor.location().x()).thenReturn(0);
        when(fakeActor.location().y()).thenReturn(0);
        when(fakeActor.actionPoints()).thenReturn(Integer.MAX_VALUE);

        test = new Move(fakeActor);

        fakeTarget = mock(Hex.class);
        when(fakeTarget.x()).thenReturn(0);
        when(fakeTarget.y()).thenReturn(0);

        fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
    }

    @Test
    public void allowed_returnsTrue_whenTargetIsAdjacent() {
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        assertTrue(true == test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void allowed_returnsFalse_whenTargetIsNotAdjacent() {
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(2);
        assertTrue(false == test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void allowed_returnsFalse_whenTargetIsEqualToCurrentPosition() {
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(0);
        assertTrue(false == test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void allowed_returnsFalse_whenTargetOccupied() {
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        when(fakeTarget.occupant()).thenReturn(mock(Unit.class));
        assertTrue(false == test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void action_removesTheUnitFromItsOldSpace() {
        test.action(fakeBoard, fakeTarget);
        verify(fakeActor.location()).setOccupant(null);
    }

    @Test
    public void action_addsTheUnitToItsNewSpace() {
        test.action(fakeBoard, fakeTarget);
        verify(fakeTarget).setOccupant(fakeActor);
    }

    @Test
    public void action_updatesTheUnitsPosition() {
        test.action(fakeBoard, fakeTarget);
        verify(fakeActor).relocate(fakeTarget);
    }
}
