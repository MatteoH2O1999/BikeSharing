package BikeSharing.MVC;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import BikeSharing.Rack.Totem;
import BikeSharing.Subscription.Util.DuplicateCardException;
import BikeSharing.Subscription.Util.ExpirationException;
import BikeSharing.Usertypes.AbstractUser;
import BikeSharing.Usertypes.Util.ProofException;
import BikeSharing.Util.CreditCard;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistrationAppController {

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField cardNumberText;

    @FXML
    private TextField cvvText;

    @FXML
    private DatePicker cardExpirationDate;

    @FXML
    private ChoiceBox<String> selectedUserType;

    @FXML
    private Text passwordStatus;

    @FXML
    private Text cardNumberStatus;

    @FXML
    private Text cvvStatus;

    @FXML
    private Text expirationStatus;

    @FXML
    private Text userStatus;

    @FXML
    private ChoiceBox<String> selectedSubscriptionType;

    @FXML
    private Text subscriptionStatus;

    @FXML
    private Button submitButton;

    private Totem totem;

    private long cardNumber;

    private int cvv;

    private String password;

    private AbstractUser user;

    private int subType;

    private int expMonth;

    private int expYear;

    @FXML
    void submit(ActionEvent event) {
        String subCode;
        try {
            subCode = totem.register(this.password, new CreditCard(this.cardNumber, this.cvv, this.expYear, this.expMonth), this.user, this.subType);
        } catch (NullPointerException e) {
            e.printStackTrace();
            ErrorDialogController.newErrorDialog("Null parameters", "One of the parameters passed is null.", this);
            return;
        } catch (ProofException e) {
            ErrorDialogController.newErrorDialog("Uneligible for class", "You are not eligible for the selected class of service.", this);
            return;
        } catch (ExpirationException e) {
            ErrorDialogController.newErrorDialog("Card expiration", "Your card is not suitable for the type of subscription you selected or is already expired.", this);
            return;
        } catch (DuplicateCardException e) {
            ErrorDialogController.newErrorDialog("Duplicate card", "You have already an active subscription. Only one subscription per credit card is allowed.", this);
            return;
        } catch (RuntimeException e) {
            ErrorDialogController.newErrorDialog("Invalid card", "The parameters you entered are invalid for a credit card", this);
            return;
        }
        ErrorDialogController.newErrorDialog("Subscription registered", "Subscription registered successfully.\nYour subscription code is:\n\n" + subCode, this);
    }

    @FXML
    void updateCardNumber(KeyEvent event) {
        if (StringUtils.isNumeric(this.cardNumberText.getText())) {
            if ((this.cardNumberText.getText().trim().length() == 13) || (this.cardNumberText.getText().trim().length() == 16)) {
                this.cardNumberStatus.setText("OK");
                this.cardNumber = Long.parseLong(this.cardNumberText.getText());
                this.checkCompleted();
                return;
            }
        }
        this.cardNumberStatus.setText("Required Field");
        this.checkCompleted();
    }

    @FXML
    void updateCvv(KeyEvent event) {
        if (StringUtils.isNumeric(this.cvvText.getText())) {
            if ((Integer.parseInt(this.cvvText.getText()) > 99) && (Integer.parseInt(this.cvvText.getText()) < 1000) ) {
                this.cvvStatus.setText("OK");
                this.cvv = Integer.parseInt(this.cvvText.getText());
                this.checkCompleted();
                return;
            }
        }
        this.cvvStatus.setText("Required Field");
        this.checkCompleted();
    }

    @FXML
    void updateExpiration(ActionEvent event) {
        if (this.cardExpirationDate.getValue() != null) {
            this.expMonth = this.cardExpirationDate.getValue().getMonth().getValue() - 1;
            this.expYear = this.cardExpirationDate.getValue().getYear();
            this.expirationStatus.setText("OK");
            this.checkCompleted();
            return;
        }
        this.expirationStatus.setText("Required Field");
        this.checkCompleted();
    }

    @FXML
    void updatePassword(KeyEvent event) {
        if (this.passwordText.getText().trim().length() > 8) {
            if (this.passwordText.getText().trim().length() <= 20) {
                this.password = this.passwordText.getText().trim();
                this.passwordStatus.setText("OK");
                this.checkCompleted();
                return;
            }
        }
        this.passwordStatus.setText("Required Field");
        this.checkCompleted();
    }

    public void init () {
        this.passwordStatus.setText("Required Field");
        this.cardNumberStatus.setText("Required Field");
        this.cvvStatus.setText("Required Field");
        this.expirationStatus.setText("Required Field");
        this.userStatus.setText("Required Field");
        this.subscriptionStatus.setText("Required Field");
        this.initChoiceBoxes();
        this.checkCompleted();
    }

    private void checkCompleted() {
        if (this.passwordStatus.getText().equals("OK")) {
            if (this.cardNumberStatus.getText().equals("OK")) {
                if (this.cvvStatus.getText().equals("OK")) {
                    if (this.expirationStatus.getText().equals("OK")) {
                        if (this.userStatus.getText().equals("OK")) {
                            if (this.subscriptionStatus.getText().equals("OK")) {
                                this.submitButton.setDisable(false);
                                return;
                            }                            
                        }
                    }
                }
            }
        }
        submitButton.setDisable(true);
    }

    private void initChoiceBoxes() {
        ObservableList<String> users = FXCollections.observableArrayList("0: Standard User", "1: Student");
        this.selectedUserType.setItems(users);
        this.selectedUserType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null) {
                    userStatus.setText("Required Field");
                    checkCompleted();
                } else {
                    int type = Integer.parseInt(newValue.split(":")[0].trim());
                    selectedUserType.setDisable(true);
                    switch (type) {
                        case 0:
                            Parent stdRoot;
                            FXMLLoader stdLoader = new FXMLLoader(getClass().getResource("standarduserapp.fxml"));
                            try {
                                stdRoot = stdLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new RuntimeException();
                            }
                            Stage stdStage = new Stage();
                            stdStage.setTitle("Standard user");
                            stdStage.setScene(new Scene(stdRoot));
                            StandardUserAppController stdController = stdLoader.getController();
                            stdController.init();
                            stdController.setCaller(RegistrationAppController.this);
                            stdStage.show();
                            break;
                        case 1:
                            Parent studentRoot;
                            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("studentapp.fxml"));
                            try {
                                studentRoot = studentLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new RuntimeException();
                            }
                            Stage studentStage = new Stage();
                            studentStage.setTitle("Student");
                            studentStage.setScene(new Scene(studentRoot));
                            StudentAppController studentController = studentLoader.getController();
                            studentController.init();
                            studentController.setCaller(RegistrationAppController.this);
                            studentStage.show();
                            break;
                        default:
                            throw new RuntimeException();
                    }
                }
            }
            
        });

        ObservableList<String> subTypes = FXCollections.observableArrayList("0: Yearly subscription", "1: Weekly subscription", "2: Daily subscription");
        this.selectedSubscriptionType.setItems(subTypes);
        this.selectedSubscriptionType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null) {
                    subscriptionStatus.setText("Required Field");
                    checkCompleted();
                } else {
                    int type = Integer.parseInt(newValue.split(":")[0].trim());
                    subType = type;
                    subscriptionStatus.setText("OK");
                    checkCompleted();
                }
            }
            
        });
    }

    public void returnUser(AbstractUser usr) {
        this.selectedUserType.setDisable(false);
        this.user = usr;
        if (this.user == null) {
            this.userStatus.setText("Required Field");
            this.selectedUserType.getSelectionModel().clearSelection();
            ErrorDialogController.newErrorDialog("Error while creating user profile", "An error occurred while creating you user profile. Please try again", this);
        } else {
            this.userStatus.setText("OK");
        }
        this.checkCompleted();
    }

    public void setTotem(Totem totem) {
        this.totem = totem;
    }

}
