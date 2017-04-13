package hexoccupants;

import hex.Hex;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import players.Player;

public class PieceTest {

    @Test
    public void relocate_setsTheLocation() {
        Piece test = new StubPiece();
        Hex fakeHex = mock(Hex.class);
        test.relocate(fakeHex);
        assertTrue(fakeHex == test.location());
    }

    public class StubPiece extends Piece {
    }
}
