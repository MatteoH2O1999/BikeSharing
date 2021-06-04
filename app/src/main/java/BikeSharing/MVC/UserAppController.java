package BikeSharing.MVC;

import java.util.Collection;
import java.util.Comparator;

import BikeSharing.AppSys.AppSys;
import BikeSharing.Bike.Bike;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Rack.Rack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserAppController implements ObserverInterface {

    @FXML
    private Text userBike;

    @FXML
    private ChoiceBox<Rack> selectedRack;

    @FXML
    private ChoiceBox<Clamp> selectedClamp;

    @FXML
    private Button parkBikeButton;

    @FXML
    private Button takeBikeButton;

    @FXML
    private Button openTotemButton;

    private Bike bike;

    private Collection<Rack> racks;

    @FXML
    void openTotem(ActionEvent event) {
        if (this.selectedRack.getSelectionModel().getSelectedItem() == null) {
            throw new RuntimeException();
        }
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("totemapp.fxml"));
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        TotemAppController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("\"" + this.selectedRack.getSelectionModel().getSelectedItem().getName() + "\" Totem");
        stage.setScene(new Scene(root));
        controller.setTotem(this.selectedRack.getSelectionModel().getSelectedItem().getTotem());
        stage.show();
    }

    @FXML
    void parkBike(ActionEvent event) {
        if (this.bike == null) {
            return;
        }
        this.selectedClamp.getSelectionModel().getSelectedItem().park(this.bike);
        if (this.selectedClamp.getSelectionModel().getSelectedItem().readBike() == this.bike) {
            this.bike = null;
            updateBikeText();
        }
        updateClampChoiceBox();
    }

    @FXML
    void takeBike(ActionEvent event) {
        if (this.bike != null) {
            return;
        }
        this.bike = this.selectedClamp.getSelectionModel().getSelectedItem().take();
        updateBikeText();
        updateClampChoiceBox();
    }

    private void updateRackChoiceBox() {
        this.selectedRack.getSelectionModel().clearSelection();
        this.selectedRack.getItems().clear();
        for (Rack rack : this.racks) {
            this.selectedRack.getItems().add(rack);
        }
        this.selectedRack.getItems().sort(new Comparator<Rack>(){

            @Override
            public int compare(Rack o1, Rack o2) {
                return Long.compare(o1.getID(), o2.getID());
            }
            
        });

        if (this.selectedRack.getItems().size() > 0) {
            this.selectedRack.setDisable(false);
        } else {
            this.selectedRack.getSelectionModel().clearSelection();
            this.selectedRack.setDisable(true);
        }
    }

    private void updateClampChoiceBox() {
        if (this.selectedRack.getSelectionModel().getSelectedItem() != null) {
            this.selectedClamp.getSelectionModel().clearSelection();
            this.selectedClamp.getItems().clear();
            for (Clamp clamp : this.selectedRack.getSelectionModel().getSelectedItem().getClamps()) {
                this.selectedClamp.getItems().add(clamp);
            }
            this.selectedClamp.getItems().sort(new Comparator<Clamp>(){

                @Override
                public int compare(Clamp o1, Clamp o2) {
                    return Integer.compare(o1.id, o2.id);
                }
                
            });
        }

        if (this.selectedClamp.getItems().size() > 0) {
            this.selectedClamp.setDisable(false);
        } else {
            this.selectedClamp.getSelectionModel().clearSelection();
            this.selectedClamp.setDisable(true);
        }
    }

    private void initChoiceBoxes() {
        ObservableList<Rack> availableRacks = FXCollections.observableArrayList();
        this.selectedRack.setItems(availableRacks);
        this.selectedRack.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rack>(){

            @Override
            public void changed(ObservableValue<? extends Rack> observable, Rack oldValue, Rack newValue) {
                updateBikeText();
                if (newValue == null) {
                    selectedClamp.getSelectionModel().clearSelection();
                    selectedClamp.setDisable(true);
                    openTotemButton.setDisable(true);
                } else {
                    openTotemButton.setDisable(false);
                    updateClampChoiceBox();
                }
            }
            
        });

        ObservableList<Clamp> availableClamps = FXCollections.observableArrayList();
        this.selectedClamp.setItems(availableClamps);
        this.selectedClamp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clamp>(){

            @Override
            public void changed(ObservableValue<? extends Clamp> observable, Clamp oldValue, Clamp newValue) {
                updateBikeText();
                if (newValue == null) {
                    takeBikeButton.setDisable(true);
                    parkBikeButton.setDisable(true);
                } else {
                    takeBikeButton.setDisable(false);
                    parkBikeButton.setDisable(false);
                }
            }
            
        });
    }

    @Override
    public void update() {
        this.racks = AppSys.getSys().getAllRacks();
        updateRackChoiceBox();
    }

    private void updateBikeText() {
        if (this.bike == null) {
            this.userBike.setText("");
        } else {
            this.userBike.setText(this.bike.toString());
        }
    }

    private void closeWindowEvent(WindowEvent event) {
        AppSys.getSys().stopListening(this);
    }

    public void init() {
        AppSys.getSys().startListening(this);
        this.openTotemButton.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

        updateBikeText();
        initChoiceBoxes();
        update();


        this.openTotemButton.setDisable(true);
        this.parkBikeButton.setDisable(true);
        this.takeBikeButton.setDisable(true);
        this.selectedClamp.setDisable(true);
    }

}
