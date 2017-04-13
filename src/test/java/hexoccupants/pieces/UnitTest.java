package hexoccupants.pieces;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.actions.Action;
import hexoccupants.actions.Move;
import hexoccupants.actions.RoadMove;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import players.Player;

public class UnitTest {

    @Test
    public void constructor_setsTheCreator() {
        Player fakePlayer = mock(Player.class);
        Unit test = new StubUnit(fakePlayer, fakeLeader());
        assertTrue(test.creator() == fakePlayer);
    }

    @Test
    public void addAction_addsAnActionToTheActionList() {
        Unit test = new StubUnit(null, fakeLeader());
        Action fakeAction = mock(Action.class);
        test.addAction(fakeAction);
        assertTrue(test.actions().contains(fakeAction));
    }

    @Test
    public void constructor_addsMoveActionsToTheActionList() {
        Unit test = new StubUnit(null, fakeLeader());
        boolean foundMoveAction = false;
        boolean foundRoadMoveAction = false;
        for (Action action : test.actions()) {
            if (action.getClass().equals(Move.class)) {
                foundMoveAction = true;
            }

            if (action.getClass().equals(RoadMove.class)) {
                foundRoadMoveAction = true;
            }
        }
        assertTrue(foundMoveAction);
        assertTrue(foundRoadMoveAction);
    }

    @Test
    public void payActionCost_subtractsTheCostOfTheAction_fromTheActionPoints() {
        Unit test = new StubUnit(null, fakeLeader());
        int fakeCost = 3;
        Action fakeAction = mock(Action.class);
        when(fakeAction.cost()).thenReturn(fakeCost);
        test.payActionCost(fakeAction);
        assertTrue(test.actionPoints() == -fakeCost);
    }

    @Test
    public void constructor_setsUnitAlive() {
        Unit test = new StubUnit(null, fakeLeader());
        assertTrue(!test.dead());
    }

    @Test
    public void damage_killsTheUnit_whenPassedLethalDamage() {
        Unit test = new StubUnit(null, fakeLeader());
        test.damage(StubUnit.INITIAL_HEALTH);
        assertTrue(test.dead());
    }

    @Test
    public void damage_doesNotKillTheUnit_whenPassedLethalDamageMinusOne() {
        Unit test = new StubUnit(null, fakeLeader());
        test.damage(StubUnit.INITIAL_HEALTH - 1);
        assertTrue(!test.dead());
    }

    @Test
    public void damage_killsTheUnit_whenPassedLethalDamageInIncrements() {
        Unit test = new StubUnit(null, fakeLeader());
        test.damage(StubUnit.INITIAL_HEALTH - 1);
        test.damage(1);
        assertTrue(test.dead());
    }

    @Test
    public void heal_savesTheUnitFromDeath_whenCalledBetweenLethalDamageIncrements() {
        Unit test = new StubUnit(null, fakeLeader());
        test.damage(StubUnit.INITIAL_HEALTH - 1);
        test.heal(1);
        test.damage(1);
        assertTrue(!test.dead());
    }

    @Test
    public void heal_doesNothing_whenTheUnitIsAtMaxHealth() {
        Unit test = new StubUnit(null, fakeLeader());
        test.heal(StubUnit.INITIAL_HEALTH);
        test.damage(StubUnit.INITIAL_HEALTH);
        assertTrue(test.dead());
    }

    @Test
    public void health_returnsInitialHealth_initially() {
        Unit test = new StubUnit(null, fakeLeader());
        assertTrue(test.health() == test.initialHealth());
    }

    @Test
    public void actionPoints_returnsZero_initially() {
        Unit test = new StubUnit(null, fakeLeader());
        assertTrue(test.actionPoints() == 0);
    }

    @Test
    public void health_returnsInitialHealthMinusDamage_afterDamage() {
        Unit test = new StubUnit(null, fakeLeader());
        int damage = 3;
        int expected = test.initialHealth() - damage;
        test.damage(damage);
        assertTrue(test.health() == expected);
    }

    @Test
    public void passed_setsActionPointsToInitialPointsPlusOne_whenWithinRangeOfLeader() {
        Unit test = new StubUnit(null, fakeLeader());
        test.relocate(test.leader().location());

        int fakeCost = 3;
        Action fakeAction = mock(Action.class);
        when(fakeAction.cost()).thenReturn(fakeCost);
        test.payActionCost(fakeAction);

        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);

        test.passed(fakeBoard);

        assertTrue(test.actionPoints() == StubUnit.INITIAL_ACTION_POINTS + 1);
    }

    @Test
    public void passed_setsActionPointsToInitialPoints_whenNotWithinRangeOfLeader() {
        Unit test = new StubUnit(null, fakeLeader());
        test.relocate(test.leader().location());

        int fakeCost = 3;
        Action fakeAction = mock(Action.class);
        when(fakeAction.cost()).thenReturn(fakeCost);
        test.payActionCost(fakeAction);

        HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(10);

        test.passed(fakeBoard);

        assertTrue(test.actionPoints() == StubUnit.INITIAL_ACTION_POINTS);
    }

    public Leader fakeLeader() {
        Leader toReturn = mock(Leader.class);
        when(toReturn.location()).thenReturn(mock(Hex.class));
        when(toReturn.location().x()).thenReturn(0);
        when(toReturn.location().y()).thenReturn(0);
        when(toReturn.dead()).thenReturn(false);
        when(toReturn.upgrades()).thenReturn(mock(Upgrades.class));
        when(toReturn.upgrades().count(UpgradeTypes.MOVEMENT)).thenReturn(1);
        return toReturn;
    }

    public class StubUnit extends Unit {

        private static final int INITIAL_ACTION_POINTS = 5;
        private static final int INITIAL_HEALTH = 10;

        public StubUnit(Player creator, Leader leader) {
            super(creator, leader);
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
        public int initialActionPoints() {
            return INITIAL_ACTION_POINTS;
        }

        @Override
        protected int initialHealth() {
            return INITIAL_HEALTH;
        }
    }
}
