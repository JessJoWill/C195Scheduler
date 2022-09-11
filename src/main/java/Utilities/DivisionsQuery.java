package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLvlDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries run primarily on the first_level_divisions table.
 */
public abstract class DivisionsQuery {
    private static boolean notYetDivision = true;

    /**
     * Selects all fields and records from the first_level_divisions table.
     *
     * @throws SQLException
     */
    public static void select() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
        }
    }

    public static ObservableList<FirstLvlDivision> divisionList = FXCollections.observableArrayList();
    public static ObservableList<FirstLvlDivision> usList = FXCollections.observableArrayList();
    public static ObservableList<FirstLvlDivision> ukList = FXCollections.observableArrayList();
    public static ObservableList<FirstLvlDivision> canadaList = FXCollections.observableArrayList();

    /**
     * Selects all fields and records from the first_level_divisions table, and populates an ObservableList with the results.
     *
     * @throws SQLException
     */
    public static ObservableList<FirstLvlDivision> getDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (!notYetDivision) {
            return null;
        }
        notYetDivision = false;

        while (rs.next()) {
            int divisionId = 0;
            String division = "";
            FirstLvlDivision aDivision = new FirstLvlDivision(divisionId, division);
            aDivision.setDivisionId(rs.getInt("Division_ID"));
            aDivision.setDivision(rs.getString("Division"));
            divisionList.add(aDivision);
        }
        return divisionList;
    }

    /**
     * Selects all records from the first_level_divisions table where the country is the US, and populates an ObservableList with the results.
     *
     * @throws SQLException
     */
    public static ObservableList<FirstLvlDivision> getUSDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = 0;
            String division = "";
            FirstLvlDivision aDivision = new FirstLvlDivision(divisionId, division);
            aDivision.setDivisionId(rs.getInt("Division_ID"));
            aDivision.setDivision(rs.getString("Division"));
            usList.add(aDivision);
        }
        return usList;
    }

    /**
     * Selects all records from the first_level_divisions table where the country is the UK, and populates an ObservableList with the results.
     *
     * @throws SQLException
     */
    public static ObservableList<FirstLvlDivision> getUKDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = 0;
            String division = "";
            FirstLvlDivision aDivision = new FirstLvlDivision(divisionId, division);
            aDivision.setDivisionId(rs.getInt("Division_ID"));
            aDivision.setDivision(rs.getString("Division"));
            ukList.add(aDivision);
        }
        return ukList;
    }

    /**
     * Selects all records from the first_level_divisions table where the country is Canada, and populates an ObservableList with the results.
     *
     * @throws SQLException
     */
    public static ObservableList<FirstLvlDivision> getCanadaDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = 0;
            String division = "";
            FirstLvlDivision aDivision = new FirstLvlDivision(divisionId, division);
            aDivision.setDivisionId(rs.getInt("Division_ID"));
            aDivision.setDivision(rs.getString("Division"));
            canadaList.add(aDivision);
        }
        return canadaList;
    }

    /**
     * Selects all records from the first_level_divisions table, and adds information from the countries table and the customers table, counting the number of customers from each division.
     */
    public static ObservableList<FirstLvlDivision> custByRegion = FXCollections.observableArrayList();
    public static void byRegion() throws SQLException {
        String sql = "SELECT d.Division, c.Country, IFNULL(COUNT(p.Customer_ID), 0) AS Customer_Count FROM first_level_divisions d LEFT JOIN customers AS p ON d.Division_ID = p.Division_ID INNER JOIN countries AS c ON d.Country_ID = c.Country_ID GROUP BY d.Division;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        custByRegion.clear();
        while (rs.next()) {
            String division = rs.getString("Division");
            String country = rs.getString("Country");
            int customerCount = rs.getInt("Customer_Count");

            FirstLvlDivision cbr = new FirstLvlDivision(division, country, customerCount);
            custByRegion.add(cbr);
        }
    }
}
