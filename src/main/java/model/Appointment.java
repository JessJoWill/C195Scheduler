package model;

import java.sql.Timestamp;

public class Appointment {
    int apptId;
    String title;
    String description;
    String location;
    String type;
    java.sql.Timestamp apptStart;
    java.sql.Timestamp apptEnd;
    int customerId;
    int userId;
    int contactId;

    public Appointment(int apptId, String title, String description, String location, String type, Timestamp apptStart, Timestamp apptEnd, int customerId, int userId, int contactId) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getApptStart() {
        return apptStart;
    }

    public void setApptStart(Timestamp apptStart) {
        this.apptStart = apptStart;
    }

    public Timestamp getApptEnd() {
        return apptEnd;
    }

    public void setApptEnd(Timestamp apptEnd) {
        this.apptEnd = apptEnd;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
