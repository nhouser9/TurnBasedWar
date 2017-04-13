/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.menus;

import javafx.scene.control.Label;
import players.Player;

/**
 *
 * @author Nick Houser
 */
public class PlayerDetails extends GenericMenu {

    private final Label color;
    private final Label purchase;

    private Player player;

    public PlayerDetails() {
        super();
        add(new Label("Active Player"));
        color = new Label();
        purchase = new Label();
        add(color);
        add(purchase);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void refresh() {
        color.setText("  Color: " + player.toString());
        purchase.setText("  Purchase Points: " + player.purchasePoints());
    }
}
