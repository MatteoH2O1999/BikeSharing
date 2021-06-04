package BikeSharing.Clamp.FSM;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Subscription.Subscription;

/**
 * Interface for every state of the clam FSM
 */

public interface ClampState {

    /**
     * The user parks a bike
     * @param c clamp FSM
     * @param bike bike
     */

    public void park(Clamp c, Bike bike);

    /**
     * User takes the parked bike
     * @param c clamp FSM
     * @return the parked bike
     */

    public Bike take(Clamp c);

    /**
     * Signal to begin renting of the bike
     * @param c clamp FSM
     * @param s subscription
     */

    public void rent(Clamp c, Subscription s);

    /**
     * Start maintenance mode
     * @param c clamp FSM
     */

    public void startMaintenance(Clamp c);

    /**
     * Ends maintenance mode
     * @param c clamp FSM
     */

    public void endMaintenance(Clamp c);

    /**
     * Returns true if the clamp is ready for a new rent
     * @return true if the clap is ready for a new rent
     */

    public boolean isRentable();

    /**
     * Returns the encoding for the state
     * @return the encoding for the state
     */

    public int encode();
    
}
