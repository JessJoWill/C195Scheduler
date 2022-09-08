package controller;

import Utilities.AppointmentsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Utilities.AppointmentsQuery.userAppointments;
import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;

/**
 * Displays and controls the reports screen.
 */
public class ReportsController implements Initializable {
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
    public ObservableList<Appointment> weeksAppointments = FXCollections.observableArrayList();
    public ObservableList<Appointment> monthsAppointments = FXCollections.observableArrayList();
    public LocalDateTime currentLDT = LocalDateTime.now();
    public ZonedDateTime currentZDT = ZonedDateTime.of(currentLDT, ZoneId.of(TimeZone.getDefault().getID()));
    public ZonedDateTime currentZUTC = currentZDT.withZoneSameInstant(ZoneId.of("UTC"));
    public Button custByRegionBtn;
    public Button contactApptBtn;
    public Button custApptBtn;
    public TableView<Customer> customersByRegionTV;
    public TableColumn<Customer, String> firstDivisionCol;
    public TableColumn<Customer, String> countryCol;
    public TableColumn<Customer, Integer> numCustomersCol;
    public Button userApptBtn;
    public TableView<Appointment> apptByMonthTV;
    public TableColumn<Appointment, Integer> janCol;
    public TableColumn<Appointment, Integer> febCol;
    public TableColumn<Appointment, Integer> marCol;
    public TableColumn<Appointment, Integer> aprCol;
    public TableColumn<Appointment, Integer> mayCol;
    public TableColumn<Appointment, Integer> juneCol;
    public TableColumn<Appointment, Integer> julyCol;
    public TableColumn<Appointment, Integer> augCol;
    public TableColumn<Appointment, Integer> sepCol;
    public TableColumn<Appointment, Integer> octCol;
    public TableColumn<Appointment, Integer> novCol;
    public TableColumn<Appointment, Integer> decCol;
    public TableView<Appointment> apptsByTypeTV;
    public TableColumn<Appointment, Integer> inPersonCol;
    public TableColumn<Appointment, Integer> phoneCol;
    public TableColumn<Appointment, Integer> virtualCol;
    public TableColumn<Appointment, Integer> conferenceCol;
    public ComboBox<Contact> contactsCombo;

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
        weeksAppointments.clear();
        for(Appointment appointment : userAppointments){
            if(appointment.getStart().isAfter(ChronoLocalDateTime.from(currentZUTC)) || appointment.getStart().equals(ChronoLocalDateTime.from(currentZUTC))){
                if(appointment.getStart().isBefore(ChronoLocalDateTime.from(currentZUTC.plusWeeks(1))) || appointment.getStart().equals(ChronoLocalDateTime.from(currentZUTC.plusWeeks(1)))){
                    weeksAppointments.add(appointment);
                }
            }
        }
        fillApptTable();
    }


    /**
     * Filters the User Appointments report to the current month.
     */
    public void filterByMonth(ActionEvent actionEvent) {
        monthsAppointments.clear();
        for(Appointment appointment : userAppointments){
            if(appointment.getStart().isAfter(ChronoLocalDateTime.from(currentZUTC)) || appointment.getStart().equals(ChronoLocalDateTime.from(currentZUTC))){
                if(appointment.getStart().isBefore(ChronoLocalDateTime.from(currentZUTC.plusMonths(1))) || appointment.getStart().equals(ChronoLocalDateTime.from(currentZUTC.plusMonths(1)))){
                    monthsAppointments.add(appointment);
                }
            }
        }
        fillApptTable();
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
    public void fillApptTable() {
        selUserId = usersCombo.getSelectionModel().getSelectedItem().getUserId();
        try {
            AppointmentsQuery.userSelect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(apptsByWeekRB.isSelected()){
            userApptsTableView.setItems(weeksAppointments);
        }
        else if(apptsByMonthRB.isSelected()) {
            userApptsTableView.setItems(monthsAppointments);
        }
        else{
            userApptsTableView.setItems(userAppointments);
        }

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
    }

    public void toCustByRegion(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-by-region-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toContactApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/contact-appt-report-view.fxml")));
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

    public void toUserApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
