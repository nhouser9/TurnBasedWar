package hexoccupants.pieces;

import hexoccupants.Constructs;
import hexoccupants.actions.Construct;
import players.Player;

/**
 * Unit which can move and construct Roads or Fortifications. The Unit can only
 * construct on the Hex it occupies.
 *
 * @author Nick Houser
 */
public class Builder extends Unit {

    /**
     * Constructor which simply calls the inherited constructor.
     *
     * @param creator the Player who created the Piece
     * @param leader the Leader of this Unit
     */
    protected Builder(Player creator, Leader leader) {
        super(creator, leader);
        addAction(new Construct(this, Constructs.NONE));
        addAction(new Construct(this, Constructs.FORT));
        addAction(new Construct(this, Constructs.ROAD));
    }

    /**
     * Defines the cost of a Builder Unit.
     *
     * @return the cost of a Builder Unit
     */
    @Override
    public int cost() {
        return 2;
    }

    /**
     * Gets the path of the image used to represent this Piece on the board.
     *
     * @return the path of the image used to represent this Piece on the board
     */
    @Override
    public String imagePath() {
        return "builder";
    }

    /**
     * The initial action points for a Builder Unit.
     *
     * @return the initial number of action points for this Unit
     */
    @Override
    protected int initialActionPoints() {
        return 3;
    }

    /**
     * The initial health for a Builder Unit.
     *
     * @return the initial amount of health for this Unit
     */
    @Override
    protected int initialHealth() {
        return 5;
    }
}
