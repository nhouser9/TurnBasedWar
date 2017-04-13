package players;

import hexoccupants.pieces.Unit;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import turnbasedwar.Settings;

public class PlayerTest {

    @Test
    public void constructor_storesThePassedColor() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        Player test = new Player(fakeColor);
        assertTrue(test.playerColor() == fakeColor);
    }

    @Test
    public void finishedPurchasing_returnsFalse_initially() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        Player test = new Player(fakeColor);
        assertTrue(test.finishedPurchasing() == false);
    }

    @Test
    public void finishedPurchasing_returnsTrue_afterFinishPurchasingCalled() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        Player test = new Player(fakeColor);
        test.finishPurchasing();
        assertTrue(test.finishedPurchasing() == true);
    }

    @Test
    public void constructor_initializesPurchasePoints() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        Player test = new Player(fakeColor);
        assertTrue(test.purchasePoints() == Settings.PURCHASE_POINTS);
    }

    @Test
    public void purchase_subtractsTheCostOfThePassedUnitFromPurchasePoints() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        int fakeCost = 3;
        Unit fakeUnit = mock(Unit.class);
        when(fakeUnit.cost()).thenReturn(fakeCost);
        Player test = new Player(fakeColor);
        int expected = Settings.PURCHASE_POINTS - fakeCost;
        test.purchase(fakeUnit);
        assertTrue(test.purchasePoints() == expected);
    }

    @Test
    public void equals_returnsFalse_whenPassedANonPlayerObject() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        Player test = new Player(fakeColor);
        Object other = new Object();
        assertTrue(!test.equals(other));
    }

    @Test
    public void equals_returnsFalse_whenPassedAPlayerOfDifferentColor() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        when(fakeColor.color()).thenReturn(Color.BLUE);
        Player test = new Player(fakeColor);
        PlayerColor otherColor = mock(PlayerColor.class);
        when(fakeColor.color()).thenReturn(Color.ALICEBLUE);
        Player other = new Player(otherColor);
        assertTrue(!test.equals(other));
    }

    @Test
    public void equals_returnsTrue_whenPassedAPlayerOfSameColor() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        when(fakeColor.color()).thenReturn(Color.BLUE);
        Player test = new Player(fakeColor);
        Player other = new Player(fakeColor);
        assertTrue(test.equals(other));
    }

    @Test
    public void hashCode_returnsSameValue_forTwoPlayersOfSameColor() {
        PlayerColor fakeColor = mock(PlayerColor.class);
        when(fakeColor.color()).thenReturn(Color.BLUE);
        Player test = new Player(fakeColor);
        Player other = new Player(fakeColor);
        assertTrue(test.hashCode() == other.hashCode());
    }
}
