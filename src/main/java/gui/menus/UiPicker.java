/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.menus;

import gui.ImageCache;
import gui.shapes.RadialCoordinateConversion;
import hex.Hex;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.image.ImageView;

/**
 *
 * @author Nick Houser
 */
public class UiPicker {

    private static final int RADIUS = 100;
    private static final int SIZE = 50;
    private static final int ANGLE = 35;

    private final List<ImageView> controls;

    public UiPicker(List<String> pickerIds, ImageCache cache, double xCenter, double yCenter, Hex hex) {
        controls = new LinkedList<>();
        int angle = initialAngle(hex.x(), hex.y()) + ANGLE;
        for (String id : pickerIds) {
            ImageView view = cache.image(id, SIZE);
            RadialCoordinateConversion coords = new RadialCoordinateConversion(angle, RADIUS);
            view.setX(xCenter + coords.x() - view.getFitWidth() / 2);
            view.setY(yCenter + coords.y() - view.getFitHeight() / 2);
            angle = angle + ANGLE;
            controls.add(view);
        }
    }

    private int initialAngle(int xHex, int yHex) {
        if (Math.abs(xHex) > Math.abs(yHex) / 2) {
            if (xHex > 0) {
                return 90;
            } else {
                return 270;
            }
        } else if (yHex > 0) {
            return 180;
        } else {
            return 0;
        }
    }

    public List<ImageView> controls() {
        return Collections.unmodifiableList(controls);
    }
}
