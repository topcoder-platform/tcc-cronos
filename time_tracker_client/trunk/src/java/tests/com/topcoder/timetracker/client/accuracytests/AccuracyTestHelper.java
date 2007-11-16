/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author victorsam
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
     * Creates a client instance for testing.
     * </p>
     *
     * @param id the id
     * @return the client
     */
    public static Client createClient(long id) {
        Client client = new Client();

        client.setActive(true);
        client.setChanged(true);
        client.setCompanyId(id);
        client.setCreationDate(new Date());
        client.setCreationUser("creationUser");
        client.setEndDate(new Date());
        client.setId(id);
        client.setModificationDate(new Date());
        client.setModificationUser("modificationUser");
        client.setName("userName" + id);
        PaymentTerm term = new PaymentTerm();
        term.setId(id);
        client.setPaymentTerm(term);
        client.setSalesTax(0.1);
        client.setStartDate(new Date());

        Contact contact = new Contact();
        contact.setId(id);
        client.setContact(contact);

        Address address = new Address();
        address.setId(id);
        client.setAddress(address);

        Project project = new Project();
        project.setId(id);
        client.setProjects(new Project[] {project});

        return client;
    }

    /**
     * <p>
     * Asserts the given two <code>Client</code> should be equals.
     * </p>
     *
     * @param expected the expected <code>Notification</code> instance
     * @param actual the actual <code>Notification</code> instance
     */
    public static void assertClients(Client expected, Client actual) {
        Assert.assertEquals("The Client ids are not equals.", expected.getId(), actual.getId());
        Assert.assertEquals("The Client company ids are not equals.", expected.getCompanyId(), actual.getCompanyId());
        Assert.assertEquals("The Client active states are not equals.", expected.isActive(), actual.isActive());
        Assert.assertEquals("The Client creation users are not equals.", expected.getCreationUser(),
            actual.getCreationUser());
        Assert.assertEquals("The Client modification users are not equals.", expected.getModificationUser(),
            actual.getModificationUser());
        Assert.assertEquals("The Client payment terms are not equals.", expected.getPaymentTerm(),
            actual.getPaymentTerm());
    }

    /**
     * Clear the data of the database.
     *
     * @param dbFactory the db factory
     * @param connName the connection name
     *
     * @throws Exception any exception to JUnit
     */
    public static void setUpDatabase()
        throws Exception {
        Statement stmt = null;
        Connection conn = null;

        conn = getConnection();

        stmt = conn.createStatement();

        tearDownDataBase();
        stmt.executeUpdate("insert into company values (2, 'company2', 'passcode2', CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into company values (1, 'company1', 'passcode1', CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (1, 'project1', 1, 'passcode1', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (2, 'project2', 1, 'passcode2', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (3, 'project3', 1, 'passcode3', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (4, 'project4', 1, 'passcode4', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");

        conn.close();
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
            clearTables(connection);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Deletes data from all the tables used by this component.
     * </p>
     *
     * @param connection Connection instance to access the database
     *
     * @throws SQLException if exception occurs during database operation
     */
    private static void clearTables(Connection connection) throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("delete from client_project");
            stmt.executeUpdate("delete from project");
            stmt.executeUpdate("delete from client");
            stmt.executeUpdate("delete from company");
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * Asserts the given two <code>long</code> arrays should be equals.
     * </p>
     *
     * @param msg the message to use when the two arrays are not equal
     * @param expected the expected <code>long</code> array instance
     * @param actual the actual <code>long</code> array instance
     */
    public static void assertLongArray(String msg, long[] expected, long[] actual) {
        Assert.assertEquals(msg, expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(msg, expected[i], actual[i]);
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