package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.Constructs;
import hexoccupants.pieces.Unit;

/**
 * Action that moves a Unit from its Hex to an adjacent Hex along a road.
 * Extends a regular Move because many of the behaviors of the two are the same.
 *
 * @author Nick Houser
 */
public class RoadMove extends Move {

    /**
     * Constructor which calls the inherited constructor to save the actor.
     *
     * @param actor the Unit undergoing this Action
     */
    public RoadMove(Unit actor) {
        super(actor);
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
        if (super.actor().location().construct() != Constructs.ROAD) {
            return false;
        }

        if (target.construct() != Constructs.ROAD) {
            return false;
        }

        return super.implementationAllowed(board, target);
    }

    /**
     * Abstract method which allows implementing classes to define the action
     * point cost of the Action.
     *
     * @return the action point cost of the Action
     */
    @Override
    public int cost() {
        return 0;
    }
}
