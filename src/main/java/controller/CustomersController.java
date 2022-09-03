package controller;

import Utilities.CustomersQuery;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Utilities.CustomersQuery.tableCustomers;

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
    public TableColumn<Customer, String> customersTableFirstDiv;
    public TableColumn<Customer, String> customersTableZip;
    public TableColumn<Customer, String> customersTableCountry;
    public TableColumn<Customer, String> customersTablePhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CustomersQuery.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customersTableView.setItems(tableCustomers);

        // Populating the table views

        customersTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customersTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customersTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customersTableFirstDiv.setCellValueFactory(new PropertyValueFactory<>("division"));
        customersTableZip.setCellValueFactory(new PropertyValueFactory<>("postalCode"));;
        customersTableCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        customersTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    public void onSearchCustomers(ActionEvent actionEvent) {
    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add-mod-customer-view.fxml"));
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onUpdateCustomer(ActionEvent actionEvent) {
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onManageAppts(ActionEvent actionEvent) {
    }

}
