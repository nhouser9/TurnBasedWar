package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.Constructs;
import hexoccupants.pieces.Leader;
import hexoccupants.pieces.Unit;
import hexoccupants.pieces.UpgradeTypes;
import hexoccupants.pieces.Upgrades;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DamageCalculationTest {

    private Hex fakeTarget;
    private Unit fakeAttacker;
    private HexGrid<Hex> fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
    private Upgrades fakeDefenseUpgrades;
    private Upgrades fakeAttackUpgrades;

    @Before
    public void beforeTest() {
        fakeTarget = mock(Hex.class);
        when(fakeTarget.occupant()).thenReturn(mock(Unit.class));
        when(fakeTarget.occupant().leader()).thenReturn(mock(Leader.class));
        fakeDefenseUpgrades = mock(Upgrades.class);
        when(fakeTarget.occupant().leader().upgrades()).thenReturn(fakeDefenseUpgrades);
        when(fakeTarget.x()).thenReturn(0);
        when(fakeTarget.y()).thenReturn(0);
        when(fakeTarget.occupant().leader().location()).thenReturn(mock(Hex.class));
        when(fakeTarget.occupant().leader().location().x()).thenReturn(0);
        when(fakeTarget.occupant().leader().location().y()).thenReturn(0);

        fakeAttacker = mock(Unit.class);
        when(fakeAttacker.leader()).thenReturn(mock(Leader.class));
        fakeAttackUpgrades = mock(Upgrades.class);
        when(fakeAttacker.leader().upgrades()).thenReturn(fakeAttackUpgrades);
        when(fakeAttacker.location()).thenReturn(mock(Hex.class));
        when(fakeAttacker.location().x()).thenReturn(0);
        when(fakeAttacker.location().y()).thenReturn(0);
        when(fakeAttacker.leader().location()).thenReturn(mock(Hex.class));
        when(fakeAttacker.leader().location().x()).thenReturn(0);
        when(fakeAttacker.leader().location().y()).thenReturn(0);
    }

    @Test
    public void value_returnsBaseDamage_whenNoModifiersExist() {
        int baseDamage = 5;
        when(fakeTarget.construct()).thenReturn(Constructs.NONE);
        when(fakeAttackUpgrades.count(UpgradeTypes.OFFENSE)).thenReturn(0);
        when(fakeDefenseUpgrades.count(UpgradeTypes.DEFENSE)).thenReturn(0);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        DamageCalculation test = new DamageCalculation(fakeBoard, fakeAttacker, fakeTarget, baseDamage);
        assertTrue(test.value() == baseDamage);
    }

    @Test
    public void value_returnsBaseDamage_whenLeadersHaveModifiersButNotInRange() {
        int baseDamage = 5;
        when(fakeTarget.construct()).thenReturn(Constructs.NONE);
        when(fakeAttackUpgrades.count(UpgradeTypes.OFFENSE)).thenReturn(1);
        when(fakeDefenseUpgrades.count(UpgradeTypes.DEFENSE)).thenReturn(1);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(Leader.RANGE + 1);
        DamageCalculation test = new DamageCalculation(fakeBoard, fakeAttacker, fakeTarget, baseDamage);
        assertTrue(test.value() == baseDamage);
    }

    @Test
    public void value_adjustsValue_whenTargetIsFortified() {
        int baseDamage = 5;
        when(fakeTarget.construct()).thenReturn(Constructs.FORT);
        when(fakeAttackUpgrades.count(UpgradeTypes.OFFENSE)).thenReturn(0);
        when(fakeDefenseUpgrades.count(UpgradeTypes.DEFENSE)).thenReturn(0);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        DamageCalculation test = new DamageCalculation(fakeBoard, fakeAttacker, fakeTarget, baseDamage);
        assertTrue(test.value() == baseDamage - 1);
    }

    @Test
    public void value_adjustsValue_whenAttackingLeaderHasModifierAndInRange() {
        int baseDamage = 5;
        when(fakeTarget.construct()).thenReturn(Constructs.NONE);
        when(fakeAttackUpgrades.count(UpgradeTypes.OFFENSE)).thenReturn(1);
        when(fakeDefenseUpgrades.count(UpgradeTypes.DEFENSE)).thenReturn(0);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        DamageCalculation test = new DamageCalculation(fakeBoard, fakeAttacker, fakeTarget, baseDamage);
        assertTrue(test.value() == baseDamage + 1);
    }

    @Test
    public void value_adjustsValue_whenDefendingLeaderHasModifierAndInRange() {
        int baseDamage = 5;
        when(fakeTarget.construct()).thenReturn(Constructs.NONE);
        when(fakeAttackUpgrades.count(UpgradeTypes.OFFENSE)).thenReturn(0);
        when(fakeDefenseUpgrades.count(UpgradeTypes.DEFENSE)).thenReturn(1);
        when(fakeBoard.distance(any(int.class), any(int.class), any(int.class), any(int.class))).thenReturn(1);
        DamageCalculation test = new DamageCalculation(fakeBoard, fakeAttacker, fakeTarget, baseDamage);
        assertTrue(test.value() == baseDamage - 1);
    }
}
