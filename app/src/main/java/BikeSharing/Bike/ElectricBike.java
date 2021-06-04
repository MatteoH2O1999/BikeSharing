package BikeSharing.Bike;

import java.lang.instrument.IllegalClassFormatException;

import BikeSharing.Bike.Util.ElectricConsumption;

/**
 * Electric bike. Has a battery that can be charged.
 */

public class ElectricBike extends Bike {

    /**
     * The current charge of the battery. Must always be within 0 and 100.
     */

    private int charge;

    /**
     * Thread that simulates the discharginng of the battery.
     */

    private ElectricConsumption battery;

    /**
     * Creates a new electric bike with the specified charge level.
     * @param ch the bike's charge.
     */

    public ElectricBike(int ch) {

        if((ch > 100) | (ch < 0)){
            throw new IllegalArgumentException("Not a valid charge");
        }
        this.charge = ch;
        this.initSubscription();
        return;

    }

    /**
     * Sets the battery to the maximum charge.
     */

    public void charge() {

        this.charge = 100;

    }

    /**
     * Simulates the discharging of the battery one percentage at a time.
     * @throws IllegalClassFormatException if battery is exhausted.
     */

    public void consume() throws IllegalClassFormatException {

        if(this.charge < 1) {
            throw new IllegalClassFormatException("Battery is exhausted");
        }
        this.charge--;
        return;

    }

    /**
     * Starts the discharging of the battery.
     */

    public void startBattery() {

        battery = new ElectricConsumption(this);
        battery.start();
        return;

    }

    /**
     * Stops the discharging of the battery.
     */

    public void stopBattery() {

        battery.stopSimulation();
        return;

    }

    @Override
    public int encode() {
        return 1;
    }

    @Override
    public String toString() {
        return super.toString() + " " + Integer.toString(this.charge) + "%";
    }
    
}
