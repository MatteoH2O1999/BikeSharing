package BikeSharing.Clamp;

import BikeSharing.Bike.Bike;
import BikeSharing.Bike.ElectricBikeBoosterSeat;
import BikeSharing.Clamp.DAO.ClampDataTransfer;

/**
 * Clamp for an electric bike with a booster seat
 */

public class ElectricBoosterSeatClamp extends ElectricClampAbstract {

    /**
     * Creates a clamp from the data received from the persistent layer. This is the only way to build a clamp
     * @param data Clamp data
     */

    public ElectricBoosterSeatClamp(ClampDataTransfer data) {
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
        if(bike.getClass() == ElectricBikeBoosterSeat.class) {
            return true;
        }
        return false;
    }

    @Override
    protected Bike fillBike() {
        return new ElectricBikeBoosterSeat(100);
    }
    
}
