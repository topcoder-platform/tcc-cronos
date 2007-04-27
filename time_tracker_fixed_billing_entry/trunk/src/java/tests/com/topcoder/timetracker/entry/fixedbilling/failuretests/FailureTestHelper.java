/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;


import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerLocal;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerLocalHome;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntrySessionBean;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerLocal;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerLocalHome;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusSessionBean;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.MockUserTransaction;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Test Helper class for unit testing.
 * </p>
 *
 * @author mittu
 * @version 3.2
 */
class FailureTestHelper {
    /**
     * Represents the config file.
     */
    public static final String CONFIG = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "failure" + File.separator + "config.xml";

    /**
     * Represents the clear sql.
     */
    public static final String CLEAR_SQL = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "failure" + File.separator + "clear.sql";

    /**
     * Represents the insert sql.
     */
    public static final String INSERT = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "failure" + File.separator + "insert.sql";

    /**
     * Represents whether the mock ejb to be deployed.
     */
    private static boolean deployed = false;

    /**
     * <p>
     * Private constructor. No instances allowed.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Loads the configuration from the given configuration file.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void loadConfigs() throws Exception {
        releaseConfigs();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG);
    }

    /**
     * <p>
     * Releases the configurations.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void releaseConfigs() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iterator = cm.getAllNamespaces(); iterator.hasNext();) {
            cm.removeNamespace((String) iterator.next());
        }
    }

    /**
     * <p>
     * Deploys the mock ejb for testing purpose.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void deployEJB() throws Exception {
        if (!deployed) {
            /*
             * Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one
             * EJB are deployed by the app server, so we don't need to do it.
             */
            MockContextFactory.setAsInitial();

            Context context;
            Map env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, MockContextFactory.class.getName());
            context = new InitialContext((Hashtable) env);

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            MockContainer mockContainer = new MockContainer(context);

            /*
             * Create the deployment descriptor of the bean. Stateless and Stateful beans both use
             * SessionBeanDescriptor.
             */
            SessionBeanDescriptor beanDescriptor1 = new SessionBeanDescriptor("java:comp/env/fb/failuretest",
                    FixedBillingEntryManagerLocalHome.class, FixedBillingEntryManagerLocal.class,
                    FixedBillingEntrySessionBean.class);

            SessionBeanDescriptor beanDescriptor2 = new SessionBeanDescriptor("java:comp/env/fb/failuretest1",
                    FixedBillingStatusManagerLocalHome.class, FixedBillingStatusManagerLocal.class,
                    FixedBillingStatusSessionBean.class);

            mockContainer.deploy(beanDescriptor1);
            mockContainer.deploy(beanDescriptor2);
            MockUserTransaction mockTransaction = new MockUserTransaction();
            context.rebind("javax.transaction.UserTransaction", mockTransaction);

            deployed = true;
        }
    }

    /**
     * <p>
     * Undeploys the mock ejb.
     * </p>
     */
    public static void undeployEJB() {
        if (deployed) {
            MockContextFactory.revertSetAsInitial();
            deployed = false;
        }
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @return the connection created from DBConnectionFactory.
     *
     * @throws Exception
     *             any exception during the create connection process.
     */
    public static Connection getConnection() throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        return factory.createConnection();
    }

    /**
     * <p>
     * Prepare data for test.
     * </p>
     *
     * @param connection
     *            database connection.
     *
     * @throws SQLException
     *             error occurs when access the database.
     */
    public static void prepareData(Connection connection) throws SQLException {
        clearDatabase(connection);
        executeScript(connection, INSERT);
    }

    /**
     * <p>
     * Executes the sql script from the given file.
     * </p>
     *
     * @param connection
     *            database connection.
     * @param fileName
     *            the file name.
     *
     * @throws SQLException
     *             error occurs when access the database.
     */
    private static void executeScript(Connection connection, String fileName) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            InputStream input = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if ((line.length() != 0) && !line.startsWith("--")) {
                    statement.executeUpdate(line);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection
     *            the database connection used to access database.
     *
     * @throws SQLException
     *             error occurs when access the database.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
        executeScript(connection, CLEAR_SQL);
    }

    /**
     * <p>
     * Creates the fixed billing bean.
     * </p>
     *
     * @return the FixedBillingEntry
     */
    public static FixedBillingEntry createFixedBillingEntry() {
        FixedBillingEntry entry = new FixedBillingEntry();
        entry.setAmount(119.11);
        entry.setCompanyId(101);
        entry.setCreationDate(new Date());
        entry.setCreationUser("failure_tester");
        entry.setDate(new Date());
        entry.setDescription("failure_testing");
        FixedBillingStatus status = createFixedBillingStatus();
        entry.setFixedBillingStatus(status);
        entry.setInvoiceId(31);
        entry.setModificationDate(new Date());
        entry.setModificationUser("failure_tester");
        return entry;
    }

    /**
     * <p>
     * Creates the fixed billing status bean.
     * </p>
     *
     * @return the FixedBillingStatus
     */
    public static FixedBillingStatus createFixedBillingStatus() {
        FixedBillingStatus status = new FixedBillingStatus();
        status.setCreationDate(new Date());
        status.setCreationUser("failure_tester");
        status.setModificationDate(new Date());
        status.setModificationUser("failure_tester");
        status.setDescription("failure_testing");
        return status;
    }
}