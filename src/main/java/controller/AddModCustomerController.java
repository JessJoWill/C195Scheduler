package controller;

import Utilities.CustomersQuery;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AddModCustomerController {
    public Label addModifyCustomerLbl;
    public Label customerIdLbl;
    public TextField customerNameTxt;
    public TextField phoneNumberTxt;
    public TextField addressTxt;
    public TextField postalCodeTxt;
    public ComboBox countryCombo;
    public ComboBox firstDivCombo;
    public CheckBox addApptChk;
    public Button saveBtn;
    public Button cancelBtn;

    public void filterFirstDiv(ActionEvent actionEvent) {
    }

    public void onSaveCustomer(ActionEvent actionEvent) throws SQLException {
        String customerName = customerNameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalCodeTxt.getText();
        String phone = phoneNumberTxt.getText();
        int divisionId = (int) firstDivCombo.getSelectionModel().getSelectedItem();
        int rowsAffected = CustomersQuery.insert(customerName, address, postalCode, phone, divisionId);

    }

    public void onCancel(ActionEvent actionEvent) {
    }
}
