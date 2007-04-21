/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.Helper;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This is an utility class help <code>InformixAddressDAO</code> and <code>InformixContactDAO</code> to perform db
 * related operations such as get connections, execute statements, release resource. Also it helps to get unique
 * identifier from <code>IDGenerator</code> and audit recode through <code>AuditManager</code>.
 * </p>
 *
 * <p>
 *  <strong>Note:</strong>
 *  This class is restricted to package accessible and is intent to be used only within its own package by this
 *  component.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
class DAOHelper {

    /**
     * <p>
     * The property defined within namespace of  <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code>
     * whose value gives the namespace for <code>DBConnectionFactory</code>. Required.
     * </p>
     */
    private static final String CONNECTION_FACTORY_NAMESPACE = "connection_factory_namespace";

    /**
     * <p>
     * The property defined within namespace of  <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code>
     * whose value gives the name of <code>IDGenerator</code>. Required.
     * </p>
     */
    private static final String IDGENERATOR_NAME = "idgenerator_name";

    /**
     * <p>
     * The property defined within namespace of  <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code>
     * whose value gives the name of <code>SearchBundle</code>. Required.
     * </p>
     */
    private static final String SEARCH_BUNDLE_NAME = "search_bundle_name";

    /**
     * <p>
     * The property defined within namespace of  <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code>
     * whose value gives the namespace of <code>SearchBundleManager</code>. Required.
     * </p>
     */
    private static final String SEARCH_BUNDLE_NAMESPACE = "search_bundle_namespace";

    /**
     * <p>
     * The property defined within namespace of <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code>
     * whose value gives the key of <code>AuditManager</code> with <code>ObjectFactory</code>. Required if no instance
     * of <code>AuditManager</code> is explicitly passed into the constructor of <code>InformixAddressDAO</code> and
     * <code>InformixContaceDAO</code>.
     * </p>
     */
    private static final String AUDIT_MANAGER_KEY = "AuditManager";

    /**
     * <p>
     * The property defined within namespace of  <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code>
     * whose value gives the namespace of <code>ObjectFactory</code> to create <code>AuditManager</code>. Required if
     * no instance of <code>AuditManager</code> is explicitly passed into the constructor of
     * <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code>.
     * </p>
     */
    private static final String AUDIT_MANAGER_NAMESPACE = "audit_manager_namespace";

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private DAOHelper() {
        //does nothing.
    }

