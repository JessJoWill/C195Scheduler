package model;

import java.sql.Date;

public class Customer {
    int customerId;
    String customerName;
    String address;
    String postalCode;
    String phone;
    Date Create_Date;
    String Created_By;
    Date Last_Update;
    String Last_Updated_By;
    String country;
    String division;

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    int divisionId;

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    // Add Customer Constructor
    public Customer(String customerName, String address, int divisionId, String postalCode, String country, String phone) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.country = country;
    }

    // TableCustomer Constructor
    public Customer(int customerId, String customerName, String address, String division, String postalCode, String country, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.division = division;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }

    public Date getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        this.Create_Date = create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        this.Created_By = created_By;
    }

    public Date getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Date last_Update) {
        this.Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.Last_Updated_By = last_Updated_By;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
