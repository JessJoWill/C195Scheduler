package controller;

import Utilities.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.ContactsQuery.contactList;
import static Utilities.ContactsQuery.getContacts;
import static Utilities.CustomersQuery.tableCustomers;
import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;
import static controller.CustomerApptsController.apptAddMod;
import static controller.CustomerApptsController.selectedAppointment;
import static controller.CustomersController.selectedIndex;
import static java.time.temporal.ChronoUnit.MINUTES;

public class AddModApptController implements Initializable {
    public Label newUpdatedApptLbl;
    public Label apptIdLbl;
    public TextField apptTitleTxt;
    public TextField apptLocationTxt;
    public TextField apptTypeTxt;
    public TextField apptDescriptionTxt;
    public ComboBox<Contact> apptContactList;
    public Button saveApptBtn;
    public Button cancelBtn;
    public DatePicker apptDatePicker;
    public ComboBox<Customer> customerListCombo;
    public Label apptIdValueLbl;
    public TextField apptStartTxt;
    public TextField apptDurationTxt;
    public ComboBox<User> usersCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUsers();
            getContacts();
            customerListCombo.setItems(tableCustomers);
            apptContactList.setItems(contactList);
            usersCombo.setItems(userList);
            if (Objects.equals(CustomerApptsController.apptAddMod, "add")){
                newUpdatedApptLbl.setText("Create Appointment");
                apptIdLbl.setVisible(false);
            }
            if (Objects.equals(CustomerApptsController.apptAddMod, "mod")){
                newUpdatedApptLbl.setText("Update Appointment");
                apptIdValueLbl.setText(String.valueOf(selectedAppointment.getApptId()));
                apptTitleTxt.setText(selectedAppointment.getTitle());
                apptDescriptionTxt.setText(selectedAppointment.getDescription());
                apptLocationTxt.setText(selectedAppointment.getLocation());
                apptTypeTxt.setText(selectedAppointment.getType());
                customerListCombo.getSelectionModel().select(selectedIndex);
                apptContactList.getSelectionModel().select(selectedIndex);
                usersCombo.getSelectionModel().select(selectedIndex);
                apptDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
                apptStartTxt.setText(String.valueOf(selectedAppointment.getStart().toLocalTime()));

                apptDurationTxt.setText(String.valueOf(MINUTES.between(selectedAppointment.getStart(), selectedAppointment.getEnd())));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSaveAppt(ActionEvent actionEvent) throws DateTimeParseException, NumberFormatException, IOException, SQLException {
        try {
            if(!(apptTitleTxt.getText().isEmpty()) && !(apptDescriptionTxt.getText().isEmpty()) && !(apptLocationTxt.getText().isEmpty()) && !(apptTypeTxt.getText().isEmpty()) && !(apptStartTxt.getText().isEmpty()) && !(apptContactList.getSelectionModel().isEmpty()) && apptDatePicker.getValue() != null && !(customerListCombo.getSelectionModel().isEmpty()) && !(usersCombo.getSelectionModel().isEmpty()))
            {
                String title = apptTitleTxt.getText();
                String description = apptDescriptionTxt.getText();
                String location = apptLocationTxt.getText();
                String contact = apptContactList.getValue().toString();
                String type = apptTypeTxt.getText();
                String apptDate = apptDatePicker.getValue().toString();
                String apptTime = apptStartTxt.getText();
                LocalDateTime createDate = LocalDateTime.now();
                LocalDateTime lastUpdate = LocalDateTime.now();
                String customer = customerListCombo.getValue().toString();
                String userNum = usersCombo.getValue().toString();
                int apptId = 0;

                LocalDateTime start = LocalDateTime.parse(apptDate + "T" + apptTime);
                String getSpan = apptDurationTxt.getText();
                int toMinutes = Integer.parseInt(getSpan);
                LocalDateTime end = start.plusMinutes(toMinutes);

                int userId = Integer.parseInt(userNum.replaceAll("[^0-9]", ""));
                int customerId = Integer.parseInt(customer.replaceAll("[^0-9]", ""));
                int contactId = Integer.parseInt(contact.replaceAll("[^0-9]", ""));
                int rowsAffected = 0;
                System.out.println(apptAddMod);
                if(Objects.equals(CustomerApptsController.apptAddMod, "add")) {
                    rowsAffected = AppointmentsQuery.insert(title, description, location, contactId, type, start, end, createDate, customerId, userId);
                }
                else{
                    apptId = Integer.parseInt(apptIdValueLbl.getText());
                    rowsAffected = AppointmentsQuery.update(title,description,location,contactId,type,start,end,lastUpdate,customerId,userId,apptId);
                }

                if (rowsAffected == 0) {
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
        /*}catch(NumberFormatException e){
            Alert nfe = new Alert(Alert.AlertType.ERROR);
            nfe.setTitle("Input Error");
            nfe.setContentText("Please enter only numbers in the duration field.");
            nfe.showAndWait();*/
        }catch(DateTimeParseException e){
            Alert dtpe = new Alert(Alert.AlertType.ERROR);
            dtpe.setTitle("Input Error");
            dtpe.setContentText("Please enter Appointment Start Time in HH:MM format.");
            dtpe.showAndWait();
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
}
