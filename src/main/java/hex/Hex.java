package hex;

import hexoccupants.Constructs;
import hexoccupants.pieces.Unit;

/**
 * A single hexagonal location on the board. A Hex can contain up to one Unit
 * and up to one Construct.
 *
 * @author Nick Houser
 */
public class Hex {

    //the Construct on this Hex
    private Constructs construct;

    //the Unit on this Hex
    private Unit piece;

    //position variables
    private final int xPosition;
    private final int yPosition;

    public Hex(HexGrid<Hex> grid, int x, int y) {
        xPosition = x;
        yPosition = y;
        grid.set(x, y, this);
        construct = Constructs.NONE;
    }

    /**
     * Gets the x co-ordinate of this Hex in its HexGrid.
     *
     * @return the x co-ordinate of this Hex in its HexGrid
     */
    public int x() {
        return xPosition;
    }

    /**
     * Gets the y co-ordinate of this Hex in its HexGrid.
     *
     * @return the y co-ordinate of this Hex in its HexGrid
     */
    public int y() {
        return yPosition;
    }

    /**
     * Gets the Construct occupying this Hex.
     *
     * @return the Construct on this Hex, or Constructs.NONE if none exists
     */
    public Constructs construct() {
        return construct;
    }

    /**
     * Sets the Construct occupying this Hex.
     *
     * @param construct the Construct which is being built on this Hex
     */
    public void setConstruct(Constructs construct) {
        this.construct = construct;
    }

    /**
     * Gets the Unit occupying this Hex.
     *
     * @return the Unit on this Hex, or null if none exists
     */
    public Unit occupant() {
        return piece;
    }

    /**
     * Sets the Unit occupying this Hex.
     *
     * @param occupant the Unit which is moving into this Hex
     */
    public void setOccupant(Unit occupant) {
        piece = occupant;
    }
}
