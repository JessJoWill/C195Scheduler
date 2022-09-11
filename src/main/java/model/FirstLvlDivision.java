package model;

/**
 * FirstLevelDivision class.
 */
public class FirstLvlDivision {
    int divisionId;
    String division;
    int customerId;
    String country;
    int customerCount;

    /**
     * Constructor for combobox.
     * @param divisionId
     * @param division
     */
    public FirstLvlDivision(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    /**
     * Constructor for Customers By Region report.
     * @param division
     * @param country
     * @param customerCount
     */
    public FirstLvlDivision(String division, String country, int customerCount) {
        this.division = division;
        this.country = country;
        this.customerCount = customerCount;
    }

    /**
     * @return country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set country.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Set division ID.
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return division name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division name.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Override toString method to print a readable string to a combobox for each division.
     * @return a readable string representing each division.
     */
    @Override
    public String toString() {
        return (divisionId + " " + division);
    }
}
