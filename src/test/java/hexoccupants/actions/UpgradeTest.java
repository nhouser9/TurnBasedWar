package hexoccupants.actions;

import hex.Hex;
import hexoccupants.pieces.Leader;
import hexoccupants.pieces.UpgradeTypes;
import hexoccupants.pieces.Upgrades;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpgradeTest {

    private Leader fakeActor;
    private UpgradeTypes testType;
    private Upgrade test;
    private Hex fakeTarget;

    @Before
    public void beforeTest() {
        fakeActor = mock(Leader.class);
        when(fakeActor.upgrades()).thenReturn(mock(Upgrades.class));
        when(fakeActor.actionPoints()).thenReturn(Integer.MAX_VALUE);

        testType = UpgradeTypes.DAMAGE;

        test = new Upgrade(fakeActor, 0, testType);

        fakeTarget = mock(Hex.class);
    }

    @Test
    public void action_callsUpgradeOnActor() {
        test.action(null, null);
        verify(fakeActor.upgrades()).upgrade(testType);
    }

    @Test
    public void allowed_returnsFalse_whenLevelGreaterThanOrEqualActorLevel() {
        when(fakeActor.level()).thenReturn(0);
        when(fakeActor.upgrades().level()).thenReturn(0);
        when(fakeTarget.occupant()).thenReturn(fakeActor);
        assertFalse(test.allowed(null, fakeTarget));
    }

    @Test
    public void allowed_returnsFalse_whenLevelNotEqualToNextUpgradeLevel() {
        when(fakeActor.level()).thenReturn(1);
        when(fakeActor.upgrades().level()).thenReturn(1);
        when(fakeTarget.occupant()).thenReturn(fakeActor);
        assertFalse(test.allowed(null, fakeTarget));
    }

    @Test
    public void allowed_returnsFalse_whenTargetNotEqualToSelf() {
        when(fakeActor.level()).thenReturn(1);
        when(fakeActor.upgrades().level()).thenReturn(0);
        when(fakeTarget.occupant()).thenReturn(null);
        assertFalse(test.allowed(null, fakeTarget));
    }

    @Test
    public void allowed_returnsTrue_whenPassedValidConditions() {
        when(fakeActor.level()).thenReturn(1);
        when(fakeActor.upgrades().level()).thenReturn(0);
        when(fakeTarget.occupant()).thenReturn(fakeActor);
        assertTrue(test.allowed(null, fakeTarget));
    }
}
