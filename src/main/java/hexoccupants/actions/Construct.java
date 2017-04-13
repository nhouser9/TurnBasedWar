package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.Constructs;
import hexoccupants.pieces.Unit;

/**
 * Action that builds a Construct on the Hex that the actor occupies.
 *
 * @author Nick Houser
 */
public class Construct extends Action {

    //the type of construct to create
    private final Constructs type;

    /**
     * Constructor which calls the inherited constructor to save the actor
     * before saving the type of construct to create.
     *
     * @param actor the Unit undergoing this Action
     * @param type the type of Construct to create
     */
    public Construct(Unit actor, Constructs type) {
        super(actor);
        this.type = type;
    }

    /**
     * The definition of a Construct Action, which is building the specified
     * type of Construct on the occupied Hex.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     */
    @Override
    protected void action(HexGrid<Hex> board, Hex target) {
        target.setConstruct(type);
    }

    /**
     * The definition for when a Heal Action is valid, which is only when the
     * Construct targets the Hex occupied by the actor and there is no
     * preexisting Construct on the Hex.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @return true if the action is allowed on the current board state, false
     * otherwise
     */
    @Override
    protected boolean implementationAllowed(HexGrid<Hex> board, Hex target) {
        if (target.construct() == Constructs.OBJECTIVE_CENTER) {
            return false;
        }

        if (target.construct() == Constructs.OBJECTIVE_EDGE) {
            return false;
        }

        if (type == Constructs.NONE) {
            if (target.construct() == Constructs.NONE) {
                return false;
            }
        } else if (target.construct() != Constructs.NONE) {
            return false;
        }

        return (super.actor() == target.occupant());
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
     * Gets the image id needed to get the image for this Upgrade's button on
     * the UI.
     *
     * @return the image id for this Upgrade's button
     */
    @Override
    public String pickerId() {
        return type.id();
    }
}
