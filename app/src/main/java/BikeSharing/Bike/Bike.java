package BikeSharing.Bike;

import BikeSharing.Subscription.Subscription;

/**
 * Abstract model of a rented bike.
 */

public abstract class Bike {

    /**
     * The renting subscription.
     */

    private Subscription renter;

    /**
     * Returns the subscription that is renting the bike.
     * @return the renting subscription.
     */

    public Subscription getSubscription() {

        return renter;

    }

    /**
     * Sets the current renting subscription to the one passed as argument.
     * @param sub the new renting subscription.
     * @throws NullPointerException if argument is null.
     */

    public void setSubscription(Subscription sub) throws NullPointerException {

        if(sub == null) {
            throw new NullPointerException("Subscription is null.");
        }
        this.renter = sub;
        return;
        
    }

    /**
     * Sets the current renting subscription to null (used when parked).
     */

    public void initSubscription() {

        this.renter = null;
        return;

    }

    /**
     * returns the encoding for the bike
     * @return the encoding for the bike
     */

    public abstract int encode();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
