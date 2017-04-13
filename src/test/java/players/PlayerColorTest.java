/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package players;

import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nick Houser
 */
public class PlayerColorTest {

    @Test
    public void constructor_savesThePassedColor() {
        Color expectedColor = Color.ANTIQUEWHITE;
        String expectedString = "ihu83";
        PlayerColor test = new PlayerColor(expectedString, expectedColor);
        assertTrue(test.color().equals(expectedColor));
    }

    @Test
    public void constructor_savesThePassedName() {
        Color expectedColor = Color.ANTIQUEWHITE;
        String expectedString = "ihu83";
        PlayerColor test = new PlayerColor(expectedString, expectedColor);
        assertTrue(test.name().equals(expectedString));
    }

    @Test
    public void equals_returnsFalse_whenPassedANonPlayerColorObject() {
        Color expectedColor = Color.ANTIQUEWHITE;
        String expectedString = "ihu83";
        PlayerColor test = new PlayerColor(expectedString, expectedColor);
        assertFalse(test.equals(new Object()));
    }

    @Test
    public void equals_returnsFalse_whenPassedAPlayerColorOfDifferentColor() {
        Color expectedColor = Color.ANTIQUEWHITE;
        String expectedString = "ihu83";
        PlayerColor test = new PlayerColor(expectedString, expectedColor);
        Color otherColor = Color.AQUA;
        PlayerColor other = new PlayerColor(expectedString, otherColor);
        assertFalse(test.equals(other));
    }

    @Test
    public void equals_returnsTrue_whenPassedAPlayerColorOfSameColor() {
        Color expectedColor = Color.ANTIQUEWHITE;
        String expectedString = "ihu83";
        PlayerColor test = new PlayerColor(expectedString, expectedColor);
        String otherString = "984u";
        PlayerColor other = new PlayerColor(otherString, expectedColor);
        assertTrue(test.equals(other));
    }

    @Test
    public void hashCode_returnsSameValue_forTwoPlayersColorsOfSameColor() {
        Color expectedColor = Color.ANTIQUEWHITE;
        String expectedString = "ihu83";
        PlayerColor test = new PlayerColor(expectedString, expectedColor);
        String otherString = "984u";
        PlayerColor other = new PlayerColor(otherString, expectedColor);
        assertTrue(test.hashCode() == other.hashCode());
    }

    @Test
    public void hashCode_returnsDifferentValues_forTwoPlayerColorsOfDifferentColors() {
        Color expectedColor = Color.ANTIQUEWHITE;
        String expectedString = "ihu83";
        PlayerColor test = new PlayerColor(expectedString, expectedColor);
        Color otherColor = Color.AQUA;
        PlayerColor other = new PlayerColor(expectedString, otherColor);
        assertTrue(test.hashCode() != other.hashCode());
    }
}
