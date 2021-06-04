package BikeSharing.Clamp;

import BikeSharing.Bike.Bike;
import BikeSharing.Bike.NormalBike;
import BikeSharing.Clamp.DAO.ClampDataTransfer;
/**
 * Clamp for normal bike
 */

public class NormalClamp extends Clamp {

    /**
     * Creates a clamp from the data received from the persistent layer. This is the only way to build a clamp
     * @param data Clamp data
     */

    public NormalClamp(ClampDataTransfer data) {
        super(data);
    }

    /**
     * 
     */

    @Override
    public boolean isCompatible(Bike bike) {
        if (bike == null) {
            return false;
        }
        if(bike.getClass() == NormalBike.class) {
            return true;
        }
        return false;
    }

    /**
     * 
     */

    @Override
    public void parkedLoop() {
        return;        
    }

    @Override
    public void rentProcedures() {
        return;        
    }

    @Override
    protected Bike fillBike() {
        return new NormalBike();
    }
    
}
