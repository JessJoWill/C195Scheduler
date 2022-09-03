package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomersQuery {
    public static int insert(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Last_Update, Division_ID) VALUES (?, ?, ?, ?, NOW(), NOW(), ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList<Customer> tableCustomers = FXCollections.observableArrayList();
    public static void select() throws SQLException {
        String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, d.Division, f.Country, c.Phone \n" +
                "FROM ((first_level_divisions d \n" +
                "INNER JOIN customers c \n" +
                "ON c.Division_ID = d.Division_ID)\n" +
                "INNER JOIN countries f\n" +
                "ON d.Country_ID = f.Country_ID)\n" +
                ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String division = rs.getString("Division");
            String postalCode = rs.getString("Postal_Code");
            String country = rs.getString("Country");
            String phone = rs.getString("Phone");


            Customer tableCustomer = new Customer(customerId,customerName,address,division,postalCode,country,phone);
            tableCustomers.add(tableCustomer);
        }
    }
}
