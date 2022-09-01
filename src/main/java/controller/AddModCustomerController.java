package controller;

import Utilities.CountriesQuery;
import Utilities.CustomersQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.TheCountry;
import model.FirstLvlDivision;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Utilities.CountriesQuery.*;
import static Utilities.DivisionsQuery.*;

public class AddModCustomerController implements Initializable {
    public Label addModifyCustomerLbl;
    public Label customerIdLbl;
    public TextField customerNameTxt;
    public TextField phoneNumberTxt;
    public TextField addressTxt;
    public TextField postalCodeTxt;
    public ComboBox<TheCountry> countryCombo;
    public ComboBox<FirstLvlDivision> firstDivCombo;
    public CheckBox addApptChk;
    public Button saveBtn;
    public Button cancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getCountries();
            getDivisions();
            countryCombo.setItems(countryList);
            firstDivCombo.setItems(divisionList);
            }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void filterFirstDiv(ActionEvent actionEvent) throws SQLException {
            if (countryCombo.getSelectionModel().getSelectedItem().getCountryId() == 1) {
                getUSDivisions();
                firstDivCombo.setItems(usList);
            } else if (countryCombo.getSelectionModel().getSelectedItem().getCountryId() == 2) {
                getUKDivisions();
                firstDivCombo.setItems(ukList);
            } else if (countryCombo.getSelectionModel().getSelectedItem().getCountryId() == 3) {
                getCanadaDivisions();
                firstDivCombo.setItems(canadaList);
            }
    }

    public void onSaveCustomer(ActionEvent actionEvent) throws SQLException {
        String customerName = customerNameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalCodeTxt.getText();
        String phone = phoneNumberTxt.getText();
        int divisionId = (int) firstDivCombo.getSelectionModel().getSelectedItem().getDivisionId();
        int rowsAffected = CustomersQuery.insert(customerName, address, postalCode, phone, divisionId);

    }

    public void onCancel(ActionEvent actionEvent) {
    }


}
