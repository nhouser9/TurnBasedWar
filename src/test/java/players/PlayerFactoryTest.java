package players;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class PlayerFactoryTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void create_returnsPlayersWithUniqueColors_onFirstSixCalls() {
        PlayerFactory test = new PlayerFactory();
        Player players[] = new Player[6];
        for (int i = 0; i < 6; i++) {
            players[i] = test.create();
        }

        for (int i = 0; i < 6; i++) {
            assertTrue(players[i] != null);

            for (int j = 0; j < 6; j++) {
                if (i == j) {
                    continue;
                }

                assertTrue(!players[i].playerColor().equals(players[j].playerColor()));
            }
        }
    }

    @Test
    public void create_throwsError_onSeventhCall() {
        PlayerFactory test = new PlayerFactory();
        Player players[] = new Player[6];
        for (int i = 0; i < 6; i++) {
            players[i] = test.create();
        }

        expectedException.expect(PlayerFactory.TooManyPlayersError.class);
        test.create();
    }
}
