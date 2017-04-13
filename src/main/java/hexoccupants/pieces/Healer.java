package hexoccupants.pieces;

import hexoccupants.actions.Heal;
import players.Player;

/**
 * Unit which can only move and heal. The Unit can only heal adjacent Hexes
 * which it could move into.
 *
 * @author Nick Houser
 */
public class Healer extends Unit {

    /**
     * Constructor which simply calls the inherited constructor.
     *
     * @param creator the Player who created the Piece
     * @param leader the Leader of this Unit
     */
    protected Healer(Player creator, Leader leader) {
        super(creator, leader);
        addAction(new Heal(this));
    }

    /**
     * Defines the cost of a Healer Unit.
     *
     * @return the cost of a Healer Unit
     */
    @Override
    public int cost() {
        return 4;
    }

    /**
     * Gets the path of the image used to represent this Piece on the board.
     *
     * @return the path of the image used to represent this Piece on the board
     */
    @Override
    public String imagePath() {
        return "medic";
    }

    /**
     * The initial action points for a Healer Unit.
     *
     * @return the initial number of action points for this Unit
     */
    @Override
    protected int initialActionPoints() {
        return 1;
    }

    /**
     * The initial health for a Healer Unit.
     *
     * @return the initial amount of health for this Unit
     */
    @Override
    protected int initialHealth() {
        return 4;
    }
}
