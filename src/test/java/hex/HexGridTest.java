package hex;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import javafx.util.Pair;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.mock;

public class HexGridTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constructor_savesTheSize() {
        int expected = 4;
        HexGrid<FakeHex> test = new HexGrid<>(expected);
        assertTrue(test.size() == expected);
    }

    @Test
    public void get_throwsAnError_whenPassedBadCoordinates() {
        int size = 2;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        expectedException.expect(HexGridError.class);
        test.set(0, -3, new FakeHex());
    }

    @Test
    public void set_throwsAnError_whenPassedBadCoordinates() {
        int size = 3;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        expectedException.expect(HexGridError.class);
        test.get(1, 0);
    }

    @Test
    public void get_throwsAnError_whenPassedOutOfBoundsCoordinates() {
        int size = 2;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        expectedException.expect(HexGridError.class);
        test.set(-4, 2, new FakeHex());
    }

    @Test
    public void set_throwsAnError_whenPassedOutOfBoundsCoordinates() {
        int size = 1;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        expectedException.expect(HexGridError.class);
        test.get(-1, 3);
    }

    @Test
    public void get_returnsNull_initially() {
        int size = 1;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        assertTrue(test.get(0, 0) == null);
    }

    @Test
    public void set_setsThePassedElement_whenPassedValidCoordinates() {
        int size = 4;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        FakeHex expected = new FakeHex();
        int x = 1;
        int y = -5;
        test.set(x, y, expected);
        assertTrue(test.get(x, y) == expected);
    }

    @Test
    public void all_returnsAllNodes() {
        Consumer<FakeHex> fakeAction = (Consumer<FakeHex>) mock(Consumer.class);
        int size = 2;
        HexGrid<FakeHex> test = new HexGrid<>(size);

        FakeHex[] hexes = new FakeHex[7];
        for (int i = 0; i < hexes.length; i++) {
            hexes[i] = new FakeHex();
        }

        test.set(0, 0, hexes[0]);
        test.set(0, 2, hexes[1]);
        test.set(1, 1, hexes[2]);
        test.set(1, -1, hexes[3]);
        test.set(-1, 1, hexes[4]);
        test.set(-1, -1, hexes[5]);
        test.set(0, -2, hexes[6]);

        Collection<FakeHex> all = test.all();

        for (FakeHex hex : hexes) {
            assertTrue(all.contains(hex));
        }
    }

    @Test
    public void coordinates_returnsTheCoordinatesInTheGrid() {
        int size = 2;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        Set<Pair<Integer, Integer>> coords = test.coordinates();

        int expectedSize = 19;
        assertTrue(coords.size() == expectedSize);
        assertTrue(coords.contains(new Pair<>(0, 0)));

        assertTrue(coords.contains(new Pair<>(0, 2)));
        assertTrue(coords.contains(new Pair<>(1, 1)));
        assertTrue(coords.contains(new Pair<>(1, -1)));
        assertTrue(coords.contains(new Pair<>(0, -2)));
        assertTrue(coords.contains(new Pair<>(-1, -1)));
        assertTrue(coords.contains(new Pair<>(-1, 1)));

        assertTrue(coords.contains(new Pair<>(0, 4)));
        assertTrue(coords.contains(new Pair<>(1, 3)));
        assertTrue(coords.contains(new Pair<>(2, 2)));
        assertTrue(coords.contains(new Pair<>(2, 0)));
        assertTrue(coords.contains(new Pair<>(2, -2)));
        assertTrue(coords.contains(new Pair<>(1, -3)));
        assertTrue(coords.contains(new Pair<>(0, -4)));
        assertTrue(coords.contains(new Pair<>(-1, -3)));
        assertTrue(coords.contains(new Pair<>(-2, -2)));
        assertTrue(coords.contains(new Pair<>(-2, 0)));
        assertTrue(coords.contains(new Pair<>(-2, 2)));
        assertTrue(coords.contains(new Pair<>(-1, 3)));
    }

    @Test
    public void distance_returnsZero_whenPassedTheSameHexTwice() {
        int size = 2;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        int expected = 0;
        int actual = test.distance(1, 1, 1, 1);
        assertTrue(expected == actual);
    }

    @Test
    public void distance_returnsCorrectValue_whenPassedTwoDifferentHexes() {
        int size = 2;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        int expected = 2;
        int actual = test.distance(-1, -1, 1, 1);
        assertTrue(expected == actual);
    }

    @Test
    public void distance_throwsAnError_whenPassedAHexNotInTheGrid() {
        int size = 2;
        HexGrid<FakeHex> test = new HexGrid<>(size);
        expectedException.expect(HexGridError.class);
        test.distance(0, 1, 0, 0);
    }

    private class FakeHex {
    }
}
