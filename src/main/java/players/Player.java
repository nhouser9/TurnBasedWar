package players;

import hexoccupants.pieces.Unit;
import java.util.Objects;
import javafx.scene.paint.Color;
import turnbasedwar.Settings;

/**
 * A Player in the game. Contains methods for tracking purchasing points and
 * returning a playerColor for UI representation.
 *
 * @author Nick Houser
 */
public class Player {

    //the playerColor used to represent this Player on the UI
    private final PlayerColor color;

    //the amount of purchase points remaining to this Player
    private int purchasePoints;

    //whether this player is finished purchasing Units
    private boolean finishedPurchasing;

    //the player's score
    private int score;

    /**
     * Protected constructor to ensure Players are created through a factory.
     * Saves a passed Color which will be used to represent this Player on the
     * UI.
     *
     * @param color the Color used to represent this Player on the UI
     */
    protected Player(PlayerColor color) {
        this.color = color;
        purchasePoints = Settings.PURCHASE_POINTS;
        finishedPurchasing = false;
        score = 0;
    }

    /**
     * Gets the Color used to represent this Player on the UI.
     *
     * @return the Color used to represent this Player on the UI
     */
    public PlayerColor playerColor() {
        return color;
    }

    /**
     * Returns the number of purchase points remaining to this Player.
     *
     * @return the number of purchase points remaining to this Player
     */
    public int purchasePoints() {
        return purchasePoints;
    }

    /**
     * Pays the purchase cost for a Unit by subtracting its cost from the
     * Player's purchase points.
     *
     * @param unit the Unit to purchase
     */
    public void purchase(Unit unit) {
        purchasePoints = purchasePoints - unit.cost();
    }

    /**
     * Checks whether this Player is finished purchasing Units. This is used to
     * determine whether to show the purchase menu or the action menu on the
     * main UI.
     *
     * @return true if this Player is done purchasing Units, false otherwise
     */
    public boolean finishedPurchasing() {
        return finishedPurchasing;
    }

    /**
     * Marks the Player as finished purchasing Units. This is used to determine
     * whether to show the purchase menu or the action menu on the main UI.
     */
    public void finishPurchasing() {
        finishedPurchasing = true;
    }

    /**
     * Gets the Player's score.
     *
     * @return the Player's score
     */
    public int score() {
        return score;
    }

    /**
     * Increments the Player's score.
     *
     * @param increment the amount to increase the score by
     */
    public void incrementScore(int increment) {
        score = score + increment;
    }

    /**
     * Equals method for Players, which only cares if they are the same Color.
     *
     * @param other the Object to compare
     * @return true if the Objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) {
            return false;
        }

        return (playerColor().equals(((Player) other).playerColor()));
    }

    /**
     * HashCode method for Players, which only cares about the Player's Color.
     *
     * @return a hash code based on the Player's Color
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.color);
        return hash;
    }

    /**
     * Override of the toString method, which returns a String representation of
     * the Object.
     *
     * @return a String representation of the Object
     */
    @Override
    public String toString() {
        return color.name();
    }
}
