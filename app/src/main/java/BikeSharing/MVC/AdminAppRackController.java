package BikeSharing.MVC;

import java.util.Collection;
import java.util.Comparator;

import BikeSharing.AppSys.AppSys;
import BikeSharing.Clamp.Clamp;
import BikeSharing.Rack.Rack;
import BikeSharing.Usertypes.Administration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

public class AdminAppRackController implements ObserverInterface{

    @FXML
    private TextField rackNameText;

    @FXML
    private ChoiceBox<Rack> selectedRack;

    @FXML
    private ChoiceBox<String> selectedClampType;

    @FXML
    private ChoiceBox<Clamp> selectedClamp;

    @FXML
    private TextField editedNameText;

    @FXML
    private Button createRackButton;

    @FXML
    private Button deleteRackButton;

    @FXML
    private Button createClampButton;

    @FXML
    private Button deleteClampButton;

    @FXML
    private Button changeRackNameButton;

    private Administration user;

    private Collection<Rack> racks;

    private int clampType = -1;

    private int clampID = -1;

    private long rackID = -1;

    @FXML
    void createClamp(ActionEvent event) {
        AppSys.getSys().getRackManager(this.user).addClamp(this.rackID, this.clampType);
    }

    @FXML
    void createRack(ActionEvent event) {
        if (this.rackNameText.getText().equals("")) {
            return;
        }
        AppSys.getSys().getRackManager(this.user).addRack(this.rackNameText.getText());
        this.rackNameText.clear();
    }

    @FXML
    void deleteClamp(ActionEvent event) {
        AppSys.getSys().getRackManager(this.user).deleteClamp(this.rackID, this.clampID);
    }

    @FXML
    void deleteRack(ActionEvent event) {
        AppSys.getSys().getRackManager(this.user).deleteRack(this.rackID);
    }

    @FXML
    void changeRackName(ActionEvent event) {
        if (this.editedNameText.getText().equals("")) {
            return;
        }
        AppSys.getSys().getRackManager(this.user).editRack(this.rackID, this.editedNameText.getText());
    }

    @Override
    public void update() {
        this.racks = AppSys.getSys().getAllRacks();
        updateRackChoiceBox();
        updateClampChoiceBox();
    }

    public void setUser(Administration u) {
        this.user = u;
    }

    private void closeWindowEvent(WindowEvent event) {
        AppSys.getSys().stopListening(this);
    }

    private void initializeChoiceBoxes() {
        ObservableList<Rack> availableRacks = FXCollections.observableArrayList();
        this.selectedRack.setItems(availableRacks);
        this.selectedRack.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rack>(){

            @Override
            public void changed(ObservableValue<? extends Rack> observable, Rack oldValue, Rack newValue) {
                if (newValue == null) {
                    rackID = -1;
                    deleteRackButton.setDisable(true);
                    editedNameText.clear();
                    editedNameText.setDisable(true);
                    changeRackNameButton.setDisable(true);
                    selectedClampType.getSelectionModel().clearSelection();
                    selectedClampType.setDisable(true);
                    selectedClamp.getSelectionModel().clearSelection();
                    selectedClamp.getItems().clear();
                    selectedClamp.setDisable(true);
                } else {
                    rackID = newValue.getID();
                    deleteRackButton.setDisable(false);
                    editedNameText.setDisable(false);
                    changeRackNameButton.setDisable(false);
                    selectedClampType.setDisable(false);
                    updateClampChoiceBox();
                }
            }
            
        });

        this.selectedClampType.setItems(FXCollections.observableArrayList("0: Normal Clamp", "1: Electric Clamp", "2: Electric Booster Seat Clamp"));
        this.selectedClampType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null) {
                    createClampButton.setDisable(true);
                } else {
                    int type;
                    String split = newValue.split(":")[0];
                    type = Integer.parseInt(split);
                    clampType = type;
                    createClampButton.setDisable(false);
                }
            }
            
        });

        ObservableList<Clamp> availableClamps = FXCollections.observableArrayList();
        this.selectedClamp.setItems(availableClamps);
        this.selectedClamp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clamp>(){

            @Override
            public void changed(ObservableValue<? extends Clamp> observable, Clamp oldValue, Clamp newValue) {
                if (newValue == null) {
                    deleteClampButton.setDisable(true);
                } else {
                    clampID = newValue.id;
                    deleteClampButton.setDisable(false);
                }
            }
        });
    }

    private void updateRackChoiceBox() {

        //Update choicebox choices
        this.selectedRack.getSelectionModel().clearSelection();
        this.selectedRack.getItems().clear();
        for (Rack rack : racks) {
            this.selectedRack.getItems().add(rack);
        }
        this.selectedRack.getItems().sort(new Comparator<Rack>(){

            @Override
            public int compare(Rack o1, Rack o2) {
                return Long.compare(o1.getID(), o2.getID());
            }
            
        });

        //Update buttons activation
        if (this.selectedRack.getItems().size() > 0) {
            this.selectedRack.setDisable(false);
        } else {
            this.selectedRack.getSelectionModel().clearSelection();
            this.selectedRack.setDisable(true);
        }
    }

    public void updateClampChoiceBox() {

        //Update choicebox choice
        if (selectedRack.getSelectionModel().getSelectedItem() != null) {
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

            //Update button activation
            if (this.selectedClamp.getItems().size() > 0) {
                this.selectedClamp.setDisable(false);
            } else {
                this.selectedClamp.getSelectionModel().clearSelection();
                this.selectedClamp.setDisable(true);
            }
        }

    }

    public void init() {
        //Initialize system view
        AppSys.getSys().startListening(this);
        this.racks = AppSys.getSys().getAllRacks();
        this.rackNameText.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

        //Disable buttons
        this.selectedRack.setDisable(true);
        this.deleteRackButton.setDisable(true);
        this.editedNameText.setDisable(true);
        this.changeRackNameButton.setDisable(true);
        this.selectedClamp.setDisable(true);
        this.createClampButton.setDisable(true);
        this.selectedClampType.setDisable(true);
        this.deleteClampButton.setDisable(true);

        //Initializes choice boxes
        initializeChoiceBoxes();
        updateRackChoiceBox();
    }

}
