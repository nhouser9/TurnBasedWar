package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.HasCost;
import hexoccupants.pieces.Unit;

/**
 * Abstract class representing an action that can be taken by a Unit. Will be
 * used to determine which buttons to show on the UI for each unit, and to
 * associate handlers with those buttons. Implements HasCost because the action
 * costs a certain amount of action points to execute.
 *
 * @author Nick Houser
 */
public abstract class Action implements HasCost {

    //the Unit which will undertake this action
    private final Unit actor;

    /**
     * Constructor which stores the Unit that will undergo this action.
     *
     * @param actor the Unit that will undergo this action
     */
    public Action(Unit actor) {
        this.actor = actor;
    }

    /**
     * Gets the Unit that will undergo this action.
     *
     * @return the Unit that will undergo this action
     */
    public final Unit actor() {
        return actor;
    }

    /**
     * If the action is allowd and the actor has enough action points, executes
     * the action and subtracts the action cost from the actor's action points.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @throws ActionNotAllowedError if the action is not currently legal
     */
    public final void act(HexGrid<Hex> board, Hex target) throws ActionNotAllowedError {
        if (!allowed(board, target)) {
            throw new ActionNotAllowedError();
        }

        action(board, target);
        actor.payActionCost(this);
    }

    /**
     * Abstract method which allows implementing classes to define the action.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     */
    protected abstract void action(HexGrid<Hex> board, Hex target);

    /**
     * Abstract method which allows implementing classes to define the id for
     * referencing the image for the UI element representing the Action.
     *
     * @return an id for referencing the UI image for the Action
     */
    public abstract String pickerId();

    /**
     * Method which defines whether an Action is allowed. Returns false if the
     * actor cannot afford the cost, but leaves the rest of the checks to be
     * defined by implementing classes.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @return true if the action is allowed on the current board state, false
     * otherwise
     */
    public boolean allowed(HexGrid<Hex> board, Hex target) {
        if (actor.actionPoints() < cost()) {
            return false;
        }

        return implementationAllowed(board, target);
    }

    /**
     * Abstract method which allows implementing classes to define whether the
     * action is currently allowed.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @return true if the action is allowed on the current board state, false
     * otherwise
     */
    protected abstract boolean implementationAllowed(HexGrid<Hex> board, Hex target);

    /**
     * Error thrown when trying to execute an action that is not legal.
     */
    public class ActionNotAllowedError extends Error {
    }
}
