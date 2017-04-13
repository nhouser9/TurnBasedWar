package turnbasedwar;

/**
 * Class containing constants for various game settings.
 *
 * @author Nick Houser
 */
public class Settings {

    /**
     * Private constructor to prevent instantiation.
     */
    private Settings() {
    }

    /**
     * The size of the initial HexGrid.
     */
    public static final int BOARD_SIZE = 14;

    /**
     * The amount that the edge objectives are offset from the edge of the
     * board.
     */
    public static final int OBJECTIVE_OFFSET = 2;

    /**
     * The size delta of a single Hex when the board view zooms in or out.
     */
    public static final int ZOOM_INCREMENT = 5;

    /**
     * The initial zoom level of the board view.
     */
    public static final int INITIAL_ZOOM = 3;

    /**
     * The initial number of purchase points for each Player.
     */
    public static final int PURCHASE_POINTS = 20;

    /**
     * The initial number of players;
     */
    public static final int NUM_PLAYERS = 2;

    /**
     * The range within which new Units may be created with respect to their
     * Leader.
     */
    public static final int RANGE_CREATION = 4;
}
