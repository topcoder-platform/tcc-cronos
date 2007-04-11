/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.rates.ejb.LocalHomeRate;
import com.topcoder.timetracker.rates.ejb.LocalRate;
import com.topcoder.timetracker.rates.ejb.RateEjb;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

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

import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * The helper class unit tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /** Tables names used by AuthorizationManager component. */
    public static final String[] TABLES = {"comp_rate", "rate" };

    /** Status representing the deployment of the container. */
    private static boolean initialized = false;

    /**
     *Default rates.
     */
    private static Rate[] defaultRates;

/**
     * The private constructor to make this un-instantiatable.
     */
    private TestHelper() {
        // do nothing
    }

    /**
     * Backups the specified file.
     *
     * @param filename file to backup
     *
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
     * Clear the default rates.
     */
    public static void clearDefaultRates() {
        defaultRates = null;
    }

    /**
     * Clears all data in the given tables.
     *
     * @param tableNames in which data should be cleared
     * @param namespace namespace for the DBConnectionFactory
     *
     * @throws Exception if any error occurs
     */
    public static void clearTable(String[] tableNames, String namespace)
        throws Exception {
        //      create DBConnectionFactory from given namespace
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        Connection conn = factory.createConnection();
        PreparedStatement pstmt = null;

        try {
            //clears all the data
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
     * Gets default rates used in test.
     *
     * @return default rates
     */
    public static synchronized Rate[] getDefaultRates() {
        if (defaultRates == null) {
            defaultRates = new Rate[2];
            defaultRates[0] = new Rate();
            defaultRates[0].setId(1);

            Company company = new Company();
            company.setId(1);
            defaultRates[0].setCompany(company);
            defaultRates[0].setCreationDate(new Date());
            defaultRates[0].setCreationUser("user1");
            defaultRates[0].setDescription("rate1");
            defaultRates[0].setModificationDate(new Date());
            defaultRates[0].setModificationUser("user2");
            defaultRates[0].setRate(1.1);

            defaultRates[1] = new Rate();
            defaultRates[1].setId(2);
            company = new Company();
            company.setId(1);
            defaultRates[1].setCompany(company);
            defaultRates[1].setCreationDate(new Date());
            defaultRates[1].setCreationUser("user1");
            defaultRates[1].setDescription("rate1");
            defaultRates[1].setModificationDate(new Date());
            defaultRates[1].setModificationUser("user2");
            defaultRates[1].setRate(1.1);
        }

        Rate[] result = new Rate[2];
        System.arraycopy(defaultRates, 0, result, 0, 2);

        return result;
    }

    /**
     * Initialize database, clear the test tables and insert some default data.
     *
     * @throws Exception to junit
     */
    public static void initDB() throws Exception {
        Connection conn = getConnection("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        try {
            clearData(conn); //clears tables
            createDefaultRates(conn); //creates default data
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    /**
     * Initializes the JNDI configuration.
     *
     * @throws Exception if any error occurred
     */
    public static final void initJNDI() throws Exception {
        if (!initialized) {
            // initializes the mock ejb container and EntrySessionBean is returned as the bean object to calling
            // method.
            MockContextFactory.setAsInitial();

            Context context = new InitialContext();
            MockContainer mockContainer = new MockContainer(context);

            context.bind("java:comp/env/of_namespace", "TimeTrackerObjectFactory");
            context.bind("java:comp/env/of_rate_persistence_key", "informixPersistence");

            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor("rate.rateLocalHome", LocalHomeRate.class,
                    LocalRate.class, new RateEjb());
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
    }

    /**
     * Remove the specified property from given namespace and commit it.
     *
     * @param namespace namespace
     * @param property property to be removed
     *
     * @throws ConfigManagerException if any error occurs
     */
    public static void removeProperty(String namespace, String property)
        throws ConfigManagerException {
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
        new File(TestHelper.class.getClassLoader().getResource(backupFile).getFile()).delete();

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
    public static void setProperty(String namespace, String property, String value)
        throws ConfigManagerException {
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
        //clears all the data
        for (int i = 0; i < TABLES.length; i++) {
            PreparedStatement pstmt = conn.prepareStatement("delete from " + TABLES[i]);
            pstmt.executeUpdate();
        }
    }

    /**
     * <p>Close the given Connection.</p>
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
    private static void copyFile(String src, String dest)
        throws IOException {
        URL url = TestHelper.class.getClassLoader().getResource(src);
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
     * Creates default data into database.
     *
     * @param conn Connection
     *
     * @throws SQLException if any persistence error occurs
     */
    private static void createDefaultRates(Connection conn)
        throws SQLException {
        getDefaultRates();

        PreparedStatement pstmt = conn.prepareStatement(
                "insert into rate (rate_id, description, creation_date, creation_user, modification_date,"
                + "modification_user) values (?,?,?,?,?,?)");

        for (int i = 0; i < defaultRates.length; i++) {
            pstmt.setLong(1, defaultRates[i].getId());
            pstmt.setString(2, defaultRates[i].getDescription());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(4, "user");
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(6, "user");

            pstmt.executeUpdate();
        }
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
    private static Connection getConnection(String namespace)
        throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        Connection conn = factory.createConnection();
        conn.setAutoCommit(false);

        return conn;
    }

    /**
     * <p>Rolls back the current connection.</p>
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
