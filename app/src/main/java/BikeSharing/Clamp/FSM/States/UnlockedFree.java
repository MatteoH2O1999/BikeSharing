package BikeSharing.Clamp.FSM.States;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.FSM.ClampState;
import BikeSharing.Subscription.Subscription;

/**
 * The clamp is unlocked and there are no bikes in it
 */

public class UnlockedFree implements ClampState {

    /**
     * Singleton state following the SINGLETON design pattern
     */

    public static ClampState UNLOCKEDFREE = new UnlockedFree();

    /**
     * 
     */

    @Override
    public void park(Clamp c, Bike bike) {
        if (c.isCompatible(bike)) {
            c.setState(LockedFull.LOCKEDFULL);
            c.setBike(bike);
            c.endRent();
            c.parkedLoop();
        } else {
            c.setState(UnlockedFull.UNLOCKEDFULL);
            c.setBike(bike);
        }
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
