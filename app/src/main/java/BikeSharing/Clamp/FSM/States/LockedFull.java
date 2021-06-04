package BikeSharing.Clamp.FSM.States;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.FSM.ClampState;
import BikeSharing.Subscription.Subscription;

/**
 * A bike is parked and the clamp is locked
 */

public class LockedFull implements ClampState {

    /**
     * Singleton state following the SINGLETON design pattern
     */

    public static ClampState LOCKEDFULL = new LockedFull();

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
        c.setRenter(s);
        c.rentProcedures();
        c.setState(UnlockedFull.UNLOCKEDFULL);
    }

    /**
     * 
     */

    @Override
    public void startMaintenance(Clamp c) {
        c.setState(Maintenance.MAINTENANCE);
    }

    /**
     * 
     */

    @Override
    public void endMaintenance(Clamp c) {
        return;
    }

    /**
     * 
     */

    @Override
    public boolean isRentable() {
        return true;
    }

    @Override
    public int encode() {
        return 1;
    }
    
}
