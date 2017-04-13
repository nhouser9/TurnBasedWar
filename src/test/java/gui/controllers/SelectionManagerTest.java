package gui.controllers;

import hex.Hex;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SelectionManagerTest {

    @Test
    public void setSelection_setsTheSelection() {
        SelectionManager test = new SelectionManager();
        Hex fakeHex = mock(Hex.class);
        test.setSelection(fakeHex);
        assertTrue(test.getSelection() == fakeHex);
    }

    @Test
    public void setTarget_setsTheTarget() {
        SelectionManager test = new SelectionManager();
        Hex fakeHex = mock(Hex.class);
        test.setTarget(fakeHex);
        assertTrue(test.getTarget() == fakeHex);
    }
}
