/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
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
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class TestHelper {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_FACTORY_NAMESPACE =
        "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The sample configuration file for this component.
     * </p>
     */
    public static final String CONFIG_FILE = "test_files" + File.separator + "config.xml";

    /**
     * <p>
     * The sample configuration file for this component version 3.2.1.
     * </p>
     *
     * @since 3.2.1
     */
    public static final String CONFIG_FILE_3_2_1 = "test_files" + File.separator + "configSearchManager.xml";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Use the given file to config the given namespace the format of the config file is
     * ConfigManager.CONFIG_XML_FORMAT.
     * </p>
     *
     * @param namespace
     *            use the namespace to load config information to ConfigManager
     * @param fileName
     *            config file to set up environment
     *
     * @throws Exception
     *             when any exception occurs
     */
    public static void loadSingleXMLConfig(String namespace, String fileName) throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        // config namespace
        if (config.existsNamespace(namespace)) {
            config.removeNamespace(namespace);
        }

        config.add(namespace, file.getCanonicalPath(), ConfigManager.CONFIG_XML_FORMAT);
    }

    /**
     * <p>
     * Remove the given namespace in the ConfigManager.
     * </p>
     *
     * @param namespace
     *            namespace use to remove the config information in ConfigManager
     *
     * @throws Exception
     *             when any exception occurs
     */
    public static void clearConfigFile(String namespace) throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        // clear the environment
        if (config.existsNamespace(namespace)) {
            config.removeNamespace(namespace);
        }
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName
     *            config file to set up environment
     *
     * @throws Exception
     *             when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
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
     * @param expectedUser
     *            the expected user
     * @param actualUser
     *            the actual user
     */
    public static void assertUserEquals(User expectedUser, User actualUser) {
        Assert.assertEquals("The user names are not equals.", expectedUser.getUsername(), actualUser
            .getUsername());
        Assert.assertEquals("The passwords are not equals.", expectedUser.getPassword(), actualUser
            .getPassword());
        Assert.assertEquals("The statuses are not equals.", expectedUser.getStatus().getId(), actualUser
            .getStatus().getId());
        Assert.assertEquals("The addresses are not equals.", expectedUser.getAddress().getId(), actualUser
            .getAddress().getId());
        Assert.assertEquals("The company ids are not equals.", expectedUser.getCompanyId(), actualUser
            .getCompanyId());
        Assert.assertEquals("The contacts are not equals.", expectedUser.getContact().getId(), actualUser
            .getContact().getId());
        Assert.assertEquals("The creation users are not equals.", expectedUser.getCreationUser(), actualUser
            .getCreationUser());
        Assert.assertEquals("The modification users are not equals.", expectedUser.getModificationUser(),
            actualUser.getModificationUser());
    }

    /**
     * <p>
     * This method creates a user instance for testing purpose.
     * </p>
     *
     * @param missingField
     *            the field name to missing
     *
     * @return a user instance for testing purpose.
     */
    public static User createTestingUser(String missingField) {
        User user = new User();

        user.setId(300);

        if (!"UserName".equals(missingField)) {
            user.setUsername("topcoder");
        }
        if (!"Password".equals(missingField)) {
            user.setPassword("topcoder");
        }
        if (!"Status".equals(missingField)) {
            user.setStatus(Status.ACTIVE);
        }
        if (!"Address".equals(missingField)) {
            user.setAddress(createTestingAddress());
        }
        user.setAlgorithmName("algorithm");
        if (!"Contact".equals(missingField)) {
            user.setContact(createTestingContact());
        }
        if (!"CreationDate".equals(missingField)) {
            user.setCreationDate(new Date());
        }
        if (!"CreationUser".equals(missingField)) {
            user.setCreationUser("tc");
        }
        if (!"ModificationDate".equals(missingField)) {
            user.setModificationDate(new Date());
        }
        if (!"ModificationUser".equals(missingField)) {
            user.setModificationUser("tc");
        }

        return user;
    }

    /**
     * <p>
     * This method creates a contact instance for testing purpose.
     * </p>
     *
     * @return a contact instance for testing purpose.
     */
    public static Contact createTestingContact() {
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
    public static Address createTestingAddress() {
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
     * @throws Exception
     *             to JUnit
     */
    public static void setUpDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, "test_files" + File.separator + "clear_data.sql");
            executeSqlFile(connection, "test_files" + File.separator + "add_data.sql");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
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
     * @throws Exception
     *             to JUnit
     */
    public static void tearDownDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, "test_files" + File.separator + "clear_data.sql");
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
     * This method sets the user manager in the <code>UserManagerFactory</code> to null using reflection.
     * </p>
     */
    public static void resetUserManagerToNull() {
        try {
            Field field = UserManagerFactory.class.getDeclaredField("userManager");
            field.setAccessible(true);
            field.set(null, null);
        } catch (SecurityException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    /**
     * <p>
     * This method sets the user status manager in the <code>UserManagerFactory</code> to null using reflection.
     * </p>
     *
     * @since 3.2.1
     */
    public static void resetUserStatusManagerToNull() {
        try {
            Field field = UserManagerFactory.class.getDeclaredField("userStatusManager");
            field.setAccessible(true);
            field.set(null, null);
        } catch (SecurityException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    /**
     * <p>
     * This method sets the user type manager in the <code>UserManagerFactory</code> to null using reflection.
     * </p>
     *
     * @since 3.2.1
     */
    public static void resetUserTypeManagerToNull() {
        try {
            Field field = UserManagerFactory.class.getDeclaredField("userTypeManager");
            field.setAccessible(true);
            field.set(null, null);
        } catch (SecurityException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Executes the sql scripts in the given sql file.
     * </p>
     *
     * @param connection
     *            Connection instance to access the database
     * @param sqlPath
     *            the path of the sql file to execute
     *
     * @throws SQLException
     *             if exception occurs during database operation
     * @throws IOException
     *             if fails to read the sql file
     */
    private static void executeSqlFile(Connection connection, String sqlPath) throws SQLException,
        IOException {
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
     * @param path
     *            the path of the sql file
     * @return the sql scripts
     *
     * @throws IOException
     *             if fails to read the sql file
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
     * @throws ConfigurationException
     *             to JUnit
     * @throws DBConnectionException
     *             to JUnit
     */
    static Connection getConnection() throws DBConnectionException, ConfigurationException {
        return new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE).createConnection();
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param con
     *            the given Connection instance to close.
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
     * @param state
     *            the given Statement instance to close.
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
