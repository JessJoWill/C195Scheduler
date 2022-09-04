package Utilities;

import DAO.JDBC;
import controller.CustomersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static controller.CustomersController.selCustomerId;

public abstract class AppointmentsQuery {
    public static int insert(String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, int customerId, int userId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Contact_ID, Type, Start, End, Create_Date, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setInt(4, contactId);
        ps.setString(5, type);
        ps.setTimestamp(6, Timestamp.valueOf(start));
        ps.setTimestamp(7, Timestamp.valueOf(end));
        ps.setTimestamp(8, Timestamp.valueOf(createDate));
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime lastUpdate, String updatedBy, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(lastUpdate));
        ps.setString(8, updatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        int rowsAffected = ps.executeUpdate();
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
            Integer contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            Integer customerId = rs.getInt("Customer_ID");
            Integer userId = rs.getInt("User_ID");
            Appointment customerAppt = new Appointment(apptId,title,description,location,contact,type,start,end,customerId,userId);
            customerAppointments.add(customerAppt);
        }
    }
}
