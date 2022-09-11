package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries run on the users table.
 */
public abstract class UsersQuery {

    /**
     * Selects all fields from the users table.
     * @throws SQLException
     */
    public static void select() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
        }
    }

    public static ObservableList<User> userList = FXCollections.observableArrayList();
    private static boolean notYetUsers = true;

    /**
     * Selects all fields from the users table, and populates an Observable list with the results.
     * @throws SQLException
     */
    public static ObservableList<User> getUsers() throws SQLException {
        UsersQuery.select();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int userId = 0;
        String userName = "";
        String password = "";
        if(!notYetUsers){
            return null;
        }
        notYetUsers = false;
        while (rs.next()) {
            User theUser = new User(userId, userName, password);
            theUser.setUserId(rs.getInt("User_ID"));
            theUser.setUserName(rs.getString("User_Name"));
            theUser.setPassword(rs.getString("Password"));
            userList.add(theUser);
        }
        return userList;
    }
}
