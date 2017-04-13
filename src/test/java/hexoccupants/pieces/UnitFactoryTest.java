package hexoccupants.pieces;

import hex.Hex;
import hex.HexGrid;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import players.Player;
import turnbasedwar.Settings;

public class UnitFactoryTest {

    private HexGrid<Hex> fakeBoard;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void beforeTest() {
        fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(0);
    }

    @Test
    public void create_throws_whenTheHexIsOutOfRangeOfTheLeader() throws Exception {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(Settings.RANGE_CREATION + 1);
        expectedException.expect(UnitFactory.TooFarFromLeaderError.class);

        UnitFactory test = new UnitFactory();
        test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
        test.create(Healer.class, fakePlayer, fakeBoard, fakeHex);
    }

    @Test
    public void create_throws_whenTheHexIsAlreadyOccupied() throws Exception {
        Unit fakeUnit = mock(Unit.class);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(fakeUnit);
        Player fakePlayer = mock(Player.class);

        UnitFactory test = new UnitFactory();
        expectedException.expect(UnitFactory.HexOccupiedError.class);
        test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
    }

    @Test
    public void create_throws_whenTheCreatorCannotAffordTheUnit() throws Exception {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(-1);
        Hex fakeHex = mock(Hex.class);

        UnitFactory test = new UnitFactory();
        expectedException.expect(UnitFactory.InsufficentPointsException.class);
        test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
        test.create(Melee.class, fakePlayer, fakeBoard, fakeHex);
    }

    @Test
    public void create_throws_whenPassedAnInvalidUnitType() throws Exception {
        Player fakePlayer = mock(Player.class);
        Hex fakeHex = mock(Hex.class);

        UnitFactory test = new UnitFactory();
        expectedException.expect(UnitFactory.BadUnitTypeError.class);
        test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
        test.create(BadUnit.class, fakePlayer, fakeBoard, fakeHex);
    }

    @Test
    public void create_throws_whenAskedForAnotherUnitBeforeALeader() throws Exception {
        Player fakePlayer = mock(Player.class);
        Hex fakeHex = mock(Hex.class);

        UnitFactory test = new UnitFactory();
        expectedException.expect(UnitFactory.UnitCreatedBeforeLeaderError.class);
        test.create(Melee.class, fakePlayer, fakeBoard, fakeHex);
    }

    @Test
    public void create_createsBuilders_whenPassedBuilderClass() {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        UnitFactory test = new UnitFactory();
        try {
            test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
            Unit result = test.create(Builder.class, fakePlayer, fakeBoard, fakeHex);
            assertTrue(result != null && result instanceof Builder);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void create_createsLeaders_whenPassedLeaderClass() {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        UnitFactory test = new UnitFactory();
        try {
            Unit result = test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
            assertTrue(result != null && result instanceof Leader);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void create_createsHealers_whenPassedHealerClass() {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        UnitFactory test = new UnitFactory();
        try {
            test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
            Unit result = test.create(Healer.class, fakePlayer, fakeBoard, fakeHex);
            assertTrue(result != null && result instanceof Healer);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void create_createsMeleeUnits_whenPassedMeleeClass() {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        UnitFactory test = new UnitFactory();
        try {
            test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
            Unit result = test.create(Melee.class, fakePlayer, fakeBoard, fakeHex);
            assertTrue(result != null && result instanceof Melee);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void create_createsRangedUnits_whenPassedRangedClass() {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        UnitFactory test = new UnitFactory();
        try {
            test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
            Unit result = test.create(Ranged.class, fakePlayer, fakeBoard, fakeHex);
            assertTrue(result != null && result instanceof Ranged);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void create_subtractsTheUnitCostFromTheCreator() {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        UnitFactory test = new UnitFactory();
        try {
            test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
            Unit result = test.create(Ranged.class, fakePlayer, fakeBoard, fakeHex);
            verify(fakePlayer).purchase(result);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void create_addsTheUnitToThePassedHex() {
        Player fakePlayer = mock(Player.class);
        when(fakePlayer.purchasePoints()).thenReturn(Integer.MAX_VALUE);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);

        UnitFactory test = new UnitFactory();
        try {
            test.create(Leader.class, fakePlayer, fakeBoard, fakeHex);
            Unit result = test.create(Ranged.class, fakePlayer, fakeBoard, fakeHex);
            verify(fakeHex).setOccupant(result);
            assertTrue(result.location() == fakeHex);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    private class BadUnit extends Unit {

        public BadUnit() {
            super(null, null);
        }

        @Override
        public int cost() {
            return 0;
        }

        @Override
        public String imagePath() {
            return "";
        }

        @Override
        protected int initialActionPoints() {
            return 0;
        }

        @Override
        protected int initialHealth() {
            return 0;
        }
    }
}
