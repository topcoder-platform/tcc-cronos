/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.failuretests;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.io.File;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.Iterator;


/**
 * Helper class used for failure testing.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FailureTestHelper {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * private constructor.
     */
    private FailureTestHelper() {
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
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName config file to set up environment
     *
     * @throws Exception when any exception occurs
     */
    public static void loadConfig(String fileName) throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * Remove all the namespace in this component.
     */
    public static void removeNamespaces() {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            String ns = (String) iter.next();

            if (cm.existsNamespace(ns)) {
                try {
                    cm.removeNamespace(ns);
                } catch (UnknownNamespaceException e) {
                    System.err.println("error occurs in removing namespace : " + ns);
                }
            }
        }
    }

    /**
     * Create the client used for test with the specified client id.
     *
     * @param id the id
     *
     * @return the client
     */
    public static Client createCient(long id) {
        Client client = new Client();

        client.setActive(true);
        client.setAddress(new Address());
        client.setChanged(true);
        client.setCompanyId(id);
        client.setContact(new Contact());
        client.setCreationDate(new Date());
        client.setCreationUser("user");
        client.setEndDate(new Date());
        client.setId(id);
        client.setModificationDate(new Date());
        client.setModificationUser("modificationUser");
        client.setName("tc" + id);

        PaymentTerm term = new PaymentTerm();
        term.setId(id);
        client.setPaymentTerm(term);
        client.setProjects(new Project[0]);
        client.setSalesTax(0.1);
        client.setStartDate(new Date());

        Contact contact = new Contact();
        contact.setId(id);
        client.setContact(contact);

        Address address = new Address();
        address.setId(id);
        client.setId(id);
        client.setAddress(address);

        Project project1 = new Project();
        project1.setId(id);

        Project project2 = new Project();
        project2.setId(id * 2);

        client.setProjects(new Project[] {project1, project2});

        return client;
    }

    /**
     * Clear the data of the database.
     *
     * @throws Exception any exception to JUnit
     */
    public static void setUpDatabase() throws Exception {
        Statement stmt = null;
        Connection conn = null;

        conn = getConnection();

        stmt = conn.createStatement();

        tearDownDataBase();
        stmt.executeUpdate("insert into company values (2, 'company2', 'passcode2', CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into company values (1, 'company1', 'passcode1', CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate(
            "insert into project values (1, 'project1', 1, 'passcode1', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate(
            "insert into project values (2, 'project2', 1, 'passcode2', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate(
            "insert into project values (3, 'project3', 1, 'passcode3', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate(
            "insert into project values (4, 'project4', 1, 'passcode4', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");

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
    private static void clearTables(Connection connection)
        throws SQLException {
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
