package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Healer;
import hexoccupants.pieces.Unit;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import players.Player;
import players.PlayerColor;

public class HealTest {

    private Unit fakeActor;
    private Unit fakeUnit;
    private Player fakeCreator;
    private Player fakeEnemyPlayer;
    private Heal test;
    private Hex fakeTarget;
    private HexGrid<Hex> fakeBoard;

    @Before
    public void beforeTest() {
        fakeActor = mock(Unit.class);
        when(fakeActor.location()).thenReturn(mock(Hex.class));
        when(fakeActor.location().x()).thenReturn(0);
        when(fakeActor.location().y()).thenReturn(0);
        when(fakeActor.actionPoints()).thenReturn(Integer.MAX_VALUE);

        fakeUnit = mock(Unit.class);

        fakeCreator = mock(Player.class);
        when(fakeActor.creator()).thenReturn(fakeCreator);
        when(fakeCreator.playerColor()).thenReturn(mock(PlayerColor.class));
        when(fakeCreator.playerColor().color()).thenReturn(Color.BLUE);

        fakeEnemyPlayer = mock(Player.class);
        when(fakeEnemyPlayer.playerColor()).thenReturn(mock(PlayerColor.class));
        when(fakeEnemyPlayer.playerColor().color()).thenReturn(Color.ANTIQUEWHITE);

        test = new Heal(fakeActor);

        fakeTarget = mock(Hex.class);
        when(fakeTarget.x()).thenReturn(0);
        when(fakeTarget.y()).thenReturn(0);

        fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
    }

    @Test
    public void allowed_returnsFalse_whenTargetEmpty() {
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        assertFalse(test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void allowed_returnsFalse_whenTargetContainsEnemy() {
        when(fakeUnit.creator()).thenReturn(fakeEnemyPlayer);
        when(fakeTarget.occupant()).thenReturn(fakeUnit);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        assertFalse(test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void allowed_returnsFalse_whenTargetNotAdjacent() {
        when(fakeUnit.creator()).thenReturn(fakeCreator);
        when(fakeTarget.occupant()).thenReturn(fakeUnit);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(2);
        assertFalse(test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void allowed_returnsTrue_whenTargetAdjacentAndContainsAlly() {
        when(fakeUnit.creator()).thenReturn(fakeCreator);
        when(fakeTarget.occupant()).thenReturn(fakeUnit);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        assertTrue(test.allowed(fakeBoard, fakeTarget));
    }

    @Test
    public void action_callsHealOnTheTarget() {
        when(fakeTarget.occupant()).thenReturn(fakeUnit);
        test.action(fakeBoard, fakeTarget);
        verify(fakeUnit).heal(Heal.HEALING);
    }
}