    /**
     * <p>
     * Used by <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code> to get
     * the implementation of <code>DBConnectionFactory</code>.
     * </p>
     *
     * <p>
     * The given namespace should contain a property <b>"connection_factory_namespace"</b> which gives the name of
     * <code>IDGenerator</code>.
     * </p>
     *
     * @param namespace The namespace which contains the namespace of <code>DBConnectionFactory</code> as a property
     *        value.
     *
     * @return The implementation of <code>DBConnectionFactory</code>.
     *
     * @throws IllegalArgumentException - If given namespace is null or empty(trimmed).
     * @throws ConfigurationException - If property <b>"connection_factory_namespace"</b> is missing or error occurs
     *         while creating <code>DBConnectionFactory</code> from its value.
     */
    static DBConnectionFactory getConnectionFactory(String namespace) throws ConfigurationException {
        String connectionFactoryNamespace = Helper.getPropertyValue(namespace,
                CONNECTION_FACTORY_NAMESPACE, true);

        try {
            return new DBConnectionFactoryImpl(connectionFactoryNamespace);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new ConfigurationException(
                "Error occurs while creating DBConnectionFactory from namespace: " + connectionFactoryNamespace, e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Default connection name is empty.", e);
        } catch (UnknownConnectionException e) {
            throw new ConfigurationException("Default connection name is unknown.", e);
        }
    }

    /**
     * <p>
     * Used by <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code> to get
     * the implementation of <code>IDGenerator</code>.
     * </p>
     *
     * <p>
     * The given namespace should contain a property <b>"idgenerator_name"</b> which gives the name of
     * <code>IDGenerator</code>.
     * </p>
     *
     * @param namespace The namespace which contains the name of <code>IDGenerator</code>.
     *
     * @return The implementation of <code>IDGenerator</code>.
     *
     * @throws IllegalArgumentException - If given namespace is null or empty(trimmed).
     * @throws ConfigurationException - If property <b>"idgenerator_name"</b> is missing or error occurs while
     *         getting <code>IDGenerator</code> with its value.
     */
    static IDGenerator getIDGenerator(String namespace)
        throws ConfigurationException {
        String idGeneratorName = Helper.getPropertyValue(namespace, IDGENERATOR_NAME,
                true);

        try {
            return IDGeneratorFactory.getIDGenerator(idGeneratorName);
        } catch (com.topcoder.util.idgenerator.IDGenerationException e) {
            throw new ConfigurationException("Error occurs while getting IDGenerator.", e);
        }
    }

    /**
     * <p>
     * Used by <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code> to get
     * the <code>SearchBundle</code>.
     * </p>
     *
     * <p>
     * The given namespace should contain a property <b>"search_bundle_name"</b> which gives the name of
     * <code>SearchBundle</code>, and also should contain a property <b>"search_bundle_namespace"</b> which gives
     * the namespace of <code>SearchBundleManager</code>.
     * </p>
     *
     * @param namespace The namespace which gives the name of <code>SearchBundle</code> and also gives the namespace
     *        of <code>SearchBundleManager</code>.
     *
     * @return The <code>SearchBundle</code> created.
     *
     * @throws IllegalArgumentException - If given namespace is null or empty(trimmed).
     * @throws ConfigurationException - If either property <b>"search_bundle_name"</b> or
     *         <b>"search_bundle_namespace"</b> is missing or error occurs while creating
     *         <code>SearchBundleManager</code>. Or the given search bundle name does not
     *         link to an existing <code>SearchBundle</code>.
     */
    static SearchBundle getSearchBundle(String namespace) throws ConfigurationException {
        String searchBundleName = Helper.getPropertyValue(namespace,
                SEARCH_BUNDLE_NAME, true);
        String searchBundleNamespace = Helper.getPropertyValue(namespace,
                SEARCH_BUNDLE_NAMESPACE, true);

        try {
            SearchBundleManager manager = new SearchBundleManager(searchBundleNamespace);
            SearchBundle bundle = manager.getSearchBundle(searchBundleName);

            if (bundle == null) {
                throw new ConfigurationException(
                    "There is not a SearchBundle with name '" + searchBundleName + "' within namespace '"
                    + searchBundleNamespace + "'.");
            }

            return bundle;
        } catch (SearchBuilderConfigurationException e) {
            throw new ConfigurationException("Error occurs while creating SearchBundleManager.", e);
        }
    }

    /**
     * <p>
     * Used by <code>InformixAddressDAO</code> and <code>InformixContaceDAO</code> to get
     * the implementation of <code>AuditManager</code>.
     * </p>
     *
     * <p>
     * The given namespace should contain a property <b>"audit_manager_namespace"</b> which gives the namespace
     * of <code>ObjectFactory</code>, and also should contain a property <b>"AuditManager"</b> which gives the
     * key of <code>AuditManager</code> with <code>ObjectFactory</code>.
     * </p>
     *
     * @param namespace The namespace which gives the namespace for <code>ObjectFactory</code> and key
     *        in order to create <code>AuditManager</code>.
     *
     * @return The implementation of <code>AuditManager</code> created.
     *
     * @throws IllegalArgumentException - If given namespace is null or empty(trimmed).
     * @throws ConfigurationException - If either property <b>"audit_manager_namespace"</b> or <b>"AuditManager"</b>
     *         is missing or error occurs while creating <code>AuditManager</code> through <code>ObjectFactory</code>.
     */
    static AuditManager getAuditManager(String namespace) throws ConfigurationException {
        String auditManagerNamespace = Helper.getPropertyValue(namespace, AUDIT_MANAGER_NAMESPACE, true);
        String auditManagerKey = Helper.getPropertyValue(namespace, AUDIT_MANAGER_KEY, true);

        try {
            ObjectFactory of = Helper.getObjectFactory(auditManagerNamespace);

            return (AuditManager) of.createObject(auditManagerKey);
        } catch (InvalidClassSpecificationException icse) {
            throw new ConfigurationException(
                "Error occurs while create AuditManager: " + icse.getMessage(), icse);
        } catch (ClassCastException cce) {
            throw new ConfigurationException(
                "Error occurs while cast object to AuditManager: " + cce.getMessage(), cce);
        }
    }

    /**
     * <p>
     * Generates a connection from connection factory and connection name. The isolation level and
     * transaction atomic will be managed by container. In other words, the configuration should
     * use <code>JNDIConnectionProducer</code> to look up data source through JNDI and
     * <code>JDBCConnectionProducer</code> is not desired to be used.
     * </p>
     *
     * <p>
     * If the given connection name is null or empty(trimmed), the default connection defined in
     * <code>DBConnectionFactory</code> will be created.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given connection factory
     * is not null when calling this method.
     * </p>
     *
     * @param connectionFactory The <code>DBConnectionFactory</code> used to create connection.
     * @param connectionName The name of connection.
     * @param operation Represents what the connection is used for.
     *
     * @return An open connection from the connection factory.
     *
     * @throws PersistenceException - If fail to create a new connection instance.
     */
    static Connection createConnection(DBConnectionFactory connectionFactory, String connectionName,
        String operation) throws PersistenceException {
        try {
            if (connectionName == null || connectionName.trim().length() == 0) {
                return connectionFactory.createConnection();
            }
            return connectionFactory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            throw new PersistenceException(
                "Unable to create the database connection while " + operation, e);
        }
    }

    /**
     * <p>Closes the given <code>ResultSet</code> instance, <code>SQLException</code> will be ignored.</p>
     * <p>Closes the given <code>Statement</code> instance, <code>SQLException</code> will be ignored.</p>
     * <p>Closes the given <code>Connection</code> instance, <code>SQLException</code> will be ignored.</p>
     *
     * @param rs The given <code>ResultSet</code> instance to close.
     * @param statement The given <code>Statement</code> instance to close.
     * @param con The given <code>Connection</code> instance to close.
     */
    static void realeaseJDBCResource(ResultSet rs, Statement statement, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
        try {
            if (con != null) {
                con.close();
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
     * @param order The sequence number of question mark in sql expression
     * @param parameter The actual value to replace the corresponding question mark.
     * @param ps <code>PreparedStatement</code> instance to execute the sql expression
     *
     * @throws SQLException when exception occurs during the database operation
     */
    private static void setElement(int order, Object parameter, PreparedStatement ps) throws SQLException {
        // replace the question mark in sql with real value
        if (parameter == null) {
            //Special handling for Address#line2 which is null-able
            ps.setNull(order, Types.VARCHAR);
        } else if (parameter instanceof String) {
            ps.setString(order, (String) parameter);
        } else if (parameter instanceof Integer) {
            ps.setInt(order, ((Integer) parameter).intValue());
        } else if (parameter instanceof Long) {
            ps.setLong(order, ((Long) parameter).longValue());
        } else if (parameter instanceof Date) {
            ps.setTimestamp(order, new Timestamp(((Date) parameter).getTime()));
        }
    }

    /**
     * <p>
     * Sets up a <code>PreparedStatement</code> using the given parameters.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  The given <em>params</em> can be null if no parameters to set.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given prepared statement
     * is not null when calling this method.
     * </p>
     *
     * @param ps The <code>PreparedStatement</code> to setup parameters.
     * @param params The parameter array to fill the <code>PreparedStatement</code> instance.
     * @param addBatch Indicate whether add the <code>PreparedStatement</code> to batch after setting parameters.
     * @param operation Represents what the prepared statement is used for.
     *
     * @throws PersistenceException - If any exception occurs during setting up the <code>PreparedStatement</code>.
     */
    static void setUpPreparedStatement(PreparedStatement ps, Object[] params, boolean addBatch, String operation)
        throws PersistenceException {
        try {
            // set up all the necessary parameters for executing.
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    setElement(i + 1, params[i], ps);
                }
            }
            if (addBatch) {
                ps.addBatch();
            }
        } catch (SQLException e) {
            throw new PersistenceException("Unable to set up PreparedStatement while " + operation, e);
        }
    }

    /**
     * <p>
     * Prepare a statement with given <code>Connection</code> and sql query.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given connection is not null and the
     * given sql query is a valid sql clause when calling this method.
     * </p>
     *
     * @param con The <code>Connection</code> used to prepare statement.
     * @param sql The SQL query used to prepare statement.
     * @param operation Represents what the prepared statement is used for.
     *
     * @return The <code>PreparedStatement</code> created with given connection and sql query.
     *
     * @throws PersistenceException - If error occurs while preparing statement.
     */
    static PreparedStatement prepareStatement(Connection con, String sql, String operation)
        throws PersistenceException {
        try {
            return con.prepareStatement(sql);
        } catch (SQLException e) {
            throw new PersistenceException("Unable to prepare statement while " + operation, e);
        }
    }

    /**
     * <p>
     * Execute a <code>PreparedStatement</code> to perform query operation and return the <code>ResultSet</code>.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given prepared statement is not null
     * when calling this method.
     * </p>
     *
     * @param ps The <code>PreparedStatement</code> to execute query.
     * @param operation Represents what the query is used for.
     *
     * @return The <code>ResultSet</code> queried from given <code>PreparedStatement</code>.
     *
     * @throws PersistenceException - If error occurs while executing query.
     */
    static ResultSet executeQuery(PreparedStatement ps, String operation) throws PersistenceException {
        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new PersistenceException("Unable to execute query while " + operation, e);
        }
    }
    /**
     * <p>
     * Execute a <code>PreparedStatement</code> to perform update operation and return the affected row count.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given prepared statement is not null
     * when calling this method.
     * </p>
     *
     * @param ps The <code>PreparedStatement</code> to be executed.
     * @param operation Represents what the execution is for.
     *
     * @return The affected row count for INSERT, UPDATE, or DELETE statements.
     *
     * @throws PersistenceException - If error occurs while executing update.
     */
    static int executeUpdate(PreparedStatement ps, String operation) throws PersistenceException {
        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Unable to execute update while " + operation, e);
        }
    }

