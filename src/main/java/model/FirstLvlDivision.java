package model;

public class FirstLvlDivision {
    int divisionId;
    String division;
    int countryId;
    int customerId;
    String country;
    int customerCount;

    public FirstLvlDivision(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public FirstLvlDivision(String division, String country, int customerCount) {
        this.division = division;
        this.country = country;
        this.customerCount = customerCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public FirstLvlDivision(int customerId, String division, String country) {
        this.customerId = customerId;
        this.division = division;
        this.country = country;
    }


    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return (divisionId + " " + division);
    }
}
