/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>DBUtil</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class DBUtilTest extends TestCase {

    /** Database connection factory used in the unit test. */
    private DBConnectionFactoryImpl dbFactoryImpl;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(DBUtilTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfiguration("DBConnectionFactory.xml");

        dbFactoryImpl = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        TestHelper.clearNamespaces();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearNamespaces();
        super.tearDown();
    }

    /**
     * Test <code>closeConnection</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseConnection() throws Exception {
        Connection connection = dbFactoryImpl.createConnection();
        connection.close();
        DBUtil.closeConnection(connection);
    }

    /**
     * Test <code>closeStatement</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseStatement() throws Exception {
        Connection connection = dbFactoryImpl.createConnection();
        Statement statement = connection.createStatement();
        statement.close();
        DBUtil.closeStatement(statement);
        DBUtil.closeConnection(connection);
    }

    /**
     * Test <code>executeQuery</code> for failure. Condition: sql query is not valid. Expect:
     * <code>InvoiceDataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteQuery() throws Exception {
        Connection connection = dbFactoryImpl.createConnection();
        Statement statement = connection.createStatement();

        try {
            DBUtil.executeQuery(statement, "update");
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>execute</code> for failure. Condition: missing some parameters. Expect:
     * <code>InvoiceDataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute() throws Exception {
        Connection connection = dbFactoryImpl.createConnection();
        PreparedStatement ps = connection.prepareStatement("select * from invoice where invoice_id=?");

        try {
            DBUtil.execute(ps, new Object[0], 0);
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>closeResultSet</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseResultSet() throws Exception {
        Connection connection = dbFactoryImpl.createConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from invoice");
        rs.close();
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(statement);
        DBUtil.closeConnection(connection);

    }

}
