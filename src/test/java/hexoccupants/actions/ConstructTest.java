/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexoccupants.actions;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.Constructs;
import hexoccupants.pieces.Unit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Nick Houser
 */
public class ConstructTest {

    private Unit fakeActor;
    private Hex fakeTarget;
    private HexGrid<Hex> fakeBoard;

    @Before
    public void beforeTest() {
        fakeActor = mock(Unit.class);
        fakeTarget = mock(Hex.class);
        fakeBoard = (HexGrid<Hex>) mock(HexGrid.class);
    }

    @Test
    public void implementationAllowed_returnsFalse_whenTargetIsCentralObjective() {
        Construct test = new Construct(fakeActor, Constructs.NONE);
        when(fakeTarget.construct()).thenReturn(Constructs.OBJECTIVE_CENTER);
        assertFalse(test.implementationAllowed(fakeBoard, fakeTarget));
    }

    @Test
    public void implementationAllowed_returnsFalse_whenTargetIsEdgeObjective() {
        Construct test = new Construct(fakeActor, Constructs.NONE);
        when(fakeTarget.construct()).thenReturn(Constructs.OBJECTIVE_EDGE);
        assertFalse(test.implementationAllowed(fakeBoard, fakeTarget));
    }

    @Test
    public void implementationAllowed_returnsFalse_whenTryingToDeconstructHexWithNoConstruct() {
        Construct test = new Construct(fakeActor, Constructs.NONE);
        when(fakeTarget.construct()).thenReturn(Constructs.NONE);
        assertFalse(test.implementationAllowed(fakeBoard, fakeTarget));
    }

    @Test
    public void implementationAllowed_returnsFalse_whenTryingToConstructHexWithConstruct() {
        Construct test = new Construct(fakeActor, Constructs.ROAD);
        when(fakeTarget.construct()).thenReturn(Constructs.FORT);
        assertFalse(test.implementationAllowed(fakeBoard, fakeTarget));
    }

    @Test
    public void implementationAllowed_returnsFalse_whenNotTargetingActorsHex() {
        Construct test = new Construct(fakeActor, Constructs.FORT);
        when(fakeTarget.construct()).thenReturn(Constructs.NONE);
        assertFalse(test.implementationAllowed(fakeBoard, fakeTarget));
    }

    @Test
    public void implementationAllowed_returnsTrue_whenTryingToConstructHexWithNoConstruct() {
        when(fakeTarget.occupant()).thenReturn(fakeActor);
        Construct test = new Construct(fakeActor, Constructs.FORT);
        when(fakeTarget.construct()).thenReturn(Constructs.NONE);
        assertTrue(test.implementationAllowed(fakeBoard, fakeTarget));
    }

    @Test
    public void implementationAllowed_returnsTrue_whenTryingToDeconstructHexWithConstruct() {
        when(fakeTarget.occupant()).thenReturn(fakeActor);
        Construct test = new Construct(fakeActor, Constructs.NONE);
        when(fakeTarget.construct()).thenReturn(Constructs.ROAD);
        assertTrue(test.implementationAllowed(fakeBoard, fakeTarget));
    }

    @Test
    public void action_setsTheConstructOfTheTargetToTheArgument() {
        Constructs expected = Constructs.FORT;
        Construct test = new Construct(fakeActor, expected);
        test.action(fakeBoard, fakeTarget);
        verify(fakeTarget).setConstruct(expected);
    }
}
