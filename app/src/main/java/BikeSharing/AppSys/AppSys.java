package BikeSharing.AppSys;

import java.util.ArrayList;
import java.util.Collection;

import BikeSharing.Bike.Manager.BikeManager;
import BikeSharing.MVC.ObserverInterface;
import BikeSharing.Rack.Rack;
import BikeSharing.Rack.DAO.RackDao;
import BikeSharing.Rack.DAO.RackDataTransfer;
import BikeSharing.Rack.Manager.RackManager;
import BikeSharing.Statistics.DataManager;
import BikeSharing.Subscription.Manager.SubscriptionManager;
import BikeSharing.Usertypes.Administration;
import BikeSharing.Usertypes.DataAnalyst;

/**
 * Representation of the entire system
 */

public class AppSys {

    /**
     * Singleton (only a single system can be instanced at a time)
     */

    private static final AppSys singleton = new AppSys();

    /**
     * Rack collection
     */

    private final Collection<Rack> racks = new ArrayList<>();

    /**
     * Data manager
     */

    private final DataManager dataManager = new DataManager();

    /**
     * Rack manager
     */

    private final RackManager rackManager = new RackManager();

    /**
     * Bike manager
     */

    private final BikeManager bikeManager = new BikeManager();

    /**
     * Listening application. Will get notified when the state of the system changes following the observer design pattern
     */

    private final Collection<ObserverInterface> listeners = new ArrayList<>();

    /**
     * Constructor
     */

    private AppSys() {
        Collection<RackDataTransfer> data = RackDao.getRacks();
        for (RackDataTransfer rackDataTransfer : data) {
            this.racks.add(new Rack(rackDataTransfer));
        }
    }

    /**
     * Returns the system instance
     * @return the system instance
     */

    public static AppSys getSys() {
        return singleton;
    }

    /**
     * Updates the racks (used after edits to racks and clamps)
     */

    public void update() {
        this.racks.clear();
        Collection<RackDataTransfer> data = RackDao.getRacks();
        for (RackDataTransfer rackDataTransfer : data) {
            this.racks.add(new Rack(rackDataTransfer));
        }
        for (ObserverInterface listener : this.listeners) {
            listener.update();
        }
    }

    /**
     * Returns a collection with all the racks. It is considered obsolete immediately after it is returned
     * @return a collection with all the racks
     */

    public Collection<Rack> getAllRacks() {
        ArrayList<Rack> ret = new ArrayList<>();
        for (Rack rack : this.racks) {
            ret.add(rack);
        }
        return ret;
    }

    /**
     * Returns the specified rack
     * @param id rack id
     * @return the rack
     */

    public Rack getRack(long id) {
        for (Rack rack : racks) {
            if (rack.getID() == id) {
                return rack;
            }
        }
        return null;
    }

    /**
     * Returns the data manager
     * @param admin the data analyst that will use the data
     * @return the data manager
     */

    public DataManager getDataManager(DataAnalyst admin) {
        if (admin.isEligible()) {
            return this.dataManager;
        }
        return null;
    }

    /**
     * Returns the rack manager 
     * @param admin the administrator that will edit the racks
     * @return the rack manager
     */

    public RackManager getRackManager(Administration admin) {
        if (admin.isEligible()) {
            return this.rackManager;
        }
        return null;
    }

    /**
     * Returns the bike manager
     * @param admin the administrator that will edit the bikes
     * @return the bike manager
     */

    public BikeManager getBikeManager(Administration admin) {
        if (admin.isEligible()) {
            return this.bikeManager;
        }
        return null;
    }

    /**
     * Adds a listener to the system status
     * @param listener the new listener
     */

    public void startListening(ObserverInterface listener) {
        if (this.listeners.size() == 0) {
            SubscriptionManager.startDeamon();
        }
        this.listeners.add(listener);
    }

    /**
     * Removes a listener to the system status
     * @param listener the listener to remove
     */

    public void stopListening(ObserverInterface listener) {
        this.listeners.remove(listener);
        if (this.listeners.size() == 0) {
            SubscriptionManager.stopDeamon();
        }
    }
    
}
