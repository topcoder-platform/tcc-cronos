/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.persistence.TestHelper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * <p>Tests Helper class.</p>
 *
 * @author urtks, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class HelperTest extends TestCase {

    /**
     * <p>Connection factory used for testing.</p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>Represents parameter used for testing <code>assertObjectNotNull</code> method.</p>
     */
    private static final String PARAM = "param";

    /**
     * <p>Represents parameter name used for testing <code>assertObjectNotNull</code> method.</p>
     */
    private static final String PARAM_NAME = "paramName";

    /**
     * <p>Creates test suite for this TestCase.</p>
     *
     * @return test suite for this test case
     */
    public static Test suite() {

        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadConfig();

        connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * <p>Tests <code>assertObjectNotNull</code> method.
     *
     * <p>No exception is expected.</p>
     */
    public void testAssertObjectNotNull() {

        Helper.assertObjectNotNull(PARAM, PARAM_NAME);
    }

    /**
     * <p>Tests <code>assertObjectNotNull</code> method when param is null.
     *
     * <p>IllegalArgumentException is expected.</p>
     */
    public void testAssertObjectNotNullNullParam() {

        try {
            Helper.assertObjectNotNull(null, PARAM_NAME);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // test passed
        }
    }

    /**
     * <p>Tests STRING_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testStringDataTypeGetValue() throws Exception {

        testGetDataType(Helper.STRING_TYPE, "select 'a' from systables WHERE tabid = 1", "a");
    }

    /**
     * <p>Tests STRING_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testStringDataTypeSetValue() throws Exception {

        testSetDataType(Helper.STRING_TYPE, "insert into Submission(submission_id) values (?)", "a");
    }

    /**
     * <p>Tests LONG_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testLongDataTypeGetValue() throws Exception {

        testGetDataType(Helper.LONG_TYPE, "select 1 from systables WHERE tabid = 1", new Long(1));
    }

    /**
     * <p>Tests LONG_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testLongDataTypeSetValue() throws Exception {

        testSetDataType(Helper.LONG_TYPE, "insert into Submission(submission_id) values (?)", new Long(1L));
    }

    /**
     * <p>Tests DOUBLE_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testDoubleDataTypeGetValue() throws Exception {

        testGetDataType(Helper.DOUBLE_TYPE, "select 1.0 from systables WHERE tabid = 1", new Double(1.0));
    }

    /**
     * <p>Tests DOUBLE_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testDoubleDataTypeSetValue() throws Exception {

        testSetDataType(Helper.DOUBLE_TYPE, "insert into Submission(submission_id) values (?)", new Double(1));
    }

    /**
     * <p>Tests BOOLEAN_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testBooleanDataTypeGetValue() throws Exception {

        testGetDataType(Helper.BOOLEAN_TYPE, "select 1 from systables WHERE tabid = 1", Boolean.TRUE);
    }

    /**
     * <p>Tests BOOLEAN_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testBooleanDataTypeSetValue() throws Exception {

        testSetDataType(Helper.BOOLEAN_TYPE, "insert into Submission(submission_id) values (?)", Boolean.TRUE);
    }

    /**
     * <p>Tests BOOLEAN_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testBooleanDataTypeSetValueNull() throws Exception {

        testSetDataType(Helper.BOOLEAN_TYPE, "insert into Submission(submission_id) values (?)", null);
    }

    /**
     * <p>Tests DATE_TYPE data type.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testDateDataTypeGetValue() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        testGetDataType(Helper.DATE_TYPE, "select date('01/07/01') from systables WHERE tabid = 1",
                simpleDateFormat.parse("01-07-2001"));
    }

    /**
     * <p>Helper method for testing DataType setValue method.</p>
     *
     * @param dataType data type used for testing
     * @param query    used for testing
     * @param val      value to set
     *
     * @throws Exception if any error occurs
     */
    private void testSetDataType(Helper.DataType dataType, String query, Object val) throws Exception {

        PreparedStatement preparedStatement = createPreparedStatement(query);

        dataType.setValue(preparedStatement, 1, val);

        assertEquals("Invalid parameter count.", 1, preparedStatement.getParameterMetaData().getParameterCount());
    }

    /**
     * <p>Helper method for testing DataType getValue method.</p>
     *
     * @param dataType      data type for testing
     * @param query         query used for testing
     * @param expectedValue expected value
     *
     * @throws Exception if any error occurs
     */
    private void testGetDataType(Helper.DataType dataType, String query, Object expectedValue) throws Exception {

        ResultSet rs = null;

        try {
            rs = createResultSet(query);

            rs.next();
            Object result = dataType.getValue(rs, 1);

            assertEquals("Invalid value was returned by DataType.", expectedValue, result);
        } finally {
            closeResultSet(rs);
        }
    }

    /**
     * <p>Creates PreparedStatement.</p>
     *
     * @param query query string which will be used for creating prepared statement
     *
     * @return prepared statement created for query
     *
     * @throws Exception if any error occurs
     */
    private PreparedStatement createPreparedStatement(String query) throws Exception {
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();

            return connection.prepareStatement(query);
        } catch (DBConnectionException e) {
            TestHelper.close(connection);

            throw e;
        } catch (SQLException e) {
            TestHelper.close(connection);

            throw e;
        }
    }

    /**
     * <p>Creates result set from passed query string.</p>
     *
     * @param query query string which will be used to create result set
     *
     * @return result set
     *
     * @throws Exception if any error occurs
     */
    private ResultSet createResultSet(String query) throws Exception {

        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectionFactory.createConnection();
            statement = connection.createStatement();

            return statement.executeQuery(query);
        } catch (SQLException e) {

            TestHelper.close(statement);
            TestHelper.close(connection);

            throw e;
        } catch (DBConnectionException e) {

            TestHelper.close(statement);
            TestHelper.close(connection);

            throw e;
        }
    }

    /**
     * <p>Closes Statement.</p>
     *
     * @param statement statement to close
     */
    private static void closeStatement(Statement statement) {

        Connection connection = null;

        if (statement != null) {
            try {
                connection = statement.getConnection();
            } catch (SQLException e) {
                // ignore exception
            }
        }

        TestHelper.close(statement);
        TestHelper.close(connection);
    }

    /**
     * <p>Closes result set.</p>
     *
     * @param resultSet result set to close
     */
    private static void closeResultSet(ResultSet resultSet) {

        Statement statement = null;

        if (resultSet != null) {
            try {
                statement = resultSet.getStatement();
            } catch (SQLException e) {
                // ignore exception
            }
        }

        TestHelper.close(resultSet);
        closeStatement(statement);
    }
}
