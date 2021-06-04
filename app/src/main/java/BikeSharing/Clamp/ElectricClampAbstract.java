package BikeSharing.Clamp;

import BikeSharing.Bike.ElectricBike;
import BikeSharing.Clamp.DAO.ClampDataTransfer;

/**
 * Abstract clamp that provides charging functionality for electrical bikes
 */

public abstract class ElectricClampAbstract extends Clamp {

    /**
     * Creates a clamp from the data received from the persistent layer. This is the only way to build a clamp
     * @param data Clamp data
     */

    public ElectricClampAbstract(ClampDataTransfer data) {
        super(data);
    }

    /**
     * 
     */

    @Override
    public void parkedLoop() {
        ElectricBike b = (ElectricBike)readBike();
        b.stopBattery();
        b.charge();
    }

    /**
     * 
     */

    @Override
    public void rentProcedures() {
        ElectricBike b = (ElectricBike)readBike();
        b.startBattery();
    }
    
}
