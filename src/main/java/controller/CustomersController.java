package controller;

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
import model.Customer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.FirstLvlDivision;
import model.TheCountry;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Utilities.CountriesQuery.countryList;
import static Utilities.CustomersQuery.findCustomer;
import static Utilities.CustomersQuery.tableCustomers;
import static Utilities.DivisionsQuery.divisionList;

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
    private FirstLvlDivision passDivision;
    private TheCountry passCountry;
    public static int selCustomerId;
    public static String selCustomerName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillTable();

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

    public static String addMod = "";
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        addMod = "add";
        Parent root = FXMLLoader.load(getClass().getResource("/view/add-mod-customer-view.fxml"));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onUpdateCustomer(ActionEvent actionEvent) {
        addMod = "mod";
        selectedIndex = customersTableView.getSelectionModel().getSelectedIndex();
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        String selectedDivision = selectedCustomer.getDivision();
        String selectedCountry = selectedCustomer.getCountry();
        try {
            for (FirstLvlDivision division : divisionList) {
                if (division.getDivision().equals(selectedDivision)) {
                    passDivision = division;
                }
            }
            for (TheCountry country : countryList) {
                if (country.getCountryName().equals(selectedCountry)) {
                    passCountry = country;
                }
            }
            int customerId = selectedCustomer.getCustomerId();
            String customerName = selectedCustomer.getCustomerName();
            String address = selectedCustomer.getAddress();
            String postalCode = selectedCustomer.getPostalCode();
            String phone = selectedCustomer.getPhone();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add-mod-customer-view.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            AddModCustomerController modcustomercontroller = loader.getController();
            modcustomercontroller.fillForm(selectedIndex, customerId, customerName, address, passDivision, postalCode, passCountry, phone);
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

    public void onDeleteCustomer(ActionEvent actionEvent) {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Delete Error");
            noSelection.setContentText("You have not selected a customer to delete.");
            noSelection.showAndWait();
        }
        else {
            Alert delCustomerAlert = new Alert(Alert.AlertType.CONFIRMATION);
            delCustomerAlert.setTitle("Confirm Delete");
            delCustomerAlert.setContentText("Are you sure you want to delete " + selectedCustomer.getCustomerName() + "?");
            delCustomerAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
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

    public void onManageAppts(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        selectedIndex = customersTableView.getSelectionModel().getSelectedIndex();
        selCustomerId = selectedCustomer.getCustomerId();
        selCustomerName = selectedCustomer.getCustomerName();
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer-appts-view.fxml"));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add-mod-appt-view.fxml"));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
