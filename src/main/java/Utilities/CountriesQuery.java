package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TheCountry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries run on the countries table.
 */
public abstract class CountriesQuery {
    private static boolean notYetCountry = true;

    /**
     * Selects all fields and records from the countries table.
     * @throws SQLException
     */
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

    /**
     * Selects all fields and records from the countries table and populates an ObservableList with the results.
     * @throws SQLException
     */
    public static ObservableList<TheCountry> getCountries() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(!notYetCountry){
            return null;
        }
        notYetCountry = false;
         while (rs.next()) {
             int countryId = 0;
             String country = "";
             TheCountry aCountry = new TheCountry(countryId, country);
             aCountry.setCountryId(rs.getInt("Country_ID"));
             aCountry.setCountryName(rs.getString("Country"));
             countryList.add(aCountry);
        }
        return countryList;

    }
}
