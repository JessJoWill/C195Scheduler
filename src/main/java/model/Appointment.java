package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Appointment class.
 */
public class Appointment {
    Integer apptId;
    String title;
    String description;
    String location;
    String type;
    LocalDateTime start;
    LocalDateTime end;
    int customerId;
    int userId;
    int contactId;
    String userName;
    String customerName;
    String str;
    int count;

    /**
     * The constructor for adding appointments.
      */
    public Appointment(String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, int customerId, int userId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * The constructor for Appointments in the tableview.
     * @param apptId
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     */
    public Appointment(int apptId, String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * The constructor for upcoming appointments.
     * @param apptId
     * @param title
     * @param location
     * @param start
     * @param userId
     * @param userName
     * @param customerName
     */
    public Appointment(int apptId, String title, String location, LocalDateTime start, int userId, String userName, String customerName) {
        this.apptId = apptId;
        this.title = title;
        this.location = location;
        this.start = start;
        this.userName = userName;
        this.customerName = customerName;
    }

    /**
     * The constructor for appointment counts by month and type.
     * @param str Either the appointment type or the month, depending on which you're counting.
     * @param count
     */
    public Appointment(String str, int count) {
        this.str = str;
        this.count = count;
    }

    /**
     * Gets the appointment type or month, depending on which you're counting.
     * @return String representing month or appointment type.
     */
    public String getStr() {
        return str;
    }

    /**
     * @return Appointment ID.
     */
    public Integer getApptId() {
        return apptId;
    }

    /**
     * @return appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location of the appointment.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the appointment date and start time.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Set the appointment date and start time.
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * @return the appointment date and end time.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return the customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Set the customer ID.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the User ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the customer name.
     */
    public String getCustomerName() {
        return customerName;
    }
}
