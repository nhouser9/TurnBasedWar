/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.shapes;

import javafx.scene.shape.Polygon;

/**
 *
 * @author Nick Houser
 */
public class ExtrudeHexagon extends RelativePolygon {

    private static final int DEGREES_IN_CIRCLE = 360;
    private static final int DEGREES_IN_SIDE = 60;

    private static final double EXTRUSION_PROPORTION = 1.0 / 2.0;

    private final double preExtrude;
    private final double extrusionWidth;
    private final int extrusion;

    public ExtrudeHexagon(double xCenter, double yCenter, double radius, int extrusion) {
        extrusionWidth = radius * EXTRUSION_PROPORTION;
        preExtrude = (radius - extrusionWidth) / 2.0;
        this.extrusion = extrusion;

        addAbsolutePoint(xCenter - radius, yCenter);
        int angle = 60;
        for (int i = 0; i < 6; i++) {
            addSide(angle);
            angle = rotate(angle, -DEGREES_IN_SIDE);
        }
    }

    private void addSide(int initialAngle) {
        addRelativePoint(initialAngle, preExtrude);
        addRelativePoint(rotate(initialAngle, 90), extrusion);
        addRelativePoint(initialAngle, extrusionWidth);
        addRelativePoint(rotate(initialAngle, 270), extrusion);
        addRelativePoint(initialAngle, preExtrude);
    }

    private int rotate(int angle, int rotation) {
        return (angle + rotation + DEGREES_IN_CIRCLE) % DEGREES_IN_CIRCLE;
    }
}
