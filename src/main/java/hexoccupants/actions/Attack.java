package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Unit;

/**
 * Generic action that deals damage to both the unit executing the Action and
 * the unit on the Hex that is the target of the action. Allows implementing
 * classes to define how much damage.
 *
 * @author Nick Houser
 */
public abstract class Attack extends Action {

    /**
     * Constructor which calls the inherited constructor to save the actor.
     *
     * @param actor the Unit undergoing this Action
     */
    public Attack(Unit actor) {
        super(actor);
    }

    /**
     * The definition of a MeleeAttack Action, which is dealing some damage to
     * the attacker (the Unit executing the Action) and more damage to the
     * defender (the Unit on the Hex that is the target of the Action).
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     */
    @Override
    protected void action(HexGrid<Hex> board, Hex target) {
        super.actor().damage(damageToSelf());

        if (super.actor().dead()) {
            super.actor().location().setOccupant(null);
        }

        DamageCalculation damage = new DamageCalculation(board, actor(), target, damageToTarget());
        target.occupant().damage(damage.value());

        if (target.occupant().dead()) {
            target.setOccupant(null);
        }
    }

    /**
     * The definition for when a MeleeAttack Action is valid, which is only when
     * the MeleeAttack targets an adjacent Hex that contains an enemy Unit.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @return true if the action is allowed on the current board state, false
     * otherwise
     */
    @Override
    protected boolean implementationAllowed(HexGrid<Hex> board, Hex target) {
        Unit enemy = target.occupant();
        if (enemy == null) {
            return false;
        }
        if (enemy.creator().equals(super.actor().creator())) {
            return false;
        }

        Hex source = super.actor().location();
        int distance = board.distance(source.x(), source.y(), target.x(), target.y());

        return (distance > 0 && distance <= range());
    }

    /**
     * Abstract method which allows implementing classes to define the action
     * point cost of the Action.
     *
     * @return the action point cost of the Action
     */
    @Override
    public int cost() {
        return 2;
    }

    /**
     * Abstract method which allows implementing classes to define the range of
     * an Attack.
     *
     * @return the range of the Attack
     */
    protected abstract int range();

    /**
     * Abstact method which allows implementing classes to define the damage an
     * Attack does to the attacker.
     *
     * @return the amount of damage the Attack should deal to the attacker
     */
    protected abstract int damageToSelf();

    /**
     * Abstract method which allows implementing classes to define the damage an
     * Attack does to the target.
     *
     * @return the amount of damage the Attack should deal to the target
     */
    protected abstract int damageToTarget();

    @Override
    public String pickerId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
