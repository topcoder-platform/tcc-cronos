/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.UnitTestHelper;
import com.topcoder.timetracker.audit.ejb.AuditLocalHome;
import com.topcoder.timetracker.audit.ejb.AuditLocalObject;
import com.topcoder.timetracker.audit.ejb.AuditSessionBean;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Test Helper class for unit testing.
 * </p>
 *
 * @author mittu
 * @version 3.2
 */
class AccuracyTestHelper {
    /**
     * Represents the test_files folder.
     */
    public static final String CONFIG = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "accuracy" + File.separator + "config.xml";

    /**
     * Represents whether the mock ejb to be deployed.
     */
    private static boolean deployed = false;

    /**
     * <p>
     * Private constructor. No instances allowed.
     * </p>
     */
    private AccuracyTestHelper() {
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
     * @param context
     *            the initial context to set.
     * @throws Exception
     *             exception to junit.
     */
    public static void deployEJB(InitialContext context) throws Exception {
        if (!deployed) {
            /*
             * Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one
             * EJB are deployed by the app server, so we don't need to do it.
             */
            MockContextFactory.setAsInitial();

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            MockContainer mockContainer = new MockContainer(context);

            /*
             * Create the deployment descriptor of the bean. Stateless and Stateful beans both use
             * SessionBeanDescriptor.
             */
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor(
                    "java:comp/env/audit/accuracytest", AuditLocalHome.class, AuditLocalObject.class,
                    AuditSessionBean.class);

            mockContainer.deploy(beanDescriptor);
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
     * Creates a sample audit header for testing.
     * </p>
     *
     * @return the header record.
     */
    public static AuditHeader createRecord() {
        // Create the AuditHeader bean with required values
        AuditHeader auditHeader = new AuditHeader();
        auditHeader.setTableName("comp_rate");
        auditHeader.setEntityId(1);
        auditHeader.setCreationDate(new Timestamp(System.currentTimeMillis()));
        auditHeader.setCompanyId(5);
        auditHeader.setCreationUser("accuracy_test_user");
        auditHeader.setActionType(2);
        auditHeader.setClientId(3);
        auditHeader.setProjectId(1);
        auditHeader.setResourceId(6);
        auditHeader.setApplicationArea(ApplicationArea.TT_CONFIGURATION);

        List details = new ArrayList();

        // audit the rate column
        details.add(createAuditDetail("rate", "12.2", "10.0"));

        // audit the creation_date
        details.add(createAuditDetail("creation_date", new Timestamp(System.currentTimeMillis()).toString(),
                new Timestamp(System.currentTimeMillis() - 10000).toString()));

        // audit the creation_user
        details.add(createAuditDetail("creation_user", "accuracy_test_user", "unit_test_user"));

        // audit the modification_date
        details.add(createAuditDetail("modification_date", new Timestamp(System.currentTimeMillis())
                .toString(), new Timestamp(System.currentTimeMillis() - 10000).toString()));

        // audit the modificaton_user
        details.add(createAuditDetail("modificaton_user", "accuracy_test_user", "unit_test_user"));

        auditHeader.setDetails((AuditDetail[]) details.toArray(new AuditDetail[details.size()]));
        return auditHeader;
    }

    /**
     * <p>
     * Creates the audit detail from the given parameters.
     * </p>
     *
     * @param colName
     *            the column name to set
     * @param newValue
     *            the new column value
     * @param oldValue
     *            the old column value
     * @return the
     */
    private static AuditDetail createAuditDetail(String colName, String newValue, String oldValue) {
        AuditDetail detail = new AuditDetail();
        detail.setColumnName(colName);
        detail.setNewValue(newValue);
        detail.setOldValue(oldValue);
        return detail;
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
        executeScript(connection, "accuracy/insert_data.sql");
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
            InputStream input = UnitTestHelper.class.getClassLoader().getResourceAsStream(fileName);
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
        executeScript(connection, "accuracy/clear.sql");
    }

}