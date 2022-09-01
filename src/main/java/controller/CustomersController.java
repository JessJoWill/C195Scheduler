package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersController {
    public TextField customerSearchTxt;
    public Button addCustomerBtn;
    public Label selectedCustomerId;
    public Label selectedCustomerName;
    public Button updateCustomerBtn;
    public Button deleteCustomerBtn;
    public Button manageApptsBtn;
    public TableView customersTableView;
    public TableColumn customersTableId;
    public TableColumn customersTableName;
    public TableColumn customersTableAddress;
    public TableColumn customersTableFirstDiv;
    public TableColumn customersTableZip;
    public TableColumn customersTableCountry;
    public TableColumn customersTablePhone;

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
