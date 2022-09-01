package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TheCountry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public abstract class CountriesQuery {
    public static int insert(String country) throws SQLException {
        String sql = "INSERT INTO countries (Country) VALUES (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String country) throws SQLException {
        String sql = "UPDATE countries SET Country = ? WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int countryId) throws SQLException {
        String sql = "DELETE FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String country = rs.getString("Country");
        }
    }

    public static ObservableList<TheCountry> countryList = FXCollections.observableArrayList();
    public static ObservableList<TheCountry> getCountries() throws SQLException {
        CountriesQuery.select();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
         while (rs.next()) {
             int countryId = 0;
             String country = "";
            TheCountry aCountry = new TheCountry(countryId, country);
            aCountry.setCountryId(rs.getInt("Country_ID"));
            aCountry.setCountry(rs.getString("Country"));
            countryList.add(aCountry);
        }
        return countryList;

    }
}
