package BikeSharing.MVC;

import java.io.IOException;

import BikeSharing.Usertypes.DataAnalyst;
import BikeSharing.Usertypes.Proof.DataAnalystProof;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DataAppLoginController {

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

        DataAnalystProof proof = new DataAnalystProof(username, password);
        DataAnalyst user = new DataAnalyst(proof);
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
        FXMLLoader dataLoader = new FXMLLoader(getClass().getResource("dataapp.fxml"));
        try {
            root = dataLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Stage stage = new Stage();
        stage.setTitle("Data Analysis Application");
        stage.setScene(new Scene(root));
        DataAppController dataController = dataLoader.getController();
        dataController.setUser(user);
        stage.show();

        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();

    }

}
