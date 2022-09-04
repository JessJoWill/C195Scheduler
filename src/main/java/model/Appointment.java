package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    int apptId;
    String title;
    String description;
    String location;
    String type;
    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime createDate;
    String createdBy;
    int customerId;
    int userId;
    int contactId;

    // Add Appointment constructor
    public Appointment(String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, int customerId, int userId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.customerId = customerId;
        this.userId = userId;
    }

    // Constructor for table
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

    public int getApptId() {
        return apptId;
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
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
