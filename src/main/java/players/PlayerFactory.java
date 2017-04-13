package players;

import javafx.scene.paint.Color;

/**
 * Factory for Players, which handles setting up each new Player with a unique
 * Color.
 *
 * @author Nick Houser
 */
public class PlayerFactory {

    /**
     * The ordered list of Colors which will be assigned to new Players.
     */
    private static final PlayerColor[] COLORS = {
        new PlayerColor("Purple", Color.rgb(180, 20, 220)),
        new PlayerColor("Blue", Color.rgb(60, 160, 255)),
        new PlayerColor("Red", Color.rgb(255, 100, 100)),
        new PlayerColor("Orange", Color.ORANGE),
        new PlayerColor("Yellow", Color.YELLOW),
        new PlayerColor("Green", Color.GREEN)
    };

    //the number of Players that have been created so far
    private int count;

    /**
     * Constructor which initializes internal data.
     */
    public PlayerFactory() {
        count = 0;
    }

    /**
     * Factory creation method, which returns a new Player with a unique Color,
     * or throws an exception if that is not possible.
     *
     * @return a new Player with a unique Color
     */
    public Player create() {
        if (count >= COLORS.length) {
            throw new TooManyPlayersError();
        }

        Player toReturn = new Player(COLORS[count]);
        count = count + 1;
        return toReturn;
    }

    /**
     * Error thrown when the factory is asked to create more than 6 players.
     */
    public class TooManyPlayersError extends Error {
    }
}
