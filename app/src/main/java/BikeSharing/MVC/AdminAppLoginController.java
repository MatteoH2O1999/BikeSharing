package BikeSharing.MVC;

import java.io.IOException;

import BikeSharing.Usertypes.Administration;
import BikeSharing.Usertypes.Proof.AdministrationProof;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminAppLoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    void login(ActionEvent event) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        AdministrationProof proof = new AdministrationProof(username, password);
        Administration user = new Administration(proof);
        if (!user.isEligible()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("errordialog.fxml"));
            Parent dialog;
            try {
                dialog = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            ErrorDialogController controller = loader.getController();
            Stage errorDialog = new Stage();
            errorDialog.setTitle("Wrong login");
            errorDialog.setScene(new Scene(dialog));
            controller.setError("Invalid username or wrong password");
            errorDialog.show();
            return;
        }

        Parent root;
        FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("adminapp.fxml"));
        try {
            root = adminLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        AdminAppController adminController = adminLoader.getController();
        Stage stage = new Stage();
        stage.setTitle("Administration Application");
        stage.setScene(new Scene(root));
        adminController.setUser(user);
        stage.show();

        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();

    }

}
