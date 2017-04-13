/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.events;

import gui.Board;
import gui.controllers.GameController;
import hex.Hex;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Nick Houser
 */
public class MousePressedHandler implements EventHandler<MouseEvent> {

    private final Board board;
    private final GameController controller;
    private final Hex hex;

    public MousePressedHandler(Board board, GameController controller, Hex hex) {
        this.board = board;
        this.controller = controller;
        this.hex = hex;
    }

    @Override
    public void handle(MouseEvent t) {
        if (board.locked()) {
            board.unlock();
        }

        controller.selections().setSelection(hex);
        controller.selections().setTarget(hex);
        controller.ui().refresh();
    }
}
