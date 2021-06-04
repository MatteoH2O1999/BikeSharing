package BikeSharing.API;

import BikeSharing.Usertypes.Proof.AdministrationProof;
import BikeSharing.Usertypes.Proof.DataAnalystProof;

/**
 * Interacts with the administration database to recognize administrators
 */

public class AdminAPI {

    /**
     * Returns true if the user is a data analyst
     * @param proof proof of eligibility
     * @return true if the user is a data analyst
     */

    public static boolean isDataAnalyst(DataAnalystProof proof) {
        if ((proof.username.equals("data")) & (proof.password.equals("analyst"))) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the user is an administrator
     * @param proof proof of eligibility
     * @return true if the user is an administrator
     */

    public static boolean isAdministration(AdministrationProof proof) {
        if ((proof.username.equals("admin")) & (proof.password.equals("123"))) {
            return true;
        }
        return false;
    }
    
}
