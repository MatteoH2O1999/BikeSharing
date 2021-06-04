package BikeSharing.MVC;

import java.io.IOException;

import BikeSharing.Usertypes.Administration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminAppController {

    @FXML
    private Button rackButton;

    @FXML
    private Button bikeButton;

    private Administration user;

    @FXML
    void bikeButtonClick(ActionEvent event) {

        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminappbike.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Stage stage = new Stage();
        stage.setTitle("Bike Editor");
        stage.setScene(new Scene(root));
        AdminAppBikeController controller = loader.getController();
        controller.setUser(this.user);
        controller.init();
        stage.show();
        
    }

    @FXML
    void rackButtonClick(ActionEvent event) {

        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminapprack.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Stage stage = new Stage();
        stage.setTitle("Rack Editor");
        stage.setScene(new Scene(root));
        AdminAppRackController controller = loader.getController();
        controller.setUser(this.user);
        controller.init();
        stage.show();

    }

    public void setUser(Administration u) {
        this.user = u;
    }

}
