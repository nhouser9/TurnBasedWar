package gui.shapes;

import java.util.List;
import javafx.scene.shape.Polygon;

public class RelativePolygon extends Polygon {

    private double lastX;
    private double lastY;

    private List<Double> points;

    public RelativePolygon() {
        points = getPoints();
    }

    protected final void addRelativePoint(double xOffset, double yOffset) {
        points.add(lastX + xOffset);
        lastX = lastX + xOffset;
        points.add(lastY + yOffset);
        lastY = lastY + yOffset;
    }

    protected final void addRelativePoint(int angle, double length) {
        RadialCoordinateConversion conversion = new RadialCoordinateConversion(angle, length);
        addRelativePoint(conversion.x(), conversion.y());
    }

    protected final void addAbsolutePoint(double xAbsolute, double yAbsolute) {
        points.add(xAbsolute);
        lastX = xAbsolute;
        points.add(yAbsolute);
        lastY = yAbsolute;
    }
}
