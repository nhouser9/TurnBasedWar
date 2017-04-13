package hexoccupants;

import hex.Hex;

/**
 * A Piece that can reside on the HexGrid. Includes methods for tracking which
 * Player created the Piece and tracking the Piece's location.
 *
 * @author Nick Houser
 */
public abstract class Piece {

    //the Hex location of this Piece
    private Hex location;

    /**
     * Returns the Hex this Piece currently occupies.
     *
     * @return the Hex this Piece currently occupies
     */
    public Hex location() {
        return location;
    }

    /**
     * Updates the Hex this Piece currently occupies.
     *
     * @param location the new Hex for this piece
     */
    public void relocate(Hex location) {
        this.location = location;
    }
}
