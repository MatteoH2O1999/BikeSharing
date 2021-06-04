package BikeSharing.Clamp.FSM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import BikeSharing.Clamp.FSM.States.LockedFull;
import BikeSharing.Clamp.FSM.States.Maintenance;
import BikeSharing.Clamp.FSM.States.UnlockedFree;

public class StateDecoderTest {

    @Test
    public void decodeState0() {
        assertEquals(UnlockedFree.UNLOCKEDFREE, StateDecoder.decodeState(0));
    }

    @Test
    public void decodeState1() {
        assertEquals(LockedFull.LOCKEDFULL, StateDecoder.decodeState(1));
    }

    @Test
    public void decodeState2() {
        assertEquals(Maintenance.MAINTENANCE, StateDecoder.decodeState(2));
    }

    @Test
    public void decodeStateOther() {
        assertNull(StateDecoder.decodeState(-3));
    }

}