/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.ejb.CompanyBean;
import com.topcoder.timetracker.company.ejb.CompanyHomeLocal;
import com.topcoder.timetracker.company.ejb.CompanyLocal;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
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
     * Represents the config file.
     */
    public static final String CONFIG = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "accuracy" + File.separator + "config.xml";

    /**
     * <p>
     * Represents the sql file for clearing the db.
     * </p>
     */
    public static final String CLEAR_SQL = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "accuracy" + File.separator + "clear.sql";

    /**
     * <p>
     * Represents the sql file for preparing the db.
     * </p>
     */
    public static final String INSERT_SQL = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "accuracy" + File.separator + "insert_data.sql";

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

            Context context = new InitialContext();

            // Create an instance of the MockContainer and pass the JNDI context
            // that it will use to bind EJBs.
            MockContainer mockContainer = new MockContainer(context);

            context.bind("java:comp/env/SpecificationNamespace",
                    "com.topcoder.timetracker.company.ejb.objectfactory");
            context.bind("java:comp/env/CompanyDAOKey", "DbCompanyDAO");

            /*
             * Create the deployment descriptor of the bean. Stateless and Stateful beans both use
             * SessionBeanDescriptor.
             */
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor(
                    "java:comp/env/company/accuracytest", CompanyHomeLocal.class, CompanyLocal.class,
                    CompanyBean.class);

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
        executeScript(connection, INSERT_SQL);
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
     * Creates a company bean for testing.
     *
     * @return the company bean
     */
    public static Company createCompanyBean() {
        Company company = new Company();
        Address address = new Address();
        address.setId(1);
        address.setChanged(true);

        Contact contact = new Contact();
        contact.setId(1);
        contact.setChanged(true);
        company.setAddress(address);
        company.setContact(contact);
        company.setCompanyName("company");
        company.setPassCode("RSA");
        company.setChanged(true);

        return company;
    }
}