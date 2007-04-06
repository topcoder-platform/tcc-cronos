/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.base.ejb.EntryLocalHome;
import com.topcoder.timetracker.entry.base.ejb.EntryLocalObject;
import com.topcoder.timetracker.entry.base.ejb.EntrySessionBean;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This class is the helper class for failure tests.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class FailureTestHelper {
    /**
     * Tables names used by AuthorizationManager component.
     */
    public static final String[] TABLES = {"cut_off_time", "company" };

    /**
     * Status representing the deployment of the container.
     */
    private static boolean initialized = false;

    /**
     * The private constructor to make this un-instantiatable.
     */
    private FailureTestHelper() {
    }

    /**
     * Backups the specified file.
     *
     * @param filename file to backup
     * @throws IOException if write error
     */
    public static void backupConfig(String filename) throws IOException {
        copyFile(filename, filename + ".backup");
    }

    /**
     * Clear all namespace.
     *
     * @throws UnknownNamespaceException if any error occurs
     */
    public static void clearConfig() throws UnknownNamespaceException {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator iter = cm.getAllNamespaces();

        while (iter.hasNext()) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Clears all data in the given tables.
     *
     * @param tableNames in which data should be cleared
     * @param namespace namespace for the DBConnectionFactory
     *
     * @throws Exception if any error occurs
     */
    public static void clearTable(String[] tableNames, String namespace) throws Exception {
        // create DBConnectionFactory from given namespace
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        Connection conn = factory.createConnection();
        PreparedStatement pstmt = null;

        try {
            // clears all the data
            for (int i = 0; i < tableNames.length; i++) {
                pstmt = conn.prepareStatement("delete from " + tableNames[i]);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    /**
     * Initialize database, clear the test tables and insert some default data.
     *
     * @throws Exception to junit
     */
    public static void initDB() throws Exception {
        Connection conn = getConnection("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        try {
            clearData(conn); // clears tables
            addDefaultDate(conn);
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    /**
     * Adds the default data to the database.
     *
     * @throws SQLException if any error occurs.
     */
    private static void addDefaultDate(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn
            .prepareStatement("insert into company (company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) values (?,?,?,?,?,?,?)");
        pstmt.setLong(1, 1);
        pstmt.setString(2, "company1");
        pstmt.setString(3, "code1");
        pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        pstmt.setString(5, "user1");
        pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        pstmt.setString(7, "user1");

        pstmt.executeUpdate();

        pstmt.setLong(1, 2);
        pstmt.setString(2, "company2");
        pstmt.setString(3, "code2");
        pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        pstmt.setString(5, "user2");
        pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        pstmt.setString(7, "user2");

        pstmt.executeUpdate();
    }

    /**
     * Initiate the JNI configuration. It will delete the already existing JNI configuration and bin
     * new value to it.
     *
     * @throws Exception if any error occurred
     */
    public static final void initJNDI() throws Exception {
        if (!initialized) {
            // initializes the mock ejb container and EntrySessionBean is returned as the bean
            // object to calling
            // method.
            MockContextFactory.setAsInitial();

            Context context = new InitialContext();
            MockContainer mockContainer = new MockContainer(context);

            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor("tt_entry", EntryLocalHome.class,
                EntryLocalObject.class, new EntrySessionBean());
            mockContainer.deploy(beanDescriptor);

            MockUserTransaction mockTransaction = new MockUserTransaction();
            context.rebind("javax.transaction.UserTransaction", mockTransaction);
            initialized = true;
        }
    }

    /**
     * Restore the JNDI configuration.
     *
     * @throws Exception if error occurs
     */
    public static final void restoreJNDI() throws Exception {
        if (initialized) {
            MockContextFactory.revertSetAsInitial();
            initialized = false;
        }
    }

    /**
     * Load the specified config given with the config file name.
     *
     * @param name name of the config file
     *
     * @throws ConfigManagerException if any error occurs
     */
    public static void loadConfig(String name) throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(name);
    }

    /**
     * Load the configurations.
     *
     * @throws Exception to invoker
     */
    public static void loadDefaultConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("com.topcoder.util.config.ConfigManager", "com/topcoder/util/config/ConfigManager.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
    }

    /**
     * Remove the specified property from given namespace and commit it.
     *
     * @param namespace namespace
     * @param property property to be removed
     *
     * @throws ConfigManagerException if any error occurs
     */
    public static void removeProperty(String namespace, String property) throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.removeProperty(namespace, property);
        cm.commit(namespace, "testhelper");
        cm.refresh(namespace);
    }

    /**
     * Restore the backup file.
     *
     * @param filename name of the file
     *
     * @throws IOException if any error occurs while restoring
     */
    public static void restoreConfig(String filename) throws IOException {
        String backupFile = filename + ".backup";
        copyFile(backupFile, filename);
        new File(FailureTestHelper.class.getClassLoader().getResource(backupFile).getFile()).delete();

        ConfigManager.getInstance().refreshAll();
    }

    /**
     * Set the property to a new value.
     *
     * @param namespace namespace of the property
     * @param property the property name
     * @param value the new value
     *
     * @throws ConfigManagerException if any error occurs
     */
    public static void setProperty(String namespace, String property, String value) throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.setProperty(namespace, property, value);
        cm.commit(namespace, "testhelper");
        cm.refresh(namespace);
    }

    /**
     * Unload the configurations.
     *
     * @throws Exception to invoker
     */
    public static void unloadAllConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }

    /**
     * Clears the default tables.
     *
     * @param conn connection
     *
     * @throws SQLException if any error occurs
     */
    private static void clearData(Connection conn) throws SQLException {
        // clears all the data
        for (int i = 0; i < TABLES.length; i++) {
            PreparedStatement pstmt = conn.prepareStatement("delete from " + TABLES[i]);
            pstmt.executeUpdate();
        }
    }

    /**
     * <p>
     * Close the given Connection.
     * </p>
     *
     * @param conn the Statement instance to close.
     */
    private static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) {
                // Ignore
            }
        }
    }

    /**
     * copy file from the src to dest.
     *
     * @param src source file
     * @param dest destination
     *
     * @throws IOException if any error occurs
     */
    private static void copyFile(String src, String dest) throws IOException {
        URL url = FailureTestHelper.class.getClassLoader().getResource(src);
        String path = url.getFile().replaceFirst(src, "");
        File destFile = new File(path + File.separator + dest);

        BufferedReader br = new BufferedReader(new FileReader(url.getFile()));
        String line = br.readLine();

        PrintWriter pw = new PrintWriter(new FileOutputStream(destFile));

        while (line != null) {
            pw.println(line);
            line = br.readLine();
        }

        pw.close();
        br.close();
    }

    /**
     * Gets connection.
     *
     * @param namespace namespace DBConnectionFactory configured
     *
     * @return connection
     *
     * @throws Exception if any error occurs
     */
    private static Connection getConnection(String namespace) throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        Connection conn = factory.createConnection();
        conn.setAutoCommit(false);

        return conn;
    }

    /**
     * <p>
     * Rolls back the current connection.
     * </p>
     *
     * @param connection the connection to rollback
     */
    private static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
