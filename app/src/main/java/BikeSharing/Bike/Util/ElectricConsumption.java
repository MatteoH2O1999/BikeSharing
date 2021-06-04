package BikeSharing.Bike.Util;

import java.lang.instrument.IllegalClassFormatException;

import BikeSharing.Bike.ElectricBike;

/**
 * Simulates the discharging of the battery
 */

public class ElectricConsumption extends Thread {

    /**
     * The bike that is consuming the battery.
     */

    ElectricBike bike;

    /**
     * Used to stop the simulation.
     */

    boolean cont = true;

    /**
     * Creates a thread to simulate battery consumption
     * 
     * @param b the bike to simulate
     */

    public ElectricConsumption(ElectricBike b) {

        bike = b;
        return;

    }

    @Override
    public void run() {

        while (cont) {

            try {
                sleep(5000);
            } catch (InterruptedException e1) {
                continue;
            }

            try {
                bike.consume();
            } catch (IllegalClassFormatException e) {
                this.cont = false;
            }
            
        }
    }

    /**
     * Stops the simulation.
     */

    public void stopSimulation() {

        this.cont = false;
        return;

    }
    
}
