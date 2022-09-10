package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.FirstLvlDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionsQuery {
    private static boolean notYetDivision = true;
    public static int insert(String division) throws SQLException {
        String sql = "INSERT INTO first_level_divisions (Division) VALUES (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String division) throws SQLException {
        String sql = "UPDATE first_level_divisions SET Division = ? WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int divisionId) throws SQLException {
        String sql = "DELETE FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
        }
    }

    public static ObservableList<FirstLvlDivision> divisionList = FXCollections.observableArrayList();
    public static ObservableList<FirstLvlDivision> usList = FXCollections.observableArrayList();
    public static ObservableList<FirstLvlDivision> ukList = FXCollections.observableArrayList();
    public static ObservableList<FirstLvlDivision> canadaList = FXCollections.observableArrayList();

    public static ObservableList<FirstLvlDivision> getDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (!notYetDivision){
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

    public static ObservableList<FirstLvlDivision> custByRegion = FXCollections.observableArrayList();
    public static void byRegion() throws SQLException {
        String sql = "select Division, Country, COUNT(*) AS customerCount FROM (select p.Customer_ID, d.Division, c.Country from ((first_level_divisions as d left outer join customers as p on d.Division_ID = p.Division_ID) inner join countries as c on d.Country_ID = c.Country_ID) union select p.Customer_ID, d.Division, c.Country from ((first_level_divisions as d right outer join customers as p on d.Division_ID = p.Division_ID) inner join countries as c on d.Country_ID = c.Country_ID)) as j group by Division;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        custByRegion.clear();
        while (rs.next()) {
            String division = rs.getString("Division");
            String country = rs.getString("Country");
            int customerCount = rs.getInt("customerCount");

            FirstLvlDivision cbr = new FirstLvlDivision(division, country, customerCount);
            custByRegion.add(cbr);
        }
    }
}
