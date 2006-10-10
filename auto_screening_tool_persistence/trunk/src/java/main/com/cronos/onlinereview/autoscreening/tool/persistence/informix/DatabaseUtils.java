/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.cronos.onlinereview.autoscreening.tool.DAOException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

/**
 * This class contains utility methods used only by the database package of this component. This
 * class is package-visible as all methods are solely used from this package.
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class DatabaseUtils {
	/**
	 * This constant provides the DataType instance that can be used in the query methods to specify
	 * that a ResultSet column of a query result should be returned as value of type String or as
	 * null in case the ResultSet value was null, and to specify that PreparedStatement#setString()
	 * should be used for a parameter.
	 */
	static final DataType STRING_TYPE = new StringType();

	/**
	 * This constant provides the DataType instance that can be used in the query methods to specify
	 * that a ResultSet column of a query result should be returned as value of type Long or as null
	 * in case the ResultSet value was null, and to specify that PreparedStatement#setLong() should
	 * be used for a parameter.
	 */
	static final DataType LONG_TYPE = new LongType();

	/**
	 * This constant provides the DataType instance that can be used in the query methods to specify
	 * that a ResultSet column of a query result should be returned as value of type Date or as null
	 * in case the ResultSet value was null, and to specify that PreparedStatement#setTimestamp()
	 * should be used for a parameter.
	 */
	static final DataType DATE_TYPE = new DateType();

	/**
	 * <p>
	 * This class is a wrapper for type safe getting of values from a ResultSet and setting of
	 * values to a PreparedStatement. This class has been introduced to consist the behaviors of
	 * different databases and JDBC drivers so that always the expected type is returned
	 * (getObject(int) does not sufficiently do this job as the type of the value is highly
	 * database-dependent (e.g. for a BLOB column the MySQL driver returns a byte[] and the Oracle
	 * driver returns a Blob)). This class has also been introduced to make sure that the input
	 * parameter objects are all of expected types and the specified setXXX methods are used
	 * (setObject() is kind of dangerous in some cases).
	 * </p>
	 * <p>
	 * This class contains a private constructor to make sure all implementations of this class are
	 * declared inside Helper. Instances are provided to users via constants declared in Helper - so
	 * this class defines some kind of 'pseudo-enum' which cannot be instantiated externally.
	 * </p>
	 * @author TCSDEVELOPER
	 * @version 1.0
	 */
	abstract static class DataType {
		/**
		 * Empty private constructor. By using this concept, it is assured that only Helper class
		 * can contain subclasses of this class and the implementation classes cannot be
		 * instantiated externally.
		 */
		private DataType() {
		}

		/**
		 * This method gets the value at the given index from the given resultSet as instance of the
		 * subclass-dependent type.
		 * @param resultSet
		 *            the result set from which to get the value
		 * @param index
		 *            the index at which to get the value
		 * @return the retrieved value
		 * @throws IllegalArgumentException
		 *             if resultSet is null
		 * @throws SQLException
		 *             if error occurs while working with the given ResultSet or the index does not
		 *             exist in the result set
		 */
		protected abstract Object getValue(ResultSet resultSet, int index) throws SQLException;

		/**
		 * This method sets the value at the given index from the given preparedStatement as
		 * instance of the subclass-dependent type.
		 * @param preparedStatement
		 *            the prepared statement from which to set the value
		 * @param index
		 *            the index at which to set the value
		 * @param value
		 *            the value to set
		 * @throws IllegalArgumentException
		 *             if preparedStatement or value is null, or value is not an instance of the
		 *             subclass-dependent type
		 * @throws SQLException
		 *             if error occurs while working with the given preparedStatement or the index
		 *             does not exist in the prepared statement
		 */
		protected abstract void setValue(PreparedStatement preparedStatement, int index,
				Object value) throws SQLException;
	}

	/**
	 * This class is a wrapper for type safe getting of values from a ResultSet and setting of
	 * values to a PreparedStatement. The values retrieved by the getValue(java.sql.ResultSet, int)
	 * implementation of this DataType are assured to be of type String or to be null in case the
	 * ResultSet value was null. PreparedStatement#setString() will be used to set the value, which
	 * should be of String type.
	 * @author TCSDEVELOPER
	 * @version 1.0
	 */
	private static class StringType extends DataType {
		/**
		 * This method retrieves the value at the given index from the given resultSet as instance
		 * of String type.
		 * @param resultSet
		 *            the result set from which to retrieve the value
		 * @param index
		 *            the index at which to retrieve the value
		 * @return the retrieved value as String or null if the value in the ResultSet was null.
		 * @throws IllegalArgumentException
		 *             if resultSet is null
		 * @throws SQLException
		 *             if error occurs while working with the given ResultSet or the index does not
		 *             exist in the result set
		 */
		protected Object getValue(ResultSet resultSet, int index) throws SQLException {
			if (resultSet == null) {
				throw new IllegalArgumentException("resultSet should not be null.");
			}

			return resultSet.getString(index);
		}

		/**
		 * This method sets the value at the given index from the given preparedStatement as
		 * instance of String type.
		 * @param preparedStatement
		 *            the prepared statement from which to set the value
		 * @param index
		 *            the index at which to set the value
		 * @param value
		 *            the value to set
		 * @throws IllegalArgumentException
		 *             if preparedStatement or value is null, or value is not an instance of String
		 *             type
		 * @throws SQLException
		 *             if error occurs while working with the given preparedStatement or the index
		 *             does not exist in the prepared statement
		 */
		protected void setValue(PreparedStatement preparedStatement, int index, Object value)
				throws SQLException {
			if (preparedStatement == null) {
				throw new IllegalArgumentException("preparedStatement should not be null.");
			}
			if (value != null && !(value instanceof String)) {
				throw new IllegalArgumentException(
						"value should not be an instance of String, if not null.");
			}

			preparedStatement.setString(index, (String) value);
		}
	}

	/**
	 * This class is a wrapper for type safe getting of values from a ResultSet and setting of
	 * values to a PreparedStatement. The values retrieved by the getValue(java.sql.ResultSet, int)
	 * implementation of this DataType are assured to be of type Long or to be null in case the
	 * ResultSet value was null. PreparedStatement#setLong() will be used to set the value, which
	 * should be of Long type.
	 * @author TCSDEVELOPER
	 * @version 1.0
	 */
	private static class LongType extends DataType {
		/**
		 * This method retrieves the value at the given index from the given resultSet as instance
		 * of Long type.
		 * @param resultSet
		 *            the result set from which to retrieve the value
		 * @param index
		 *            the index at which to retrieve the value
		 * @return the retrieved value as Long or null if the value in the ResultSet was null.
		 * @throws IllegalArgumentException
		 *             if resultSet is null
		 * @throws SQLException
		 *             if error occurs while working with the given ResultSet or the index does not
		 *             exist in the result set
		 */
		protected Object getValue(ResultSet resultSet, int index) throws SQLException {
			if (resultSet == null) {
				throw new IllegalArgumentException("resultSet should not be null.");
			}

			long value = resultSet.getLong(index);
			return resultSet.wasNull() ? null : new Long(value);
		}

		/**
		 * This method sets the value at the given index from the given preparedStatement as
		 * instance of Long type.
		 * @param preparedStatement
		 *            the prepared statement from which to set the value
		 * @param index
		 *            the index at which to set the value
		 * @param value
		 *            the value to set
		 * @throws IllegalArgumentException
		 *             if preparedStatement or value is null, or value is not an instance of Long
		 *             type
		 * @throws SQLException
		 *             if error occurs while working with the given preparedStatement or the index
		 *             does not exist in the prepared statement
		 */
		protected void setValue(PreparedStatement preparedStatement, int index, Object value)
				throws SQLException {
			if (preparedStatement == null) {
				throw new IllegalArgumentException("preparedStatement should not be null.");
			}
			if (value != null && !(value instanceof Long)) {
				throw new IllegalArgumentException(
						"value should not be an instance of Long, if not null.");
			}

			if (value == null) {
				preparedStatement.setNull(index, Types.BIGINT);
			} else {
				preparedStatement.setLong(index, ((Long) value).longValue());
			}
		}
	}

	/**
	 * This class is a wrapper for type safe getting of values from a ResultSet and setting of
	 * values to a PreparedStatement. The values retrieved by the getValue(java.sql.ResultSet, int)
	 * implementation of this DataType are assured to be of type java.util.Date or to be null in
	 * case the ResultSet value was null. PreparedStatement#setTimestamp() will be used to set the
	 * value, which should be of java.util.Date type.
	 * @author TCSDEVELOPER
	 * @version 1.0
	 */
	private static class DateType extends DataType {
		/**
		 * This method retrieves the value at the given index from the given resultSet as instance
		 * of java.util.Date type.
		 * @param resultSet
		 *            the result set from which to retrieve the value
		 * @param index
		 *            the index at which to retrieve the value
		 * @return the retrieved value as java.util.Date or null if the value in the ResultSet was
		 *         null.
		 * @throws IllegalArgumentException
		 *             if resultSet is null
		 * @throws SQLException
		 *             if error occurs while working with the given ResultSet or the index does not
		 *             exist in the result set
		 */
		protected Object getValue(ResultSet resultSet, int index) throws SQLException {
			if (resultSet == null) {
				throw new IllegalArgumentException("resultSet should not be null.");
			}

			Timestamp timestamp = resultSet.getTimestamp(index);
			return timestamp == null ? null : new Date(timestamp.getTime());
		}

		/**
		 * This method sets the value at the given index from the given preparedStatement as
		 * instance of java.util.Date type. <p/> Note: UnsupportedOperationException is always
		 * thrown currently to ensure that this method won't be called.
		 * @param preparedStatement
		 *            the prepared statement from which to set the value
		 * @param index
		 *            the index at which to set the value
		 * @param value
		 *            the value to set
		 * @throws IllegalArgumentException
		 *             if preparedStatement or value is null, or value is not an instance of
		 *             java.util.Date type
		 * @throws SQLException
		 *             if error occurs while working with the given preparedStatement or the index
		 *             does not exist in the prepared statement
		 */
		protected void setValue(PreparedStatement preparedStatement, int index, Object value)
				throws SQLException {
			if (preparedStatement == null) {
				throw new IllegalArgumentException("preparedStatement should not be null.");
			}
			if (value != null && !(value instanceof Date)) {
				throw new IllegalArgumentException(
						"value should not be an instance of Date, if not null.");
			}

			preparedStatement.setTimestamp(index, value == null ? null : new Timestamp(
					((Date) value).getTime()));
		}
	}

	/**
	 * Private constructor to prevent this class be instantiated.
	 */
	private DatabaseUtils() {
	}

	/**
	 * <p>
	 * This method performs the given retrieval (i.e. non-DML) query on the given connection using
	 * the given query arguments. The ResultSet returned from the query is fetched into a Object[][]
	 * of Object[]s and then returned. This approach assured that all resources (the
	 * PreparedStatement and the ResultSet) allocated in this method are also de-allocated in this
	 * method.
	 * </p>
	 * <p>
	 * Note: The given connection is not closed or committed in this method.
	 * </p>
	 * @param connectionFactory
	 *            the connection factory
	 * @param connectionName
	 *            the connection name
	 * @param queryString
	 *            the query to be performed
	 * @param argumentTypes
	 *            the types of each object in queryArgs, use one of the values STRING_TYPE,
	 *            LONG_TYPE or BOOLEAN_TYPE here
	 * @param queryArgs
	 *            the arguments to be used in the query
	 * @param columnTypes
	 *            the types as which to return the result set columns
	 * @return the result of the query as Object[][] containing an Object[] for each ResultSet row
	 *         The elements of the array are of the type represented by the DataType specified at
	 *         the corresponding index in the given columnTypes array (or null in case the resultSet
	 *         value was null)
	 * @throws IllegalArgumentException
	 *             if connectionFactory is null, or if queryString is null or empty (trimmed), or if
	 *             argumentTypes is null or contains null, or if queryArgs is null or the length of
	 *             it is different from that of argumentTypes, or if columnTypes is null or contains
	 *             null or the the number of columns returned is different from that of columnTypes
	 * @throws DAOException
	 *             if any error happens
	 */
	static Object[][] doQuery(DBConnectionFactory connectionFactory, String connectionName,
			String queryString, DataType[] argumentTypes, Object[] queryArgs, DataType[] columnTypes)
			throws DAOException {
		if (queryString == null) {
			throw new IllegalArgumentException("queryString should not be null.");
		}
		if (queryString.trim().length() == 0) {
			throw new IllegalArgumentException("queryString should not be empty (trimmed).");
		}
		if (argumentTypes == null) {
			throw new IllegalArgumentException("argumentTypes should not be null.");
		}
		if (Arrays.asList(argumentTypes).contains(null)) {
			throw new IllegalArgumentException("argumentTypes should not contain null elements.");
		}
		if (queryArgs == null) {
			throw new IllegalArgumentException("queryArgs should not be null.");
		}
		if (queryArgs.length != argumentTypes.length) {
			throw new IllegalArgumentException(
					"The length of queryArgs should be equal to that of argumentTypes.");
		}
		if (columnTypes == null) {
			throw new IllegalArgumentException("columnTypes should not be null.");
		}
		if (Arrays.asList(columnTypes).contains(null)) {
			throw new IllegalArgumentException("columnTypes should not contain null elements.");
		}

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// create the connection with auto-commit mode enabled
			conn = createConnection(connectionFactory, connectionName);

			// prepare the statement
			preparedStatement = conn.prepareStatement(queryString);

			// build the statement
			for (int i = 0; i < queryArgs.length; i++) {
				argumentTypes[i].setValue(preparedStatement, i + 1, queryArgs[i]);
			}

			// execute the query and build the result into a list
			resultSet = preparedStatement.executeQuery();

			// get result list.
			List ret = new ArrayList();

			// check if the number of column is correct.
			int columnCount = resultSet.getMetaData().getColumnCount();
			if (columnTypes.length != columnCount) {
				throw new IllegalArgumentException("The column types length [" + columnTypes.length
						+ "] does not match the result set column count [" + columnCount + "].");
			}

			while (resultSet.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = columnTypes[i].getValue(resultSet, i + 1);
				}
				ret.add(rowData);
			}
			return (Object[][]) ret.toArray(new Object[][] {});
		} catch (SQLException e) {
			throw new DAOException("Error occurs while executing query [" + queryString
					+ "] using the query arguments " + Arrays.asList(queryArgs).toString() + ".", e);
		} finally {
			try {
				closeResultSet(resultSet);
			} finally {
				try {
					closeStatement(preparedStatement);
				} finally {
					closeConnection(conn);
				}
			}
		}
	}

	/**
	 * <p>
	 * This method performs the given DML (query on the given connection using the given query
	 * arguments and their types. The update count returned from the query is then returned.
	 * </p>
	 * @param connectionFactory
	 *            the connection factory
	 * @param connectionName
	 *            the connection name
	 * @param queryString
	 *            the query to be performed
	 * @param argumentTypes
	 *            the types of each object in queryArgs, use one of the values STRING_TYPE,
	 *            LONG_TYPE or BOOLEAN_TYPE here
	 * @param queryArgs
	 *            the arguments to be used in the query
	 * @return the number of database rows affected by the query
	 * @throws IllegalArgumentException
	 *             if connectionFactory is null, or if queryString is null or empty (trimmed), or if
	 *             argumentTypes is null or contains null, or if queryArgs is null or length of it
	 *             is different from that of argumentTypes
	 * @throws DAOException
	 *             if the query fails
	 */
	static int doDMLQuery(DBConnectionFactory connectionFactory, String connectionName,
			String queryString, DataType[] argumentTypes, Object[] queryArgs) throws DAOException {
		if (queryString == null) {
			throw new IllegalArgumentException("queryString should not be null.");
		}
		if (queryString.trim().length() == 0) {
			throw new IllegalArgumentException("queryString should not be empty (trimmed).");
		}
		if (argumentTypes == null) {
			throw new IllegalArgumentException("argumentTypes should not be null.");
		}
		if (Arrays.asList(argumentTypes).contains(null)) {
			throw new IllegalArgumentException("argumentTypes should not contain null elements.");
		}
		if (queryArgs == null) {
			throw new IllegalArgumentException("queryArgs should not be null.");
		}
		if (queryArgs.length != argumentTypes.length) {
			throw new IllegalArgumentException(
					"The length of queryArgs should be equal to that of argumentTypes.");
		}

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			// create the connection with auto-commit mode enabled
			conn = createConnection(connectionFactory, connectionName);

			// prepare the statement.
			preparedStatement = conn.prepareStatement(queryString);

			// build the statement.
			for (int i = 0; i < queryArgs.length; i++) {
				argumentTypes[i].setValue(preparedStatement, i + 1, queryArgs[i]);
			}

			// execute the statement.
			preparedStatement.execute();
			return preparedStatement.getUpdateCount();
		} catch (SQLException e) {
			throw new DAOException("Error occurs while executing query [" + queryString
					+ "] using the query arguments " + Arrays.asList(queryArgs).toString() + ".", e);
		} finally {
			try {
				closeStatement(preparedStatement);
			} finally {
				closeConnection(conn);
			}
		}
	}

	/**
	 * Close the connection if conn is not null.
	 * @param conn
	 *            the connection to close
	 * @throws DAOException
	 *             if error occurs when closing the connection
	 */
	static void closeConnection(Connection conn) throws DAOException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DAOException("Error occurs when closing the connection.", e);
			}
		}
	}

	/**
	 * Close the prepared statement if ps is not null.
	 * @param ps
	 *            the prepared statement to close
	 * @throws DAOException
	 *             error occurs when closing the prepared statement
	 */
	static void closeStatement(PreparedStatement ps) throws DAOException {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				throw new DAOException("Error occurs when closing the prepared statement.", e);
			}
		}
	}

	/**
	 * Close the result set if rs is not null.
	 * @param rs
	 *            the result set to close
	 * @throws DAOException
	 *             error occurs when closing the result set.
	 */
	static void closeResultSet(ResultSet rs) throws DAOException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAOException("Error occurs when closing the result set.", e);
			}
		}
	}

	/**
	 * Create a connection from the connectionFactory and connectionName.
	 * @param connectionFactory
	 *            the connection factory
	 * @param connectionName
	 *            the connection name
	 * @return the connection created
	 * @throws DAOException
	 *             if error happens when creating the connection
	 */
	static Connection createConnection(DBConnectionFactory connectionFactory, String connectionName)
			throws DAOException {
		if (connectionFactory == null) {
			throw new IllegalArgumentException("connectionFactory should not be null.");
		}

		try {
			// create the connection.
			Connection conn = connectionName == null ? connectionFactory.createConnection()
					: connectionFactory.createConnection(connectionName);

			return conn;
		} catch (DBConnectionException e) {
			throw new DAOException(
					"Error occurs when getting the connection using "
							+ (connectionName == null ? "default name"
									: ("name [" + connectionName + "].")), e);
		}
	}

	/**
	 * Wraps a long value to a Long object. If the long value is Long.MIN_VALUE, null will be
	 * returned.
	 * @param value
	 *            the long value to wrap
	 * @return the Long object
	 */
	static Long wrapLongValue(long value) {
		return value == Long.MIN_VALUE ? null : new Long(value);
	}

	/**
	 * Unwraps a Long object to a long value. If the Long object is null, Long.MIN_VALUE will be
	 * returned.
	 * @param object
	 *            the Long object to unwrap
	 * @return the long value
	 */
	static long unwrapLongOjbect(Long object) {
		return object == null ? Long.MIN_VALUE : object.longValue();
	}
}
