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
import javafx.stage.WindowEvent;

public class AdminAppBikeController implements ObserverInterface {

    @FXML
    private ChoiceBox<Rack> selectedRack;

    @FXML
    private ChoiceBox<Clamp> selectedClamp;

    @FXML
    private Button addBikeButton;

    @FXML
    private Button removeBikeButton;

    @FXML
    private Button startMaintenanceButton;

    @FXML
    private Button endMaintenanceButton;

    @FXML
    private ChoiceBox<Rack> selectedSourceRack;

    @FXML
    private ChoiceBox<Clamp> selectedSourceClamp;

    @FXML
    private ChoiceBox<Rack> selectedTargetRack;

    @FXML
    private ChoiceBox<Clamp> selectedTargetClamp;

    @FXML
    private Button moveBikeButton;

    private Administration user;

    private Collection<Rack> racks;

    @FXML
    void addBike(ActionEvent event) {
        AppSys.getSys().getBikeManager(this.user).addBike(this.selectedClamp.getValue());
        updateClampChoiceBoxes(this.selectedSourceClamp, this.selectedSourceRack);
        updateClampChoiceBoxes(this.selectedTargetClamp, this.selectedTargetRack);
        updateClampChoiceBoxes(this.selectedClamp, this.selectedRack);
    }

    @FXML
    void moveBike(ActionEvent event) {
        AppSys.getSys().getBikeManager(this.user).moveBike(this.selectedSourceClamp.getValue(), this.selectedTargetClamp.getValue());
        updateClampChoiceBoxes(this.selectedSourceClamp, this.selectedSourceRack);
        updateClampChoiceBoxes(this.selectedTargetClamp, this.selectedTargetRack);
        updateClampChoiceBoxes(this.selectedClamp, this.selectedRack);
    }

    @FXML
    void removeBike(ActionEvent event) {
        AppSys.getSys().getBikeManager(this.user).deleteBike(this.selectedClamp.getValue());
        updateClampChoiceBoxes(this.selectedSourceClamp, this.selectedSourceRack);
        updateClampChoiceBoxes(this.selectedTargetClamp, this.selectedTargetRack);
        updateClampChoiceBoxes(this.selectedClamp, this.selectedRack);
    }

    @FXML
    void startMaintenance(ActionEvent event) {
        AppSys.getSys().getBikeManager(this.user).startMaintenance(this.selectedClamp.getValue());
        updateClampChoiceBoxes(this.selectedSourceClamp, this.selectedSourceRack);
        updateClampChoiceBoxes(this.selectedTargetClamp, this.selectedTargetRack);
        updateClampChoiceBoxes(this.selectedClamp, this.selectedRack);
    }

    @FXML
    void endMaintenance(ActionEvent event) {
        AppSys.getSys().getBikeManager(this.user).endMaintenance(this.selectedClamp.getValue());
        updateClampChoiceBoxes(this.selectedSourceClamp, this.selectedSourceRack);
        updateClampChoiceBoxes(this.selectedTargetClamp, this.selectedTargetRack);
        updateClampChoiceBoxes(this.selectedClamp, this.selectedRack);
    }

    public void setUser(Administration u) {
        this.user = u;
    }

    public void initializeChoiceBoxes() {
        ObservableList<Rack> availableRacks = FXCollections.observableArrayList();
        this.selectedRack.setItems(availableRacks);
        this.selectedRack.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rack>(){

            @Override
            public void changed(ObservableValue<? extends Rack> observable, Rack oldValue, Rack newValue) {
                if (newValue == null) {
                    selectedClamp.getSelectionModel().clearSelection();
                    selectedClamp.setDisable(true);
                } else {
                    updateClampChoiceBoxes(selectedClamp, selectedRack);
                }
            }
            
        });

