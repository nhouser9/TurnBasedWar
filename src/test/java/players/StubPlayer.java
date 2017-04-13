package players;

import javafx.scene.paint.Color;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StubPlayer {

    public static Player create(Color color) {
        Player fakePlayer = mock(Player.class);
        PlayerColor fakeColor = mock(PlayerColor.class);
        when(fakeColor.color()).thenReturn(color);
        when(fakePlayer.playerColor()).thenReturn(fakeColor);
        return fakePlayer;
    }
}