    /**
     * <p>
     * Execute a <code>Statement</code> to perform batch operation.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given prepared statement is not null
     * when calling this method.
     * </p>
     *
     * @param ps The <code>Statement</code> to be executed.
     * @param operation Represents what the execution is for.
     *
     * @return An array of update counts containing one element for each command in the batch.
     *         The elements of the array are ordered according to the order in which commands were added to the batch.
     *
     * @throws BatchOperationException - If one of the batch commands sent to the database fails to execute properly.
     * @throws PersistenceException - If a database access error occurs.
     */
    static int[] executeBatch(Statement ps, String operation) throws PersistenceException {
        try {
            return ps.executeBatch();
        } catch (SQLException e) {
            if (e instanceof BatchUpdateException) {
                int[] counts = ((BatchUpdateException) e).getUpdateCounts();
                boolean[] results = new boolean[counts.length];
                for (int i = 0; i < counts.length; i++) {
                    results[i] = counts[i] != Statement.EXECUTE_FAILED;
                }
                throw new BatchOperationException("Unable to execute batch while " + operation, e, results);
            }
            throw new PersistenceException("Unable to execute batch while " + operation, e);
        }
    }

    /**
     * <p>
     * Get an unique identifier from <code>IDGenerator</code>.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given <code>IDGenerator</code> is not null
     * when calling this method.
     * </p>
     *
     * @param idGenerator The <code>IDGenerator</code> to get next id.
     *
     * @param operation Represents what the id is used for.
     *
     * @return The unique identifier got from <code>IDGenerator</code>.
     *
     * @throws IDGenerationException - If error occurs while getting id or the id got is non-positive.
     */
    static long getNextId(IDGenerator idGenerator, String operation) throws IDGenerationException {
        try {
            long id = idGenerator.getNextID();
            if (id <= 0) {
                throw new IDGenerationException(
                    "Got a non-positive id: " + id + " from IDGenerator while " + operation);
            }
            return id;
        } catch (com.topcoder.util.idgenerator.IDGenerationException e) {
            throw new IDGenerationException("Unable to get a positive id from IDGenerator while " + operation, e);
        }
    }

