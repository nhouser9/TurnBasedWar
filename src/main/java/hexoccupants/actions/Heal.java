package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Unit;

/**
 *
 * Action that restores health to the unit on the Hex that is the target of the
 * action.
 *
 * @author Nick Houser
 */
public class Heal extends Action {

    /**
     * The amount of health a Heal restores to its target.
     */
    protected static final int HEALING = 5;

    /**
     * Constructor which calls the inherited constructor to save the actor.
     *
     * @param actor the Unit undergoing this Action
     */
    public Heal(Unit actor) {
        super(actor);
    }

    /**
     * The definition of a Heal Action, which is restoring health to the Unit on
     * the Hex that is the target of the Action.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     */
    @Override
    protected void action(HexGrid<Hex> board, Hex target) {
        target.occupant().heal(HEALING);
    }

    /**
     * The definition for when a Heal Action is valid, which is only when the
     * Heal targets an adjacent Hex that contains an enemy Unit.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @return true if the action is allowed on the current board state, false
     * otherwise
     */
    @Override
    protected boolean implementationAllowed(HexGrid<Hex> board, Hex target) {
        Unit ally = target.occupant();
        if (ally == null) {
            return false;
        }
        if (!ally.creator().equals(super.actor().creator())) {
            return false;
        }

        Hex source = super.actor().location();
        int distance = board.distance(source.x(), source.y(), target.x(), target.y());
        return (distance == 1);
    }

    /**
     * Abstract method which allows implementing classes to define the action
     * point cost of the Action.
     *
     * @return the action point cost of the Action
     */
    @Override
    public int cost() {
        return 1;
    }

    @Override
    public String pickerId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