        this.selectedSourceRack.setItems(availableRacks);
        this.selectedSourceRack.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rack>(){

            @Override
            public void changed(ObservableValue<? extends Rack> observable, Rack oldValue, Rack newValue) {
                if (newValue == null) {
                    selectedSourceClamp.getSelectionModel().clearSelection();
                    selectedSourceClamp.setDisable(true);
                } else {
                    updateClampChoiceBoxes(selectedSourceClamp, selectedSourceRack);
                }
            }
            
        });

        this.selectedTargetRack.setItems(availableRacks);
        this.selectedTargetRack.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rack>(){

            @Override
            public void changed(ObservableValue<? extends Rack> observable, Rack oldValue, Rack newValue) {
                if (newValue == null) {
                    selectedTargetClamp.getSelectionModel().clearSelection();
                    selectedTargetClamp.setDisable(true);
                } else {
                    updateClampChoiceBoxes(selectedTargetClamp, selectedTargetRack);
                }
            }
            
        });

        ObservableList<Clamp> availableClamps = FXCollections.observableArrayList();
        this.selectedClamp.setItems(availableClamps);
        this.selectedClamp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clamp>(){

            @Override
            public void changed(ObservableValue<? extends Clamp> observable, Clamp oldValue, Clamp newValue) {
                if (newValue == null) {
                    addBikeButton.setDisable(true);
                    removeBikeButton.setDisable(true);
                    startMaintenanceButton.setDisable(true);
                    endMaintenanceButton.setDisable(true);
                } else {
                    addBikeButton.setDisable(false);
                    removeBikeButton.setDisable(false);
                    startMaintenanceButton.setDisable(false);
                    endMaintenanceButton.setDisable(false);
                }
            }
            
        });

        availableClamps = FXCollections.observableArrayList();
        this.selectedSourceClamp.setItems(availableClamps);
        this.selectedSourceClamp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clamp>(){

            @Override
            public void changed(ObservableValue<? extends Clamp> observable, Clamp oldValue, Clamp newValue) {
                if (newValue == null) {
                    selectedTargetRack.getSelectionModel().clearSelection();
                    selectedTargetRack.setDisable(true);
                } else {
                    selectedTargetRack.setDisable(false);
                }
            }
            
        });

        availableClamps = FXCollections.observableArrayList();
        this.selectedTargetClamp.setItems(availableClamps);
        this.selectedTargetClamp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clamp>(){

            @Override
            public void changed(ObservableValue<? extends Clamp> observable, Clamp oldValue, Clamp newValue) {
                if (newValue == null) {
                    moveBikeButton.setDisable(true);
                } else {
                    moveBikeButton.setDisable(false);
                }
            }
            
        });
    }

    public void updateRackChoiceBoxes() {
        this.selectedRack.getSelectionModel().clearSelection();
        this.selectedSourceRack.getSelectionModel().clearSelection();
        this.selectedTargetRack.getSelectionModel().clearSelection();
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
            this.selectedSourceRack.setDisable(false);
        } else {
            this.selectedRack.setDisable(true);
            this.selectedSourceRack.setDisable(true);
        }
    }

    public void updateClampChoiceBoxes(ChoiceBox<Clamp> clampBox, ChoiceBox<Rack> rackBox) {
        if (rackBox.getSelectionModel().getSelectedItem() != null) {
            clampBox.getSelectionModel().clearSelection();
            clampBox.getItems().clear();
            for (Clamp clamp : rackBox.getSelectionModel().getSelectedItem().getClamps()) {
                clampBox.getItems().add(clamp);
            }
            clampBox.getItems().sort(new Comparator<Clamp>(){

                @Override
                public int compare(Clamp o1, Clamp o2) {
                    return Integer.compare(o1.id, o2.id);
                }
                
            });

            if (clampBox.getItems().size() > 0) {
                clampBox.setDisable(false);
            } else {
                clampBox.setDisable(true);
            }
        }
    }

    @Override
    public void update() {
        this.racks = AppSys.getSys().getAllRacks();
        updateRackChoiceBoxes();
    }

    private void closeWindowEvent(WindowEvent event) {
        AppSys.getSys().stopListening(this);
    }

    public void init() {
        //Initialize system view
        AppSys.getSys().startListening(this);
        this.selectedRack.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

        //Disable everything
        this.selectedRack.setDisable(true);
        this.selectedClamp.setDisable(true);
        this.addBikeButton.setDisable(true);
        this.removeBikeButton.setDisable(true);
        this.startMaintenanceButton.setDisable(true);
        this.endMaintenanceButton.setDisable(true);
        this.selectedSourceRack.setDisable(true);
        this.selectedSourceClamp.setDisable(true);
        this.selectedTargetRack.setDisable(true);
        this.selectedTargetClamp.setDisable(true);
        this.moveBikeButton.setDisable(true);

        //update rack information
        initializeChoiceBoxes();
        update();
    }

}
