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
import model.Appointment;
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

import static Utilities.AppointmentsQuery.customerAppointments;
import static Utilities.ContactsQuery.contactList;
import static Utilities.ContactsQuery.getContacts;
import static Utilities.CustomersQuery.tableCustomers;
import static Utilities.UsersQuery.getUsers;
import static Utilities.UsersQuery.userList;
import static controller.CustomerApptsController.selectedAppointment;
import static controller.CustomersController.selCustomerId;
import static controller.CustomersController.selectedIndex;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Displays and controls the Add/Modify Appointments screen.
 */
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
    public static ZonedDateTime utcStart;
    public static ZonedDateTime utcEnd;
    public static int currentApptId = 0;

    /**
     * Populates Add/Modify Appointments form, inlcuding combo boxes.
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

                /*/*********************************************************************************
                // FOR TESTING
                //*********************************************************************************
                apptTitleTxt.setText("TestTitle");
                apptDescriptionTxt.setText("TestDescription");
                apptLocationTxt.setText("TestLocation");
                apptContactList.getSelectionModel().select(0);
                apptTypeTxt.setText("TestType");
                customerListCombo.getSelectionModel().select(0);
                usersCombo.getSelectionModel().select(0);
                LocalDate testDate = LocalDate.parse("2000-01-01");
                apptDatePicker.setValue(testDate);
                //********************************************************************************/
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
     * @throws DateTimeParseException
     * @throws NumberFormatException
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveAppt(ActionEvent actionEvent) throws DateTimeParseException, NumberFormatException, IOException, SQLException {

        validateForm();
        try{

            // Extract values from textboxes, the datepicker and the combo boxes
            if(apptIdLbl.isVisible()){
                currentApptId = Integer.parseInt(apptIdValueLbl.getText());
            }
            System.out.println(currentApptId);
            String title = apptTitleTxt.getText();
            String description = apptDescriptionTxt.getText();
            String location = apptLocationTxt.getText();
            String contact = apptContactList.getValue().toString();
            String type = apptTypeTxt.getText();
            String apptDate = apptDatePicker.getValue().toString();
            String apptTime = apptStartTxt.getText();
            String customer = customerListCombo.getValue().toString();
            String userNum = usersCombo.getValue().toString();
            int apptId = 0; // initializing for update()

            if (pmBtn.isSelected()) {
                apptTime += " PM";
            }
            if(amBtn.isSelected()){
                apptTime += " AM";
            }
            String apptMilitary = LocalTime.parse(apptTime, DateTimeFormatter.ofPattern("hh:mm a")).format(DateTimeFormatter.ofPattern("HH:mm")); // Convert am/pm to military time

            // Set business hours
            String startOfBusiness = "08:00";
            String endOfBusiness = "22:00";

            // Format time output
            DateTimeFormatter time = DateTimeFormatter.ofPattern("hh:mm a");

            // Create LocalDateTime objects and EST ZonedDateTime objects for business hours and convert to users' local time zones
            LocalDateTime ldtSoB = LocalDateTime.parse(apptDate + "T" + startOfBusiness); // Determine start of business hours on the appt date
            LocalDateTime ldtEoB = LocalDateTime.parse(apptDate + "T" + endOfBusiness); // Determine end of business hours on the appt date
            ZonedDateTime zonedSoB = ZonedDateTime.of(ldtSoB, newYorkZone); // Specify EST for business hours
            ZonedDateTime zonedEoB = ZonedDateTime.of(ldtEoB, newYorkZone); // Specify EST for business hours
            ZonedDateTime zonedEoBToLocal = zonedEoB.withZoneSameInstant(localZone); // Convert start of business hours to local time zone
            ZonedDateTime zonedSobToLocal = zonedSoB.withZoneSameInstant(localZone); // Convert end of business hours to local time zone

            // Appointment time conversions
            LocalDateTime start = LocalDateTime.parse(apptDate + "T" + apptMilitary); // parse the collected appointment date and military start time into LocalDateTime object

            // Create ZonedDateTime objects for appt start and end in EST, UTC and local time zones
            ZonedDateTime zonedStart = ZonedDateTime.of(start, localZone); // convert the start LocalDateTime to a ZonedDateTime
            int toMinutes = Integer.parseInt(apptDurationTxt.getText()); // converting the appointment duration to int to add to start time
            ZonedDateTime zonedEnd = zonedStart.plusMinutes(toMinutes); // adding duration to start, for end time

            utcStart = zonedStart.withZoneSameInstant(utcZone); // converting the start time to UTC for the database
            utcEnd = zonedEnd.withZoneSameInstant(utcZone); // converting the end time to UTC for the database

            ZonedDateTime estStart = utcStart.withZoneSameInstant(newYorkZone); // appointment start time converted to EST to check for business hours
            ZonedDateTime estEnd = utcEnd.withZoneSameInstant(newYorkZone); // appointment end time converted to EST to check for business hours

            ZonedDateTime localStart = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEnd = utcEnd.withZoneSameInstant(localZone);
            if (zonedStart.isBefore(zonedSoB) || zonedEnd.isAfter(zonedEoB)) {
                Alert tooEarly = new Alert(Alert.AlertType.ERROR);
                tooEarly.setTitle("Invalid Time");
                tooEarly.setContentText("You've chosen an appointment outside our business hours. Please choose an appointment time between " + time.format(zonedSobToLocal) + " and " + time.format(zonedEoBToLocal) + ".");
                tooEarly.showAndWait();
            }

            if(checkOverlap()) { // checks for appointments already scheduled that conflict with the new one.

                int userId = Integer.parseInt(userNum.replaceAll("[^0-9]", "")); // extracting the user ID from the user combobox value
                int customerId = Integer.parseInt(customer.replaceAll("[^0-9]", "")); // extracting the customer ID from the customer combobox value
                int contactId = Integer.parseInt(contact.replaceAll("[^0-9]", "")); // extracting the contact ID from the contact combobox value
                int rowsAffected = 0;

                // For adding new appointments
                if (Objects.equals(CustomerApptsController.apptAddMod, "add")) {
                    rowsAffected = AppointmentsQuery.insert(title, description, location, contactId, type, utcStart, utcEnd, customerId, userId);
                }
                // For updating existing appointments
                else {
                    apptId = Integer.parseInt(apptIdValueLbl.getText());
                    rowsAffected = AppointmentsQuery.update(title, description, location, contactId, type, utcStart, utcEnd, customerId, userId, apptId);
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Checks that all fields are filled in before saving
     */
    private void validateForm() {
        if (apptTitleTxt.getText().isEmpty() || apptDescriptionTxt.getText().isEmpty() || apptLocationTxt.getText().isEmpty() || apptTypeTxt.getText().isEmpty() || apptStartTxt.getText().isEmpty() || apptDurationTxt.getText().isEmpty() || apptContactList.getSelectionModel().isEmpty() || apptDatePicker.getValue() == null || customerListCombo.getSelectionModel().isEmpty() || usersCombo.getSelectionModel().isEmpty()) {
            Alert incompleteForm = new Alert(Alert.AlertType.ERROR);
            incompleteForm.setTitle("Form Incomplete");
            incompleteForm.setContentText("Please complete all fields before saving.");
            incompleteForm.showAndWait();
        }
    }

    /**
     * Checks to make sure the appointment being scheduled doesn't overlap with any already scheduled.
     * @return
     * @throws SQLException
     */
    private boolean checkOverlap() throws SQLException {
        boolean result = true;
        System.out.println("Running checkOverlap");
        selCustomerId = customerListCombo.getSelectionModel().getSelectedItem().getCustomerId();
        AppointmentsQuery.select();
        System.out.println(selCustomerId);
        System.out.println(customerAppointments.size());
        for (Appointment appointment : customerAppointments) {
            if(appointment.getApptId() != currentApptId) {

                System.out.println(appointment.getStart() + ": Start from DB");

                ZonedDateTime scheduledStart = ZonedDateTime.of(appointment.getStart(), utcZone);

                System.out.println(scheduledStart + ": scheduledStart");

                ZonedDateTime scheduledEnd = ZonedDateTime.of(appointment.getEnd(), utcZone);

                System.out.println(scheduledEnd + ": scheduledEnd");
                System.out.println(utcStart + ": utcStart");
                System.out.println(utcEnd + ": utcEnd");

                if ((utcStart.isAfter(scheduledStart) || utcStart.equals(scheduledStart)) && utcStart.isBefore(scheduledEnd) || (utcEnd.isAfter(scheduledStart) && (utcEnd.isBefore(scheduledEnd) || utcEnd.equals(scheduledEnd))) || ((utcStart.isBefore(scheduledStart) || utcStart.equals(scheduledStart)) && (utcEnd.isAfter(scheduledEnd) || utcEnd.equals(scheduledEnd)))) {
                    Alert overlap = new Alert(Alert.AlertType.ERROR);
                    overlap.setTitle("Overlapping Appointments");
                    overlap.setContentText("Your selected appointment time conflicts with an already scheduled appointment.");
                    overlap.showAndWait();
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns the user to the main Customers screen.
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
