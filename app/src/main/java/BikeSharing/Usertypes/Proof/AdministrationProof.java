package BikeSharing.Usertypes.Proof;

/**
 * Proof that the user is an administrator
 */

public class AdministrationProof {

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

    public AdministrationProof(String usr, String psw) {
        this.username = usr;
        this.password = psw;
    }
    
}
