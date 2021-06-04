package BikeSharing.MVC;

import BikeSharing.Bike.BikeBuilder;
import BikeSharing.Rack.Totem;
import BikeSharing.Rack.Util.NoFreeBikesException;
import BikeSharing.Subscription.Util.InvalidSubscriptionException;
import BikeSharing.Subscription.Util.WrongLoginException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RentAppController {

    @FXML
    private TextField subCodeText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private ChoiceBox<String> selectedBikeType;

    @FXML
    private Button rentButton;

    private Totem totem;

    private int bikeType = -1;

    @FXML
    void rent(ActionEvent event) {
        String password = "" + this.passwordText.getText().trim();
        String subCode = "" + this.subCodeText.getText().trim();
        int clampId;
        try {
            clampId = totem.rentBike(BikeBuilder.decode(this.bikeType), subCode, password);
        } catch (WrongLoginException e) {
            ErrorDialogController.newErrorDialog("Wrong login", "The credentials you provided are invalid.", this);
            return;
        } catch (InvalidSubscriptionException e) {
            ErrorDialogController.newErrorDialog("Invalid subscription", "Your subscription is not suitable for a new rent.", this);
            return;
        } catch (NoFreeBikesException e) {
            ErrorDialogController.newErrorDialog("No available bikes", "There are no available bikes of the selected type in this rack.", this);
            return;
        }
        ErrorDialogController.newErrorDialog("Rent successfull", "You have successfully rented a bike. Proceed to the specified clamp to take it:\n\n" + Integer.toString(clampId), this);
        return;
    }

    public void setTotem(Totem totem) {
        this.totem = totem;
    }

    public void init() {
        initChoiceBox();
    }

    private void initChoiceBox() {
        ObservableList<String> bikeTypes = FXCollections.observableArrayList("0: Normal Bike", "1: Electric Bike", "2: Electric Bike with Booster Seat");
        this.selectedBikeType.setItems(bikeTypes);
        this.selectedBikeType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    int type;
                    type = Integer.parseInt(newValue.split(":")[0].trim());
                    bikeType = type;
                }
            }
            
        });
    }

}
