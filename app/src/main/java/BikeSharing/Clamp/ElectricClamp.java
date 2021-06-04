package BikeSharing.Clamp;

import BikeSharing.Bike.Bike;
import BikeSharing.Bike.ElectricBike;
import BikeSharing.Bike.ElectricBikeBoosterSeat;
import BikeSharing.Clamp.DAO.ClampDataTransfer;

/**
 * Clamp for an electric bike
 */

public class ElectricClamp extends ElectricClampAbstract {

    /**
     * Creates a clamp from the data received from the persistent layer. This is the only way to build a clamp
     * @param data Clamp data
     */

    public ElectricClamp(ClampDataTransfer data) {
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
        if((bike.getClass() == ElectricBike.class) & (bike.getClass() != ElectricBikeBoosterSeat.class)) {
            return true;
        }
        return false;
    }

    @Override
    protected Bike fillBike() {
        return new ElectricBike(100);
    }
    
}
