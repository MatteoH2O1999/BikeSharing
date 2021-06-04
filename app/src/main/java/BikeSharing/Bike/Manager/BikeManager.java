package BikeSharing.Bike.Manager;

import BikeSharing.Bike.ElectricBike;
import BikeSharing.Bike.ElectricBikeBoosterSeat;
import BikeSharing.Bike.NormalBike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Clamp.FSM.States.LockedFull;
import BikeSharing.Clamp.FSM.States.UnlockedFree;

/**
 * Allows to edit bikes in the system
 */

public class BikeManager {

    /**
     * Adds a bike in the specified clamp
     * @param c the clamp
     */

    public void addBike(Clamp c) {
        if (c.readBike() != null) {
            return;
        }
        if (c.isCompatible(new NormalBike())) {
            c.setBike(new NormalBike());
        } else if (c.isCompatible(new ElectricBike(0))) {
            c.setBike(new ElectricBike(100));
        } else if (c.isCompatible(new ElectricBikeBoosterSeat(0))) {
            c.setBike(new ElectricBikeBoosterSeat(100));
        }
        c.setState(LockedFull.LOCKEDFULL);
    }

    /**
     * Deletes the bike in the specified clamp
     * @param c the clamp
     */

    public void deleteBike(Clamp c) {
        if (c.readBike() == null) {
            return;
        }
        c.getBike();
        c.setState(UnlockedFree.UNLOCKEDFREE);
    }

    /**
     * Moves a bike from a full clamp to a free one
     * @param source source clamp
     * @param target target clamp
     */

    public void moveBike(Clamp source, Clamp target) {
        if ((source.readBike() == null) || (target.readBike() != null)) {
            return;
        }
        if (!target.isCompatible(source.readBike())) {
            return;
        }
        deleteBike(source);
        addBike(target);
    }

    /**
     * Starts maintenance mode on a full clamp
     * @param c the clamp
     */

    public void startMaintenance(Clamp c) {
        c.startMaintenance();
    }

    /**
     * Ends maintenance mode on a clamp on maintenance mode
     * @param c the clamp
     */

    public void endMaintenance(Clamp c) {
        c.endMaintenance();
    }
    
}
