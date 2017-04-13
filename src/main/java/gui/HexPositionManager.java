package gui;

import gui.shapes.Hexagon;

/**
 * An object which manages the position of hexes on the the game board. Also
 * allows zooming in and out.
 *
 * @author Nick Houser
 */
public class HexPositionManager {

    //the size of the hex grid being displayed
    private final int size;

    //how much the radius changes each zoom
    private final int increment;

    //the minimum zoom level
    private final int minimum;

    //the radius of a hex at the current zoom level
    private int zoom;

    //a reference hexagon used for sizing and positioning
    private Hexagon reference;

    /**
     * Constructor which sets the grid size, zoom increment, and minimum zoom.
     *
     * @param size the size of the hex grid being managed
     * @param increment the zoom increment
     * @param minimum the minimum zoom
     */
    public HexPositionManager(int size, int increment, int minimum) {
        this.size = size;
        this.increment = increment;
        this.minimum = minimum;
        zoom = minimum;
        updateReferenceHexagon();
    }

    /**
     * Zooms in, making all game objects on the board bigger.
     */
    public void zoomIn() {
        zoom = zoom + 1;
        updateReferenceHexagon();
    }

    /**
     * Zooms out, making all game objects on the board smaller.
     */
    public void zoomOut() {
        zoom = zoom - 1;
        zoom = Math.max(zoom, minimum);
        updateReferenceHexagon();
    }

    /**
     * Returns the center of the hex at the passed co-ordinates.
     *
     * @param x the x co-ordinate to return the center of
     * @return the x co-ordinate of the center of the hex at the passed
     * co-ordinates
     */
    public double xCenter(int x) {
        double hexOffset = radius() + reference.shortSide();
        return x * (hexOffset + (Math.sqrt(3) * zoom));
    }

    /**
     * Returns the center of the hex at the passed co-ordinates.
     *
     * @param y the y co-ordinate to return the center of
     * @return the x co-ordinate of the center of the hex at the passed
     * co-ordinates
     */
    public double yCenter(int y) {
        double hexOffset = reference.longSide();
        return y * (hexOffset + zoom);
    }

    /**
     * Returns the radius of a Hex at the current zoom level.
     *
     * @return the radius of a hex at the current zoom level
     */
    public double radius() {
        return reference.radius();
    }

    /**
     * Returns the vertical gap between two Hexes at the current zoom level.
     *
     * @return the vertical gap between two Hexes at the current zoom level.
     */
    public int gap() {
        return zoom;
    }

    /**
     * Gets the minimum x co-ordinate that should be rendered, which is useful
     * for offsetting co-ordinates so that the Grid is actually anchored in the
     * top left.
     *
     * @return the minimum x co-ordinate that should be rendered
     */
    public double xOffset() {
        double hexOffset = size * (2 * reference.radius() - reference.shortSide());
        double gapOffset = Math.sqrt(3) * size * gap();
        double originOffset = reference.radius();
        return hexOffset + gapOffset + originOffset;
    }

    /**
     * Gets the minimum y co-ordinate that should be rendered, which is useful
     * for offsetting co-ordinates so that the Grid is actually anchored in the
     * top left.
     *
     * @return the minimum y co-ordinate that should be rendered
     */
    public double yOffset() {
        double hexOffset = size * 2 * reference.longSide();
        double gapOffset = size * 2 * gap();
        double originOffset = reference.longSide();
        return hexOffset + gapOffset + originOffset;
    }

    /**
     * Updates the reference hexagon to a hexagon whose size is based on the new
     * zoom.
     */
    private void updateReferenceHexagon() {
        reference = new Hexagon(0, 0, increment * zoom);
    }
}
