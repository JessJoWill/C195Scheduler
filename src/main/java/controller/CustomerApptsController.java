package controller;

import Utilities.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public TableColumn customerApptCustIdCol;
    public TableColumn customerApptUserIdCol;
    public Button addApptBtn;
    public Button modifyApptBtn;
    public Button cancelApptBtn;
    public static String apptAddMod;
    public static Appointment selectedAppointment;

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
    }


}
