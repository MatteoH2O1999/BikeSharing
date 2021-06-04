package BikeSharing.Rack.DAO;

import java.util.ArrayList;

import BikeSharing.Clamp.Clamp;

/**
 * Data transfer object for a rack in accordance with the DAO design pattern
 */

public class RackDataTransfer {

    /**
     * Rack id
     */

    public long id;

    /**
     * Rack name
     */

    public String name;

    /**
     * Clamp arraylist
     */

    public ArrayList<Clamp> clamps;
    
}
