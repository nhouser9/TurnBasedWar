package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.Constructs;
import hexoccupants.pieces.Leader;
import hexoccupants.pieces.Unit;
import hexoccupants.pieces.UpgradeTypes;

/**
 * Damage calculation for an attack, which takes into account base damage,
 * whether the defender is in a Fortification, and whether any Leaders nearby
 * are providing buffs to either the attacker or the defender.
 *
 * @author Nick Houser
 */
public class DamageCalculation {

    //the result of the calculation
    private int value;

    /**
     * Constructor which calculates the amount of damage dealt in an attack.
     *
     * @param board the board on which the attack will take place
     * @param target the Hex that is under attack
     * @param base the amount of base damage
     * @param attacker the leader of the attacker
     */
    public DamageCalculation(HexGrid<Hex> board, Unit attacker, Hex target, int base) {
        value = base;

        if (target.construct() == Constructs.FORT) {
            value = value - 1;
        }

        Leader attackLeader = attacker.leader();
        if (attackLeader != null) {
            int attackDistance = board.distance(
                    attacker.location().x(),
                    attacker.location().y(),
                    attackLeader.location().x(),
                    attackLeader.location().y());
            if (attackDistance <= Leader.RANGE) {
                value = value + attackLeader.upgrades().count(UpgradeTypes.OFFENSE);
            }
        }

        Leader defenseLeader = target.occupant().leader();
        if (defenseLeader != null) {
            int defenseDistance = board.distance(
                    target.x(),
                    target.y(),
                    defenseLeader.location().x(),
                    defenseLeader.location().y());
            if (defenseDistance <= Leader.RANGE) {
                value = value - defenseLeader.upgrades().count(UpgradeTypes.DEFENSE);
            }
        }
    }

    /**
     * Gets the amount of damage dealt by the attack.
     *
     * @return the amount of damage dealt by the attack
     */
    public int value() {
        return value;
    }
}
