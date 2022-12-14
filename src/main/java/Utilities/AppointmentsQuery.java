package Utilities;

import DAO.JDBC;
import controller.ContactApptsReportController;
import controller.LoginController;
import controller.UserApptsReportController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static controller.CustomersController.selCustomerId;

/**
 * Queries run on the Appointments table.
 */
public abstract class AppointmentsQuery {

    /**
     * Inserts new appointments into the appointments table.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param contactId The ID of the appointment contact.
     * @param type The appointment type.
     * @param utcStart The date and start time of the appointment, in UTC.
     * @param utcEnd The date and end time of the appointment, in UTC.
     * @param customerId The ID of the customer attached to the appointment.
     * @param userId The ID of the user entering the appointment.
     * @throws SQLException
     */
    public static int insert(String title, String description, String location, int contactId, String type, ZonedDateTime utcStart, ZonedDateTime utcEnd, int customerId, int userId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setInt(4, contactId);
        ps.setString(5, type);
        ps.setTimestamp(6, Timestamp.valueOf(utcStart.toLocalDateTime()));
        ps.setTimestamp(7, Timestamp.valueOf(utcEnd.toLocalDateTime()));
        ps.setInt(8, customerId);
        ps.setInt(9, userId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an existing record in the appointments table.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param contactId The ID of the appointment contact.
     * @param type The appointment type.
     * @param utcStart The date and start time of the appointment, in UTC.
     * @param utcEnd The date and end time of the appointment, in UTC.
     * @param customerId The ID of the customer attached to the appointment.
     * @param userId The ID of the user entering the appointment.
     * @throws SQLException
     */
    public static int update(String title, String description, String location, int contactId, String type, ZonedDateTime utcStart, ZonedDateTime utcEnd, int customerId, int userId, int apptId) throws SQLException {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement psu = JDBC.connection.prepareStatement(sql);
        psu.setString(1, title);
        psu.setString(2, description);
        psu.setString(3, location);
        psu.setInt(4, contactId);
        psu.setString(5, type);
        psu.setTimestamp(6, Timestamp.valueOf(utcStart.toLocalDateTime()));
        psu.setTimestamp(7, Timestamp.valueOf(utcEnd.toLocalDateTime()));
        psu.setInt(8, customerId);
        psu.setInt(9, userId);
        psu.setInt(10, apptId);
        //This is where it flips!!
        int rowsAffected = psu.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes a record from the appointments table, based on the Appointment_ID.
     * @param appointmentId The ID of the appointment being deleted.
     * @throws SQLException
     */
    public static int delete(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Selects all fields and records from the appointments table, and populates an ObservableList with the results.
     * @throws SQLException
     */
    public static void selectAll() throws SQLException {
        String sql = "SELECT * FROM appointments;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        allAppointments.clear();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            Appointment customerAppt = new Appointment(apptId,title,description,location,contact,type,start,end,customerId,userId);
            allAppointments.add(customerAppt);
        }
    }

    public static ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

    /**
     * Selects all fields and records from the appointment table for a single customer, and populates an ObservableList with the results.
     * @throws SQLException
     */
    public static void select() throws SQLException {
        String sql = "SELECT * from appointments where Customer_ID = " + selCustomerId + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        customerAppointments.clear();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            Appointment customerAppt = new Appointment(apptId,title,description,location,contact,type,start,end,customerId,userId);
            customerAppointments.add(customerAppt);
        }
    }

    public static ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();

    /**
     * Selects all fields and records from the appointment table assigned to a specific user, and populates an ObservableList with the results.
     * @throws SQLException
     */
    public static void userSelect() throws SQLException {
        String sql = "SELECT * from appointments where User_ID = " + UserApptsReportController.selUserId + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        userAppointments.clear();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            Appointment userAppt = new Appointment(apptId,title,description,location,contact,type,start,end,customerId,userId);
            userAppointments.add(userAppt);
        }
    }

    public static ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

    /**
     * Selects all fields and records from the appointments table assigned to a specific contact, and populates an ObservableList with the results.
     * @throws SQLException
     */
    public static void contactSelect() throws SQLException {
        String sql = "SELECT * from appointments where Contact_ID = " + ContactApptsReportController.selContactId + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        contactAppointments.clear();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            Appointment userAppt = new Appointment(apptId,title,description,location,contact,type,start,end,customerId,userId);
            contactAppointments.add(userAppt);
        }
    }

    public static ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();

    /**
     * Selects fields from the appointments, users, and customers tables, and shows upcoming appointments for a specific user. The results are placed in an ObservableList.
     * @throws SQLException
     */
    public static void upcomingSelect() throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Location, a.Start, u.User_ID, u.User_Name, c.Customer_Name FROM ((appointments a INNER JOIN users u on a.User_ID = u.User_ID) INNER JOIN customers c on a.Customer_ID = c.Customer_ID) WHERE User_Name = \"" + LoginController.currentUserName + "\";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        upcomingAppointments.clear();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String location = rs.getString("Location");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String customerName = rs.getString("Customer_Name");
            Appointment upcomingAppointment = new Appointment(apptId,title,location,start,userId,userName,customerName);
            upcomingAppointments.add(upcomingAppointment);
        }
    }

    public static ObservableList<Appointment> apptsByMonth = FXCollections.observableArrayList();

    /**
     * Determines the month from the Start field, and counts the number of appointments in each month, and populates an ObservableList with the results. Adds the months with no appointments to the ObservableList afterward, with zero count values.
     * @throws SQLException
     */
    public static void countByMonth() throws SQLException {
        String sql = "SELECT CAST(MONTHNAME(Start) AS CHAR) AS Month,COUNT(*) AS Month_Count FROM appointments GROUP BY Month";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        apptsByMonth.clear();

        while(rs.next()) {
            String str = rs.getString("Month");
            int count = rs.getInt("Month_Count");

            Appointment month_appt = new Appointment(str, count);
            apptsByMonth.add(month_appt);
        }
        ObservableList<String> missingMonths = FXCollections.observableArrayList();
        missingMonths.add("January");
        missingMonths.add("February");
        missingMonths.add("March");
        missingMonths.add("April");
        missingMonths.add("May");
        missingMonths.add("June");
        missingMonths.add("July");
        missingMonths.add("August");
        missingMonths.add("September");
        missingMonths.add("October");
        missingMonths.add("November");
        missingMonths.add("December");

        for(Appointment eachAppt : apptsByMonth) {
            if(missingMonths.contains(eachAppt.getStr())){
                missingMonths.remove(String.valueOf(eachAppt.getStr()));
            }
        }
        for(String addMonths : missingMonths){
            Appointment noAppts = new Appointment(addMonths, 0);
            apptsByMonth.add(noAppts);
        }
    }

    public static ObservableList<Appointment> apptsByType = FXCollections.observableArrayList();


    /**
     * Counts the number of appointments for each existing type, and populates an ObservableList with the results.
      * @throws SQLException
     */
    public static void countByType() throws SQLException {
        String sql = "SELECT Type, COUNT(*) AS Type_Count FROM appointments GROUP BY Type;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        apptsByType.clear();

        while(rs.next()) {
            String str = rs.getString("Type");
            int count = rs.getInt("Type_Count");

            Appointment appt = new Appointment(str, count);
            apptsByType.add(appt);
        }
    }
}

