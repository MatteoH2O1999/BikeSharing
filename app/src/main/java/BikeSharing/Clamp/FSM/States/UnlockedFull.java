package BikeSharing.Clamp.FSM.States;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.FSM.ClampState;
import BikeSharing.Subscription.Subscription;

/**
 * The clamp is unlocked but there is still a bike in it
 */

public class UnlockedFull implements ClampState {

    /**
     * Singleton state following the SINGLETON design pattern
     */

    public static ClampState UNLOCKEDFULL = new UnlockedFull();

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
        c.setState(UnlockedFree.UNLOCKEDFREE);
        return c.getBike();
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
        return;
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
        return 0;
    }
    
}
