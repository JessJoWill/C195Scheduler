package model;

public class FirstLvlDivision {
    int divisionId;
    String division;
    int countryId;

    public FirstLvlDivision(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
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
