package BikeSharing.Rack.Manager;

import BikeSharing.AppSys.AppSys;
import BikeSharing.Clamp.DAO.ClampDao;
import BikeSharing.Rack.DAO.RackDao;
import BikeSharing.Rack.DAO.RackDataTransfer;

/**
 * Allows to edit racks and clamps
 */

public class RackManager {

    /**
     * Creates a new rack
     * @param name the rack's name
     */

    public void addRack(String name) {
        RackDao.createRack(name);
        AppSys.getSys().update();
    }

    /**
     * Changes the name of the specified rack
     * @param id the rack id
     * @param name the new name
     */

    public void editRack(long id, String name) {
        RackDataTransfer data = new RackDataTransfer();
        data.id = id;
        data.name = name;
        RackDao.editRack(data);
        AppSys.getSys().update();
    }

    /**
     * Deletes the specified rack and all the clamps in it
     * @param id the rack id
     */

    public void deleteRack(long id) {
        RackDao.deleteRack(id);
        AppSys.getSys().update();
    }

    /**
     * Adds a clamp of the specified type to the specified rack
     * @param id the rack id
     * @param type the clamp type
     */

    public void addClamp(long id, int type) {
        ClampDao.createClamp(id, type);
        AppSys.getSys().update();
    }

    /**
     * Deletes the specified clamp in the specified rack
     * @param rack the rack id
     * @param clamp the clamp id
     */

    public void deleteClamp(long rack, int clamp) {
        ClampDao.deleteClamp(clamp, rack);
        AppSys.getSys().update();
    }
    
}
