package BikeSharing.Payrate.Util;

/**
 * Contains rates for bike type
 */

public abstract class BikeRate {

    /**
     * Rate for the first half hour
     */

    protected float firstHalf;

    /**
     * Rate for the second half hour
     */

    protected float secondHalf;

    /**
     * Rate for the third half hour
     */

    protected float thirdHalf;

    /**
     * Rate for the fourth half hour
     */

    protected float fourthHalf;

    /**
     * Rate for every extra hour or portion of it
     */

    protected float extraHours;

    /**
     * Fine amount for not returning the bike in 24 hours
     */

    public float fine;

    /**
     * Creates a rate initializing the fields
     */

    public BikeRate() {
        this.setData();
        return;
    }

    /**
     * Gets the rate for the first half hour
     * @return the rate for the first half hour
     */

    public float getFirstHalf() {
        return this.firstHalf;
    }

    /**
     * Gets the rate for the second half hour
     * @return the rate for the second half hour
     */

    public float getSecondHalf() {
        return this.secondHalf;
    }

    /**
     * Gets the rate for the third half hour
     * @return the rate for the third half hour
     */

    public float getThirdHalf() {
        return this.thirdHalf;
    }

    /**
     * Gets the rate for the fourth half hour
     * @return the rate for the fourth half hour
     */

    public float getFourthHalf() {
        return this.fourthHalf;
    }

    /**
     * Gets the rate for every extra hour
     * @return the rate for every extra hour
     */

    public float getExtraHours() {
        return this.extraHours;
    }

    /**
     * Gets the fine amount for not returning the bike in 24 hours
     * @return the fine amount for not returning the bike in 24 hours
     */

    public float getFine() {
        return this.fine;
    }

    /**
     * Sets the data for the fields
     */

    protected abstract void setData();
    
}
