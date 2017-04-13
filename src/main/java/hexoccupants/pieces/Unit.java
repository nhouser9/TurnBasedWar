package hexoccupants.pieces;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.HasCost;
import players.Player;
import hexoccupants.Piece;
import hexoccupants.actions.Action;
import hexoccupants.actions.Move;
import hexoccupants.actions.RoadMove;
import java.util.LinkedList;
import java.util.List;

/**
 * Extension of Piece which represents a type of Piece that can take actions.
 * Contains methods which allow implementers to define actions and methods to
 * control a point system used for limiting how many actions a Unit can take in
 * a turn. Implements HasCost because a Unit must be purchases for its cost by a
 * Player at the start of the game.
 *
 * @author Nick Houser
 */
public abstract class Unit extends Piece implements HasCost {

    //the player that created this Piece
    private final Player creator;

    //the list of valid actions for this Unit
    private final List<Action> actions;

    //the Leader of this Unit
    private final Leader leader;

    //the number of action points remaining to the Unit this turn
    private int actionPoints;

    //the health of the Unit
    private int health;

    /**
     * Constructor which calls super() before initializing the action points and
     * actions list. The constructor is protected to ensure all Units are
     * created through a Unit factory.
     *
     * @param creator the Player who created the Piece
     * @param leader the Leader of the Piece
     */
    protected Unit(Player creator, Leader leader) {
        super();
        this.creator = creator;
        this.leader = leader;
        health = initialHealth();
        actionPoints = 0;
        actions = new LinkedList<>();
        actions.add(new RoadMove(this));
        actions.add(new Move(this));
    }

    /**
     * Returns the Player who created the Piece.
     *
     * @return the Player who created the Piece
     */
    public Player creator() {
        return creator;
    }

    /**
     * Returns the Leader of the Piece.
     *
     * @return the Leader of the Piece
     */
    public Leader leader() {
        if (leader == null || leader.dead()) {
            return null;
        }
        return leader;
    }

    /**
     * Gets a list of Actions that can be undertaken by this Unit.
     *
     * @return a list of valid Actions for this Unit
     */
    public List<Action> actions() {
        return actions;
    }

    /**
     * Gets the number of action points available for this Unit's actions this
     * turn. A Unit cannot undertake any action whose cost is greater than the
     * Unit's remaining action points.
     *
     * @return the number of action points remaining to the Unit this turn
     */
    public int actionPoints() {
        return actionPoints;
    }

    /**
     * Pays for the Action by reducing the remaining action points by the cost
     * of the passed Action.
     *
     * @param action the Action being paid for
     */
    public void payActionCost(Action action) {
        actionPoints = actionPoints - action.cost();
    }

    /**
     * Deals damage to the Unit by subtracting from its health.
     *
     * @param damage the damage to be dealt to the Unit
     */
    public void damage(int damage) {
        health = health - damage;
    }

    /**
     * Heals the Unit by adding to its health. Does not set the health over the
     * initial health.
     *
     * @param restore the amount of health to restore
     */
    public void heal(int restore) {
        health = health + restore;
        health = Math.min(health, initialHealth());
    }

    /**
     * Returns the health of the Unit.
     *
     * @return the health of the Unit.
     */
    public int health() {
        return health;
    }

    /**
     * Checks whether the Unit has been dealt lethal damage.
     *
     * @return true if the Unit has been dealt lethal damage, false otherwise
     */
    public boolean dead() {
        return (health <= 0);
    }

    /**
     * Method which allows implementing classes to add to the list of valid
     * Actions.
     *
     * @param action the Action to add
     */
    protected final void addAction(Action action) {
        actions.add(action);
    }

    /**
     * The initial action points for implementations of Unit, which the
     * implementing classes must provide.
     *
     * @return the initial number of action points for this Unit
     */
    protected abstract int initialActionPoints();

    /**
     * The initial health for implementations of Unit, which the implementing
     * classes must provide.
     *
     * @return the initial amount of health for this Unit
     */
    protected abstract int initialHealth();

    /**
     * Gets the path of the image used to represent this Unit on the board.
     *
     * @return the path of the image used to represent this Unit on the board
     */
    public abstract String imagePath();

    /**
     * Method which handles end of turn actions. Default is to simply reset the
     * Unit's action points, but implementing classes can add further
     * functionality.
     *
     * @param hexes the list of Hexes on the board
     */
    public void passed(HexGrid<Hex> hexes) {
        actionPoints = initialActionPoints();
        Leader lead = leader();
        if (lead == null) {
            return;
        }
        int distance = hexes.distance(lead.location().x(), lead.location().y(), location().x(), location().y());
        if (distance < Leader.RANGE && distance != 0 && leader.upgrades().count(UpgradeTypes.MOVEMENT) > 0) {
            actionPoints = actionPoints + 1;
        }
    }

    /**
     * Override of the toString method, which returns a String representation of
     * the Object.
     *
     * @return a String representation of the Object
     */
    @Override
    public String toString() {
        return creator().toString() + " " + getClass().getSimpleName();
    }
}
