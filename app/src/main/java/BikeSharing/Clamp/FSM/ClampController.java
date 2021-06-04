package BikeSharing.Clamp.FSM;

import BikeSharing.Bike.Bike;
import BikeSharing.Subscription.Subscription;
import BikeSharing.Subscription.Util.InvalidSubscriptionException;

/**
 * Controller for the clamp FSM
 */

public class ClampController {

    /**
     * The parked bike
     */

    private Bike bike;

    /**
     * Initializator constructor
     */

    public ClampController() {
        this.bike = null;
    }

    /**
     * Removes the bike from the parking lot
     * @return the bike
     */

    public Bike getBike() {
        Bike ret = this.bike;
        this.bike = null;
        return ret;
    }

    /**
     * Parks a bike
     * @param b the bike
     */

    public void setBike(Bike b) {
        this.bike = b;
        return;
    }

    /**
     * Returns the bike to perform maintenance
     * @return the bike
     */

    public Bike readBike() {
        return this.bike;
    }

    /**
     * Sets the bike new subscription
     * @param s subscription
     */

    public void setRenter(Subscription s) {
        this.bike.setSubscription(s);
        try {
            s.startRent(this.bike);
        } catch (InvalidSubscriptionException e) {
            //Lazy exception handling as this situation should never happen
            throw new RuntimeException();
        }
    }

    /**
     * Ends the subscription rent of the bike
     */

    public void endRent() {
        this.bike.getSubscription().endRent();
    }
    
}
