/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.menus.UiPicker;
import gui.controllers.GameController;
import gui.events.*;
import gui.shapes.ShapeFactory;
import hex.Hex;
import hex.HexGrid;
import hexoccupants.Constructs;
import hexoccupants.actions.Action;
import hexoccupants.pieces.Builder;
import hexoccupants.pieces.Healer;
import hexoccupants.pieces.Melee;
import hexoccupants.pieces.Ranged;
import hexoccupants.pieces.Unit;
import hexoccupants.pieces.UnitFactory;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import turnbasedwar.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 *
 * @author Nick Houser
 */
public class Board extends Pane {

    //the amount to scale images in relation to the Hex radius
    private static final double IMAGE_SCALE = 1.8;

    //the board being displayed
    private final GameController controller;

    //the posistion manager that handles display locations for the hexes
    private final HexPositionManager positioner;

    //the factory for Hex shapes
    private final ShapeFactory shapeFactory;

    //the cache for images
    private final ImageCache imageFactory;

    //the shapes to display on the board
    private final HexGrid<Shape> shapes;

    //the images to display on the board
    private final HexGrid<ImageView> images;

    private UiPicker picker;

    public Board(GameController controller) {
        super();
        this.controller = controller;
        int size = controller.size();
        positioner = new HexPositionManager(size, Settings.ZOOM_INCREMENT, Settings.INITIAL_ZOOM);
        imageFactory = new ImageCache();
        shapeFactory = new ShapeFactory();
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        shapes = new HexGrid<>(size);
        images = new HexGrid<>(size);
        refresh();
    }

    public void refresh() {
        for (Hex hex : controller.hexes()) {
            refreshShape(hex);
            refreshImage(hex);
        }
    }

    private void refreshShape(Hex hex) {
        double xCenter = positioner.xCenter(hex.x()) + positioner.xOffset();
        double yCenter = positioner.yCenter(hex.y()) + positioner.yOffset();
        Shape correctShape = shapeFactory.create(hex.construct(), xCenter, yCenter, positioner.radius(), positioner.gap());
        addEventHandlers(correctShape, hex);

        Shape currentShape = shapes.get(hex.x(), hex.y());

        if (currentShape == null) {
            getChildren().add(correctShape);
            shapes.set(hex.x(), hex.y(), correctShape);
            currentShape = correctShape;
        } else if (!correctShape.getClass().equals(currentShape.getClass())) {
            getChildren().remove(currentShape);

            ImageView toDraw = images.get(hex.x(), hex.y());
            if (toDraw != null) {
                getChildren().remove(toDraw);
                images.set(hex.x(), hex.y(), null);
            }

            getChildren().add(correctShape);
            shapes.set(hex.x(), hex.y(), correctShape);
            currentShape = correctShape;
        }

        if (hex.occupant() == null) {
            currentShape.setFill(Color.WHITE);
        } else {
            currentShape.setFill(hex.occupant().creator().playerColor().color());
        }

        if (hex.construct() == Constructs.OBJECTIVE_CENTER || hex.construct() == Constructs.OBJECTIVE_EDGE) {
            currentShape.setFill(((Color) currentShape.getFill()).darker());
        }
    }

    private void refreshImage(Hex hex) {
        ImageView toDraw = images.get(hex.x(), hex.y());

        if (hex.occupant() == null) {
            if (toDraw != null) {
                images.set(hex.x(), hex.y(), null);
                getChildren().remove(toDraw);
            }
            return;
        }

        if (toDraw == null) {
            double xCenter = positioner.xCenter(hex.x()) + positioner.xOffset();
            double yCenter = positioner.yCenter(hex.y()) + positioner.yOffset();

            ImageView image = imageFactory.image(hex.occupant().imagePath(), IMAGE_SCALE * positioner.radius());
            image.setX(xCenter - image.getFitWidth() / 2);
            image.setY(yCenter - image.getFitHeight() / 2);
            addEventHandlers(image, hex);

            getChildren().add(image);
            images.set(hex.x(), hex.y(), image);
        }
    }

    private void addEventHandlers(Node node, Hex hex) {
        node.setOnDragDetected(event -> node.startFullDrag());
        node.setOnMouseDragEntered(new MouseDragHandler(this, controller, hex));
        node.setOnMousePressed(new MousePressedHandler(this, controller, hex));
        node.setOnMouseReleased(new MouseReleasedHandler(this, controller));
    }

    public void setPicker(List<Action> actions, double xCenter, double yCenter, Hex hex) {
        List<String> ids = new LinkedList<>();
        for (Action action : actions) {
            ids.add(action.pickerId());
        }

        picker = new UiPicker(ids, imageFactory, xCenter, yCenter, hex);

        List<ImageView> controls = picker.controls();
        for (int i = 0; i < controls.size(); i++) {
            addActionPick(controls.get(i), actions.get(i));
        }
    }

    private void addActionPick(ImageView button, Action action) {
        getChildren().add(button);
        button.setOnMouseClicked(event -> tryAction(action));
    }

    public void setPicker(double xCenter, double yCenter, Hex hex) {
        List<String> unitIds = new ArrayList<>();
        unitIds.add("offense");
        unitIds.add("movement");
        unitIds.add("build");
        unitIds.add("healing");

        picker = new UiPicker(unitIds, imageFactory, xCenter, yCenter, hex);

        List<ImageView> controls = picker.controls();
        controls.get(0).setOnMouseClicked(event -> tryAdd(Melee.class));
        controls.get(1).setOnMouseClicked(event -> tryAdd(Ranged.class));
        controls.get(2).setOnMouseClicked(event -> tryAdd(Builder.class));
        controls.get(3).setOnMouseClicked(event -> tryAdd(Healer.class));

        getChildren().addAll(controls);
    }

    public boolean locked() {
        return (picker != null);
    }

    public void unlock() {
        if (picker != null) {
            for (ImageView control : picker.controls()) {
                getChildren().remove(control);
            }
            picker = null;
        }
    }

    public void tryAction(Action action) {
        controller.act(action);
        controller.ui().refresh();
        unlock();
    }

    public void tryAdd(Class<? extends Unit> type) {
        try {
            controller.add(type);
        } catch (UnitFactory.InsufficentPointsException ex) {
            new ErrorAlert("Not enough points").show();
        }

        unlock();
    }
}
