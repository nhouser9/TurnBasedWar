package hex;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javafx.util.Pair;

/**
 * A hexagonal grid of generic elements. Size in a HexGrid is defined in terms
 * of distance from the center; that is, N nodes are reached when starting at
 * the center of a HexGrid of size N and travelling in a straight line to the
 * edge in any direction, including the destination but not the origin.
 * Co-ordinates are measured from the center point which is defined to be (0,
 * 0). By convention two nodes on the grid with equal x co-ordinates must lie on
 * the same x axis and two nodes on the grid with equal y co-ordinates must lie
 * on the same y axis. This means that some co-ordinates do not exist in the
 * grid, for example, (0, 1). The nodes surrounding (0, 0), starting to the
 * north and proceeding clockwise, are: (0, 2), (1, 1), (1, -1), (0, -2), (-1,
 * -1), and (-1, 1).
 *
 * @param <T> the type of Object stored in this grid
 * @author Nick Houser
 */
public class HexGrid<T> {

    //the size of the grid
    private final int size;

    //arrays for the actual data stored in the grid
    private final AbstractMap<Pair<Integer, Integer>, T> data;

    /**
     * Constructor for the HexGrid sets the size of the grid to the passed
     * argument and initializes internal data structures.
     *
     * @param size the size of the grid
     */
    public HexGrid(int size) {
        this.size = size;
        data = new HashMap<>();

        for (int col = -size; col <= size; col++) {
            int rowMax = (2 * size) - Math.abs(col);
            for (int row = -rowMax; row <= rowMax; row = row + 2) {
                data.put(new Pair<>(col, row), null);
            }
        }
    }

    /**
     * Gets the size of the HexGrid. See class documentation for the specifics
     * of how size is defined.
     *
     * @return the size of the HexGrid
     */
    public int size() {
        return size;
    }

    /**
     * Sets the passed index to the passed element. Throws an error if passed
     * invalid indices.
     *
     * @param x the x index to set
     * @param y the y index to set
     * @param element the element to set
     */
    public void set(int x, int y, T element) {
        validateCoordinates(x, y);
        data.put(new Pair<>(x, y), element);
    }

    /**
     * Gets the element at the passed index. Throws an error if passed invalid
     * indices.
     *
     * @param x the x index to get
     * @param y the y index to get
     * @return the requested element
     */
    public T get(int x, int y) {
        validateCoordinates(x, y);
        return data.get(new Pair<>(x, y));
    }

    /**
     * Returns the unmodifiable set of nodes in this HexGrid.
     *
     * @return the unmodifiable set of nodes in this HexGrid
     */
    public Collection<T> all() {
        return Collections.unmodifiableCollection(data.values());
    }

    /**
     * Returns the unmodifiable set of valid co-ordinates for this HexGrid.
     *
     * @return the unmodifiable set of valid co-ordinates for this HexGrid
     */
    public Set<Pair<Integer, Integer>> coordinates() {
        return Collections.unmodifiableSet(data.keySet());
    }

    /**
     * Returns the distance between two nodes in the grid. Distance is defined
     * in terms of the minimum number of nodes that would need to be traversed
     * to reach the target node from the starting node, including the target
     * itself. Throws an error if either of the nodes are not in the grid.
     *
     * @param x1 the x co-ordinate of the first point to compare
     * @param y1 the y co-ordinate of the first point to compare
     * @param x2 the x co-ordinate of the second point to compare
     * @param y2 the y co-ordinate of the second point to compare
     * @return the distance between the two nodes in the grid
     */
    public int distance(int x1, int y1, int x2, int y2) {
        validateCoordinates(x1, y1);
        validateCoordinates(x2, y2);

        int deltaX = Math.abs(x1 - x2);
        int deltaY = Math.abs(y1 - y2);
        return Math.max(deltaX, (deltaX + deltaY) / 2);
    }

    /**
     * Checks whether the passed co-ordinates are valid. This consists of two
     * checks. The first is whether the co-ordinates are invalid. For example,
     * the co-ordinates (0, 1) never exist because of the conventions for
     * definition of rows and columns. The second is whether the co-ordinates
     * are within the bounds of the current HexGrid's size.
     *
     * @param x the x co-ordinate to check
     * @param y the y co-ordinate to check
     * @throws an Error if passed bad co-ordinates
     */
    private void validateCoordinates(int x, int y) {
        if (!data.keySet().contains(new Pair<>(x, y))) {
            String errorMessage = "The passed co-ordinates do not exist in the grid. They are either invalid (see javadoc) or out of bounds.";
            throw new HexGridError(errorMessage, size, x, y);
        }
    }
}
