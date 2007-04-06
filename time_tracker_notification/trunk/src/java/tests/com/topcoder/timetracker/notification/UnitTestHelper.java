/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;
import java.lang.reflect.Field;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.config.ConfigManager;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
/**
 * Defines helper methods used in unit tests of this component.
 *
 * @author kzhu
 * @version 3.2
 */
final public class UnitTestHelper {
    /**
     * Represents the db factory namespace.
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
    /**
     * This private constructor prevents the creation of a new instance.
     */
    private UnitTestHelper() {
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance. If the instance is null, the field is a static field. If any error occurs, null is returned.
     *
     * @param type
     *            the class which the private field belongs to
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be retrieved
     * @return the value of the private field
     */
    static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;
        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);
            // set the field accessible
            field.setAccessible(true);
            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
        return obj;
    }

    /**
     * Sets the value of a private field in the given class.
     *
     * @param type
     *            the class which the private field belongs to
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     */
    static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;
        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);
            // set the field accessible
            field.setAccessible(true);
            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
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
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * Clear the data of the database.
     *
     * @param dbFactory the database connection factory.
     * @param connName the connection name.
     *
     * @throws Exception any exception to JUnit
     */
    public static void clearDatabase(DBConnectionFactory dbFactory, String connName) throws Exception {
        Statement stmt = null;
        Connection conn = null;

        conn = createConnection(dbFactory, connName, true);

        stmt = conn.createStatement();

        stmt.executeUpdate("delete from notification");
        stmt.executeUpdate("delete from notify_clients");
        stmt.executeUpdate("delete from notify_projects");
        stmt.executeUpdate("delete from notify_resources");

        conn.close();
    }

    /**
     * <p>
     * Get database connection from the db connection factory. It will set the connection to auto commit as required.
     * </p>
     * @param dbFactory the database connection factory.
     * @param connName the connection name.
     * @param autoCommit auto commit flag
     *
     * @return A database connection.
     *
     * @throws NotificationPersistenceException If can't get connection or fails to set the auto commit value.
     */
    public static Connection createConnection(DBConnectionFactory dbFactory, String connName, boolean autoCommit)
        throws NotificationPersistenceException {
        try {
            // create a DB connection
            Connection conn = dbFactory.createConnection(connName);

            // Begin transaction.
            conn.setAutoCommit(autoCommit);

            return conn;
        } catch (DBConnectionException dbce) {
            throw new NotificationPersistenceException("Can't get the connection from database.", dbce);
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error while setting auto commit", sqle);
        }
    }
    
    /**
     * Insert notifications to database.
     *
     * @throws Exception any exception to Junit
     */
    public static void insertToDatabase(NotificationPersistence persistence) throws Exception {
        for (int i = 1; i < 5; i++) {
            persistence.createNotification(createNotification(i), false);
        }
    }

    /**
     * Create notification.
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
        notification.setCreationUser("creation_user");
        notification.setModificationDate(new Date());
        notification.setModificationUser("modification_user");
        notification.setFromAddress("aaa@topcoder.com");
        notification.setId(id);
        notification.setLastTimeSent(new Date());
        notification.setMessage("Hello, you win.");
        notification.setNextTimeToSend(new Date());
        notification.setScheduleId(id);
        notification.setSubject("hello");

        long[] ids = new long[] {1, 2, 3, 4, 5};

        notification.setToClients(ids);
        notification.setToProjects(ids);
        notification.setToResources(ids);

        return notification;
    }
}