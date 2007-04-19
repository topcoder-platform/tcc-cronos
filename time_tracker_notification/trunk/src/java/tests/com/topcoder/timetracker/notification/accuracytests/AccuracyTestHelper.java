/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

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
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

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
            stmt.executeUpdate("DELETE FROM notify_clients");
            stmt.executeUpdate("DELETE FROM notify_projects");
            stmt.executeUpdate("DELETE FROM notify_resources");
            stmt.executeUpdate("DELETE FROM notification");
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * Asserts the given two <code>Notification</code> should be equals.
     * </p>
     *
     * @param expected the expected <code>Notification</code> instance
     * @param actual the actual <code>Notification</code> instance
     */
    public static void assertNotifications(Notification expected, Notification actual) {
        Assert.assertEquals("The notification ids are not equals.", expected.getId(), actual.getId());
        Assert.assertEquals("The notification company ids are not equals.", expected.getCompanyId(),
            actual.getCompanyId());
        Assert.assertEquals("The notification active states are not equals.", expected.isActive(), actual.isActive());
        Assert.assertEquals("The notification creation users are not equals.", expected.getCreationUser(),
            actual.getCreationUser());
        Assert.assertEquals("The notification modification users are not equals.", expected.getModificationUser(),
            actual.getModificationUser());
        Assert.assertEquals("The notification from addresses are not equals.", expected.getFromAddress(),
            actual.getFromAddress());
        Assert.assertEquals("The notification schedule ids are not equals.", expected.getScheduleId(),
            actual.getScheduleId());
        Assert.assertEquals("The notification messages are not equals.", expected.getMessage(), actual.getMessage());
        Assert.assertEquals("The notification subjects are not equals.", expected.getSubject(), actual.getSubject());

        assertLongArray("The clients ids are not equals.", expected.getToClients(), actual.getToClients());
        assertLongArray("The projects ids are not equals.", expected.getToProjects(), actual.getToProjects());
        assertLongArray("The resources ids are not equals.", expected.getToResources(), actual.getToResources());
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
     * Creates notification for testing.
     * </p>
     *
     * @param id used as the id of the company id and so on
     *
     * @return the notification
     */
    public static Notification createNotification(long id) {
        Notification notification = new Notification();

        notification.setActive(true);
        notification.setCompnayId(id);
        notification.setCreationDate(new Date());
        notification.setCreationUser("tc");
        notification.setModificationDate(new Date());
        notification.setModificationUser("tc");
        ConfigManager cm = ConfigManager.getInstance();
        if (cm.existsNamespace("email.source")) {
            try {
                cm.removeNamespace("email.source");
            } catch (UnknownNamespaceException e) {
                //do nothing.
            }
        }
        try {
            cm.add("EmailEngineTest.xml");
        } catch (ConfigManagerException e) {
            //do nothing.
        }
        String sender = null;
        try {
            sender = cm.getString("email.source", "from");
        } catch (UnknownNamespaceException e) {
            //do nothing.
        }
        notification.setFromAddress(sender);
        notification.setId(id);
        notification.setLastTimeSent(new Date());
        notification.setMessage("You Got A Message.");
        notification.setNextTimeToSend(new Date());
        notification.setScheduleId(id);
        notification.setSubject("Message");

        long[] ids = new long[] {1, 2};

        notification.setToClients(ids);
        notification.setToProjects(ids);
        notification.setToResources(ids);

        return notification;
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