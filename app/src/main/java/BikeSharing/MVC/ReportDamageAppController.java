package BikeSharing.MVC;

import BikeSharing.Rack.Totem;
import BikeSharing.Rack.Util.BikeAlreadyRentedException;
import BikeSharing.Subscription.Util.WrongLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ReportDamageAppController {

    @FXML
    private TextField subCodeText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField clampIDText;

    @FXML
    private Button reportButton;

    private Totem totem;

    @FXML
    void report(ActionEvent event) {
        String clampId = clampIDText.getText().trim();
        if (clampId.length() == 0) {
            ErrorDialogController.newErrorDialog("Invalid clamp id", "The id you provaded couldn't be parsed.", this);
            return;
        }
        int clamp = Integer.parseInt(clampId);
        try {
            this.totem.reportDamage(clamp, this.subCodeText.getText().trim(), this.passwordText.getText().trim());
        } catch (WrongLoginException e) {
            ErrorDialogController.newErrorDialog("Wrong login", "The credentials you provided are invalid.", this);
            return;
        } catch (BikeAlreadyRentedException e) {
            ErrorDialogController.newErrorDialog("Bike already rented", "The bike has already been rented to another customer. Thank you for the report.", this);
            return;
        }
        ErrorDialogController.newErrorDialog("Thank you", "Thank you for your report", this);
    }

    public void setTotem(Totem totem) {
        this.totem = totem;
    }

}
