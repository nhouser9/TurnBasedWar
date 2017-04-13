package hexoccupants.pieces;

import hexoccupants.actions.MeleeAttack;
import players.Player;

/**
 * Unit which can only move and attack. The Unit can only attack adjacent Hexes
 * which it could move into.
 *
 * @author Nick Houser
 */
public class Melee extends Unit {

    /**
     * Constructor which simply calls the inherited constructor.
     *
     * @param creator the Player who created the Piece
     * @param leader the Leader of this Unit
     */
    protected Melee(Player creator, Leader leader) {
        super(creator, leader);
        addAction(new MeleeAttack(this));
    }

    /**
     * Defines the cost of a Melee Unit.
     *
     * @return the cost of a Melee Unit
     */
    @Override
    public int cost() {
        return 1;
    }

    /**
     * Gets the path of the image used to represent this Piece on the board.
     *
     * @return the path of the image used to represent this Piece on the board
     */
    @Override
    public String imagePath() {
        return "melee";
    }

    /**
     * The initial action points for a Melee Unit.
     *
     * @return the initial number of action points for this Unit
     */
    @Override
    protected int initialActionPoints() {
        return 2;
    }

    /**
     * The initial health for a Melee Unit.
     *
     * @return the initial amount of health for this Unit
     */
    @Override
    protected int initialHealth() {
        return 10;
    }
}
