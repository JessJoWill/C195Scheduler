package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Displays and controls the login screen.
 */
public class LoginController implements Initializable {
    public TextField userIdTxt;
    public TextField passwordTxt;
    public Label zoneIdLbl;
    public Button loginBtn;
    public Button cancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onLogin(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) {

    }
}
