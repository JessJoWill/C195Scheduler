package model;

import java.sql.Date;

/**
 * Customer class.
 */
public class Customer {
    int customerId;
    String customerName;
    String address;
    String postalCode;
    String phone;
    String country;
    String division;
    int divisionId;

    /**
     * Constructor for adding new customers.
     * @param customerName
     * @param address
     * @param divisionId
     * @param postalCode
     * @param country
     * @param phone
     */
    public Customer(String customerName, String address, int divisionId, String postalCode, String country, String phone) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.country = country;
    }

    /**
     * Constructor for adding customers to the tableview.
     * @param customerId
     * @param customerName
     * @param address
     * @param division
     * @param postalCode
     * @param country
     * @param phone
     */
    public Customer(int customerId, String customerName, String address, String division, String postalCode, String country, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.division = division;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }

    /**
     * @return the first level division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * @return the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Set customer name.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return customer address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set customer address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Override the toString method to print customer information in a combobox in a readable format.
     * @return readable string representing each customer.
     */
    @Override
    public String toString() {
        return (customerId + " " + customerName);
    }
}
