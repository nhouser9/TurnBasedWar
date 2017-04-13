package hexoccupants.pieces;

import hex.Hex;
import hex.HexGrid;
import java.util.Collection;
import java.util.HashSet;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import players.Player;
import players.StubPlayer;

public class LeaderTest {

    private Player fakePlayerLeader;
    private Player fakePlayerOther;
    private Leader test;
    private Unit other;
    private HexGrid<Hex> fakeBoard;
    private Collection<Hex> fakeHexes;

    @Before
    public void beforeTest() {
        fakePlayerLeader = StubPlayer.create(Color.CORAL);
        fakePlayerOther = StubPlayer.create(Color.ALICEBLUE);

        Hex fakeLocation = mock(Hex.class);
        when(fakeLocation.x()).thenReturn(0);
        when(fakeLocation.y()).thenReturn(0);

        test = new Leader(fakePlayerLeader);
        test.relocate(fakeLocation);

        other = mock(Unit.class);
        when(other.location()).thenReturn(fakeLocation);
        when(fakeLocation.occupant()).thenReturn(other);

        fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        fakeHexes = new HashSet<>();
        fakeHexes.add(fakeLocation);
        when(fakeBoard.all()).thenReturn(fakeHexes);
    }

    @Test
    public void passed_damagesNearbyEnemies_whenDamageUpgradeChosen() {
        test.upgrades().upgrade(UpgradeTypes.DAMAGE);
        when(other.creator()).thenReturn(fakePlayerOther);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        test.passed(fakeBoard);
        verify(other).damage(Leader.BUFF);
    }

    @Test
    public void passed_doesNotDamageNearbyEnemies_whenDamageUpgradeNotChosen() {
        when(other.creator()).thenReturn(fakePlayerOther);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        test.passed(fakeBoard);
        verify(other, times(0)).damage(Leader.BUFF);
    }

    @Test
    public void passed_doesNotDamageNearbyAllies_whenDamageUpgradeChosen() {
        test.upgrades().upgrade(UpgradeTypes.DAMAGE);
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        test.passed(fakeBoard);
        verify(other, times(0)).damage(Leader.BUFF);
    }

    @Test
    public void passed_healsNearbyAllies_whenHealingUpgradeChosen() {
        test.upgrades().upgrade(UpgradeTypes.HEALING);
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        test.passed(fakeBoard);
        verify(other).heal(Leader.BUFF);
    }

    @Test
    public void passed_doesNotHealNearbyAllies_whenHealingUpgradeNotChosen() {
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        test.passed(fakeBoard);
        verify(other, times(0)).heal(Leader.BUFF);
    }

    @Test
    public void passed_doesNotHealNearbyEnemies_whenHealingUpgradeChosen() {
        test.upgrades().upgrade(UpgradeTypes.HEALING);
        when(other.creator()).thenReturn(fakePlayerOther);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        test.passed(fakeBoard);
        verify(other, times(0)).heal(Leader.BUFF);
    }

    @Test
    public void passed_doesNotBuffUnits_whenTheUnitsOutOfRange() {
        test.upgrades().upgrade(UpgradeTypes.HEALING);
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(Leader.RANGE + 1);
        test.passed(fakeBoard);
        verify(other, times(0)).heal(Leader.BUFF);
    }

    @Test
    public void passed_doesNotBuffSelf() {
        test.upgrades().upgrade(UpgradeTypes.HEALING);
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(0);
        test.passed(fakeBoard);
        verify(other, times(0)).heal(Leader.BUFF);
    }

    @Test
    public void passed_doesNotError_whenBoardContainsEmptySquares() {
        test.upgrades().upgrade(UpgradeTypes.HEALING);
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(0);
        Hex fakeHex = mock(Hex.class);
        when(fakeHex.occupant()).thenReturn(null);
        fakeHexes.add(fakeHex);
        test.passed(fakeBoard);
    }

    @Test
    public void passed_incrementsLevel_afterTheProperNumberOfCalls() {
        test.upgrades().upgrade(UpgradeTypes.MOVEMENT);
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(0);

        int initial = test.level();
        for (int i = 0; i < Leader.TURNS_UNITL_LEVEL; i++) {
            test.passed(fakeBoard);
        }
        assertTrue(test.level() == initial + 1);
    }

    @Test
    public void passed_doesNotIncrementLevel_beforeTheProperNumberOfCalls() {
        test.upgrades().upgrade(UpgradeTypes.MOVEMENT);
        when(other.creator()).thenReturn(fakePlayerLeader);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(0);

        int initial = test.level();
        for (int i = 0; i < Leader.TURNS_UNITL_LEVEL - 1; i++) {
            test.passed(fakeBoard);
        }
        assertTrue(test.level() == initial);
    }
}
