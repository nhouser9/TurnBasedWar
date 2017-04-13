/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.menus;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Nick Houser
 */
public abstract class GenericMenu extends GridPane {

    private int uiIndex;

    public GenericMenu() {
        super();
        uiIndex = 0;
    }

    public final void add(Node toAdd) {
        add(toAdd, 0, uiIndex);
        uiIndex = uiIndex + 1;
    }

    public abstract void refresh();
}
