package gui.controllers;

import gui.Board;
import gui.menus.Menu;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UiManagerTest {

    @Test
    public void refresh_updatesTheRegisteredBoard_afterRegisterBoard() {
        UiManager test = new UiManager();
        Board fakeBoard = mock(Board.class);
        test.registerBoard(fakeBoard);
        test.refresh();
        verify(fakeBoard).refresh();
    }

    @Test
    public void refresh_updatesTheRegisteredMenu_afterRegisterMenu() {
        UiManager test = new UiManager();
        Menu fakeMenu = mock(Menu.class);
        test.registerMenu(fakeMenu);
        test.refresh();
        verify(fakeMenu).refresh();
    }

    @Test
    public void refresh_doesNotCrash_whenNothingRegistered() {
        UiManager test = new UiManager();
        test.refresh();
    }
}
