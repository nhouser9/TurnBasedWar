package turnbasedwar;

import gui.Board;
import gui.menus.Menu;
import gui.controllers.GameController;
import gui.controllers.LocalController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Main class and entry point for the program.
 *
 * @author Nick Houser
 */
public class TurnBasedWar extends Application {

    /**
     * Main method and entry point for the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Entry point for JavaFX.
     *
     * @param stage the main stage of the application
     * @throws Exception if something goes wrong during app creation
     */
    @Override
    public void start(Stage stage) throws Exception {
        GameController controller = new LocalController();
        Board board = new Board(controller);
        Menu menu = new Menu(controller);
        controller.ui().registerBoard(board);
        controller.ui().registerMenu(menu);

        GridPane layout = new GridPane();
        layout.add(board, 0, 0);
        layout.add(menu, 1, 0);

        stage.setTitle("Wargame");
        Scene scene = new Scene(layout);
        stage.setScene(scene);

        stage.show();
    }
}
