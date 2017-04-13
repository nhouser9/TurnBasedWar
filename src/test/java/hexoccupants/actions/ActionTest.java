package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Unit;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActionTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constructor_savesTheActor() {
        Unit fakeUnit = mock(Unit.class);
        Action test = new StubAction(fakeUnit, false, 0);
        assertTrue(test.actor() == fakeUnit);
    }

    @Test
    public void act_throws_whenAllowedReturnsFalse() throws Exception {
        Unit fakeUnit = mock(Unit.class);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        Hex fakeTarget = mock(Hex.class);
        StubAction test = new StubAction(fakeUnit, false, 0);
        expectedException.expect(Action.ActionNotAllowedError.class);
        test.act(fakeBoard, fakeTarget);
        assertTrue(test.acted() == false);
    }

    @Test
    public void act_throws_whenActorHasInsufficentActionPoints() throws Exception {
        Unit fakeUnit = mock(Unit.class);
        when(fakeUnit.actionPoints()).thenReturn(0);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        Hex fakeTarget = mock(Hex.class);
        StubAction test = new StubAction(fakeUnit, true, 1);
        expectedException.expect(Action.ActionNotAllowedError.class);
        test.act(fakeBoard, fakeTarget);
        assertTrue(test.acted() == false);
    }

    @Test
    public void act_doesAct_whenActorHasEnoughActionPointsAndAllowedReturnsTrue() {
        Unit fakeUnit = mock(Unit.class);
        when(fakeUnit.actionPoints()).thenReturn(1);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        Hex fakeTarget = mock(Hex.class);
        StubAction test = new StubAction(fakeUnit, true, 1);
        try {
            test.act(fakeBoard, fakeTarget);
            assertTrue(test.acted() == true);
        } catch (Action.ActionNotAllowedError e) {
            fail("Unexpected exception while executing action");
        }
    }

    @Test
    public void act_subtractsTheCostFromTheActorsActionPoints_whenSuccessful() {
        Unit fakeUnit = mock(Unit.class);
        when(fakeUnit.actionPoints()).thenReturn(1);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        Hex fakeTarget = mock(Hex.class);
        StubAction test = new StubAction(fakeUnit, true, 1);
        try {
            test.act(fakeBoard, fakeTarget);
            verify(fakeUnit).payActionCost(test);
        } catch (Action.ActionNotAllowedError e) {
            fail("Unexpected exception while executing action");
        }
    }

    public class StubAction extends Action {

        private final boolean allowed;
        private final int cost;
        private boolean acted;

        public StubAction(Unit actor, boolean allowed, int cost) {
            super(actor);
            this.allowed = allowed;
            this.cost = cost;
            acted = false;
        }

        @Override
        public void action(HexGrid<Hex> board, Hex target) {
            acted = true;
        }

        @Override
        public boolean implementationAllowed(HexGrid<Hex> board, Hex target) {
            return allowed;
        }

        @Override
        public int cost() {
            return cost;
        }

        public boolean acted() {
            return acted;
        }

        @Override
        public String pickerId() {
            return "";
        }
    }
}
