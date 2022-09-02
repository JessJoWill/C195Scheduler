package controller;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;

/**
 * Displays and controls the login screen.
 */
public class LoginController implements Initializable {
    public TextField userIdTxt;
    public TextField passwordTxt;
    public Label zoneIdLbl;
    public Button loginBtn;
    public Button cancelBtn;
    public Label label1;
    public Label label2;
    public Label label3;
    public Label label4;
    public String wrongLoginTitle = "Incorrect Login";
    public String wrongLoginContent = "The login you entered is not in the system.";
    public String needInputTitle = "Enter Login Information";
    public String needInputContent = "Please enter your Username and Password to log in.";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Locale.setDefault(new Locale("fr"));
            ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")){
                label1.setText(rb.getString("label1"));
                label2.setText(rb.getString("label2"));
                label3.setText(rb.getString("label3"));
                label4.setText(rb.getString("label4"));
                loginBtn.setText(rb.getString("loginBtn"));
                cancelBtn.setText(rb.getString("cancelBtn"));
                wrongLoginTitle = rb.getString("wrongLoginTitle");
                wrongLoginContent = rb.getString("wrongLoginContent");
                needInputTitle = rb.getString("needInputTitle");
                needInputContent = rb.getString("needInputContent");
            }
            getUsers();
            ZoneId zoneId = ZoneId.systemDefault();
            zoneIdLbl.setText(String.valueOf(zoneId));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLogin(ActionEvent actionEvent) throws IOException {
        if (!(userIdTxt.getText().isEmpty()) && !(passwordTxt.getText().isEmpty())) {
            String loginUser = userIdTxt.getText();
            String loginPwd = passwordTxt.getText();
            ObservableList<User> possibleList = FXCollections.observableArrayList();

            for (User user : userList) {
                if (user.getUserName().contains(loginUser)) {
                    possibleList.add(user);
                }
            }
            if(possibleList.isEmpty()){
                Alert wrongLogin = new Alert(Alert.AlertType.ERROR);
                wrongLogin.setTitle(wrongLoginTitle);
                wrongLogin.setContentText(wrongLoginContent);
                wrongLogin.showAndWait();
            }
            for (User user : possibleList) {
                if (user.getPassword().contains(loginPwd)) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/customers-view.fxml"));
                    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1000, 645);
                    primaryStage.setTitle("Scheduler");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } else {
                    Alert wrongLogin = new Alert(Alert.AlertType.ERROR);
                    wrongLogin.setTitle(wrongLoginTitle);
                    wrongLogin.setContentText(wrongLoginContent);
                    wrongLogin.showAndWait();
                }
            }
        }
        else{
                Alert needInput = new Alert(Alert.AlertType.ERROR);
                needInput.setTitle(needInputTitle);
                needInput.setContentText(needInputContent);
                needInput.showAndWait();
            }
        }


    public void onCancel(ActionEvent actionEvent) {

    }
}
