package BikeSharing.Clamp;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.DAO.ClampDao;
import BikeSharing.Clamp.DAO.ClampDataTransfer;
import BikeSharing.Clamp.FSM.ClampController;
import BikeSharing.Clamp.FSM.ClampState;
import BikeSharing.Clamp.FSM.States.LockedFull;
import BikeSharing.Clamp.FSM.States.Maintenance;
import BikeSharing.Subscription.Subscription;

/**
 * abstract FSM of a clamp
 */

public abstract class Clamp {

    /**
     * Clamp id (unique per rack)
     */

    public final int id;

    /**
     * Rack id
     */

    public final long rackID;

    /**
     * Current state
     */

    private ClampState state;

    /**
     * FSM controller
     */

    private ClampController controller = new ClampController();

    /**
     * Creates a clamp from the data received from the persistent layer. This is the only way to build a clamp
     * @param data Clamp data
     */

    public Clamp(ClampDataTransfer data) {
        this.id = data.clampID;
        this.rackID = data.rackID;
        this.state = data.state;
        this.controller.setBike(null);
        if ((this.state == LockedFull.LOCKEDFULL) || (this.state == Maintenance.MAINTENANCE)) {
            this.controller.setBike(fillBike());
        }
    }

    /**
     * User parks the bike
     * @param bike the bike
     */

    public void park(Bike bike) {
        state.park(this, bike);
    }

    /**
     * User tries to take the bike
     * @return the bike (or null if the bike cannot be taken)
     */

    public Bike take() {
        return state.take(this);
    }

    /**
     * Signal to start the renting of the bike
     * @param s renting subscription
     */

    public void rent(Subscription s) {
        state.rent(this, s);
    }

    /**
     * Signal to enter maintenance mode
     */

    public void startMaintenance() {
        state.startMaintenance(this);
    }

    /**
     * Signal to exit maintenance mode
     */

    public void endMaintenance() {
        state.endMaintenance(this);
    }

    /**
     * Sets the parked bike value
     * @param bike the bike to park
     */

    public void setBike(Bike bike) {
        controller.setBike(bike);
    }

    /**
     * Removes the bike from the clamp and gives it to the user
     * @return the bike
     */

    public Bike getBike() {
        return controller.getBike();
    }

    /**
     * Returns the parked bike without removing it from the clamp to perform maintenance operations
     * @return the bike
     */

    public Bike readBike() {
        return controller.readBike();
    }

    /**
     * Sets the bike new renter
     * @param s subscription
     */

    public void setRenter(Subscription s) {
        controller.setRenter(s);
    }

    /**
     * Ends the subscription rent
     */

    public void endRent() {
        controller.endRent();
    }

    /**
     * Sets the new state for the FSM
     * @param s the new state
     */

    public void setState(ClampState s) {
        this.state = s;
        this.save();
        return;
    }

    /**
     * Returns true if the clamp is ready for a new rent
     * @return true if the clap is ready for a new rent
     */

    public boolean isRentable() {
        return state.isRentable();
    }

    /**
     * Checks if the bike passed as an argument is compatible with the clamp
     * @param bike the bike
     * @return true if the bike is compatible with the clamp
     */

    public abstract boolean isCompatible(Bike bike);

    /**
     * Actions to do while the bike is parked (ie charging the battery, resetting the GPS, etc.)
     */

    public abstract void parkedLoop();

    /**
     * Bike dependant actions to perform before releasing the bike
     */

    public abstract void rentProcedures();

    /**
     * Returns a compatible bike. Used for the initialization phase
     * @return a compatible bike
     */

    protected abstract Bike fillBike();

    /**
     * Saves the current state of the clamp
     */

    private void save() {
        ClampDataTransfer toSave = new ClampDataTransfer();
        toSave.clampID = this.id;
        toSave.rackID = this.rackID;
        toSave.state = this.state;
        ClampDao.updateClamp(toSave);
    }

    @Override
    public String toString() {
        return Integer.toString(this.id) + ": " + this.getClass().getSimpleName() + " (" + this.state.getClass().getSimpleName() + ")";
    }
    
}
