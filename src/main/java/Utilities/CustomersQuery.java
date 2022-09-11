package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;

/**
 * Queries run on the customers table.
 */
public abstract class CustomersQuery {

    /**
     * Inserts new customers into the customers table.
     * @param customerName Name of the customer.
     * @param address Address of the customer.
     * @param postalCode Postal code of the customer.
     * @param phone Phone number of the customer.
     * @param divisionId ID number of the division the customer address is in.
     * @throws SQLException
     */
    public static int insert(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates customer records in the customers table.
     * @param customerName Name of the customer.
     * @param address Address of the customer.
     * @param postalCode Postal code of the customer.
     * @param phone Phone number of the customer.
     * @param divisionId ID of the division the customer address is in.
     * @param customerId ID number of the customer record.
     * @throws SQLException
     */
    public static int update(String customerName, String address, String postalCode, String phone, int divisionId, int customerId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Removes a customer record from the customers table.
     * @param customerId
     * @throws SQLException
     */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList<Customer> tableCustomers = FXCollections.observableArrayList();

    /**
     * Selects the fields needed for the Customers table from the first_level_divisions, customers and countries tables, and populates an ObservableList with the information.
     * @throws SQLException
     */
    public static void select() throws SQLException {
        String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, d.Division, f.Country, c.Phone FROM ((first_level_divisions d INNER JOIN customers c ON c.Division_ID = d.Division_ID) INNER JOIN countries f ON d.Country_ID = f.Country_ID);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        tableCustomers.clear();
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

    /**
     * Searches the ObservableList from the Customers table by customer name or ID and populates another ObservableList with the results.
     * @param customerName
     */
    public static ObservableList<Customer> findCustomer(String customerName) {
        ObservableList<Customer> foundList = FXCollections.observableArrayList();

        for (Customer customer : tableCustomers) {
            if (customer.getCustomerName().toLowerCase().contains(customerName.toLowerCase())) {
                foundList.add(customer);
            }
        }
        return foundList;
    }
    public static ObservableList<Customer> findCustomer(int customerId) {
        ObservableList<Customer> foundList = FXCollections.observableArrayList();

        for (Customer customer : tableCustomers) {
            if (customer.getCustomerId() == (customerId)) {
                foundList.add(customer);
            }
        }
        return foundList;
    }
}
