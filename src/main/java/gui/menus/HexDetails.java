/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.menus;

import hex.Hex;
import hexoccupants.pieces.Unit;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Nick Houser
 */
public class HexDetails extends GenericMenu {

    private static final int STRINGBUILDER_SIZE = 100;

    private final Label coordinates;
    private final Label unit;
    private final Label health;
    private final Label actionpoints;

    private Hex currentHex;
    private Unit currentUnit;

    public HexDetails() {
        super();
        add(new Label("Selected Hex"));
        coordinates = new Label();
        unit = new Label();
        health = new Label();
        actionpoints = new Label();
        add(coordinates);
        add(unit);
        add(health);
        add(actionpoints);
    }

    public void setHex(Hex hex) {
        currentHex = hex;

        currentUnit = null;
        if (hex != null) {
            currentUnit = hex.occupant();
        }
    }

    @Override
    public void refresh() {
        refreshCoordinates();
        refreshOccupant();
        refreshHealth();
        refreshActionpoints();
    }

    private void refreshCoordinates() {
        StringBuilder builder = new StringBuilder(STRINGBUILDER_SIZE);

        builder.append("  Co-ordinates: (");
        if (currentHex == null) {
            builder.append("---");
        } else {
            builder.append(currentHex.x());
            builder.append(", ");
            builder.append(currentHex.y());
        }
        builder.append(")");

        coordinates.setText(builder.toString());
    }

    private void refreshOccupant() {
        StringBuilder builder = new StringBuilder(STRINGBUILDER_SIZE);

        builder.append("  Type: ");
        if (currentUnit == null) {
            builder.append("---");
        } else {
            builder.append(currentUnit.toString());
        }

        unit.setText(builder.toString());
    }

    private void refreshHealth() {
        StringBuilder builder = new StringBuilder(STRINGBUILDER_SIZE);

        builder.append("  Health: ");
        if (currentUnit == null) {
            builder.append("---");
        } else {
            builder.append(currentUnit.health());
        }

        health.setText(builder.toString());
    }

    private void refreshActionpoints() {
        StringBuilder builder = new StringBuilder(STRINGBUILDER_SIZE);

        builder.append("  Action Points: ");
        if (currentUnit == null) {
            builder.append("---");
        } else {
            builder.append(currentUnit.actionPoints());
        }

        actionpoints.setText(builder.toString());
    }
}
