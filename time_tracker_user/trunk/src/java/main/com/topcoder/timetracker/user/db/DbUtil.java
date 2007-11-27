/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.Util;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Utility class for database related operation.
 * </p>
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
final class DbUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private DbUtil() {
        // nothing to do
    }

    /**
     * <p>
     * Generates a connection from connection factory using default connection name.
     * </p>
     *
     * @param connFactory
     *            the given database connection factory
     * @param connName
     *            the given database connection name, maybe null
     * @param useTransactions
     *            the flag to decide whether the auto commit feature of the connection should turn on or off
     * @return an open connection from the connection factory defined in the constructor.
     *
     * @throws DataAccessException
     *             if fail to create a new connection instance or set the auto commit feature of the connection to
     *             false.
     * @throws NullPointerException
     *             in dbFactory is null
     */
    static Connection getConnection(DBConnectionFactory connFactory, String connName, boolean useTransactions)
        throws DataAccessException {
        Connection connection = null;

        try {
            connection =
                (connName == null) ? connFactory.createConnection() : connFactory.createConnection(connName);

            if (useTransactions) {
                connection.setAutoCommit(false);
            }

            return connection;
        } catch (DBConnectionException e) {
            closeConnection(connection);
            throw new DataAccessException(
                "DBConnectionException occurs when creating the database connection.", e);
        } catch (SQLException e) {
            closeConnection(connection);
            throw new DataAccessException("SQLException occurs when disabling auto commit.", e);
        }
    }

    /**
     * <p>
     * Closes the given Connection instance, SQLException will be ignored.
     * </p>
     *
     * @param con
     *            the given Connection instance to close.
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
     * This function is used to execute a update sql expression in database persistence.
     * </p>
     *
     * @param stat
     *            the given PreparedStatement
     * @param idExpected
     *            the expected
     * @param expectedResult
     *            number of expected results
     * @return the number of rows affects by the current operation
     *
     * @throws SQLException
     *             when exception occurs during the database operation
     * @throws UnrecognizedEntityException
     *             if the expected entity with id can't be returned
     * @throws NullPointerException
     *             if connection is null or sql is null
     */
    static int executeUpdate(PreparedStatement stat, long idExpected, int expectedResult)
        throws SQLException, UnrecognizedEntityException {

        int result = stat.executeUpdate();
        if (expectedResult != -1 && stat.getUpdateCount() != expectedResult) {
            throw new UnrecognizedEntityException(idExpected);
        }

        return result;

    }

    /**
     * <p>
     * This methods fill the <code>PreparedStatement</code> instance with the given parameters.
     * </p>
     *
     * @param pstmt
     *            the <code>PreparedStatement</code> instance to fill data
     * @param params
     *            the parameter list to fill the <code>PreparedStatement</code> instance
     *
     * @throws SQLException
     *             if a database access error occurs
     */
    static void fillPreparedStatement(PreparedStatement pstmt, List params) throws SQLException {
        pstmt.clearParameters();

        // set up all the necessary parameters for executing.
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                Object obj = params.get(i);
                if (obj instanceof SqlType) {
                    pstmt.setNull(i + 1, ((SqlType) obj).getType());
                } else {
                    setElement(i + 1, obj, pstmt);
                }
            }
        }
    }

    /**
     * <p>
     * Closes the given Statement instance, SQLException will be ignored.
     * </p>
     *
     * @param statement
     *            the given Statement instance to close.
     */
    static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given ResultSet instance, SQLException will be ignored.
     * </p>
     *
     * @param rs
     *            the given ResultSet instance to close.
     */
    static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Sets the actual value to replace the corresponding question mark.
     * </p>
     *
     * @param order
     *            the sequence number of question mark in sql expression
     * @param parameter
     *            the actual value to replace the corresponding question mark.
     * @param ps
     *            PreparedStatement instance to execute the sql expression
     *
     * @throws SQLException
     *             when exception occurs during the database operation
     */
    private static void setElement(int order, Object parameter, PreparedStatement ps) throws SQLException {
        // replace the question mark in sql with real value
        if (parameter instanceof String) {
            ps.setString(order, (String) parameter);
        } else if (parameter instanceof Long) {
            ps.setLong(order, ((Long) parameter).longValue());
        } else if (parameter instanceof Date) {
            ps.setTimestamp(order, new Timestamp(((Date) parameter).getTime()));
        } else {
            ps.setObject(order, parameter);
        }
    }

    /**
     * <p>
     * This method is used to commit or rollback the connection after a batch operation.
     * </p>
     * <p>
     * If <code>success</code> is true, then the connection will be committed, otherwise rollbacked.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @param causes
     *            the <code>Throwable</code> array containing all the exceptions in the batch
     * @param success
     *            a flag to determine whether the batch is success or not
     * @param exceptionMsg
     *            the exception message for the <code>BatchOperationException</code>
     * @param useTransactions
     *            Whether transactions should be used or not. (This should be false if used with EJBs)
     * @throws DataAccessException
     *             if fails to access the database
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if <code>success</code> is false
     */
    static void finishBatchOperation(Connection conn, Throwable[] causes, boolean success,
        String exceptionMsg, boolean useTransactions) throws DataAccessException {
        if (success) {
            try {
                if (useTransactions) {
                    conn.commit();
                }
            } catch (SQLException e) {
                rollback(conn, useTransactions);
                throw new DataAccessException("Failed to commit the connection.", e);
            }
        } else {
            rollback(conn, useTransactions);
            throw new BatchOperationException(exceptionMsg, causes);
        }
    }

    /**
     * <p>
     * Rollbacks the current connection if any error occurs while updating the persistence.
     * </p>
     * <p>
     * Note, SQLException will be ignored.
     * </p>
     *
     * @param con
     *            the given Connection instance to roll back
     * @param useTransactions
     *            Whether transactions should be used or not. (This should be false if used with EJBs)
     */
    private static void rollback(Connection con, boolean useTransactions) {
        try {
            if (useTransactions && con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            // Just ignore
        }
    }

    /**
     * <p>
     * Prepares parameters for time tracker bean's query.
     * </p>
     *
     * @param params
     *            the list of parameters to which the parameters will be added
     * @param bean
     *            the given TimeTrackerBean
     * @throws NullPointerException
     *             if bean or params is null
     */
    static void prepareTimeTrackerBeanParams(List params, TimeTrackerBean bean) {
        params.add(bean.getCreationDate());
        params.add(bean.getCreationUser());
        params.add(bean.getModificationDate());
        params.add(bean.getModificationUser());
    }

    /**
     * <p>
     * Prepares search bundle.
     * </p>
     *
     * @param searchBundleManagerNamespace
     *            the namespace of search bundle manager
     * @param searchBundleName
     *            the name of the search bundle
     * @return the intended search bundle
     * @throws ConfigurationException
     *             if there is any exception when building the search bundle
     * @throws IllegalArgumentException
     *             if searchBundleManagerNamespace or searchBundleName is null or empty
     */
    static SearchBundle prepareSearchBundle(String searchBundleManagerNamespace, String searchBundleName)
        throws ConfigurationException {
        Util.checkString(searchBundleManagerNamespace, "searchBundleManagerNamespace");
        Util.checkString(searchBundleName, "searchBundleName");

        // creates the search bundle
        try {
            SearchBundleManager searchBundleManager = new SearchBundleManager(searchBundleManagerNamespace);
            SearchBundle searchBundle = searchBundleManager.getSearchBundle(searchBundleName);
            if (searchBundle == null) {
                throw new ConfigurationException("There is no search bundle with name [" + searchBundleName
                    + "]");
            }
            return searchBundle;
        } catch (SearchBuilderConfigurationException e) {
            throw new ConfigurationException("Failed to build search bundle manager with namespace "
                + searchBundleManagerNamespace, e);
        }
    }

    /**
     * <p>
     * This method creates a <code>IDGenerator</code> instance based on the given <code>key</code>.
     * </p>
     *
     * @param key
     *            the id generator name
     * @return the <code>IDGenerator</code> instance for the given <code>key</code>
     *
     * @throws ConfigurationException
     *             if fails to create the <code>IDGenerator</code> instance
     */
    static IDGenerator createIDGenerator(String key) throws ConfigurationException {
        try {
            return IDGeneratorFactory.getIDGenerator(key);
        } catch (IDGenerationException e) {
            throw new ConfigurationException("IDGenerationException occurs when getting "
                + "the id generator for " + key, e);
        }
    }

    /**
     * <p>
     * Checks that the given <code>TimeTrackerBean</code> is valid, that is, every required property is set
     * properly.
     * </p>
     *
     * @param bean
     *            the given TimeTrackerBean
     * @throws IllegalArgumentException
     *             if some beans is not valid
     */
    static void checkTimeTrackerBean(TimeTrackerBean bean) {
        // null creation user is not allowed
        if (bean.getCreationUser() == null) {
            throw new IllegalArgumentException("Some user has null creation user.");
        }

        // null creation date is not allowed
        if (bean.getCreationDate() == null) {
            throw new IllegalArgumentException("Some user has null creation date.");
        }

        // null modification user is not allowed
        if (bean.getModificationUser() == null) {
            throw new IllegalArgumentException("Some user has null modification user.");
        }

        // null modification date is not allowed
        if (bean.getModificationDate() == null) {
            throw new IllegalArgumentException("Some user has null modification date.");
        }
    }

    /**
     * <p>
     * This is a data class and contains a sql type.
     * </p>
     *
     * <p>
     * This class is used to represents a <code>NULL</code> column value when filling a
     * <code>PreparedStatement</code> instance.
     * </p>
     *
     * <p>
     * Thread Safety : This class is immutable and so is thread safe.
     * </p>
     *
     * @author biotrail
     * @version 3.2
     */
    static class SqlType {
        /**
         * <p>
         * Represents the sql type for a column.
         * </p>
         */
        private int type;

        /**
         * <p>
         * Constructs a <code>SqlType</code> with the sql type given.
         * </p>
         *
         * @param type
         *            the sql type
         */
        SqlType(int type) {
            this.type = type;
        }

        /**
         * <p>
         * Gets the sql type for a column.
         * </p>
         *
         * @return the sql type for a column.
         */
        int getType() {
            return type;
        }
    }
}
