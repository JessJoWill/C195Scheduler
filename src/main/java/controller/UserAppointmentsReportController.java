package controller;

import Utilities.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Utilities.AppointmentsQuery.customerAppointments;
import static Utilities.AppointmentsQuery.userAppointments;
import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;

public class UserAppointmentsReportController implements Initializable {
    public RadioButton allApptsRB;
    public RadioButton apptsByWeekRB;
    public RadioButton apptsByMonthRB;
    public ToggleGroup apptFilterToggle;
    public TableView userApptsTableView;
    public TableColumn apptIdCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptLocationCol;
    public TableColumn apptContactCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn apptCustomerIdCol;
    public TableColumn apptUserIdCol;
    public Button mainMenuBtn;
    public ComboBox<User> usersCombo;
    public static int selUserId;

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
    public void filterByWeek(ActionEvent actionEvent) {
    }

    public void filterByMonth(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) {
    }


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
