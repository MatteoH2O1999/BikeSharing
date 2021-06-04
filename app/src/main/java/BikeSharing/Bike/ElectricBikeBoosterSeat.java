package BikeSharing.Bike;

/**
 * Electric bike with booster seat.
 */

public class ElectricBikeBoosterSeat extends ElectricBike {

    /**
     * Creates an electric bike with a booster seat with the specified charge.
     * @param ch the bike's charge.
     */

    public ElectricBikeBoosterSeat(int ch) {

        super(ch);

    }

    @Override
    public int encode() {
        return 2;
    }
    
}
