package BikeSharing.MVC;

import BikeSharing.Rack.Totem;
import BikeSharing.Subscription.Util.WrongLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ReturnAppController {

    @FXML
    private TextField subCodeText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button checkButton;

    private Totem totem;

    @FXML
    void check(ActionEvent event) {
        boolean check;
        try {
            check = this.totem.returnBike(this.subCodeText.getText().trim(), this.passwordText.getText().trim());
        } catch (WrongLoginException e) {
            ErrorDialogController.newErrorDialog("Wrong login", "The credentials you provided are invalid", this);
            return;
        }
        if (check) {
            ErrorDialogController.newErrorDialog("Bike successfully returned", "Your last rented bike was successfully returned", this);
            return;
        } else {
            ErrorDialogController.newErrorDialog("Bike not retured", "Your last rented bike hasn't been returned yet", this);
        }
    }

    public void setTotem(Totem totem) {
        this.totem = totem;
    }

}
