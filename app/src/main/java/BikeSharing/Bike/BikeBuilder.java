package BikeSharing.Bike;

/**
 * Factory class for bike decoding
 */

public class BikeBuilder {

    /**
     * Returns the bike corresponding to the encoding
     * @param bike the encoded bike
     * @return the bike
     */

    public static Bike decode(int bike) {

        switch (bike) {
            case 0:
                return new NormalBike();
            case 1:
                return new ElectricBike(100);
            case 2:
                return new ElectricBikeBoosterSeat(100);
            default:
                return null;
        }

    }
    
}
