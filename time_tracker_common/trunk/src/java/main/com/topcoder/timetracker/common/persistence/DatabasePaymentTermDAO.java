/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.common.CommonManagerConfigurationException;
import com.topcoder.timetracker.common.DuplicatePaymentTermException;
import com.topcoder.timetracker.common.Helper;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.PaymentTermDAO;
import com.topcoder.timetracker.common.PaymentTermDAOException;
import com.topcoder.timetracker.common.PaymentTermNotFoundException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;

/**
 * <p>
 * Database implementation of the <code>PaymentTermDAO</code> interface. It is capable of persisting and retrieving
 * <code>PaymentTerm</code> information from the database. Add, Retrieve, Update, Delete methods are provided.
 * DB Connection is realized using <code>DBConnectionFactory</code>. An <code>IDGenerator</code> instance is used to
 * generate unique identifiers for <code>PaymentTerm</code> that should be added in the persistence.
 * </p>
 *
 * <p>
 *  <strong>Sample Configuration:</strong>
 *  All properties are required and must have non-empty(trimmed) values.
 *  <pre>
 *   &lt;Config name="DatabasePaymentTermDAO"&gt;
 *       &lt;!-- namespace of ObjectFactory --&gt;
 *       &lt;Property name="of_namespace"&gt;
 *          &lt;Value&gt;ObjectFactoryNS&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;!-- key of DBConnectionFactory within ObjectFactory --&gt;
 *       &lt;Property name="db_connection_factory_key"&gt;
 *          &lt;Value&gt;DBConnectionFactoryKey&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;!-- connection name within DBConnectionFactory --&gt;
 *       &lt;Property name="connection_name"&gt;
 *          &lt;Value&gt;Informix&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;!-- IDGenerator name used to generate id for PaymentTerm --&gt;
 *       &lt;Property name="id_generator_name"&gt;
 *          &lt;Value&gt;PaymentTermGenerator&lt;/Value&gt;
 *       &lt;/Property&gt;
 *   &lt;/Config&gt;
 *  </pre>
 * </p>
 *
 * <p>
 *  <strong>To use IDGenerator:</strong>
 *  You must configure a namespace "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl" which will be used by
 *  IDGenerator component to get <code>DBConnectionFactoryImpl</code>. And you should configure a default connection
 *  which will be used IDGenerator component to create <code>Connection</code> or use an Oracle sequence.
 *  Also ensure IDGenerator is generating positive long values. For detail information, please refer to the doc of
 *  IDGenerator component.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  The class itself is thread safe because of immutability. NOTE: All modification database operations will
 *  be put in transaction. Single query string are simple operations and they not need to be placed in transactions.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public class DatabasePaymentTermDAO implements PaymentTermDAO {

    /**
     * <p>
     * The count of seconds within one day(24h * 60m * 60s).
     * </p>
     */
    private static final int ONE_DAY = 24 * 60 * 60;

    /**
     * <p>
     * The name of property which gives the namespace of {@link ObjectFactory}.
     * </p>
     */
    private static final String OF_NAMESPACE = "of_namespace";

    /**
     * <p>
     * The name of property which gives the key of {@link DBConnectionFactory} within the {@link ObjectFactory}.
     * </p>
     */
    private static final String DBCONNECTIONFACTORY_KEY = "db_connection_factory_key";

    /**
     * <p>
     * The name of property which gives the connection name within {@link DBConnectionFactory}.
     * </p>
     */
    private static final String CONNECTION_NAME = "connection_name";

    /**
     * <p>
     * The name of property which gives the name of {@link IDGenerator}.
     * </p>
     */
    private static final String IDGENERATOR_NAME = "id_generator_name";

    /**
     * <p>
     * The SQL order by clause on payment_terms_id column.
     * </p>
     */
    private static final String ORDER_BY_CLAUSE = " order by payment_terms_id";

    /**
     * <p>
     * The SQL where clause to filter a {@link PaymentTerm} by id.
     * </p>
     */
    private static final String WHERE_CLAUSE_BY_ID = " where payment_terms_id = ?";

    /**
     * <p>
     * The SQL query to insert a {@link PaymentTerm} into data store.
     * </p>
     */
    private static final String INSERT_PT =
        "insert into payment_terms "
        + "(description,creation_date,creation_user,modification_date,modification_user,active,term,payment_terms_id) "
        + "values (?,?,?,?,?,?,?,?)";

    /**
     * <p>
     * The SQL query to update a {@link PaymentTerm} within data store.
     * </p>
     */
    private static final String UPDATE_PT_BY_ID =
        "update payment_terms "
        + "set description = ?, creation_date = ?, creation_user = ?, "
           +  "modification_date = ?, modification_user = ?, active = ?, term = ?"
           +  WHERE_CLAUSE_BY_ID;

    /**
     * <p>
     * The SQL query to select all {@link PaymentTerm} entries from data store.
     * </p>
     */
    private static final String SELECT_ALL_PT = "select * from payment_terms";

    /**
     * <p>
     * The SQL query to select a {@link PaymentTerm} entry by id from data store.
     * </p>
     */
    private static final String SELECT_PT_BY_ID = SELECT_ALL_PT + WHERE_CLAUSE_BY_ID;

    /**
     * <p>
     * The SQL query to select all active {@link PaymentTerm} entries from data store.
     * </p>
     */
    private static final String SELECT_ACTIVE_PT = SELECT_ALL_PT + " where active = 1";

    /**
     * <p>
     * The SQL query to select recently created {@link PaymentTerm} entries from data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *   <li>
     *   Because the machine of Informix db may have different time clock with the machine running
     *   the Time Tracker Application, so do not use the "CURRENT" function to get current time.
     *   </li>
     *   <li>
     *   The max days represented by INTERVAL DAY TO DAY is INTERVAL(999999999) DAY(9) TO DAY, which
     *   is smaller than <code>Integer.MAX_VALUE</code>, so do not user the "INTERVAL".
     *   </li>
     *  </ul>
     * </p>
     */
    private static final String SELECT_RECENT_CREATED_PT = SELECT_ALL_PT
        + " where ? - creation_date <= ? UNITS SECOND";

    /**
     * <p>
     * The SQL query to select recently modified {@link PaymentTerm} entries from data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *   <li>
     *   Because the machine of Informix db may have different time clock with the machine running
     *   the Time Tracker Application, so do not use the "CURRENT" function to get current time.
     *   </li>
     *   <li>
     *   The max days represented by INTERVAL DAY TO DAY is INTERVAL(999999999) DAY(9) TO DAY, which
     *   is smaller than <code>Integer.MAX_VALUE</code>, so do not user the "INTERVAL".
     *   </li>
     *  </ul>
     * </p>
     */
    private static final String SELECT_RECENT_MODIFIED_PT = SELECT_ALL_PT
        + " where ? - modification_date <= ? UNITS SECOND";

    /**
     * <p>
     * The SQL query to delete all {@link PaymentTerm} entries from data store.
     * </p>
     */
    private static final String DELETE_ALL_PT = "delete from payment_terms";

    /**
     * <p>
     * The SQL query to delete all {@link PaymentTerm} entries from data store.
     * </p>
     */
    private static final String DELETE_PT_BY_ID = DELETE_ALL_PT + WHERE_CLAUSE_BY_ID;

    /**
     * <p>
     * The count of fields of <code>PaymentTerm</code>.
     * </p>
     */
    private static final int BEAN_FIELDS_COUNT = 8;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represents the connection name to obtain the db connection from {@link DBConnectionFactory}.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Can not be null or empty(trimmed) string.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *  Initialized in the constructor.
     * </p>
     *
     * <p>
     *  <strong>Immutability:</strong>
     *  Immutable.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represents the {@link DBConnectionFactory} object to obtain the db connection.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Can not be null.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *  Initialized in the constructor.
     * </p>
     *
     * <p>
     *  <strong>Immutability:</strong>
     *  Immutable.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represents an instance of {@link IDGenerator} that is used to generate ids for the {@link PaymentTerm} entities.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Can not be null.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *  Initialized in the constructor. Used in {@link this#addPaymentTerm(PaymentTerm)} to generate id for
     *  {@link PaymentTerm}.
     * </p>
     *
     * <p>
     *  <strong>Immutability:</strong>
     *  Immutable.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * Constructor to load configuration values from the given namespace.
     * </p>
     *
     * <p>
     * See class doc for sample configuration.
     * </p>
     *
     * @param namespace the namespace to load configuration values. Must not be null or empty(trimmed).
     *
     * @throws IllegalArgumentException if the argument is null or empty(trimmed) string.
     *
     * @throws CommonManagerConfigurationException if any configured value is invalid or any required value is missing.
     */
    public DatabasePaymentTermDAO(String namespace) throws CommonManagerConfigurationException {
        //Get DBConnectionFactory
        String connectionFactoryKey = Helper.getPropertyValue(namespace, DBCONNECTIONFACTORY_KEY);

        try {
            this.connectionFactory = (DBConnectionFactory)
                (Helper.getObjectFactory(namespace, OF_NAMESPACE).createObject(connectionFactoryKey));
        } catch (InvalidClassSpecificationException e) {
            throw new CommonManagerConfigurationException("Error occurs while instantiating DBConnectionFactory", e);
        } catch (ClassCastException e) {
            throw new CommonManagerConfigurationException(
                    "The object created by ObjectFactory is not type of DBConnectionFactory", e);
        }

        //Get connection name
        this.connectionName = Helper.getPropertyValue(namespace, CONNECTION_NAME);

        //Get IDGenerator
        String idGeneratorName = Helper.getPropertyValue(namespace, IDGENERATOR_NAME);
        this.idGenerator = this.getIDGenerator(idGeneratorName);
    }

    /**
     * <p>
     * Constructor with <code>DBConnectionFactory</code>, connection name. Also the name of <code>IDGenerator</code>
     * is provided.
     * </p>
     *
     * @param connectionFactory the <code>DBConnectionFactory</code> object to obtain the connection. Must not be null.
     * @param connectionName the connection name. Must not be null or empty(trimmed).
     * @param idGeneratorName the name of <code>IDGenerator</code>. Must not be null or empty(trimmed).
     *
     * @throws IllegalArgumentException if any argument is null or empty(trimmed) string.
     * @throws CommonManagerConfigurationException if fails to create the <code>IDGenerator</code> instance
     *         with the given name.
     */
    public DatabasePaymentTermDAO(DBConnectionFactory connectionFactory, String connectionName,
        String idGeneratorName) throws CommonManagerConfigurationException {
        Helper.validateNotNull(connectionFactory, "The connection factory");
        Helper.validateString(connectionName, "The connection name");
        Helper.validateString(idGeneratorName, "The idGenerator name");
        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
        this.idGenerator = this.getIDGenerator(idGeneratorName);
    }

    /**
     * <p>
     * Gets an instance of <code>IDGenerator</code> with given name.
     * </p>
     *
     * @param idGeneratorName The name of <code>IDGenerator</code>.
     *
     * @return <code>IDGenerator</code> created.
     *
     * @throws CommonManagerConfigurationException If error occurs while getting <code>IDGenerator</code>.
     */
    private IDGenerator getIDGenerator(String idGeneratorName) throws CommonManagerConfigurationException {
        try {
            //Create id generator
            return IDGeneratorFactory.getIDGenerator(idGeneratorName);
        } catch (IDGenerationException e) {
            throw new CommonManagerConfigurationException(
                    "Failed to get id generator with name '" + idGeneratorName + "'", e);
        }
    }

    /**
     * <p>
     * Generates a connection from connection factory and connection name.
     * </p>
     *
     * @param autoCommit Indicate whether need turn off the auto commit feature.
     *
     * @return an open connection from the connection factory defined in the constructor.
     *
     * @throws PaymentTermDAOException If fail to create a new connection instance or
     *         set the auto commit feature of the connection to false.
     */
    private Connection getConnection(boolean autoCommit) throws PaymentTermDAOException {
        Connection connection = null;
        try {
            connection = this.connectionFactory.createConnection(this.connectionName);

            //the auto commit feature is turned off
            if (!autoCommit) {
                connection.setAutoCommit(autoCommit);
            }

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return connection;
        } catch (DBConnectionException e) {
            throw new PaymentTermDAOException(
                "DBConnectionException occurs when creating the database connection.", e);
        } catch (SQLException e) {
            closeConnection(connection);
            throw new PaymentTermDAOException("SQLException occurs when creating the database connection.", e);
        }
    }
    /**
     * <p>
     * Closes the given Statement instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param statement the given Statement instance to close.
     */
    private void closeStatement(Statement statement) {
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
     * Closes the given Connection instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    private void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given ResultSet instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param rs the given ResultSet instance to close.
     */
    private void closeResultSet(ResultSet rs) {
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
     * Roll back the current connection if any error occurs while updating the persistence.
     * </p>
     *
     * <p>
     * Note, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to roll back
     */
    private void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            // Just ignore
        }
    }

    /**
     * <p>
     * Sets the actual value to replace the corresponding question mark.
     * </p>
     *
     * @param order the sequence number of question mark in sql expression
     * @param parameter the actual value to replace the corresponding question mark.
     * @param ps <code>PreparedStatement</code> instance to execute the sql expression
     *
     * @throws SQLException when exception occurs during the database operation
     */
    private void setElement(int order, Object parameter, PreparedStatement ps) throws SQLException {
        // replace the question mark in sql with real value
        if (parameter instanceof String) {
            ps.setString(order, (String) parameter);
        } else if (parameter instanceof Integer) {
            ps.setInt(order, ((Integer) parameter).intValue());
        } else if (parameter instanceof Long) {
            ps.setLong(order, ((Long) parameter).longValue());
        } else if (parameter instanceof Boolean) {
            ps.setInt(order, ((Boolean) parameter).booleanValue() ? 1 : 0);
        } else if (parameter instanceof Date) {
            ps.setTimestamp(order, new Timestamp(((Date) parameter).getTime()));
        }
    }

    /**
     * <p>
     * Sets up a <code>PreparedStatement</code> instance using the given sql and parameters.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  The given <em>params</em> can be null if no parameters to set.
     * </p>
     *
     * @param connection the database connection to access database.
     * @param sql the sql query to setup a <code>PreparedStatement</code> instance.
     * @param params the parameter array to fill the <code>PreparedStatement</code> instance.
     *
     * @return a <code>PreparedStatement</code> instance for executing.
     *
     * @throws SQLException if any exception occurs during setting up the <code>PreparedStatement</code> instance.
     */
    private PreparedStatement setUpPreparedStatement(Connection connection, String sql, Object[] params)
        throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);

        // set up all the necessary parameters for executing.
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                setElement(i + 1, params[i], ps);
            }
        }

        return ps;
    }

    /**
     * <p>
     * This function is used to execute a UPDATE sql query into database persistence.
     * </p>
     *
     * @param con the connection instance for database operation.
     * @param sql the UPDATE sql query.
     * @param params the parameters for executing update in database.
     *
     * @return the number of rows affects by the current operation.
     *
     * @throws SQLException when exception occurs during the database operation.
     */
    private int executeUpdate(Connection con, String sql, Object[] params) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = setUpPreparedStatement(con, sql, params);
            return pstmt.executeUpdate();
        } finally {
            // release database resource
            closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * Load list of <code>PaymentTerm</code>s according to the given SQL query and parameters.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *     <li>The given connection may be null, in such case a new connection will be created and finally closed;
     *     Otherwise the passed in non-null connection is used and will not be closed.</li>
     *     <li>The given SQL query is to be a SELECT clause.</li>
     *     <li>The given <em>params</em> may be null if the SQL query contains no conditions.</li>
     *  </ul>
     * </p>
     *
     * @param con The connection used to load <code>PaymentTerm</code>s. May be null.
     * @param query The SQL query used to load <code>PaymentTerm</code>s.
     * @param params The parameters to be set into SQL query. May be null.
     * @param errMsg The message to display if any error occurs.
     *
     * @return The list of <code>PaymentTerm</code>s loaded.
     *
     * @throws PaymentTermDAOException If any error occurs while accessing data store.
     */
    private PaymentTerm[] loadListOfPaymentTerms(Connection con, String query, Object[] params, String errMsg)
        throws PaymentTermDAOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List paymentTerms = new ArrayList();
        try {
            conn = con == null ? this.getConnection(true) : con;
            ps = this.setUpPreparedStatement(conn, query + ORDER_BY_CLAUSE, params);
            rs = ps.executeQuery();
            while (rs.next()) {
                PaymentTerm paymentTerm = new PaymentTerm();
                paymentTerm.setCreationDate(rs.getTimestamp("creation_date"));
                paymentTerm.setCreationUser(rs.getString("creation_user"));
                paymentTerm.setModificationDate(rs.getTimestamp("modification_date"));
                paymentTerm.setModificationUser(rs.getString("modification_user"));
                paymentTerm.setDescription(rs.getString("description"));
                paymentTerm.setActive(rs.getBoolean("active"));
                paymentTerm.setTerm(rs.getInt("term"));
                paymentTerm.setId(rs.getLong("payment_terms_id"));
                //Set changed as false
                paymentTerm.setChanged(false);
                paymentTerms.add(paymentTerm);
            }
            return (PaymentTerm[]) paymentTerms.toArray(new PaymentTerm[paymentTerms.size()]);
        } catch (SQLException e) {
            throw new PaymentTermDAOException(errMsg, e);
        } finally {
            this.closeResultSet(rs);
            this.closeStatement(ps);
            //Connection passed in is null, close the newly created connection
            if (con == null) {
                this.closeConnection(conn);
            }
        }
    }

    /**
     * <p>
     * Validate the given <code>PaymentTerm</code>, and if valid, convert its fields to an array of <code>Object</code>.
     * </p>
     *
     * <p>
     *  <strong>Validation on given <code>PaymentTerm</code>:</strong>
     *  <ul>
     *      <li>If the given <code>PaymentTerm</code> is null;</li>
     *      <li>If the creation user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the modification user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the description of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the creation date of given <code>PaymentTerm</code> is null or exceeds current date;</li>
     *      <li>If the modification date of given <code>PaymentTerm</code> is null or exceeds current date;</li>
     *      <li>If the creation date does not equal modification date when adding</li>
     *      <li>If the modification date does not exceed creation date when updating</li>
     *      <li>If the term of given <code>PaymentTerm</code> is not positive.</li>
     *      <li>If <em>validateId</em> is true, and the id of given <code>PaymentTerm</code> is not positive.</li>
     *  </ul>
     *  <code>IllegalArgumentException</code> will be raised.
     * </p>
     *
     * @param paymentTerm The <code>PaymentTerm</code> to validate and convert.
     * @param usage The usage of the given <code>PaymentTerm</code>.
     * @param validateId Indicates whether to validate id of the given <code>PaymentTerm</code>. This will be true
     *        when given <code>PaymentTerm</code> is to be updated; false when given <code>PaymentTerm</code> is
     *        to be added.
     *
     * @return An array of <code>Object</code> consisted of the fields of the given <code>PaymentTerm</code>.
     *
     * @throws IllegalArgumentException If the given <code>PaymentTerm</code> is invalid.
     */
    private Object[] convertPaymentTerm(PaymentTerm paymentTerm, String usage, boolean validateId) {
        Helper.validatePaymentTerm(paymentTerm, usage, validateId, true);
        Date creationDate = paymentTerm.getCreationDate();
        Date modificationDate = paymentTerm.getModificationDate();
        Date currentDate = new Date();

        //Check modificationDate <= currentDate
        Helper.validateNotExceed(modificationDate, currentDate, null,
                "The modification date of " + usage, "current date");

        //For update, check creationDate < modificationDate
        //For add, check creationDate == modificationDate
        Helper.validateNotExceed(creationDate, modificationDate, new Boolean(!validateId),
                "The creation date of " + usage, "the modification date");

        long id = -1;
        if (validateId) {
            id = paymentTerm.getId();
        }

        Object[] fields = new Object[BEAN_FIELDS_COUNT];
        int index = 0;
        fields[index++] = paymentTerm.getDescription();
        fields[index++] = paymentTerm.getCreationDate();
        fields[index++] = paymentTerm.getCreationUser();
        fields[index++] = paymentTerm.getModificationDate();
        fields[index++] = paymentTerm.getModificationUser();
        fields[index++] = new Boolean(paymentTerm.isActive());
        fields[index++] = new Integer(paymentTerm.getTerm());
        fields[index] = new Long(id);

        return fields;
    }

    /**
     * <p>
     * Add the given <code>PaymentTerm</code> into the data store. The id(which is the primary key) for given
     * <code>PaymentTerm</code> is generated through <code>IDGenerator</code>.
     * </p>
     *
     * <p>
     *  <strong>Validation on given <code>PaymentTerm</code>:</strong>
     *  <ul>
     *      <li>If the given <code>PaymentTerm</code> is null;</li>
     *      <li>If the creation user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the modification user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the description of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the creation date of given <code>PaymentTerm</code> is null or != modification date;</li>
     *      <li>If the modification date of given <code>PaymentTerm</code> is null or &gt; current date;</li>
     *      <li>If the term of given <code>PaymentTerm</code> is not positive.</li>
     *  </ul>
     *  <code>IllegalArgumentException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *   <li>
     *   The previous id of given <code>PaymentTerm</code> is ignored and will be replaced by the id generated by
     *   <code>IDGenerator</code>.
     *   </li>
     *   <li>
     *   If the id returned by <code>IDGenerator</code> is not positive, <code>PaymentTermDAOException</code> will
     *   be raised.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  After adding successfully, the changed status of given <code>PaymentTerm</code> will be set as false.
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to add into data store.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is invalid.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws DuplicatePaymentTermException if given <code>PaymentTerm</code> is already added.
     */
    public void addPaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {
        Object[] fields = this.convertPaymentTerm(paymentTerm, "PaymentTerm to be added", false);
        long id = 0;
        try {
            id = this.idGenerator.getNextID();
            if (!Helper.checkPositive(id)) {
                throw new PaymentTermDAOException("The id '" + id + "' returned by IDGenerator is not positive.");
            }
            paymentTerm.setId(id);
            fields[fields.length - 1] = new Long(id);
        } catch (IDGenerationException e) {
            throw new PaymentTermDAOException("Error occurs while generating next id for PaymentTerm.", e);
        }

        Connection con = null;
        try {
            con = this.getConnection(false);
            if (this.retrievePaymentTermById(con, id) != null) {
                throw new DuplicatePaymentTermException(
                        "There has a PaymentTerm with id '" + id + "' already been added.", paymentTerm);
            }
            this.executeUpdate(con, INSERT_PT, fields);
            con.commit();
            //Set changed as false
            paymentTerm.setChanged(false);
        } catch (SQLException e) {
            this.rollback(con);
            throw new PaymentTermDAOException("Error occurs while adding PaymentTerm.", e);
        } finally {
            this.closeConnection(con);
        }
    }

    /**
     * <p>
     * Update the given <code>PaymentTerm</code> in the data store.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  Validation will be performed on given <code>PaymentTerm</code> first:
     *  <ul>
     *      <li>If the given <code>PaymentTerm</code> is null;</li>
     *      <li>If the creation user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the modification user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the description of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the creation date of given <code>PaymentTerm</code> is null
     *      or &gt;= modification date;</li>
     *      <li>If the modification date of given <code>PaymentTerm</code> is null
     *      or &gt; current date;</li>
     *      <li>If the term of given <code>PaymentTerm</code> is not positive.</li>
     *      <li>If the id of given <code>PaymentTerm</code> is not positive.</li>
     *  </ul>
     *  <code>IllegalArgumentException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  <ul>
     *   <li>
     *   After successfully validating, if the changed status of given <code>PaymentTerm</code> is found to be false,
     *   this method will simply return.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of given <code>PaymentTerm</code> will be set as false.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to update.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is invalid.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if the given <code>PaymentTerm</code> to update was not found
     *         in the data store.
     */
    public void updatePaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {
        Object[] fields = this.convertPaymentTerm(paymentTerm, "PaymentTerm to be updated", true);

        if (!paymentTerm.isChanged()) {
            return;
        }
        Connection con = null;
        try {
            con = this.getConnection(false);
            long id = paymentTerm.getId();
            //It's able to check whether there is a PaymentTerm existed by checking the row count affected.
            //So no need execute a query to check existence first, it's obviously duplicating functionality.
            if (this.executeUpdate(con, UPDATE_PT_BY_ID, fields) == 0) {
                throw new PaymentTermNotFoundException(
                        "There does not exist a PaymentTerm with id '" + id + "'.", id);
            }
            con.commit();
            //Set changed as false
            paymentTerm.setChanged(false);
        } catch (SQLException e) {
            this.rollback(con);
            throw new PaymentTermDAOException("Error occurs while updating PaymentTerm.", e);
        } catch (PaymentTermNotFoundException e) {
            this.rollback(con);
            throw e;
        } finally {
            this.closeConnection(con);
        }
    }

    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  Null is returned if the corresponding <code>PaymentTerm</code> is not found.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>PaymentTerm</code>(if not null) will have the changed status set as false.
     * </p>
     *
     * @param id The id of <code>PaymentTerm</code> to be retrieved.
     *
     * @return the <code>PaymentTerm</code> corresponding to given id. May be null.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm retrievePaymentTerm(long id) throws PaymentTermDAOException {
        Helper.validatePositive(id, "The id of PaymentTerm to retrieve");
        Connection con = null;
        try {
            con = this.getConnection(true);
            return this.retrievePaymentTermById(con, id);
        } finally {
            this.closeConnection(con);
        }
    }

    /**
     * <p>
     * Retrieve a <code>PaymentTerm</code> with given id.
     * </p>
     *
     * @param con Connection.
     * @param id The id of <code>PaymentTerm</code> to retrieve.
     *
     * @return The <code>PaymentTerm</code> corresponding to given id. May be null if does not exist in data store.
     *
     * @throws PaymentTermDAOException If error occurs while retrieving <code>PaymentTerm</code>.
     */
    private PaymentTerm retrievePaymentTermById(Connection con, long id) throws PaymentTermDAOException {
        String errMsg = "Error occurs while retrieving PaymentTerm with id '" + id + "'";

        PaymentTerm[] paymentTerms =
            this.loadListOfPaymentTerms(con, SELECT_PT_BY_ID, new Object[]{new Long(id)}, errMsg);
        if (paymentTerms.length == 0) {
            return null;
        }
        return paymentTerms[0];
    }
    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code>s corresponding to given ids from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *      <li>
     *      If some ids are not found within the data store, they will be simply ignored and this method continues to
     *      retrieve <code>PaymentTerm</code> for next id. So if some ids are not found, the returned array will
     *      have the length less than the length of the passed in array of ids.
     *      </li>
     *      <li>
     *      If the given array contains duplicate ids, the returned array of <code>PaymentTerm</code> will also contains
     *      duplication.
     *      </li>
     *      <li>
     *      If given ids array is empty or no <code>PaymentTerm</code>s are found in the data store, an empty array of
     *      <code>PaymentTerm</code> is returned.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @param ids the array with ids of <code>PaymentTerm</code> to be retrieved. Can be empty.
     *
     * @return the array with <code>PaymentTerm</code> corresponding to given ids. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the given array is null or contains non-positive values.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrievePaymentTerms(long[] ids) throws PaymentTermDAOException {
        if (!Helper.validateIdsArray(ids, "The id of PaymentTerm to retrieve")) {
            return new PaymentTerm[0];
        }
        Connection con = null;
        try {
            con = this.getConnection(true);
            List paymentTerms = new ArrayList();
            for (int i = 0; i < ids.length; i++) {
                PaymentTerm paymentTerm =
                    this.retrievePaymentTermById(con, ids[i]);
                if (paymentTerm != null) {
                    paymentTerms.add(paymentTerm);
                }
            }
            return (PaymentTerm[]) paymentTerms.toArray(new PaymentTerm[paymentTerms.size()]);
        } finally {
            this.closeConnection(con);
        }
    }
    /**
     * <p>
     * Return an array with all the <code>PaymentTerm</code>s within the datastore.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code> exist in data store, an empty array is returned.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @return the array with all the <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveAllPaymentTerms() throws PaymentTermDAOException {
        return this.loadListOfPaymentTerms(null,
                 SELECT_ALL_PT, null, "Error occurs while retrieving all PaymentTerms.");
    }

    /**
     * <p>
     * Retrieve an array with all the active <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no active <code>PaymentTerm</code> is found, an empty array is returned.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @return the array with all the active <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveActivePaymentTerms() throws PaymentTermDAOException {
        return this.loadListOfPaymentTerms(null,
                SELECT_ACTIVE_PT, null, "Error occurs while retrieving active PaymentTerms.");
    }

    /**
     * <p>
     * Get an array with recently created <code>PaymentTerm</code>s with specified recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  The given recent days must be a positive value or equal to -1,
     *  <ul>
     *      <li>
     *      If given recent days is -1, all <code>PaymentTerm</code>s within data store will be returned.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array is returned.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently created PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays)
        throws PaymentTermDAOException {
        return this.retrieveRecentlyPaymentTerms(recentDays, true);
    }

    /**
     * <p>
     * Retrieve recently created or modified <code>PaymentTerm</code>s.
     * </p>
     *
     * @param recentDays The number of recent days.
     * @param created Indicates whether to retrieve recently created or modified <code>PaymentTerm</code>s.
     *
     * @return Recently created or modified <code>PaymentTerm</code>s.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    private PaymentTerm[] retrieveRecentlyPaymentTerms(int recentDays, boolean created) throws PaymentTermDAOException {
        Helper.validateRecentDays(recentDays);
        String errMsg = "Error Occurs while retrieving recently " + (created ? "created" : "modified")
            + "PaymentTerms, recentDays = " + recentDays;
        if (recentDays == -1) {
            return this.loadListOfPaymentTerms(null, SELECT_ALL_PT, null, errMsg);
        } else {
            return this.loadListOfPaymentTerms(null, created ? SELECT_RECENT_CREATED_PT : SELECT_RECENT_MODIFIED_PT,
                     new Object[]{new Date(), this.convertDaysToSeconds(recentDays)}, errMsg);
        }
    }
    /**
     * <p>
     * Convert days to seconds. Each day equals to 24 * 60 * 60 seconds.
     * </p>
     *
     * @param days The number of days to convert.
     *
     * @return The <code>Long</code> value represents the seconds.
     */
    private Long convertDaysToSeconds(int days) {
        long seconds = days;
        seconds *= ONE_DAY;
        return new Long(seconds);
    }

    /**
     * <p>
     * Get an array with recently modified <code>PaymentTerm</code>s with specified recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  The given recent days must be a positive value or equal to -1,
     *  <ul>
     *      <li>
     *      If given recent days is -1, all <code>PaymentTerm</code>s within data store will be returned.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array is returned.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently modified PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays)
        throws PaymentTermDAOException {
        return this.retrieveRecentlyPaymentTerms(recentDays, false);
    }

    /**
     * <p>
     * Delete the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If this id doesn't exist in the persistence, <code>PaymentTermNotFoundException</code> will be raised.
     * </p>
     *
     * @param id the id of <code>PaymentTerm</code> to be deleted.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if there is no <code>PaymentTerm</code> with the given id in the data store.
     */
    public void deletePaymentTerm(long id) throws PaymentTermDAOException {
        this.deletePaymentTerms(new long[]{id});
    }

    /**
     * <p>
     * Delete the <code>PaymentTerm</code>s corresponding to the given ids from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *      <li>
     *      If the given array is empty, nothing happen.
     *      </li>
     *      <li>
     *      If any of the given ids doesn't exist in the persistence, <code>PaymentTermNotFoundException</code>
     *      will be raised.
     *      </li>
     *      <li>
     *      If you delete a <code>PaymentTerm</code> twice, that is, the given array contain duplicate ids,
     *      <code>PaymentTermNotFoundException</code> will be raised.
     *      </li>
     *  </ul>
     * </p>
     *
     * @param ids the array with ids of <code>PaymentTerm</code>s to be deleted. Can be empty.
     *
     * @throws IllegalArgumentException if the given array is null or contains non-positive values.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if there is no <code>PaymentTerm</code> like one of the given ids in the
     *         data store.
     */
    public void deletePaymentTerms(long[] ids) throws PaymentTermDAOException {
        if (!Helper.validateIdsArray(ids, "The id of PaymentTerm to be deleted")) {
            return;
        }
        Connection con = null;
        List deletedIdList = new ArrayList();
        try {
            con = this.getConnection(false);
            //It's able to check whether there is a PaymentTerm existed by checking the row count affected.
            //So no need execute a query to check existence first, it's obviously duplicating functionality.
            for (int i = 0; i < ids.length; i++) {
                Long idLong = new Long(ids[i]);
                if (this.executeUpdate(con, DELETE_PT_BY_ID, new Object[]{idLong}) == 0) {
                    if (deletedIdList.contains(idLong)) {
                        throw new PaymentTermNotFoundException(
                                "Cannot delete twice PaymentTerm with id '" + idLong + "'.", ids[i]);
                    } else {
                        throw new PaymentTermNotFoundException(
                            "There does not exist a PaymentTerm with id '" + idLong + "'.", ids[i]);
                    }
                }
                deletedIdList.add(idLong);
            }

            con.commit();
        } catch (SQLException e) {
            this.rollback(con);
            throw new PaymentTermDAOException("Error occurs while deleting PaymentTerm.", e);
        } catch (PaymentTermNotFoundException e) {
            this.rollback(con);
            throw e;
        } finally {
            this.closeConnection(con);
        }
    }

    /**
     * <p>
     * Delete all the <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public void deleteAllPaymentTerms() throws PaymentTermDAOException {
        Connection con = null;
        try {
            con = this.getConnection(false);
            this.executeUpdate(con, DELETE_ALL_PT, null);
            con.commit();
        } catch (SQLException e) {
            this.rollback(con);
            throw new PaymentTermDAOException("Error occurs while deleting all PaymentTerm.", e);
        } finally {
            this.closeConnection(con);
        }
    }
}
