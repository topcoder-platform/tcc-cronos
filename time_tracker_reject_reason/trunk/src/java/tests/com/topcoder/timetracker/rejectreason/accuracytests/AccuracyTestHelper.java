/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOLocal;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOLocalHome;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailSessionBean;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOLocal;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOLocalHome;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonSessionBean;

import com.topcoder.util.config.ConfigManager;

import java.io.File;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;


/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class AccuracyTestHelper {
    /** Represents the id generator name. */
    public static final String ID_GENERATOR_NAME = "TimeTrackerID";
    /** Represent the default date format. */
    public static final DateFormat DATEFORMAT = new SimpleDateFormat("yyMMddHHmmssZ");

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
    public static final String CONFIG_FILE_BASE = "test_files" + File.separator + "accuracytests" + File.separator;

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
        File file = new File(CONFIG_FILE_BASE + fileName);

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
    private static void clearTables(Connection connection)
        throws SQLException {
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM comp_rej_reason");
            stmt.executeUpdate("DELETE FROM comp_rej_email");
            stmt.executeUpdate("DELETE FROM reject_email");
            stmt.executeUpdate("DELETE FROM reject_reason");
        } finally {
            closeStatement(stmt);
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

    /**
     * <p>
     * Create Reject email with specified id.
     * </p>
     *
     * @param beanId the email id
     * @param companyId the company id
     *
     * @return the generated reject email
     */
    static RejectEmail createRejectEmail(long beanId, long companyId) {
        RejectEmail email = new RejectEmail();

        if (beanId > 0) {
            email.setId(beanId);
        }

        email.setCompanyId(companyId);
        email.setBody("Email body.");

        return email;
    }

    /**
     * <p>
     * Create Reject email with specified id.
     * </p>
     *
     * @param beanId the email id
     * @param companyId the company id
     *
     * @return the generated reject email
     */
    static RejectReason createRejectReason(long beanId, long companyId) {
        RejectReason reason = new RejectReason();

        if (beanId > 0) {
            reason.setId(beanId);
        }

        reason.setCompanyId(companyId);
        reason.setDescription("Email body.");
        reason.setActive(true);

        return reason;
    }

    /**
     * Compare two reject email.
     *
     * @param first the first one.
     * @param second the second one.
     *
     * @return if two email equal to each other
     */
    static boolean isRejectEmailEqual(RejectEmail first, RejectEmail second) {
        if (first.getId() != second.getId()) {
            return false;
        }

        if (first.getCompanyId() != second.getCompanyId()) {
            return false;
        }

        if (!first.getBody().equals(second.getBody())) {
            return false;
        }

        if (!AccuracyTestHelper.DATEFORMAT.format(first.getModificationDate())
                                          .equals(AccuracyTestHelper.DATEFORMAT.format(second.getModificationDate()))) {
            return false;
        }

        if (!first.getModificationUser().equals(second.getModificationUser())) {
            return false;
        }

        if (!AccuracyTestHelper.DATEFORMAT.format(first.getCreationDate())
                                              .equals(AccuracyTestHelper.DATEFORMAT.format(second.getCreationDate()))) {
            return false;
        }

        if (!first.getCreationUser().equals(second.getCreationUser())) {
            return false;
        }

        return true;
    }

    /**
     * Compare two reject reason.
     *
     * @param first the first one.
     * @param second the second one.
     *
     * @return if two email equal to each other
     */
    static boolean isRejectReasonEqual(RejectReason first, RejectReason second) {
        if (first.getId() != second.getId()) {
            return false;
        }

        if (first.getCompanyId() != second.getCompanyId()) {
            return false;
        }

        if (!first.getDescription().equals(second.getDescription())) {
            return false;
        }

        if (!AccuracyTestHelper.DATEFORMAT.format(first.getModificationDate())
                                          .equals(AccuracyTestHelper.DATEFORMAT.format(second.getModificationDate()))) {
            return false;
        }

        if (!first.getModificationUser().equals(second.getModificationUser())) {
            return false;
        }

        if (!AccuracyTestHelper.DATEFORMAT.format(first.getCreationDate())
                                              .equals(AccuracyTestHelper.DATEFORMAT.format(second.getCreationDate()))) {
            return false;
        }

        if (!first.getCreationUser().equals(second.getCreationUser())) {
            return false;
        }

        if (first.getActive() != second.getActive()) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * Set up the ejb environment for test.
     * </p>
     *
     * @throws Exception to Junit
     */
    public static void setUpEJB() throws Exception {

        // We need to set MockContextFactory as our JNDI provider
        // This method sets the necessary system properties
        MockContextFactory.setAsInitial();

        // Create an instance of the MockContainer
        Context context = new InitialContext();

        // Create deployment descriptor of our mock beans
        // MockEjb uses it instead of XML deployment descriptors
        // Finally deploy operation creates Homes and binds it to JNDI
        MockContainer container = new MockContainer(context);

        container.deploy(new SessionBeanDescriptor(RejectEmailDAOLocalHome.EJB_REF_HOME, RejectEmailDAOLocalHome.class,
                RejectEmailDAOLocal.class, new RejectEmailSessionBean()));

        container.deploy(new SessionBeanDescriptor(RejectReasonDAOLocalHome.EJB_REF_HOME,
                RejectReasonDAOLocalHome.class, RejectReasonDAOLocal.class, new RejectReasonSessionBean()));

        // Bind DataSource
        context.rebind("java:jdbc/TXDATASOURCE", new RejectReasonDataSource());

        // Bind necessary environment variables
        context.rebind("of_namespace", "AuditManager.factory");
        context.rebind("audit_manager_key", "AuditManager");
        context.rebind("id_generator_name", ID_GENERATOR_NAME);
        context.rebind("java:comp/env", context);
        context.rebind("java:comp/env/" + ID_GENERATOR_NAME, "");
        context.rebind("java:comp/env/connection_name", "informix_connect");
        context.rebind("java:comp/env/of_namespace",
                "com.topcoder.timetracker.rejectreason");
        context.rebind("java:comp/env/audit_manager_key", "audit_manager");
        context.rebind("java:comp/env/db_connection_factory_key", "db_connection_factory");
        // we use MockTransaction outside of the app server.
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }
}
