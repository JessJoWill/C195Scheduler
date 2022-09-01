package model;

public class TheCountry {
    int countryId;
    String country;

    public TheCountry(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

     public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return (countryId + " " + country);
    }
}
