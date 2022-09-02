package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TheCountry;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UsersQuery {
    public static int insert(String userName, String password) throws SQLException {
        String sql = "INSERT INTO users (User_Name, Password) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String userName, String password) throws SQLException {
        String sql = "UPDATE users SET User_Name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

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
    public static ObservableList<User> getUsers() throws SQLException {
        UsersQuery.select();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = 0;
            String userName = "";
            String password = "";
            User theUser = new User(userId, userName, password);
            theUser.setUserId(rs.getInt("User_ID"));
            theUser.setUserName(rs.getString("User_Name"));
            theUser.setPassword(rs.getString("Password"));
            userList.add(theUser);
        }
        return userList;

    }
}
