/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.persistence;

import com.cronos.timetracker.project.ConfigurationException;
import com.cronos.timetracker.project.TestHelper;
import com.cronos.timetracker.project.searchfilters.BinaryOperation;
import com.cronos.timetracker.project.searchfilters.BinaryOperationFilter;
import com.cronos.timetracker.project.searchfilters.CompareOperation;
import com.cronos.timetracker.project.searchfilters.Filter;
import com.cronos.timetracker.project.searchfilters.MultiValueFilter;
import com.cronos.timetracker.project.searchfilters.NotFilter;
import com.cronos.timetracker.project.searchfilters.ValueFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;


/**
 * Unit tests for DatabaseSearchUtility implementation.
 *
 * @author TCSDEVELOPER, costty000
 * @version 2.0
 */
public class DatabaseSearchUtilityTest extends TestCase {
    /**
     * The SQL statement to insert a client.
     * from 2.0: added new parameters for company id
     */
    private static final String SQL_INS_CLIENT = "INSERT INTO client VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * The DatabaseSearchUtility instance to test against.
     */
    private DatabaseSearchUtility utility = null;

    /**
     * The search filter to apply.
     */
    private Filter filter = null;

    /**
     * The database connection to be used to generate the prepared statements.
     */
    private Connection connection = null;

    /**
     * The prepared statement generated by the utility.
     */
    private PreparedStatement pstmt = null;

    /**
     * The resultset obtained by executing the prepared statement.
     */
    private ResultSet rs = null;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DatabaseSearchUtilityTest.class);

        return suite;
    }

    /**
     * Prepares a DatabaseSearchUtility instance for testing. Also adds a client to the database so that it can be
     * selected by the generated prepared statement. The client has the name "name" and creation user "creationUser".
     *
     * @throws Exception if any unexpected exception occurs.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
        DBHelper.clearTables();

        // prepare the connection and search utility
        connection = DBHelper.getConnection();
        utility = new DatabaseSearchUtility(connection, TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);

        // add a client to the database
        Date date = new Date();
        PreparedStatement pstmt = connection.prepareStatement(SQL_INS_CLIENT);

        // from 2.0: added new parameters for company id
        pstmt.setInt(1, 1);
        pstmt.setInt(2, 1);
        pstmt.setString(3, "name");
        pstmt.setTimestamp(4, DBUtil.toSQLDate(date));
        pstmt.setString(5, "creationUser");
        pstmt.setTimestamp(6, DBUtil.toSQLDate(date));
        pstmt.setString(7, "modificationUser");

        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Clears all the namespaces and database resources.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    protected void tearDown() throws Exception {
        DBHelper.clearTables();
        TestHelper.unloadConfig();
        DBUtil.close(rs);
        DBUtil.close(pstmt);
        DBUtil.close(connection);
    }

    /**
     * Test of constructor with null connection. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NullConnection() throws Exception {
        try {
            new DatabaseSearchUtility(null, TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);
            fail("Creates DatabaseSearchUtility with null connection");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null namespace. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NullNamespace() throws Exception {
        try {
            new DatabaseSearchUtility(connection, null);
            fail("Creates DatabaseSearchUtility with null namespace");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with empty namespace. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_EmptyNamespace() throws Exception {
        try {
            new DatabaseSearchUtility(connection, "");
            fail("Creates DatabaseSearchUtility with empty namespace");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with non-existing namespace. Expects ConfigurationException.
     */
    public void testConstructor_NonExistNamespace() {
        try {
            new DatabaseSearchUtility(connection, "non-exist");
            fail("Creates DatabaseSearchUtility with non-existing namespace");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test of constructor with bad namespaces (missing properties and values). Expects ConfigurationException.
     */
    public void testConstructor_BadNamespaces() {
        String[] namespaces = TestHelper.BAD_SEARCH_UTILITY_NAMESPACES;

        for (int i = 0; i < namespaces.length; i++) {
            try {
                new DatabaseSearchUtility(connection, namespaces[i]);
                fail("Creates DatabaseSearchUtility with bad namespace");
            } catch (ConfigurationException e) {
                // good
            }
        }
    }

    /**
     * Test of prepareSearchStatement method with null filter. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testPrepareSearchStatement_NullFilter()
        throws Exception {
        try {
            utility.prepareSearchStatement(null);
            fail("Prepares statement with null filter");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of prepareSearchStatement method with invalid filter (non-existing column name). Expects
     * IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testPrepareSearchStatement_InvalidFilter()
        throws Exception {
        try {
            utility.prepareSearchStatement(new ValueFilter(CompareOperation.EQUAL, "non-exist", "value"));
            fail("Prepares statement with invalid filter");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of prepareSearchStatement method with value filter. Verifies if the prepared statement selects the matching
     * client by executing it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testPrepareSearchStatement_ValueFilter()
        throws Exception {
        filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "creation%");
        pstmt = utility.prepareSearchStatement(filter);
        rs = pstmt.executeQuery();
        assertTrue("Prepares an incorrect statement", rs.next());
    }

    /**
     * Test of prepareSearchStatement method with multi-value filter. Verifies if the prepared statement selects the
     * matching client by executing it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testPrepareSearchStatement_MultiValueFilter()
        throws Exception {
        filter = new MultiValueFilter("Creation User", new Object[] {"creationUser", "modificationUser"});
        pstmt = utility.prepareSearchStatement(filter);
        rs = pstmt.executeQuery();
        assertTrue("Prepares an incorrect statement", rs.next());
    }

    /**
     * Test of prepareSearchStatement method with binary operation filter. Verifies if the prepared statement selects
     * the matching client by executing it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testPrepareSearchStatement_BinaryOperationFilter()
        throws Exception {
        Filter leftOperand = new ValueFilter(CompareOperation.EQUAL, "Name", "name");
        Filter rightOperand = new ValueFilter(CompareOperation.EQUAL, "Creation User", "creationUser");

        filter = new BinaryOperationFilter(BinaryOperation.AND, leftOperand, rightOperand);
        pstmt = utility.prepareSearchStatement(filter);
        rs = pstmt.executeQuery();
        assertTrue("Prepares an incorrect statement", rs.next());
    }

    /**
     * Test of prepareSearchStatement method with not filter. Verifies if the prepared statement selects the matching
     * client by executing it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testPrepareSearchStatement_NotFilter()
        throws Exception {
        Filter operand = new ValueFilter(CompareOperation.EQUAL, "Name", "abc");

        filter = new NotFilter(operand);
        pstmt = utility.prepareSearchStatement(filter);
        rs = pstmt.executeQuery();
        assertTrue("Prepares an incorrect statement", rs.next());
    }
}
