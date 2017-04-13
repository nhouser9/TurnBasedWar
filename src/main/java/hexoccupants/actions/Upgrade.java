package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.pieces.Leader;
import hexoccupants.pieces.UpgradeTypes;

/**
 * Action that upgrades a Leader to add an additional ability.
 *
 * @author Nick Houser
 */
public class Upgrade extends Action {

    //the upgrade level, which is used to ensure that upgrades are applied in the right order
    private final int level;

    //the upgrade type of the upgrade
    private final UpgradeTypes type;

    /**
     * Constructor which narrows the scope of the super() constructor to enforce
     * that only Leaders can upgrade. Calls super() to save the actor.
     *
     * @param actor the Unit that will undergo this action
     * @param level the upgrade level
     * @param type the upgrade type
     */
    public Upgrade(Leader actor, int level, UpgradeTypes type) {
        super(actor);
        this.level = level;
        this.type = type;
    }

    /**
     * The definition of an Upgrade Action, which is adding an upgrade to the
     * Leader that is enacting the Action.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     */
    @Override
    protected void action(HexGrid<Hex> board, Hex target) {
        Leader actor = (Leader) super.actor();
        actor.upgrades().upgrade(type);
    }

    /**
     * The definition for when an Upgrade Action is valid, which is only when
     * the Upgrade targets the Hex occupied by the actor.
     *
     * @param board the board on which the action will take place
     * @param target the target Hex for the action
     * @return true if the action is allowed on the current board state, false
     * otherwises
     */
    @Override
    protected boolean implementationAllowed(HexGrid<Hex> board, Hex target) {
        Leader actor = (Leader) super.actor();

        if (level >= actor.level()) {
            return false;
        }

        if (actor.upgrades().level() != level) {
            return false;
        }

        return (actor == target.occupant());
    }

    /**
     * Abstract method which allows implementing classes to define the action
     * point cost of the Action.
     *
     * @return the action point cost of the Action
     */
    @Override
    public int cost() {
        return 3;
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
