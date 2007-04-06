/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
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
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AccuracyTestHelper {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The sample configuration file for this component.
     * </p>
     */
    public static final String CONFIG_FILE = "test_files" + File.separator + "accuracytests" + File.separator
        + "config.xml";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private AccuracyTestHelper() {
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName config file to set up environment
     *
     * @throws Exception when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        //set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * Verifies whether the given two users are equals or not.
     * </p>
     *
     * @param expectedUser the expected user
     * @param actualUser the actual user
     */
    public static void assertUserEquals(User expectedUser, User actualUser) {
        Assert.assertEquals("The user names are not equals.", expectedUser.getUsername(), actualUser.getUsername());
        Assert.assertEquals("The passwords are not equals.", expectedUser.getPassword(), actualUser.getPassword());
        Assert.assertEquals("The statuses are not equals.", expectedUser.getStatus().getId(),
            actualUser.getStatus().getId());
        Assert.assertEquals("The addresses are not equals.", expectedUser.getAddress().getId(),
            actualUser.getAddress().getId());
        Assert.assertEquals("The company ids are not equals.", expectedUser.getCompanyId(), actualUser.getCompanyId());
        Assert.assertEquals("The contacts are not equals.", expectedUser.getContact().getId(),
            actualUser.getContact().getId());
        Assert.assertEquals("The creation users are not equals.", expectedUser.getCreationUser(),
            actualUser.getCreationUser());
        Assert.assertEquals("The modification users are not equals.", expectedUser.getModificationUser(),
            actualUser.getModificationUser());
    }

    /**
     * <p>
     * This method creates a user instance for testing purpose.
     * </p>
     *
     * @return a user instance for testing purpose.
     */
    public static User createUser() {
        User user = new User();

        user.setId(300);
        user.setUsername("topcoder");
        user.setPassword("topcoder");
        user.setStatus(Status.ACTIVE);
        user.setAddress(createAddress());
        user.setAlgorithmName("algorithm");
        user.setContact(createContact());
        user.setCreationDate(new Date());
        user.setCreationUser("tc");
        user.setModificationDate(new Date());
        user.setModificationUser("tc");

        return user;
    }

    /**
     * <p>
     * This method creates a contact instance for testing purpose.
     * </p>
     *
     * @return a contact instance for testing purpose.
     */
    public static Contact createContact() {
        Contact contact = new Contact();
        contact.setId(300);
        contact.setContactType(ContactType.USER);
        contact.setFirstName("contact");
        contact.setLastName("person");
        contact.setPhoneNumber("1234567890");
        contact.setEmailAddress("contact@person.net");
        contact.setCreationDate(new Date());
        contact.setCreationUser("tc");
        contact.setModificationDate(new Date());
        contact.setModificationUser("tc");

        return contact;
    }

    /**
     * <p>
     * This method creates an address instance for testing purpose.
     * </p>
     *
     * @return an address instance for testing purpose.
     */
    public static Address createAddress() {
        Address address = new Address();
        address.setId(300);
        address.setAddressType(AddressType.USER);
        address.setLine1("street");
        address.setLine2("appt");
        address.setCity("city");
        State state = new State();
        state.setId(1);
        address.setState(state);
        Country country = new Country();
        country.setId(1);
        address.setCountry(country);
        address.setPostalCode("10028");
        address.setCreationDate(new Date());
        address.setCreationUser("tc");
        address.setModificationDate(new Date());
        address.setModificationUser("tc");

        return address;
    }

    /**
     * <p>
     * Inserts some data to the tables which this component depends on.
     * </p>
     *
     * <p>
     * This is used to simplify the testing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void setUpDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, "test_files" + File.separator + "accuracytests" + File.separator
                + "clear_data.sql");
            executeSqlFile(connection, "test_files" + File.separator + "accuracytests" + File.separator
                + "add_data.sql");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Clears all the data from the tables using by this component.
     * </p>
     *
     * <p>
     * This is used to simplify the testing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void tearDownDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, "test_files" + File.separator + "accuracytests" + File.separator
                + "clear_data.sql");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Executes the sql scripts in the given sql file.
     * </p>
     *
     * @param connection Connection instance to access the database
     * @param sqlPath the path of the sql file to execute
     *
     * @throws SQLException if exception occurs during database operation
     * @throws IOException if fails to read the sql file
     */
    private static void executeSqlFile(Connection connection, String sqlPath) throws SQLException, IOException {
        String[] sqlStatements = loadSqlFile(sqlPath);

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            for (int i = 0; i < sqlStatements.length; i++) {
                if (sqlStatements[i].trim().length() != 0) {
                    stmt.executeUpdate(sqlStatements[i]);
                }
            }
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * This method return the sql scripts from the given sql file.
     * </p>
     *
     * @param path the path of the sql file
     * @return the sql scripts
     *
     * @throws IOException if fails to read the sql file
     */
    private static String[] loadSqlFile(String path) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        try {
            String line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.length() != 0 && !line.startsWith("--")) {
                    sb.append(line);
                }

                line = reader.readLine();
            }

            return sb.toString().split(";");
        } finally {
            reader.close();
        }
    }

    /**
     * <p>
     * Returns a new connection to be used for persistence.
     * </p>
     *
     * @return the connection instance for database operation
     *
     * @throws ConfigurationException to JUnit
     * @throws DBConnectionException to JUnit
     */
    static Connection getConnection() throws DBConnectionException, ConfigurationException {
        return new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE).createConnection();
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    static void closeConnection(Connection con) {
        try {
            if ((con != null) && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     *
     * @param state the given Statement instance to close.
     */
    static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }
}
