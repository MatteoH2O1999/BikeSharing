package BikeSharing.Usertypes;

import BikeSharing.API.AdminAPI;
import BikeSharing.Payrate.Payrate;
import BikeSharing.Usertypes.Proof.DataAnalystProof;

/**
 * Data analyst, able to obtain data from the persistent level
 */

public class DataAnalyst extends AbstractUser {

    /**
     * Proof of eligibility
     */

    private DataAnalystProof proof;

    /**
     * Basic constructor
     * @param p proof of eligibility
     */

    public DataAnalyst(DataAnalystProof p) {
        this.proof = p;
    }

    /**
     * 
     */

    @Override
    public boolean isEligible() {
        return AdminAPI.isDataAnalyst(this.proof);
    }

    /**
     * 
     */

    @Override
    public Payrate getPayrate() {
        throw new RuntimeException("Unavailable method for this class");
    }
    
}
