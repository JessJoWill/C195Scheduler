package controller;

import Utilities.CountriesQuery;
import Utilities.CustomersQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.TheCountry;
import model.FirstLvlDivision;

import java.io.IOException;
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

    public void onSaveCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        String customerName = customerNameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalCodeTxt.getText();
        String phone = phoneNumberTxt.getText();
        int divisionId = (int) firstDivCombo.getSelectionModel().getSelectedItem().getDivisionId();
        int rowsAffected = CustomersQuery.insert(customerName, address, postalCode, phone, divisionId);

        if(rowsAffected == 0){
            System.out.println("Something went wrong.");
        }
        // Back to main screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/customers-view.fxml"));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onCancel(ActionEvent actionEvent) {
    }


}
