package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Unit;

/**
 * Action that moves a Unit from its Hex to an adjacent Hex.
 *
 * @author Nick Houser
 */
public class Move extends Action {

    /**
     * Constructor which calls the inherited constructor to save the actor.
     *
     * @param actor the Unit undergoing this Action
     */
    public Move(Unit actor) {
        super(actor);
    }

    /**
     * The definition of a Move Action, which is moving a Unit from its Hex to
     * an ajacent Hex.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     */
    @Override
    protected void action(HexGrid<Hex> board, Hex target) {
        super.actor().location().setOccupant(null);
        target.setOccupant(super.actor());
        super.actor().relocate(target);
    }

    /**
     * The definition for when a Move Action is valid, which is only when the
     * Move targets an adjacent Hex.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @return true if the action is allowed on the current board state, false
     * otherwise
     */
    @Override
    protected boolean implementationAllowed(HexGrid<Hex> board, Hex target) {
        if (target.occupant() != null) {
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
