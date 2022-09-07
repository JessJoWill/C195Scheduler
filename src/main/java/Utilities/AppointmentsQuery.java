package Utilities;

import DAO.JDBC;
import controller.LoginController;
import controller.UserAppointmentsReportController;
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

public abstract class AppointmentsQuery {
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

    public static int delete(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
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
    public static void userSelect() throws SQLException {
        String sql = "SELECT * from appointments where User_ID = " + UserAppointmentsReportController.selUserId + ";";
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

    public static ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
    public static void upcomingSelect() throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Location, a.Start, u.User_Name, c.Customer_Name FROM ((appointments a INNER JOIN users u on a.User_ID = u.User_ID) INNER JOIN customers c on a.Customer_ID = c.Customer_ID) WHERE User_Name = \"" + LoginController.currentUserName + "\";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        upcomingAppointments.clear();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String location = rs.getString("Location");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            String userName = rs.getString("User_Name");
            String customerName = rs.getString("Customer_Name");
            Appointment upcomingAppointment = new Appointment(apptId,title,location,start,userName,customerName);
            upcomingAppointments.add(upcomingAppointment);
        }
    }
}