    /**
     * <p>
     * Audit given <code>AuditHeader</code> using given <code>AuditManager</code>.
     * </p>
     *
     * <p>
     * Since this method is restricted to package scope and is not intent to be used as a public API,
     * so no need to validate the passed in arguments. But please ensure the given <code>AuditManager</code> and
     * <code>AuditHeader</code> are not null when calling this method.
     * </p>
     *
     * @param manager The <code>AuditManager</code> used to audit.
     * @param header The <code>AuditHeader</code> to be audited.
     * @param operation Represents what the audit is used for.
     *
     * @throws AuditException - If error occurs while auditing.
     */
    static void audit(AuditManager manager, AuditHeader header, String operation) throws AuditException {
        try {
            manager.createAuditRecord(header);
        } catch (AuditManagerException e) {
            throw new AuditException("Unable to audit while " + operation, e);
        } catch (IllegalArgumentException e) {
            throw new AuditException("Unable to audit while " + operation, e);
        }
    }

    /**
     * <p>
     * Create an instance of <code>AuditHeader</code> with given parameters.
     * </p>
     *
     * @param actionType action type
     * @param creationUser creation user
     * @param tableName table name
     * @param entityId Represents the ID of the entity this audit is related to
     *
     * @return <code>AuditHeader</code> created
     */
    static AuditHeader getAuditHeader(int actionType, String creationUser, String tableName, long entityId) {
        AuditHeader auditHeader = new AuditHeader();
        auditHeader.setActionType(actionType);
        auditHeader.setCreationDate(new Timestamp(new Date().getTime()));
        auditHeader.setCreationUser(creationUser);
        auditHeader.setTableName(tableName);
        auditHeader.setEntityId(entityId);
        return auditHeader;
    }

