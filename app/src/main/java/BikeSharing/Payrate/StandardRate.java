package BikeSharing.Payrate;

import BikeSharing.Bike.Bike;
import BikeSharing.Bike.ElectricBike;
import BikeSharing.Bike.ElectricBikeBoosterSeat;
import BikeSharing.Bike.NormalBike;
import BikeSharing.Payrate.Util.BikeRate;

/**
 * Standard renting rate
 */

public class StandardRate extends Payrate {

    /**
     * Returns the rate of the retned bike
     * @param bike rented bike
     * @return the rate of the rented bike
     */

    private BikeRate getRateClass (Bike bike) {
        
        if (bike.getClass() == NormalBike.class) {
            return new NormalRate();
        }
        if (bike.getClass() == ElectricBike.class) {
            return new ElectricRate();
        }
        if (bike.getClass() == ElectricBikeBoosterSeat.class) {
            return new ElectricBoosterSeatRate();
        }

        return null;

    }
    
    @Override
    public float getDuePayment(int minutes, Bike bike) {

        BikeRate rateClass = getRateClass(bike);
        float payment = (float)0.0;

        if (minutes >= 0) {
            payment += rateClass.getFirstHalf();
        }
        if (minutes > 30) {
            payment += rateClass.getSecondHalf();
        }
        if (minutes > 60) {
            payment += rateClass.getThirdHalf();
        }
        if (minutes > 90) {
            payment += rateClass.getFourthHalf();
        }
        if (minutes > 120) {
            minutes -= 120;
            while (minutes > 0) {
                payment += rateClass.getExtraHours();
                minutes -= 60;
            }
        }

        return payment;

    }

    @Override
    public float getFine(Bike bike) {

        BikeRate rateClass = getRateClass(bike);

        return rateClass.getFine();

    }

    @Override
    public int getEncoding() {
        return 0;
    }

    /**
     * Data for normal bike rate
     */

    private class NormalRate extends BikeRate {

        @Override
        protected void setData() {
            this.firstHalf = (float)0.00;
            this.secondHalf = (float)0.50;
            this.thirdHalf = (float)0.50;
            this.fourthHalf = (float)0.50;
            this.extraHours = (float)2.00;
            this.fine = (float)150.00;
        }

    }

    /**
     * Data for electric bike rate
     */

    private class ElectricRate extends BikeRate {

        @Override
        protected void setData() {
            this.firstHalf = (float)0.25;
            this.secondHalf = (float)0.50;
            this.thirdHalf = (float)1.00;
            this.fourthHalf = (float)2.00;
            this.extraHours = (float)4.00;
            this.fine = (float)150.00;
        }

    }

    /**
     * Data for electric bike with booster seat rate
     */

    private class ElectricBoosterSeatRate extends BikeRate {

        @Override
        protected void setData() {
            this.firstHalf = (float)0.25;
            this.secondHalf = (float)0.50;
            this.thirdHalf = (float)1.00;
            this.fourthHalf = (float)2.00;
            this.extraHours = (float)4.00;
            this.fine = (float)150.00;
        }

    }

}
