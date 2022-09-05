package controller;

import Utilities.CustomersQuery;
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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.CountriesQuery.*;
import static Utilities.DivisionsQuery.*;
import static Utilities.UsersQuery.*;
import static controller.CustomersController.selectedCustomer;
import static controller.CustomersController.selectedDivName;

public class AddModCustomerController implements Initializable {
    public Label addModifyCustomerLbl;
    public Label customerIdLbl;
    public TextField customerNameTxt;
    public TextField phoneNumberTxt;
    public TextField addressTxt;
    public TextField postalCodeTxt;
    public ComboBox<TheCountry> countryCombo;
    public ComboBox<FirstLvlDivision> firstDivCombo;
    public Button saveBtn;
    public Button cancelBtn;
    public ComboBox<User> userCombo;
    public Label indexLbl;
    public Label customerIdValueLbl;
    private int countryId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getCountries();
            getDivisions();
            countryCombo.setItems(countryList);
            firstDivCombo.setItems(divisionList);
            userCombo.setItems(userList);

            if (Objects.equals(CustomersController.addMod, "add")) {
                addModifyCustomerLbl.setText("Add New Customer");
                customerIdLbl.setVisible(false);
            }
            if (Objects.equals(CustomersController.addMod, "mod")) {
                addModifyCustomerLbl.setText("Update Customer Record");
            }
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
        if(!(customerNameTxt.getText().isEmpty()) && !(addressTxt.getText().isEmpty()) && !(postalCodeTxt.getText().isEmpty()) && !(phoneNumberTxt.getText().isEmpty()) && !(userCombo.getSelectionModel().isEmpty()) && !(firstDivCombo.getSelectionModel().isEmpty())){

        String customerName = customerNameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalCodeTxt.getText();
        String phone = phoneNumberTxt.getText();
        LocalDateTime create_date = LocalDateTime.now();
        String created_by = userCombo.getSelectionModel().getSelectedItem().toString();
        LocalDateTime last_updated = LocalDateTime.now();
        String updatedBy = userCombo.getSelectionModel().getSelectedItem().toString();
        int divisionId = (int) firstDivCombo.getSelectionModel().getSelectedItem().getDivisionId();
        int rowsAffected = 0;
        if(CustomersController.addMod.equals("add")) {
            rowsAffected = CustomersQuery.insert(customerName, address, postalCode, phone, create_date, created_by, divisionId);
        }
        else{
            int customerId = Integer.parseInt(customerIdLbl.getText());
            rowsAffected = CustomersQuery.update(customerName,address,postalCode,phone,last_updated,updatedBy,divisionId,customerId);
        }
        if(rowsAffected == 0){
            System.out.println("Something went wrong.");
        }
        // Back to main screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
        }
        else{
            Alert incompleteForm = new Alert(Alert.AlertType.ERROR);
            incompleteForm.setTitle("Form Incomplete");
            incompleteForm.setContentText("Please complete all fields before saving.");
            incompleteForm.showAndWait();
        }
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 645);
            primaryStage.setTitle("Scheduler");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    public void findCountry(int divisionId){
        if(divisionId < 60){
            countryId = 1;
        }
        else if(divisionId < 100){
            countryId = 3;
        }
        else {
            countryId = 2;
        }
    }

    public void fillForm(int selectedIndex, int customerId, String customerName, String address, String postalCode, String phone) {
        indexLbl.setText(String.valueOf(selectedIndex));
        customerIdLbl.setText(String.valueOf(customerId));
        customerNameTxt.setText(customerName);
        addressTxt.setText(address);

        for (FirstLvlDivision division : divisionList){
            if (Objects.equals(division.getDivision(), selectedCustomer.getDivision())){
                firstDivCombo.getSelectionModel().select(division);
            }
        }
        for (TheCountry aCountry : countryList)
        {
            if (Objects.equals(aCountry.getCountryName(), selectedCustomer.getCountry()))
            {
                countryCombo.getSelectionModel().select(aCountry);
            }
        }

        postalCodeTxt.setText(postalCode);
        phoneNumberTxt.setText(phone);
    }
}
