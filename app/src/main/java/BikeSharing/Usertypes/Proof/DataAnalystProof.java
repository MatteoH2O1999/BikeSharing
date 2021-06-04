package BikeSharing.Usertypes.Proof;

/**
 * Proof that the user is a data analyst
 */

public class DataAnalystProof {

    /**
     * Username
     */

    public String username;

    /**
     * Password
     */

    public String password;

    /**
     * Constructs a proof with the specified data
     * @param usr username
     * @param psw password
     */

    public DataAnalystProof(String usr, String psw) {
        this.username = usr;
        this.password = psw;
    }

}
