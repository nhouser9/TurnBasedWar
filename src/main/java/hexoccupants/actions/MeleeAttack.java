package hexoccupants.actions;

import hexoccupants.pieces.Unit;

/**
 * Action that deals damage to both the unit executing the Action and the unit
 * on the Hex that is the target of the action. More damage is dealt to the
 * target than to the attacker.
 *
 * @author Nick Houser
 */
public class MeleeAttack extends Attack {

    /**
     * Constructor which calls the inherited constructor to save the actor.
     *
     * @param actor the Unit undergoing this Action
     */
    public MeleeAttack(Unit actor) {
        super(actor);
    }

    /**
     * Method which defines the damage a MeleeAttack does to the attacker.
     *
     * @return the amount of damage the Attack should deal to the attacker
     */
    @Override
    protected int damageToSelf() {
        return 2;
    }

    /**
     * Method which defines the damage a MeleeAttack does to the target.
     *
     * @return the amount of damage the Attack should deal to the target
     */
    @Override
    protected int damageToTarget() {
        return 4;
    }

    /**
     * Method which defines the range of a MeleeAttack.
     *
     * @return the range of the Attack
     */
    @Override
    protected int range() {
        return 1;
    }
}
