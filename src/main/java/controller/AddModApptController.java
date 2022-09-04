package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static Utilities.ContactsQuery.contactList;
import static Utilities.ContactsQuery.getContacts;
import static Utilities.CustomersQuery.tableCustomers;
import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;

public class AddModApptController implements Initializable {
    public Label newUpdatedApptLbl;
    public Label apptIdLbl;
    public TextField apptTitleTxt;
    public TextField apptLocationTxt;
    public TextField apptTypeTxt;
    public TextField apptDescriptionTxt;
    public ComboBox apptContactList;
    public Button saveApptBtn;
    public Button cancelBtn;
    public DatePicker apptDatePicker;
    public ComboBox customerListCombo;
    public Label apptIdValueLbl;
    public TextField apptStartTxt;
    public TextField apptDurationTxt;
    public ComboBox usersCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (CustomerApptsController.apptAddMod == "add"){
            newUpdatedApptLbl.setText("Create Appointment");
            apptIdLbl.setVisible(false);
        }
        if (CustomerApptsController.apptAddMod == "mod"){
            newUpdatedApptLbl.setText("Update Appointment");
        }

        try {
            getUsers();
            getContacts();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerListCombo.setItems(tableCustomers);
        apptContactList.setItems(contactList);
        usersCombo.setItems(userList);


    }

    private void showTimes() {

    }

    public void onSaveAppt(ActionEvent actionEvent) throws IOException, SQLException {
        String title = apptTitleTxt.getText();
        String description = apptDescriptionTxt.getText();
        String location = apptLocationTxt.getText();
        String contactId = apptContactList.getValue().toString();
        String type = apptTypeTxt.getText();
        String apptDate = apptDatePicker.getValue().toString();
        String apptTime = apptStartTxt.getText();
        LocalDateTime createDate = LocalDateTime.now();

        LocalDateTime start = LocalDateTime.parse(apptDate + "T" + apptTime);
        System.out.println(start);

        /*int rowsAffected = AppointmentsQuery.insert(title, description, location, contactId, type, start, end, createDate, customerId, userId);

        if(rowsAffected == 0){
            System.out.println("Something went wrong.");
        }*/
        // Back to main screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/customers-view.fxml"));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void getTimes(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customers-view.fxml"));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
