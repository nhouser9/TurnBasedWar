package gui.controllers;

import gui.Board;
import gui.menus.Menu;

/**
 * Manager for the UI which handles exposes an API to re-render the screen and
 * menu.
 *
 * @author Nick Houser
 */
public class UiManager {

    //the UI elements
    private Board board;
    private Menu menu;

    /**
     * Registers a Board UI object to update as needed.
     *
     * @param board the Board object to update as needed
     */
    public void registerBoard(Board board) {
        this.board = board;
    }

    /**
     * Registers a Menu UI object to update as needed.
     *
     * @param menu the Menu object to update as needed
     */
    public void registerMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * Updates all registered UI objects.
     */
    public void refresh() {
        if (board != null) {
            board.refresh();
        }

        if (menu != null) {
            menu.refresh();
        }
    }

    /**
     * Unlocks the UI by closing all popup menus and readying for new actions to
     * be processed.
     */
    public void unlock() {
        board.unlock();
    }
}
