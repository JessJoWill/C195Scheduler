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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.AppointmentsQuery.customerAppointments;
import static controller.CustomersController.*;

public class CustomerApptsController implements Initializable {
    public Label customerIdLbl;
    public Label customerNameLbl;
    public TableView customerApptsTableView;
    public TableColumn customerApptIdCol;
    public TableColumn customerApptTitleCol;
    public TableColumn customerApptDescriptionCol;
    public TableColumn customerApptLocationCol;
    public TableColumn customerApptContactCol;
    public TableColumn customerApptTypeCol;
    public TableColumn customerApptStartCol;
    public TableColumn customerApptEndCol;
    public TableColumn customerApptUserIdCol;
    public TableColumn customerApptCustIdCol;
    public Button addApptBtn;
    public Button modifyApptBtn;
    public Button cancelApptBtn;
    public static String apptAddMod;
    public static Appointment selectedAppointment;
    public Button backBtn;
    public Button viewReportsBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillApptTable();
        customerIdLbl.setText(String.valueOf(selCustomerId));
        customerNameLbl.setText(selCustomerName);
    }

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
    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        apptAddMod = "add";
        Parent root = FXMLLoader.load(getClass().getResource("/view/add-mod-appt-view.fxml"));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onModifyAppt(ActionEvent actionEvent) throws IOException {
        apptAddMod = "mod";
        selectedAppointment = (Appointment) customerApptsTableView.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(getClass().getResource("/view/add-mod-appt-view.fxml"));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void onDeleteAppt(ActionEvent actionEvent) {
        Appointment selectedAppointment = (Appointment) customerApptsTableView.getSelectionModel().getSelectedItem();
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


    public void toCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
