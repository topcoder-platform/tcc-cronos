/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.TransactionManager;
import org.mockejb.TransactionPolicy;
import org.mockejb.interceptor.AspectSystemFactory;
import org.mockejb.interceptor.ClassPointcut;
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
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>This is the base test case which abstracts the common behavior.</p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class BaseTestCase extends TestCase {
    /**
     * <p>
     * The formatter to format the <code>Date</code> to pattern "yyyy_MM_dd hh:mm:ss".
     * </p>
     *
     * <p>
     * Validate from year to second because the Informix type of creation date and modification date
     * is DATETIME YEAR TO SECOND.
     * </p>
     */
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy_MM_dd hh:mm:ss");

    /**
     * <p>
     * The default namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DBCONNECTION_FACTORY_NAMESPACE
        = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    public static final String ERROR_MESSAGE = "error message";

    /**
     * <p>
     * An exception instance used as the cause exception.
     * </p>
     */
    public static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Represents the connection factory that is used for performing CRUD operations.
     * </p>
     */
    private static DBConnectionFactory connectionFactory = null;

    /**
     * <p>
     * Whether <code>setUp()</code> has been called.
     * </p>
     */
    private static boolean setup = false;

    /**
     * <p>Naming context used to lookup data source and EJB in UnitTests.</p>
     */
    private static Context context;

    /**
     * <p>Mock EJB container.</p>
     */
    private static MockContainer mockContainer;

    /**
     * <p>
     * Sql to run to insert required information into data store.
     * </p>
     */
    private static String insertSql = null;

    /**
     * <p>Get JDBC connection.</p>
     *
     * @return Connection
     *
     * @throws Exception If error occurred while retrieving connection.
     */
    protected Connection getJDBCConnection() throws Exception {
        try {
            if (connectionFactory == null) {
                connectionFactory = new DBConnectionFactoryImpl(DBCONNECTION_FACTORY_NAMESPACE);
            }

            Connection con = connectionFactory.createConnection("Informix_Connection_Producer");
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return con;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw e;
        }
    }
    /**
     * <p>
     * Bind given value to given JNDI name.
     * </p>
     *
     * @param name JNDI name
     * @param value value binded
     *
     * @throws Exception to JUnit
     */
    protected void bind(String name, Object value) throws Exception {
        context.rebind(name, value);
    }

    /**
     * <p>deploy SLSB to the mock EJB container and add transaction interceptor.</p>
     *
     * @param jndi the JNDI name to deploy
     * @param home the class of home interface
     * @param ejbObject the class of EJB object
     * @param bean the class of bean implementation
     *
     * @throws Exception to JUnit
     */
    protected void deploySessionBean(String jndi, Class home, Class ejbObject, Class bean)
        throws Exception {
        SessionBeanDescriptor descriptor = new SessionBeanDescriptor(jndi, home, ejbObject, bean);
        mockContainer.deploy(descriptor);
        AspectSystemFactory.getAspectSystem().addFirst(new ClassPointcut(bean, false),
            new TransactionManager(TransactionPolicy.REQUIRED));
    }
    /**
     * <p>
     * Assert the transaction was committed.
     * </p>
     *
     * @param transaction to assert
     */
    protected void assertTransactionCommited(MockTransaction transaction) {
        assertTrue("Transaction should be committed", transaction.wasCommitCalled());
    }
    /**
     * <p>
     * Initial the <code>ConfigManager</code> with the configuration files in the test_files.
     * </p>
     *
     * @return initialized ConfigManager instance
     *
     * @throws ConfigManagerException to JUnit
     */
    protected ConfigManager initialConfigManager()
        throws ConfigManagerException {
        removeConfigManagerNS();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracytests" + File.separator + "config.xml");
        return cm;
    }

    /**
     * <p>Remove all namespaces from config manager.</p>
     *
     * @throws ConfigManagerException to JUnit
     */
    protected void removeConfigManagerNS()
        throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Bind mock transaction to context.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void bindTransaction() throws Exception {
        MockTransaction mockTransaction = new MockTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * <p>
     * Get transaction currently binded.
     * </p>
     *
     * @return An instance of <code>MockTransaction</code>.
     *
     * @throws NamingException to JUnit
     */
    protected MockTransaction getTransaction() throws NamingException {
        return (MockTransaction) context.lookup("javax.transaction.UserTransaction");
    }

    /**
     * <p>
     * Get an array of <code>FixedBillingEntry</code>.
     * </p>
     * @return an array of <code>FixedBillingEntry</code>.
     */
    protected FixedBillingEntry[] getFixedBillingEntries() {
        FixedBillingEntry[] entries = new FixedBillingEntry[3];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc" + i);
            status.setCreationUser("user" + i);
            status.setModificationUser("modifyuser" + i);
            entries[i].setFixedBillingStatus(status);
            entries[i].setDescription("desc" + i);
            entries[i].setDate(new Date());
            entries[i].setCompanyId(100);
            entries[i].setCreationUser("user" + i);
            entries[i].setModificationUser("m-user" + i);
        }
        return entries;
    }
    /**
     * <p>
     * Get an array of <code>FixedBillingStatus</code>.
     * </p>
     * @return an array of <code>FixedBillingStatus</code>.
     */
    protected FixedBillingStatus[] getFixedBillingStatus() {
        FixedBillingStatus[] statuses = new FixedBillingStatus[3];

        for (int i = 0; i < statuses.length; i++) {
            statuses[i] = new FixedBillingStatus();
            statuses[i].setDescription("desc" + i);
            statuses[i].setCreationUser("user" + i);
            statuses[i].setModificationUser("modifyuser" + i);
        }
        return statuses;
    }

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        if (!setup) {
            setup = true;
            super.setUp();
            this.initialConfigManager();
            MockContextFactory.setAsInitial();
            context = new InitialContext();
            mockContainer = new MockContainer(context);
            this.bindTransaction();
            deploySessionBean(
                "java:comp/env/ejb/FBEntryAccuracyLocal",
                FixedBillingEntryManagerLocalHome.class,
                FixedBillingEntryManagerLocal.class,
                FixedBillingEntrySessionBean.class);
            deploySessionBean(
                "java:comp/env/ejb/FBStatusAccuracyLocal",
                FixedBillingStatusManagerLocalHome.class,
                FixedBillingStatusManagerLocal.class,
                FixedBillingStatusSessionBean.class);
            this.wrapDB();
        } else {
            super.setUp();
        }
    }

    /**
     * <p>Remove db information.</p>
     *
     * @throws Exception to JUnit
     */
    protected void wrapDB() throws Exception {
        Connection conn = getJDBCConnection();

        Statement ps = conn.createStatement();

        try {

            //wrap date
            ps.addBatch("delete from fb_reject_reason");
            //Possibly referenced by other tables, check your database. This component do not consider
            //the other tables reference reject_reason
            //ps.addBatch("delete from reject_reason");
            ps.addBatch("delete from fix_bill_entry");
            ps.addBatch("delete from fix_bill_status");

            //Possibly referenced by other tables, check your database. This component do not consider
            //the other tables reference invoice and company
            //ps.addBatch("delete from invoice");
            //ps.addBatch("delete from company");

            //wrap id sequences used in test case
            ps.addBatch("delete from id_sequences where name='FBEntryAccuracyIDGenerator' or " +
            		"name='FBStatusAccuracyIDGenerator'");
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }
    /**
     * <p>
     * Closes the given Statement instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param statement the given Statement instance to close.
     */
    protected void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>
     * Closes the given Connection instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    protected void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>
     * Roll back the current connection if any error occurs while updating the persistence.
     * </p>
     *
     * <p>
     * Note, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to roll back
     */
    protected void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            // Just ignore
        }
    }
    /**
     * <p>Set up required db information to run Unit tests.</p>
     *
     * @throws Exception to JUnit
     */
    protected void setupDB() throws Exception {
        Statement ps = null;
        Connection conn = null;

        try {
            //wrapDB();

            if (insertSql == null) {
                insertSql = "";

                InputStream is =
                    this.getClass().getResourceAsStream("/accuracytests/data.sql");
                BufferedReader in = new BufferedReader(new InputStreamReader(is));

                String s = in.readLine();

                while (s != null) {
                    insertSql += s;
                    s = in.readLine();
                }

                in.close();
            }

            conn = getJDBCConnection();

            StringTokenizer st = new StringTokenizer(insertSql, ";");

            ps = conn.createStatement();

            while (st.hasMoreTokens()) {
                ps.addBatch(st.nextToken());
            }

            ps.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }
}
