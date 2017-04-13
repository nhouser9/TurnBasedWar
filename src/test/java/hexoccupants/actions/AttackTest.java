package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Unit;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import players.Player;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AttackTest {

    @Test
    public void allowed_returnsTrue_whenTargetIsInRangeAndContainsEnemy() {
        Player fakeAttackerCreator = mock(Player.class);
        Player fakeDefenderCreator = mock(Player.class);

        Unit fakeAttacker = mock(Unit.class);
        when(fakeAttacker.creator()).thenReturn(fakeAttackerCreator);
        when(fakeAttacker.actionPoints()).thenReturn(Integer.MAX_VALUE);
        Unit fakeDefender = mock(Unit.class);
        when(fakeDefender.creator()).thenReturn(fakeDefenderCreator);

        Hex fakeLocation = mock(Hex.class);
        when(fakeLocation.x()).thenReturn(0);
        when(fakeLocation.y()).thenReturn(0);
        when(fakeAttacker.location()).thenReturn(fakeLocation);
        when(fakeDefender.location()).thenReturn(fakeLocation);
        when(fakeLocation.occupant()).thenReturn(fakeDefender);

        int range = 3;

        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(range);

        Attack test = new StubAttack(fakeAttacker, 1, 1, range);
        assertTrue(test.allowed(fakeBoard, fakeLocation));
    }

    @Test
    public void allowed_returnsFalse_whenTargetIsOutOfRange() {
        Player fakeAttackerCreator = mock(Player.class);
        Player fakeDefenderCreator = mock(Player.class);

        Unit fakeAttacker = mock(Unit.class);
        when(fakeAttacker.creator()).thenReturn(fakeAttackerCreator);
        when(fakeAttacker.actionPoints()).thenReturn(Integer.MAX_VALUE);
        Unit fakeDefender = mock(Unit.class);
        when(fakeDefender.creator()).thenReturn(fakeDefenderCreator);

        Hex fakeLocation = mock(Hex.class);
        when(fakeLocation.x()).thenReturn(0);
        when(fakeLocation.y()).thenReturn(0);
        when(fakeAttacker.location()).thenReturn(fakeLocation);
        when(fakeDefender.location()).thenReturn(fakeLocation);
        when(fakeLocation.occupant()).thenReturn(fakeDefender);

        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(2);

        Attack test = new StubAttack(fakeAttacker, 1, 1, 1);
        assertFalse(test.allowed(fakeBoard, fakeLocation));
    }

    @Test
    public void allowed_returnsFalse_whenTargetIsEqualToCurrentPosition() {
        Player fakeAttackerCreator = mock(Player.class);
        Player fakeDefenderCreator = mock(Player.class);

        Unit fakeAttacker = mock(Unit.class);
        when(fakeAttacker.creator()).thenReturn(fakeAttackerCreator);
        when(fakeAttacker.actionPoints()).thenReturn(Integer.MAX_VALUE);
        Unit fakeDefender = mock(Unit.class);
        when(fakeDefender.creator()).thenReturn(fakeDefenderCreator);

        Hex fakeLocation = mock(Hex.class);
        when(fakeLocation.x()).thenReturn(0);
        when(fakeLocation.y()).thenReturn(0);
        when(fakeAttacker.location()).thenReturn(fakeLocation);
        when(fakeDefender.location()).thenReturn(fakeLocation);
        when(fakeLocation.occupant()).thenReturn(fakeDefender);

        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(0);

        Attack test = new StubAttack(fakeAttacker, 1, 1, 1);
        assertFalse(test.allowed(fakeBoard, fakeLocation));
    }

    @Test
    public void allowed_returnsFalse_whenTargetEmpty() {
        Player fakeAttackerCreator = mock(Player.class);
        Player fakeDefenderCreator = mock(Player.class);

        Unit fakeAttacker = mock(Unit.class);
        when(fakeAttacker.creator()).thenReturn(fakeAttackerCreator);
        when(fakeAttacker.actionPoints()).thenReturn(Integer.MAX_VALUE);
        Unit fakeDefender = mock(Unit.class);
        when(fakeDefender.creator()).thenReturn(fakeDefenderCreator);

        Hex fakeLocation = mock(Hex.class);
        when(fakeLocation.x()).thenReturn(0);
        when(fakeLocation.y()).thenReturn(0);
        when(fakeAttacker.location()).thenReturn(fakeLocation);
        when(fakeDefender.location()).thenReturn(fakeLocation);
        when(fakeLocation.occupant()).thenReturn(null);

        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);

        Attack test = new StubAttack(fakeAttacker, 1, 1, 1);
        assertFalse(test.allowed(fakeBoard, fakeLocation));
    }

    @Test
    public void allowed_returnsFalse_whenTargetContainsAlly() {
        Player fakeAttackerCreator = mock(Player.class);

        Unit fakeAttacker = mock(Unit.class);
        when(fakeAttacker.creator()).thenReturn(fakeAttackerCreator);
        when(fakeAttacker.actionPoints()).thenReturn(Integer.MAX_VALUE);
        Unit fakeDefender = mock(Unit.class);
        when(fakeDefender.creator()).thenReturn(fakeAttackerCreator);

        Hex fakeLocation = mock(Hex.class);
        when(fakeLocation.x()).thenReturn(0);
        when(fakeLocation.y()).thenReturn(0);
        when(fakeAttacker.location()).thenReturn(fakeLocation);
        when(fakeDefender.location()).thenReturn(fakeLocation);
        when(fakeLocation.occupant()).thenReturn(fakeDefender);

        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);

        Attack test = new StubAttack(fakeAttacker, 1, 1, 1);
        assertFalse(test.allowed(fakeBoard, fakeLocation));
    }

    @Test
    public void action_damagesTheOccupantOfTheTarget() {
        Unit fakeAttacker = mock(Unit.class);
        Hex fakeSource = mock(Hex.class);
        when(fakeAttacker.location()).thenReturn(fakeSource);

        Unit fakeDefender = mock(Unit.class);
        Hex fakeTarget = mock(Hex.class);
        when(fakeTarget.occupant()).thenReturn(fakeDefender);

        int expected = 3;
        Attack test = new StubAttack(fakeAttacker, 1, expected, 1);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        test.action(fakeBoard, fakeTarget);
        verify(fakeDefender).damage(expected);
    }

    @Test
    public void action_damagesTheActor() {
        Unit fakeAttacker = mock(Unit.class);
        Hex fakeSource = mock(Hex.class);
        when(fakeAttacker.location()).thenReturn(fakeSource);

        Unit fakeDefender = mock(Unit.class);
        Hex fakeTarget = mock(Hex.class);
        when(fakeTarget.occupant()).thenReturn(fakeDefender);

        int expected = 4;
        Attack test = new StubAttack(fakeAttacker, expected, 1, 1);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        test.action(fakeBoard, fakeTarget);
        verify(fakeAttacker).damage(expected);
    }

    @Test
    public void action_removesTheActorFromItsHex_whenTheActionKillsTheActor() {
        Unit fakeAttacker = mock(Unit.class);
        Hex fakeSource = mock(Hex.class);
        when(fakeAttacker.location()).thenReturn(fakeSource);

        Unit fakeDefender = mock(Unit.class);
        Hex fakeTarget = mock(Hex.class);
        when(fakeTarget.occupant()).thenReturn(fakeDefender);

        Attack test = new StubAttack(fakeAttacker, 1, 1, 1);
        when(fakeAttacker.dead()).thenReturn(true);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        test.action(fakeBoard, fakeTarget);
        verify(fakeSource).setOccupant(null);
    }

    @Test
    public void action_removesTheDefenderFromItsHex_whenTheActionKillsTheDefender() {
        Unit fakeAttacker = mock(Unit.class);
        Hex fakeSource = mock(Hex.class);
        when(fakeAttacker.location()).thenReturn(fakeSource);

        Unit fakeDefender = mock(Unit.class);
        Hex fakeTarget = mock(Hex.class);
        when(fakeTarget.occupant()).thenReturn(fakeDefender);

        Attack test = new StubAttack(fakeAttacker, 1, 1, 1);
        when(fakeDefender.dead()).thenReturn(true);
        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        test.action(fakeBoard, fakeTarget);
        verify(fakeTarget).setOccupant(null);
    }

    private class StubAttack extends Attack {

        private final int selfDamage;
        private final int targetDamage;
        private final int range;

        public StubAttack(Unit actor, int selfDamage, int targetDamage, int range) {
            super(actor);
            this.selfDamage = selfDamage;
            this.targetDamage = targetDamage;
            this.range = range;
        }

        @Override
        protected int damageToSelf() {
            return selfDamage;
        }

        @Override
        protected int damageToTarget() {
            return targetDamage;
        }

        @Override
        protected int range() {
            return range;
        }
    }
}
