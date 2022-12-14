package controller;

import Utilities.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.AppointmentsQuery.contactAppointments;
import static Utilities.ContactsQuery.contactList;
import static Utilities.ContactsQuery.getContacts;

/**
 * Displays and controls the Contact Schedule screen.
 */
public class ContactApptsReportController implements Initializable {
    public ComboBox<Contact> contactsCombo;
    public TableView<Appointment> contactTableView;
    public TableColumn<Appointment, Integer> apptIdCol;
    public TableColumn<Appointment, String> apptTitleCol;
    public TableColumn<Appointment, String> apptDescriptionCol;
    public TableColumn<Appointment, String> apptLocationCol;
    public TableColumn<Appointment, String> apptTypeCol;
    public TableColumn<Appointment, Timestamp> apptStartCol;
    public TableColumn<Appointment, Timestamp> apptEndCol;
    public TableColumn<Appointment, Integer> apptCustomerIdCol;
    public Button userApptBtn;
    public Button custByRegionBtn;
    public Button custApptBtn;
    public Button mainMenuBtn;
    public static int selContactId;
    public AnchorPane anchor;
    public Label countLabel;

    /**
     * Populates the contacts combobox.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getContacts();
            contactsCombo.setItems(contactList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the tableview with the selected contact's scheduled appointments.
     */
    public void fillApptTable(ActionEvent actionEvent) {
        selContactId = contactsCombo.getSelectionModel().getSelectedItem().getContactId();
        try {
            AppointmentsQuery.contactSelect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactTableView.setItems(contactAppointments);

        // Populating the table views
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Takes the user to the User Schedule report screen.
     */
        public void toUserApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user-appt-report-view.fxml")));
        Stage primaryStage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the Customers By Region report screen.
     */
    public void toCustByRegion(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-by-region-view.fxml")));
        Stage primaryStage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the Customer Appointments report screen.
     */
    public void toCustApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer-appts-report-view.fxml")));
        Stage primaryStage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user back to the main Customers screen.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
