package gui.shapes;

import hexoccupants.Constructs;
import javafx.scene.shape.Shape;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShapeFactoryTest {

    @Test
    public void create_returnsAnExtrudeHexagon_whenPassedARoad() {
        ShapeFactory test = new ShapeFactory();
        Shape result = test.create(Constructs.ROAD, 0, 0, 0, 0);
        assertTrue(result instanceof ExtrudeHexagon);
    }

    @Test
    public void create_returnsAnIndentHexagon_whenPassedAFortress() {
        ShapeFactory test = new ShapeFactory();
        Shape result = test.create(Constructs.FORT, 0, 0, 0, 0);
        assertTrue(result instanceof IndentHexagon);
    }

    @Test
    public void create_returnsAHexagon_whenPassedNone() {
        ShapeFactory test = new ShapeFactory();
        Shape result = test.create(Constructs.NONE, 0, 0, 0, 0);
        assertTrue(result instanceof Hexagon);
    }

    @Test
    public void create_returnsAHexagon_whenPassedACenterObjective() {
        ShapeFactory test = new ShapeFactory();
        Shape result = test.create(Constructs.OBJECTIVE_CENTER, 0, 0, 0, 0);
        assertTrue(result instanceof Hexagon);
    }

    @Test
    public void create_returnsAHexagon_whenPassedAnEdgeObjective() {
        ShapeFactory test = new ShapeFactory();
        Shape result = test.create(Constructs.OBJECTIVE_EDGE, 0, 0, 0, 0);
        assertTrue(result instanceof Hexagon);
    }
}
