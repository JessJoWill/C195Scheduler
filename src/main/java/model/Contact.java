package model;

/**
 * Contact class.
 */
public class Contact {
    int contactId;
    String contactName;
    String contactEmail;

    /**
     * Constructor for all contacts.
     * @param contactId
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return the contact ID.
     */
    public int getContactId() { return contactId; }

    /**
     * Set the contact ID.
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * set the contact name.
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * set the contact email.
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Overrides the toString method to display objects in the contacts combobox in a way a user can understand them.
     * @return readable string representing each contact.
     */
    @Override
    public String toString() {
        return (contactId + " " + contactName);
    }
}
