package BikeSharing.MVC;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorDialogController {

    @FXML
    private Text errorMessage;

    @FXML
    private Button closeButton;

    @FXML
    void close(ActionEvent event) {
        
        Stage s = (Stage) this.closeButton.getScene().getWindow();
        s.close();

    }

    public void setError(String error) {
        this.errorMessage.setText(error);
    }

    public static void newErrorDialog(String title, String message, Object obj) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(obj.getClass().getResource("errordialog.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        ErrorDialogController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        controller.setError(message);
        stage.show();
        return;
    }

}
