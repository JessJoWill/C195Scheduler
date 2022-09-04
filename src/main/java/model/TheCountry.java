package model;

public class TheCountry {
    int countryId;
    String countryName;

    public TheCountry(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

     public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return (countryId + " " + countryName);
    }
}
