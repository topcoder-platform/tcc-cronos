/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;


/**
 * <p>
 * This base test case provides common functionality for configuration and database.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseTestCase extends TestCase {
    /**
     * <p>
     * Represents the date format for parsing date string.
     * </p>
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * <p>
     * Represents the property file for configuration persistence.
     * </p>
     */
    private static final String UNITTEST_PROPERTIES_FILE = "unittests.properties";

    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     * </p>
     */
    private  static DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getDBConnectionFactory();
    }

    /**
     * <p>
     * Returns the <code>DBConnectionFactory</code> instance.
     * </p>
     *
     * @return the <code>DBConnectionFactory</code> instance
     * @throws Exception to JUnit
     */
    public  static DBConnectionFactory getDBConnectionFactory() throws Exception {

        if (dbConnectionFactory == null) {
            ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(UNITTEST_PROPERTIES_FILE);

            dbConnectionFactory = new DBConnectionFactoryImpl(configurationFileManager.getConfiguration(
                        "InformixDBConnectionFactory"));
        }
        return dbConnectionFactory;
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename
     *            the file name.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public static void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = getDBConnectionFactory().createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            bufferedReader = new BufferedReader(
                    new InputStreamReader(BaseTestCase.class.getResourceAsStream(filename)));

            while (null != (sql = bufferedReader.readLine())) {
                stmt.executeUpdate(sql);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * <p>
     * Executes the sql statements against the database.
     * </p>
     *
     * @param sqls
     *            the array of sql statements.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public  static void executeSQL(String[] sqls) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getDBConnectionFactory().createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            for (int i = 0; i < sqls.length; i++) {
                System.out.println(sqls[i]);
                stmt.executeUpdate(sqls[i]);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Closes the connection. It will be used in finally block.
     * </p>
     *
     * @param conn
     *            the database connection.
     */
    public static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the result set. It will be used in finally block.
     * </p>
     *
     * @param rs
     *            the result set.
     */
    public static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the statement. It will be used in finally block.
     * </p>
     *
     * @param stmt
     *            the statement.
     */
    public static void closeStatement(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Gets the field value of an object.
     * </p>
     *
     * @param obj
     *            the object where to get the field value.
     * @param fieldName
     *            the name of the field.
     * @return the field value
     * @throws Exception
     *             any exception occurs.
     */
    public static Object getFieldValue(Object obj, String fieldName)
        throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        return field.get(obj);
    }
}
