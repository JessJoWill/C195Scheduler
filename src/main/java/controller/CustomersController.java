package controller;

import Utilities.AppointmentsQuery;
import Utilities.CustomersQuery;
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
import model.Customer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.FirstLvlDivision;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Utilities.AppointmentsQuery.customerAppointments;
import static Utilities.AppointmentsQuery.upcomingAppointments;
import static Utilities.CustomersQuery.findCustomer;
import static Utilities.CustomersQuery.tableCustomers;
import static Utilities.UsersQuery.userList;
import static controller.LoginController.currentUserName;

/**
 * Displays and controls the main Customers screen.
 */
public class CustomersController implements Initializable {
    public TextField customerSearchTxt;
    public Button addCustomerBtn;
    public Label selectedCustomerId;
    public Label selectedCustomerName;
    public Button updateCustomerBtn;
    public Button deleteCustomerBtn;
    public Button manageApptsBtn;
    public TableView<Customer> customersTableView;
    public TableColumn<Customer, Integer> customersTableId;
    public TableColumn<Customer, String> customersTableName;
    public TableColumn<Customer, String> customersTableAddress;
    public TableColumn<Customer, FirstLvlDivision> customersTableFirstDiv;
    public TableColumn<Customer, String> customersTableZip;
    public TableColumn<Customer, String> customersTableCountry;
    public TableColumn<Customer, String> customersTablePhone;
    public Label custSearchNoResultsLbl;
    public static int selectedIndex;
    public static Customer selectedCustomer;
    public Button scheduleAppt;
    public static int selCustomerId;
    public static String selCustomerName;
    public static String selectedDivName;
    public static String addMod = "";
    public Button reportsBtn;
    ZonedDateTime plusFifteen;
    int currentUser;
    public static boolean onLogin = false;

    private void checkSchedule() throws SQLException {
        AppointmentsQuery.upcomingSelect();
        ZonedDateTime currentLocal = ZonedDateTime.now();
        boolean triggerAlert = false;

        for(Appointment upcomingAppt : upcomingAppointments){
            ZonedDateTime utcStart = upcomingAppt.getStart().atZone(ZoneId.of("UTC"));
            ZonedDateTime localStart = utcStart.withZoneSameInstant(ZoneId.of(TimeZone.getDefault().getID()));
            plusFifteen = currentLocal.plusMinutes(15);
            if(localStart.isBefore(plusFifteen) && currentLocal.isBefore(localStart)) {
                Alert apptSoon = new Alert(Alert.AlertType.INFORMATION);
                apptSoon.setTitle("Upcoming Appointment");
                apptSoon.setContentText("User #" + upcomingAppt.getUserId() + " has an upcoming appointment #" + upcomingAppt.getApptId() + " " + upcomingAppt.getTitle() + " with " + upcomingAppt.getCustomerName() + "today, " + upcomingAppt.getStart().toLocalDate() + ", which begins at " + upcomingAppt.getStart().toLocalTime() + ".");
                apptSoon.showAndWait();
                triggerAlert = true;
            }
        }
        if(triggerAlert == false){
            for(User user : userList){
                if (user.getUserName().equals(currentUserName)){
                    currentUser = user.getUserId();
                }
            }
            Alert noApptSoon = new Alert(Alert.AlertType.INFORMATION);
            noApptSoon.setTitle("No Upcoming Appointments");
            noApptSoon.setContentText("User #" + currentUser + " has no upcoming appointments within the next fifteen minutes.");
            noApptSoon.showAndWait();
        }
    }

