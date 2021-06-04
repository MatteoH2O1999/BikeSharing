package BikeSharing.Clamp.FSM;

import BikeSharing.Clamp.FSM.States.LockedFull;
import BikeSharing.Clamp.FSM.States.Maintenance;
import BikeSharing.Clamp.FSM.States.UnlockedFree;

/**
 * Decodes the states from their encoding in the persistent layer
 * Current encoding:
 * 0: Unlocked free
 * 1: Locked full
 * 2: Maintenance
 */

public class StateDecoder {

    /**
     * Returns the state corresponding to the encoding
     * @param state the int encoding the state
     * @return the decoded state
     */

    public static ClampState decodeState(int state) {

        switch (state) {
            case 0:
                return UnlockedFree.UNLOCKEDFREE;
            case 1:
                return LockedFull.LOCKEDFULL;
            case 2:
                return Maintenance.MAINTENANCE;
            default:
                return null;
        }

    }
    
}
