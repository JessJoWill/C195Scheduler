package controller;

import Utilities.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.AppointmentsQuery.customerAppointments;
import static controller.CustomersController.*;

/**
 * Displays and controls the Customer Appointments screen.
 */
public class CustomerApptsController implements Initializable {
    public Label customerIdLbl;
    public Label customerNameLbl;
    public TableView<Appointment> customerApptsTableView;
    public TableColumn<Appointment, Integer> customerApptIdCol;
    public TableColumn<Appointment, String> customerApptTitleCol;
    public TableColumn<Appointment, String> customerApptDescriptionCol;
    public TableColumn<Appointment, String> customerApptLocationCol;
    public TableColumn<Appointment, String> customerApptContactCol;
    public TableColumn<Appointment, String> customerApptTypeCol;
    public TableColumn<Appointment, Timestamp> customerApptStartCol;
    public TableColumn<Appointment, Timestamp> customerApptEndCol;
    public TableColumn<Appointment, Integer> customerApptUserIdCol;
    public TableColumn<Appointment, Integer> customerApptCustIdCol;
    public Button addApptBtn;
    public Button modifyApptBtn;
    public Button cancelApptBtn;
    public static String apptAddMod;
    public static Appointment selectedAppointment;
    public Button backBtn;
    public Button viewReportsBtn;

    /**
     * Runs the method to fill the table view and sets the customer name and ID labels.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillApptTable();
        customerIdLbl.setText(String.valueOf(selCustomerId));
        customerNameLbl.setText(selCustomerName);
    }

    /**
     * Populates the Appointments tableview.
     */
    private void fillApptTable() {
        try {
            AppointmentsQuery.select();

            customerApptsTableView.setItems(customerAppointments);

            // Populating the table views
            customerApptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
            customerApptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            customerApptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            customerApptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            customerApptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            customerApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            customerApptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            customerApptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerApptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerApptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the user to the Add/Modify Appointments screen, and specifies that an appointment is to be added.
     */
    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        apptAddMod = "add";
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add-mod-appt-view.fxml")));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the Add/Modify Appointments screen and specifies that an appointment is to be modified.
     */
    public void onModifyAppt(ActionEvent actionEvent) throws IOException {
        apptAddMod = "mod";
        selectedAppointment = customerApptsTableView.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add-mod-appt-view.fxml")));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Gets confirmation from the user before canceling an appointment, and shows confirmation afterward.
     */
    public void onDeleteAppt(ActionEvent actionEvent) {
        Appointment selectedAppointment = customerApptsTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Cancel Error");
            noSelection.setContentText("You have not selected an appointment to cancel.");
            noSelection.showAndWait();
        }
        else {
            Alert cancelApptAlert = new Alert(Alert.AlertType.CONFIRMATION);
            cancelApptAlert.setTitle("Confirm Delete");
            cancelApptAlert.setContentText("Are you sure you want to cancel " + selectedAppointment.getApptId() + " " + selectedAppointment.getType() + "?");
            cancelApptAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        AppointmentsQuery.delete(selectedAppointment.getApptId());
                        fillApptTable();
                        Alert customerDeleted = new Alert(Alert.AlertType.INFORMATION);
                        customerDeleted.setTitle("Cancel Successful");
                        customerDeleted.setContentText(selectedAppointment.getApptId() + " has been deleted.");
                        customerDeleted.showAndWait();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * Returns the user to the main Customers screen.
     */
    public void toCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the Reports screen.
     */
    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
