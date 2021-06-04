package BikeSharing.Clamp;

import BikeSharing.Clamp.DAO.ClampDataTransfer;

/**
 * Factory class for clamps from persistent layers
 */

public class ClampBuilder {

    /**
     * Builds a clamp with the specified data
     * @param data the data
     * @return the clamp
     */

    public static Clamp buildClamp(ClampDataTransfer data) {

        switch (data.type) {
            case 0:
                //Normal Clamp
                return new NormalClamp(data);
            case 1:
                //Electic Clamp
                return new ElectricClamp(data);
            case 2:
                //Electric Booster Seat Clamp
                return new ElectricBoosterSeatClamp(data);
            default:
                return null;
        }

    }
    
}
