package BikeSharing.Bike;

/**
 * Normal rented bike.
 */

public class NormalBike extends Bike {

    /**
     * Creates a new normal bike with a null subscription.
     */

    public NormalBike() {

        this.initSubscription();
        return;
        
    }

    @Override
    public int encode() {
        return 0;
    }
    
}
