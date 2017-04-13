package hexoccupants.actions;

import hexoccupants.pieces.Unit;

/**
 *
 * Action that deals damage to the unit on the Hex that is the target of the
 * action.
 *
 * @author Nick Houser
 */
public class RangedAttack extends Attack {

    /**
     * Constructor which calls the inherited constructor to save the actor.
     *
     * @param actor the Unit undergoing this Action
     */
    public RangedAttack(Unit actor) {
        super(actor);
    }

    /**
     * Method which defines the damage a RangedAttack does to the attacker.
     *
     * @return the amount of damage the Attack should deal to the attacker
     */
    @Override
    protected int damageToSelf() {
        return 0;
    }

    /**
     * Method which defines the damage a RangedAttack does to the target.
     *
     * @return the amount of damage the Attack should deal to the target
     */
    @Override
    protected int damageToTarget() {
        return 3;
    }

    /**
     * Method which defines the range of a RangedAttack.
     *
     * @return the range of the Attack
     */
    @Override
    protected int range() {
        return 2;
    }
}
