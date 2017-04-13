/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.menus;

import gui.controllers.GameController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Nick Houser
 */
public class Menu extends GenericMenu {

    private final GameController controller;

    private final PlayerDetails player;
    private final Label scores;
    private final HexDetails selected;

    public Menu(GameController controller) {
        super();

        setPrefWidth(200);

        this.controller = controller;

        player = new PlayerDetails();
        add(player);

        scores = new Label();
        add(scores);

        selected = new HexDetails();
        add(selected);

        add(createFinishedButton());

        refresh();
    }

    public void refresh() {
        player.setPlayer(controller.active());
        player.refresh();

        String scoresText = "Scores";
        for (String score : controller.score()) {
            scoresText = scoresText + System.lineSeparator() + "  " + score;
        }

        scores.setText(scoresText);

        selected.setHex(controller.selections().getSelection());
        selected.refresh();
    }

    private Button createFinishedButton() {
        Button button = new Button();
        button.setText("Done");
        button.setOnAction(e -> {
            controller.ui().unlock();
            controller.pass();
            controller.ui().refresh();
        });
        return button;
    }
}
