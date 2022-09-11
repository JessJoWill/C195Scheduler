package Utilities;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries run on the contacts table.
 */
public abstract class ContactsQuery {
    private static boolean notYetContacts = true;
    public static ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * Selects all fields and records from the contacts table and populates an ObservableList with the results.
     * @throws SQLException
     */
    public static ObservableList<Contact> getContacts() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(!notYetContacts){
            return null;
        }
        notYetContacts = false;
        while (rs.next()) {
            int contactId = 0;
            String contactName = "";
            String contactEmail = "";
            Contact aContact = new Contact(contactId, contactName, contactEmail);
            aContact.setContactId(rs.getInt("Contact_ID"));
            aContact.setContactName(rs.getString("Contact_Name"));
            aContact.setContactEmail(rs.getString("Email"));
            contactList.add(aContact);
        }
        return contactList;
    }
}
