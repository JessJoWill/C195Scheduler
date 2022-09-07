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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.AppointmentsQuery.userAppointments;
import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;

/**
 * Displays and controls the reports screen.
 */
public class UserAppointmentsReportController implements Initializable {
    public RadioButton allApptsRB;
    public RadioButton apptsByWeekRB;
    public RadioButton apptsByMonthRB;
    public ToggleGroup apptFilterToggle;
    public TableView<Appointment> userApptsTableView;
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
    public Button mainMenuBtn;
    public ComboBox<User> usersCombo;
    public static int selUserId;

    /**
     * Populates the users combobox and initializes the radio buttons to all.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        usersCombo.setItems(userList);
        allApptsRB.setSelected(true);
    }

    /**
     * Filters the User Appointments report to the current week.
     */
    public void filterByWeek(ActionEvent actionEvent) {
    }

    /**
     * Filters the User Appointments report to the current month.
     */
    public void filterByMonth(ActionEvent actionEvent) {
    }

    /**
     * Returns the user to the Customers screen.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Populates the table view for the User Appointments report.
     */
    public void fillApptTable(ActionEvent actionEvent) {
            try {
                selUserId = usersCombo.getSelectionModel().getSelectedItem().getUserId();
                allApptsRB.setSelected(true);

                AppointmentsQuery.userSelect();

                userApptsTableView.setItems(userAppointments);

                // Populating the table views
                apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
                apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
                apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
                apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
                apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
