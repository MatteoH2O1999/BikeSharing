package BikeSharing.MVC;

import java.io.File;

import BikeSharing.AppSys.AppSys;
import BikeSharing.Usertypes.DataAnalyst;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class DataAppController {

    @FXML
    private TextField rentPath;

    @FXML
    private TextField subscriptionPath;

    @FXML
    private Button rentDirectoryChooser;

    @FXML
    private Button subscriptionDirectoryChooser;

    @FXML
    private Button writeRentDataButton;

    @FXML
    private Button writeSubscriptionDataButton;

    private DataAnalyst user;

    @FXML
    void chooseRentDirectory(ActionEvent event) {
        DirectoryChooser rentDirChooser = new DirectoryChooser();
        File selectedDir = rentDirChooser.showDialog(this.rentDirectoryChooser.getScene().getWindow());
        try {
            this.rentPath.setText(selectedDir.getAbsolutePath());
        } catch (Exception e) {
        }
    }

    @FXML
    void chooseSubscriptionDirectory(ActionEvent event) {
        DirectoryChooser subDirChooser = new DirectoryChooser();
        File selectedDir = subDirChooser.showDialog(this.subscriptionDirectoryChooser.getScene().getWindow());
        this.subscriptionPath.setText(selectedDir.getAbsolutePath());
    }

    @FXML
    void writeRentData(ActionEvent event) {
        AppSys.getSys().getDataManager(user).writeRentData(this.rentPath.getText());
    }

    @FXML
    void writeSubscriptionData(ActionEvent event) {
        AppSys.getSys().getDataManager(user).writeSubscriptionData(this.subscriptionPath.getText());
    }

    public void setUser(DataAnalyst u) {
        this.user = u;
    }

}
