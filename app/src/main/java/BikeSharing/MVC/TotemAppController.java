package BikeSharing.MVC;

import java.io.IOException;

import BikeSharing.Rack.Totem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TotemAppController {

    @FXML
    private Button registerSubscriptionButton;

    @FXML
    private Button rentBikeButton;

    @FXML
    private Button returnBikeButton;

    @FXML
    private Button reportDamageButton;

    private Totem totem;

    @FXML
    void registerSubscription(ActionEvent event) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registrationapp.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        RegistrationAppController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Create subscription");
        stage.setScene(new Scene(root));
        controller.setTotem(this.totem);
        controller.init();
        stage.show();
    }

    @FXML
    void rentBike(ActionEvent event) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rentapp.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        RentAppController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Rent bike");
        stage.setScene(new Scene(root));
        controller.setTotem(this.totem);
        controller.init();
        stage.show();
    }

    @FXML
    void reportDamage(ActionEvent event) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("damageapp.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        ReportDamageAppController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Report a damage");
        stage.setScene(new Scene(root));
        controller.setTotem(this.totem);
        stage.show();
    }

    @FXML
    void returnBike(ActionEvent event) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("returnapp.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        ReturnAppController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Return check");
        stage.setScene(new Scene(root));
        controller.setTotem(this.totem);
        stage.show();
    }

    public void setTotem(Totem t) {
        this.totem = t;
    }

}
