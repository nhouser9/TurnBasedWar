package hexoccupants.pieces;

import hexoccupants.actions.RangedAttack;
import players.Player;

/**
 * Unit which can only move and attack. The Unit can attack Hexes within 2 Hexes
 * of itself.
 *
 * @author Nick Houser
 */
public class Ranged extends Unit {

    /**
     * Constructor which simply calls the inherited constructor.
     *
     * @param creator the Player who created the Piece
     * @param leader the Leader of this Unit
     */
    protected Ranged(Player creator, Leader leader) {
        super(creator, leader);
        addAction(new RangedAttack(this));
    }

    /**
     * Defines the cost of a Ranged Unit.
     *
     * @return the cost of a Ranged Unit
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
        return "ranged";
    }

    /**
     * The initial action points for a Ranged Unit.
     *
     * @return the initial number of action points for this Unit
     */
    @Override
    protected int initialActionPoints() {
        return 2;
    }

    /**
     * The initial health for a Ranged Unit.
     *
     * @return the initial amount of health for this Unit
     */
    @Override
    protected int initialHealth() {
        return 8;
    }
}
