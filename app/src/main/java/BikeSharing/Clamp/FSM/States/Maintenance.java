package BikeSharing.Clamp.FSM.States;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.FSM.ClampState;
import BikeSharing.Subscription.Subscription;

/**
 * Maintenance state
 */

public class Maintenance implements ClampState {

    /**
     * Singleton state following the SINGLETON design pattern
     */

    public static ClampState MAINTENANCE = new Maintenance();

    /**
     * 
     */

    @Override
    public void park(Clamp c, Bike bike) {
        return;
    }

    /**
     * 
     */

    @Override
    public Bike take(Clamp c) {
        return null;
    }

    /**
     * 
     */

    @Override
    public void rent(Clamp c, Subscription s) {
        return;
    }

    /**
     * 
     */

    @Override
    public void startMaintenance(Clamp c) {
        return;
    }

    /**
     * 
     */

    @Override
    public void endMaintenance(Clamp c) {
        c.setState(LockedFull.LOCKEDFULL);
    }

    /**
     * 
     */

    @Override
    public boolean isRentable() {
        return false;
    }

    @Override
    public int encode() {
        return 2;
    }
    
}
