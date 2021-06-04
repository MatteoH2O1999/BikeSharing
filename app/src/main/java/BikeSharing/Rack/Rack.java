package BikeSharing.Rack;

import java.util.ArrayList;
import java.util.Collection;

import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Rack.DAO.RackDao;
import BikeSharing.Rack.DAO.RackDataTransfer;
import BikeSharing.Rack.Util.BikeAlreadyRentedException;
import BikeSharing.Subscription.Subscription;

/**
 * Set of clamps and a totem
 */

public class Rack {

    /**
     * Name of the rack
     */

    private final String name;

    /**
     * Rack id
     */

    private final long id;

    /**
     * Arraylist of the clamps
     */

    private final ArrayList<Clamp> clamps;

    /**
     * Totem 
     */

    private final Totem totem = new Totem(this);

    /**
     * Creates a rack from the persistent layer. It is the only way to instance a rack
     * @param data database data
     */

    public Rack(RackDataTransfer data) {
        this.name = data.name;
        this.id = data.id;
        this.clamps = data.clamps;
    }

    /**
     * Returns the name of the rack
     * @return the name of the rack
     */

    public String getName() {
        return this.name;
    }

    /**
     * Returns the id of the rack
     * @return the id of the rack
     */

    public long getID() {
        return this.id;
    }

    /**
     * Returns the totem
     * @return the totem
     */

    public Totem getTotem() {
        return this.totem;
    }

    /**
     * Returns the specified clamp
     * @param id the clamp id
     * @return the specified clamp
     */

    public Clamp getClamp(int id) {
        for (Clamp clamp : clamps) {
            if (clamp.id == id) {
                return clamp;
            }
        }
        return null;
    }

    /**
     * Signals the system that the bike parked by a user in a certain clamp is damaged
     * @param s the user subscription
     * @param clampID the clamp id
     * @throws BikeAlreadyRentedException if the bike is already rented again
     */

    public void lastRentDamaged(Subscription s, int clampID) throws BikeAlreadyRentedException {
        Clamp userClamp = null;
        for (Clamp clamp : clamps) {
            if(clamp.id == clampID) {
                userClamp = clamp;
            }
        }
        if (userClamp == null) {
            return;
        }
        Bike userBike = userClamp.readBike();
        if (userBike == null) {
            throw new BikeAlreadyRentedException();
        } else {
            if (userBike.getSubscription().equals(s)) {
                userClamp.startMaintenance();
            } else {
                throw new BikeAlreadyRentedException();
            }
        }
    }

    /**
     * Returns a clamp that contains a rentable bike of the specified type
     * @param b bike
     * @return the clamp
     */

    public Clamp getFreeBike(Bike b) {
        for (Clamp clamp : clamps) {
            if (clamp.isRentable()) {
                if (clamp.isCompatible(b)) {
                    return clamp;
                }
            }
        }
        return null;
    }

    /**
     * Updates the rack's clamp list
     */

    public void updateRack() {
        RackDataTransfer data = RackDao.getRack(this.id);
        ArrayList<Clamp> clmps = data.clamps;
        this.clamps.clear();
        for (Clamp clamp : clmps) {
            this.clamps.add(clamp);
        }
    }

    /**
     * Returns a collection containing all the clamps
     * @return a collection with all the clamps
     */

    public Collection<Clamp> getClamps() {
        ArrayList<Clamp> ret = new ArrayList<>();
        for (Clamp clamp : this.clamps) {
            ret.add(clamp);
        }
        return ret;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
