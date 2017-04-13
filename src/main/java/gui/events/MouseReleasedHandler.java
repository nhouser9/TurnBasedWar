/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.events;

import gui.Board;
import gui.ErrorAlert;
import gui.controllers.GameController;
import hexoccupants.actions.Action;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Nick Houser
 */
public class MouseReleasedHandler implements EventHandler<MouseEvent> {

    private final Board board;
    private final GameController controller;

    public MouseReleasedHandler(Board board, GameController controller) {
        this.board = board;
        this.controller = controller;
    }

    @Override
    public void handle(MouseEvent t) {
        if (!controller.connected()) {
            return;
        }

        if (board.locked()) {
            return;
        }

        if (controller.selections().getSelection() == null || controller.selections().getTarget() == null) {
            return;
        }

        if (controller.active().finishedPurchasing()) {
            handleMoveState(t);
        } else {
            handleBuyState(t);
        }

        controller.ui().refresh();
    }

    private void handleMoveState(MouseEvent t) {
        if (controller.selections().getSelection().occupant() == null) {
            return;
        }

        if (!controller.selections().getSelection().occupant().creator().equals(controller.active())) {
            return;
        }

        List<Action> actions = controller.actions();
        if (controller.selections().getSelection() == controller.selections().getTarget()) {
            if (!actions.isEmpty()) {
                board.setPicker(actions, t.getX(), t.getY(), controller.selections().getTarget());
            }
        } else if (actions.isEmpty()) {
            new ErrorAlert("No valid actions target that Hex").show();
        } else {
            board.tryAction(actions.get(0));
        }
    }

    private void handleBuyState(MouseEvent t) {
        if (controller.selections().getTarget().occupant() != null) {
            return;
        }

        if (!controller.purchasable(controller.selections().getTarget())) {
            return;
        }

        board.setPicker(t.getX(), t.getY(), controller.selections().getTarget());
    }
}
