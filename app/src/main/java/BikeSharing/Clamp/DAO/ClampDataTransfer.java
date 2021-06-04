package BikeSharing.Clamp.DAO;

import BikeSharing.Clamp.FSM.ClampState;

/**
 * Data transfer object for a clamp following the DAO design pattern
 */

public class ClampDataTransfer {

    /**
     * State of the clamp
     */

    public ClampState state;

    /**
     * Id of the clamp
     */

    public int clampID;

    /**
     * Id of the clamp's rack
     */

    public long rackID;

    /**
     * Clamp type
     */

    public int type;
    
}
