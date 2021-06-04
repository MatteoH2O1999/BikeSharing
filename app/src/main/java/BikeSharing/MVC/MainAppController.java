package BikeSharing.MVC;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainAppController {

    @FXML
    private Button AdminButton;

    @FXML
    private Button UserButton;

    @FXML
    private Button DataButton;

    @FXML
    void handleAdminButtonClick(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("adminapplogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void handleDataButtonClick(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("dataapplogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void handleUserButtonClick(ActionEvent event) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userapp.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        UserAppController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("User Application");
        stage.setScene(new Scene(root));
        controller.init();
        stage.show();
    }

}
