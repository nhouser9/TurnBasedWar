package hex;

/**
 * Custom runtime error to be thrown when bad co-ordinates are passed to a
 * HexGrid. This can happen when it is passed totally invalid co-ordinates or
 * when it is passed co-ordinates which would be valid but are out of the range
 * of the current HexGrid's size. See HexGrid documentation for formal
 * definition of the co-ordinate system used to describe a HexGrid.
 *
 * @author Nick Houser
 */
public class HexGridError extends Error {

    /**
     * The size of the HexGrid that threw this.
     */
    public final int size;

    /**
     * The bad x co-ordinate that was passed.
     */
    public final int x;

    /**
     * The bad y co-ordinate that was passed.
     */
    public final int y;

    /**
     * Constructor which calls super() with a passed message before storing some
     * info about the bad call that generated the error.
     *
     * @param message the error message
     * @param size the size of the HexGrid which recieved the bad call
     * @param x the bad x co-ordinate that was passed
     * @param y the bad y co-ordinate that was passed
     */
    public HexGridError(String message, int size, int x, int y) {
        super(message);
        this.size = size;
        this.x = x;
        this.y = y;
    }
}
