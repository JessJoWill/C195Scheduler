package model;

/**
 * User class.
 */
public class User {
    int userId;
    String userName;
    String password;

    /**
     * Constructor for all User objects.
     * @param userId
     * @param userName
     * @param password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set user ID.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set username.
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Override toString method to produce a readable string for a combobox.
     * @return a readable string for each user.
     */
    @Override
    public String toString() {
        return (userId + " " + userName);
    }
}