    /**
     * Runs the method to check a user's upcoming appointments and to fill the customers table view, and adds the functionality to the search bar.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            if(onLogin == false) {
                checkSchedule();
            }
            onLogin = true;
            fillTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Search functionality for customers table
        customerSearchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(newValue.isEmpty())) {
                String t = customerSearchTxt.getText();
                ObservableList<Customer> foundList = findCustomer(t);

                if(foundList.size() == 0) {
                    try {
                        custSearchNoResultsLbl.setText("No results found.");
                        int customerId = Integer.parseInt(t);
                        foundList = findCustomer(customerId);
                        if(foundList.size() != 0) {
                            customersTableView.setItems(foundList);
                            custSearchNoResultsLbl.setText("");
                        }
                    }
                    catch (NumberFormatException e){
                        customersTableView.setItems(foundList);
                    }
                }
                else{
                    customersTableView.setItems(foundList);
                }
            }
            else{
                customersTableView.setItems(tableCustomers);
                custSearchNoResultsLbl.setText("");
            }
        });
    }

    /**
     * Populates the customers table view.
     */
    private void fillTable(){
            try {
                CustomersQuery.select();

                customersTableView.setItems(tableCustomers);

                // Populating the table views
                customersTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                customersTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                customersTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                customersTableFirstDiv.setCellValueFactory(new PropertyValueFactory<>("division"));
                customersTableZip.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                customersTableCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
                customersTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    /**
     * Takes the user to the Add/Modify customer screen, and specifies that a customer will be added.
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        addMod = "add";
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add-mod-customer-view.fxml")));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the Add/Modify customer screen, and specifies that a customer will be modified. Also extracts the pre-fill information for the form fields on the Modify customer form.
     */
    public void onUpdateCustomer(ActionEvent actionEvent) {
        addMod = "mod";
        selectedIndex = customersTableView.getSelectionModel().getSelectedIndex();
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        try{
            int customerId = selectedCustomer.getCustomerId();
            String customerName = selectedCustomer.getCustomerName();
            String address = selectedCustomer.getAddress();
            String postalCode = selectedCustomer.getPostalCode();
            String phone = selectedCustomer.getPhone();
            selectedDivName = selectedCustomer.getDivision();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add-mod-customer-view.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            AddModCustomerController modcustomercontroller = loader.getController();
            modcustomercontroller.fillForm(selectedIndex, customerId, customerName, address, postalCode, phone);
            Scene scene = new Scene(root, 1000, 645);
            primaryStage.setTitle("Scheduler");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Invalid Selection");
            noSelection.setContentText("Please select a customer from the table before clicking Update.");
            noSelection.showAndWait();
        }
    }

    /**
     * With confirmation from the user, deletes the selected customer from the customer list. Before deleting the customer, it deletes all the customer's scheduled appointments.
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        selCustomerId = selectedCustomer.getCustomerId();
        AppointmentsQuery.select();
        if(selectedCustomer == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Delete Error");
            noSelection.setContentText("You have not selected a customer to delete.");
            noSelection.showAndWait();
        }
        else {

            Alert delCustomerAlert = new Alert(Alert.AlertType.CONFIRMATION);
            delCustomerAlert.setTitle("Confirm Delete");
            delCustomerAlert.setContentText(selectedCustomer.getCustomerName() + " has " + customerAppointments.size() + " appointment(s) scheduled. Are you sure you want to delete?");
            delCustomerAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        //Delete all appointments
                        if (customerAppointments.size() > 0) {
                            for (Appointment customerAppointment : customerAppointments){
                                int theId = customerAppointment.getApptId();
                                AppointmentsQuery.delete(theId);
                            }
                        }
                        //Delete customer
                        CustomersQuery.delete(selectedCustomer.getCustomerId());
                        fillTable();
                        Alert customerDeleted = new Alert(Alert.AlertType.INFORMATION);
                        customerDeleted.setTitle("Delete Successful");
                        customerDeleted.setContentText(selectedCustomer.getCustomerName() + " has been deleted.");
                        customerDeleted.showAndWait();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * Takes the user to the customer appointments screen, where they can add, modify or cancel appointments.
     */
    public void onManageAppts(ActionEvent actionEvent) throws IOException {
        try {
            Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
            selectedIndex = customersTableView.getSelectionModel().getSelectedIndex();
            selCustomerId = selectedCustomer.getCustomerId();
            selCustomerName = selectedCustomer.getCustomerName();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer-appts-view.fxml")));
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 645);
            primaryStage.setTitle("Scheduler");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Customer Selected");
            noSelection.setContentText("Please select a customer whose appointments you would like to manage.");
            noSelection.showAndWait();
        }
    }

    /**
     * Takes the user to the Add/Modify appointments screen, and specifies that a new appointment will be added.
     */
    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        CustomerApptsController.apptAddMod = "add";
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add-mod-appt-view.fxml")));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-by-region-view.fxml")));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
