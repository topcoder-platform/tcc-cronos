/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.User;
import com.topcoder.util.config.ConfigManager;

/**
 * A helper class for stress test.
 *
 * @author Chenhong
 * @version 3.2
 */
class DBUtil {

    /**
     * Private constructor.
     *
     */
    private DBUtil() {
        // empty.
    }

    /**
     * Clear all the config namespace.
     *
     * @throws Exception
     *             to junit.
     */
    static void clearConfigNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Create a Contact instance for testing.
     *
     * @param id
     *            the id for Contact
     *
     * @return Contact instance.
     */
    static Contact createContact(long id) {
        Contact contact = new Contact();
        contact.setId(id);
        contact.setContactType(ContactType.USER);
        contact.setFirstName("laura");
        contact.setLastName("simmon");
        contact.setPhoneNumber("1234567890");
        contact.setEmailAddress("simmon@topcoder.com");
        contact.setCreationDate(new Date());
        contact.setCreationUser("reviewer");
        contact.setModificationDate(new Date());
        contact.setModificationUser("reviewer");
        return contact;
    }

    /**
     * Create an Address instance.
     *
     * @param id
     *            the id for address.
     * @return Address instance.
     */
    static Address createAddress(long id) {
        Address address = new Address();
        address.setId(id);
        address.setAddressType(AddressType.USER);
        address.setLine1("line1");
        address.setLine2("line2");
        address.setCity("City");
        State state = new State();
        state.setId(1);
        address.setState(state);
        Country country = new Country();
        country.setId(1);
        address.setCountry(country);
        address.setPostalCode("510275");
        address.setCreationDate(new Date());
        address.setCreationUser("review");
        address.setModificationDate(new Date());
        address.setModificationUser("reviewer");

        return address;
    }

    /**
     * Create a User for testing.
     *
     * @param id
     *            the id for the user.
     * @return a User instance.
     */
    static User createUser(int id) {
        User user = new User();

        user.setId(id);

        user.setUsername("topcoder");
        user.setPassword("topcoder");
        user.setStatus(Status.ACTIVE);
        user.setAddress(createAddress(id));
        user.setAlgorithmName("algorithm");
        user.setContact(createContact(id));
        user.setCreationDate(new Date());
        user.setCreationUser("tc");
        user.setModificationDate(new Date());
        user.setModificationUser("tc");

        return user;
    }

    /**
     * Create a connection to database.
     *
     * @return the sql connection
     * @throws Exception
     *             to junit.
     */
    static Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        return factory.createConnection();
    }

    /**
     * Clear the tables.
     *
     * @throws Exception
     *             to junit.
     */
    static void clearTables() throws Exception {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM contact_relation");
            statement.executeUpdate("DELETE FROM address_relation");
            statement.executeUpdate("DELETE FROM contact");
            statement.executeUpdate("DELETE FROM address");
            statement.executeUpdate("DELETE FROM user_account");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Close the connection to the database.
     *
     * @param connection
     *            the connection to be closed.
     */
    static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore.
            }
        }
    }

    /**
     * Close the Statement instance.
     *
     * @param statement
     *            the Statement instance to be closed.
     */
    static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore.
            }
        }
    }
}