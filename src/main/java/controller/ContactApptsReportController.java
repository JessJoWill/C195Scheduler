package controller;

import Utilities.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class ContactApptsReportController implements Initializable {
    public ComboBox<Contact> contactsCombo;
    public TableView<Appointment> contactTableView;
    public TableColumn<Appointment, Integer> apptIdCol;
    public TableColumn<Appointment, String> apptTitleCol;
    public TableColumn<Appointment, String> apptDescriptionCol;
    public TableColumn<Appointment, String> apptLocationCol;
    public TableColumn<Appointment, String> apptContactCol;
    public TableColumn<Appointment, String> apptTypeCol;
    public TableColumn<Appointment, Timestamp> apptStartCol;
    public TableColumn<Appointment, Timestamp> apptEndCol;
    public TableColumn<Appointment, Integer> apptCustomerIdCol;
    public TableColumn<Appointment, Integer> apptUserIdCol;
    public Button userApptBtn;
    public Button custByRegionBtn;
    public Button custApptBtn;
    public Button mainMenuBtn;
    public static int selContactId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getContacts();
            contactsCombo.setItems(contactList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillApptTable(ActionEvent actionEvent) {
        selContactId = contactsCombo.getSelectionModel().getSelectedItem().getContactId();
        System.out.println(selContactId);
        try {
            AppointmentsQuery.contactSelect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(contactAppointments);
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

    public void toUserApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toCustByRegion(ActionEvent actionEvent) throws IOException {Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-by-region-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toCustApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer-appts-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
