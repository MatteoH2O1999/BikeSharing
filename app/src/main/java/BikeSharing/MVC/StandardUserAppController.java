package BikeSharing.MVC;

import BikeSharing.Usertypes.StandardUser;
import BikeSharing.Usertypes.Proof.StandardUserProof;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StandardUserAppController {

    @FXML
    private CheckBox humanBool;

    @FXML
    private Button submitButton;

    private RegistrationAppController caller;

    private boolean sent;

    @FXML
    void submit(ActionEvent event) {
        if (!this.sent) {
            this.caller.returnUser(new StandardUser(new StandardUserProof(this.humanBool.isSelected())));
            this.sent = true;
        }
        Stage stage = (Stage) this.submitButton.getScene().getWindow();
        stage.close();
    }

    public void setCaller(RegistrationAppController controller) {
        this.caller = controller;
    }

    public void init() {
        this.sent = false;
        this.submitButton.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void closeWindowEvent(WindowEvent event) {
        if (!this.sent) {
            this.caller.returnUser(null);
            this.sent = true;
        }
    }

}
