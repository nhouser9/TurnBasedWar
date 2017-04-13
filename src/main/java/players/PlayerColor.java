/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package players;

import java.util.Objects;
import javafx.scene.paint.Color;

/**
 *
 * @author Nick Houser
 */
public class PlayerColor {

    private final String name;
    private final Color color;

    public PlayerColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String name() {
        return name;
    }

    public Color color() {
        return color;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PlayerColor)) {
            return false;
        }

        return color.equals(((PlayerColor) other).color());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.color);
        return hash;
    }
}
