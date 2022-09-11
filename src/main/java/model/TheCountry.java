package model;

/**
 * TheCountry class
 */
public class TheCountry {
    int countryId;
    String countryName;

    /**
     * Constructor for all TheCountry objects.
     * @param countryId
     * @param countryName
     */
    public TheCountry(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * @return country ID.
     */
     public int getCountryId() {
        return countryId;
    }

    /**
     * Set country ID.
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set country name.
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Override toString method to create a readable string to populate a combobox.
     * @return a readable string for each TheCountry object.
     */
    @Override
    public String toString() {
        return (countryId + " " + countryName);
    }
}