    /**
     * <p>
     * Based on the given type, set the <code>ApplicationArea</code> and related id into given <code>AuditHeader</code>.
     * </p>
     *
     * @param header The <code>AuditHeader</code> to set the <code>ApplicationArea</code> and related id
     * @param type The <code>ContactType</code> or <code>AddressType</code>
     * @param id The related id
     */
    static void setApplicationAreaAndRelatedId(AuditHeader header, Enum type, long id) {
        if (type.getOrdinal() == 0) {
            header.setApplicationArea(ApplicationArea.TT_PROJECT);
            header.setProjectId(id);
        } else if (type.getOrdinal() == 1) {
            header.setApplicationArea(ApplicationArea.TT_CLIENT);
            header.setClientId(id);
        } else if (type.getOrdinal() == 2) {
            header.setApplicationArea(ApplicationArea.TT_COMPANY);
            header.setCompanyId(id);
        } else if (type.getOrdinal() == AddressType.USER.getOrdinal()) {
            header.setResourceId(id);
            header.setApplicationArea(ApplicationArea.TT_USER);
        }
    }

    /**
     * <p>
     * Create an instance of <code>AuditDetail</code> with given parameters.
     * </p>
     *
     * @param columnName Column name
     * @param oldValue old value
     * @param newValue new value
     *
     * @return <code>AuditDetail</code> created
     */
    static AuditDetail getAuditDetail(String columnName, String oldValue, String newValue) {
        AuditDetail detail = new AuditDetail();
        detail.setColumnName(columnName);
        detail.setOldValue(oldValue);
        detail.setNewValue(newValue);
        return detail;
    }

    /**
     * <p>
     * Convert given ids array to a string appended to the end of given <code>StringBuffer</code>.
     * </p>
     *
     * @param sb the <code>StringBuffer</code> contains the string converted from ids
     * @param ids the array of ids
     * @param usage the usage of the id
     *
     * @return A string converted from given <code>StringBuffer</code> and ids.
     */
    static String convertIds(StringBuffer sb, long[] ids, String usage) {
        sb.append("(");
        for (int i = 0; i < ids.length; i++) {
            sb.append(ids[i] + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }
}
