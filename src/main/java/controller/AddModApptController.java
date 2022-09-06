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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Utilities.ContactsQuery.contactList;
import static Utilities.ContactsQuery.getContacts;
import static Utilities.CustomersQuery.tableCustomers;
import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;
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
    public ToggleButton amBtn;
    public ToggleButton pmBtn;
    //Timezones
    String ampmPattern = "hh:mm";
    String ckpmPattern = "a";
    DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern(ampmPattern);
    DateTimeFormatter ckpmFormatter = DateTimeFormatter.ofPattern(ckpmPattern);
    ZoneId newYorkZone = ZoneId.of("America/New_York");
    ZoneId londonZone = ZoneId.of("Europe/London");
    ZoneId phoenixZone = ZoneId.of("America/Phoenix");
    ZoneId utcZone = ZoneId.of("UTC");
    ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

    /**
     * Populates Add/Modify Appointments form, inlcuding combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            getUsers();
            getContacts();
            customerListCombo.setItems(tableCustomers);
            apptContactList.setItems(contactList);
            usersCombo.setItems(userList);
            // For Add New Appointment
            if (Objects.equals(CustomerApptsController.apptAddMod, "add")){
                newUpdatedApptLbl.setText("Create Appointment"); //Heading
                apptIdLbl.setVisible(false); //Hide Appointment ID
            }
            // For Modify Existing Appointment
            if (Objects.equals(CustomerApptsController.apptAddMod, "mod")){
                newUpdatedApptLbl.setText("Update Appointment"); //Heading
                apptIdValueLbl.setText(String.valueOf(selectedAppointment.getApptId()));
                apptTitleTxt.setText(selectedAppointment.getTitle());
                apptDescriptionTxt.setText(selectedAppointment.getDescription());
                apptLocationTxt.setText(selectedAppointment.getLocation());
                apptTypeTxt.setText(selectedAppointment.getType());
                customerListCombo.getSelectionModel().select(selectedIndex);
                apptContactList.getSelectionModel().select(selectedIndex);
                usersCombo.getSelectionModel().select(selectedIndex);
                apptDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
                LocalDateTime dbStart = selectedAppointment.getStart();
                ZonedDateTime zonedStart = ZonedDateTime.of(dbStart, utcZone);
                ZonedDateTime localStart = zonedStart.withZoneSameInstant(localZone);
                LocalTime localStartTime = LocalTime.from(localStart);
                apptStartTxt.setText(String.valueOf((localStartTime).format(ampmFormatter)));
                if(zonedStart.format(ckpmFormatter).contains("PM")){
                    pmBtn.setSelected(true);
                }
                else{
                    amBtn.setSelected(true);
                }
                apptDurationTxt.setText(String.valueOf(MINUTES.between(selectedAppointment.getStart(), selectedAppointment.getEnd())));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds new appointments to the database, and updates existing appointments in the database.
     * @param actionEvent
     * @throws DateTimeParseException
     * @throws NumberFormatException
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveAppt(ActionEvent actionEvent) throws DateTimeParseException, NumberFormatException, IOException, SQLException {
        //try {
            if(!(apptTitleTxt.getText().isEmpty()) && !(apptDescriptionTxt.getText().isEmpty()) && !(apptLocationTxt.getText().isEmpty()) && !(apptTypeTxt.getText().isEmpty()) && !(apptStartTxt.getText().isEmpty()) && !(apptContactList.getSelectionModel().isEmpty()) && apptDatePicker.getValue() != null && !(customerListCombo.getSelectionModel().isEmpty()) && !(usersCombo.getSelectionModel().isEmpty()))
            {
                // Extract values from textboxes, the datepicker and the combo boxes
                String title = apptTitleTxt.getText();
                String description = apptDescriptionTxt.getText();
                String location = apptLocationTxt.getText();
                String contact = apptContactList.getValue().toString();
                String type = apptTypeTxt.getText();
                String apptDate = apptDatePicker.getValue().toString();
                String apptTime = apptStartTxt.getText();
                if(pmBtn.isSelected()){
                    apptTime += " PM";
                }
                String apptMilitary = LocalTime.parse(apptTime,DateTimeFormatter.ofPattern("hh:mm a")).format(DateTimeFormatter.ofPattern("HH:mm"));
                String customer = customerListCombo.getValue().toString();
                String userNum = usersCombo.getValue().toString();
                String startOfBusiness = "08:00";
                String endOfBusiness = "22:00";
                DateTimeFormatter time = DateTimeFormatter.ofPattern("hh:mm a");
                LocalDateTime localSoB = LocalDateTime.parse(apptDate + "T" + startOfBusiness);
                LocalDateTime localEoB = LocalDateTime.parse(apptDate + "T" + endOfBusiness);
                ZonedDateTime zonedSoB = ZonedDateTime.of(localSoB,newYorkZone);
                ZonedDateTime zonedEoB = ZonedDateTime.of(localEoB,newYorkZone);
                ZonedDateTime zonedEoBToLocal = zonedEoB.withZoneSameInstant(localZone);
                ZonedDateTime zonedSobToLocal = zonedSoB.withZoneSameInstant(localZone);

                int apptId = 0; // initializing for update()

                LocalDateTime start = LocalDateTime.parse(apptDate + "T" + apptMilitary);
                ZonedDateTime zonedStart = ZonedDateTime.of(start, localZone); // turn the strings into a LocalDateTime object
                String getSpan = apptDurationTxt.getText(); // the appointment duration
                int toMinutes = Integer.parseInt(getSpan); // converting the appointment duration to int to add to start time
                ZonedDateTime end = zonedStart.plusMinutes(toMinutes); // adding duration to start, for end time
                ZonedDateTime utcStart = zonedStart.withZoneSameInstant(utcZone); // converting the start time to UTC for the database
                ZonedDateTime utcEnd = end.withZoneSameInstant(utcZone); // converting the end time to UTC for the database
                ZonedDateTime bhStart = utcStart.withZoneSameInstant(newYorkZone); // appointment start time converted to EST to check for business hours
                ZonedDateTime bhEnd = utcEnd.withZoneSameInstant(newYorkZone); // appointment end time converted to EST to check for busieness hours
                ZonedDateTime localBhStart = utcStart.withZoneSameInstant(localZone);
                ZonedDateTime localBhEnd = utcEnd.withZoneSameInstant(localZone);
                if(zonedStart.isBefore(zonedSoB) || end.isAfter(zonedEoB)){
                    Alert tooEarly = new Alert(Alert.AlertType.ERROR);
                    tooEarly.setTitle("Invalid Time");
                    tooEarly.setContentText("You've chosen an appointment outside our business hours. Please choose an appointment time between " + time.format(zonedSobToLocal) + " and " + time.format(zonedEoBToLocal) + ".");
                    tooEarly.showAndWait();
                }
                int userId = Integer.parseInt(userNum.replaceAll("[^0-9]", "")); // extracting the user ID from the user combobox value
                int customerId = Integer.parseInt(customer.replaceAll("[^0-9]", "")); // extracting the customer ID from the customer combobox value
                int contactId = Integer.parseInt(contact.replaceAll("[^0-9]", "")); // extracting the contact ID from the contact combobox value
                int rowsAffected = 0;

                // For adding new appointments
                if(Objects.equals(CustomerApptsController.apptAddMod, "add")) {
                    rowsAffected = AppointmentsQuery.insert(title, description, location, contactId, type, utcStart, utcEnd, customerId, userId);
                }
                // For updating existing appointments
                else{
                    apptId = Integer.parseInt(apptIdValueLbl.getText());
                    rowsAffected = AppointmentsQuery.update(title,description,location,contactId,type,utcStart,utcEnd,customerId,userId,apptId);
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
            // If there are empty fields
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
            nfe.showAndWait();
        }catch(DateTimeParseException e){
            Alert dtpe = new Alert(Alert.AlertType.ERROR);
            dtpe.setTitle("Input Error");
            dtpe.setContentText("Please enter Appointment Start Time in HH:MM format.");
            dtpe.showAndWait();
        }*/
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
