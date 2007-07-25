/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.rates.ConfigHelper;
import com.topcoder.timetracker.rates.ParameterCheck;
import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RateConfigurationException;
import com.topcoder.timetracker.rates.RatePersistence;
import com.topcoder.timetracker.rates.RatePersistenceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>The InformixRatePersistence class is the default persistence plugin for this component - it stores the rate
 * information inside Informix tables. Implementing the RatePersistence interface, it provides the basic CRUD
 * operations. In addition, two private utility methods have been provided, to help load rates from a database row, as
 * well as to auditing any insert/delete/update action. All internal members are set on construction and immutable
 * thereafter - the log and connection names are optional parameters read from configuration, and the connection
 * factory, log and audit managers are initialized using default constructors. As all internal members are immutable
 * and thread safe, this persistence implementation is also thread-safe (providing the database connection is secure)</p>
 *  <p></p>
 *  <P><b>Note</b>When we need to rollback the Audit actions we need todo it at the level of the transaction
 * itself. We do not utilize here the auditManager's ability to rollback the audit steps. This means that we do not
 * call rollback or delete for the AuditManager anymore. This is because the transaction control is done one level
 * higher than this class in a stateless session ejb which will be a delegate between the manager and persistence. Any
 * exceptions thrown from here will result in a rollback operation (either for audit or for straight persistence of
 * rates)</p>
 *
 * @author AleaActaEst, sql_lall, TCSDEVELOPER
 * @version 3.2
 */
public class InformixRatePersistence implements RatePersistence {

    /** Name of the table this class modifies. */
    private static final String AUDIT_TABLE_NAME = "comp_rate";

    /** SQL constant used to insert a single row into the table. */
    private static final String SQL_INSERT_RATE = "INSERT INTO comp_rate (company_id, rate_id, rate, creation_date, " +
        "creation_user, modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /** SQL constant used to update a single row in the table, identified by the current ID/ID pair. */
    private static final String SQL_UPDATE_RATE = "UPDATE comp_rate SET creation_date = ?, creation_user = ?, " +
        "rate = ?, modification_date = ?, modification_user = ? WHERE company_id = ? AND rate_id = ? ";

    /** SQL constant used to remove a rate, identified by the ID/ID pair. */
    private static final String SQL_DELETE_RATE = "DELETE FROM comp_rate WHERE company_id = ? AND rate_id = ? ";

    /** SQL constant used to obtain all rates that belong to a given company id. */
    private static final String SQL_SELECT_RATES = "SELECT CR.rate_id, CR.rate, CR.creation_date, CR.creation_user, " +
        "CR.modification_date, CR.modification_user, R.description FROM comp_rate as CR, rate as R " +
        "WHERE R.rate_id = CR.rate_id AND CR.company_id = ?";

    /** SQL constant used to obtain the first rate that belongs to a given company id, identified by its description. */
    private static final String SQL_SELECT_BY_DESC = "SELECT CR.rate_id, CR.rate, CR.creation_date, CR.creation_user," +
        " CR.modification_date, CR.modification_user, R.description FROM comp_rate as CR, rate as R " +
        "WHERE R.rate_id = CR.rate_id AND CR.company_id = ? AND R.description = ?";

    /** SQL constant used to obtain the rate that belongs to a given company id, identified by its rate id. */
    private static final String SQL_SELECT_BY_ID = "SELECT CR.rate_id, CR.rate, CR.creation_date, CR.creation_user, " +
        "CR.modification_date, CR.modification_user, R.description FROM comp_rate as CR, rate as R " +
        "WHERE R.rate_id = CR.rate_id AND CR.company_id = ? AND CR.rate_id = ?";

    /**
     * Represents the columns name for table "comp_rate" in database. This constant is used to parse the Rate
     * into array of String representation of its field.
     */
    private static final String[] COMPANY_RATE_COLUMNS = {
            "company_id", "rate_id", "rate", "creation_date", "creation_user", "modification_date", "modification_user"
        };

    /**
     * This audit manager gives the persistence layer the ability to audit each insert/update/delete action
     * that takes place. For this object, it is used within auditAction method, which is called from the
     * add/update/deleteRates methods whenever the audit flag is set to true. It is initialized on construction
     * through using the object factory, and immutable thereafter. The manager instance itself is a service locator -
     * for each of its methods, it looks up a local stateless EJB controller and invokes the corresponding bean's
     * method. For more details on how the auditing manager communicates, see the Time Tracker Audit component.
     */
    private final AuditManager auditManager;

    /**
     * Factory used to obtain the database connection from - this is initialized on construction using the
     * no-arg constructor. It is then immutable, and used with the connectionName during the CRUD operations whenever
     * a connection is required. This instance can never be null - if there are problems constructing it, a
     * RateConfigurationException is to be thrown.
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * The log instance used to log information when persistence fails - This is initialized at construction -
     * a log name is read in, and the log factory component is used to build a log instance which is then stored for
     * later use. After construction, this will be non-null, and immutable.
     */
    private final Log log;

    /**
     * The name of the log instance, used to retrieve a connection from the connectionFactory member. This is
     * read in from construction, and optional - if not provided, this member is null and the default connection is
     * retrieved. After construction, this is immutable.
     */
    private final String connectionName;

    /**
     * Constructs a new Informix Rate Persistence implementation, taking values from configuration. This reads in the
     * (optional) log and connection names, setting them to null if nothing is configured. In addition, the connection
     * factory and audit manager are initialized for later use. If there are any troubles setting up the members, a
     * RateConfigurationException is to be thrown.
     *
     *
     * @param namespace
     *            location that configuration parameters are read from.
     * @throws RateConfigurationException
     *             If there are any problems initializing the class and its members.
     * @throws IllegalArgumentException
     *             if namespace is null or empty
     */
    public InformixRatePersistence(String namespace) throws RateConfigurationException {
        ParameterCheck.checkNullEmpty("namespace", namespace);

        // initializes the logger
        String logName = ConfigHelper.getStringProperty(namespace, "logName", false);
        String useLog = ConfigHelper.getStringProperty(namespace, "useLog", false);

        if (useLog != null && "true".equals(useLog)) {
            this.log = LogManager.getLog(logName);
        } else {
            this.log = null;
        }

        // connection name
        this.connectionName = ConfigHelper.getStringProperty(namespace, "connectionName", false);

        // creates Object Factory according to config
        String objFactoryNs = ConfigHelper.getStringProperty(namespace, "objectFactoryNamespace", true);
        ObjectFactory factory;

        try {
            factory = new ObjectFactory(new ConfigManagerSpecificationFactory(objFactoryNs));
        } catch (SpecificationConfigurationException e) {
            throw new RateConfigurationException("failed to create ObjectFactory", e);
        } catch (IllegalReferenceException e) {
            throw new RateConfigurationException("failed to create ObjectFactory", e);
        }

        // creates DBConnectionFactory via ObjectFactory
        String connFactoryKey = ConfigHelper.getStringProperty(namespace, "connectionFactoryKey", true);

        try {
            this.connectionFactory = (DBConnectionFactory) factory.createObject(connFactoryKey);
        } catch (InvalidClassSpecificationException e) {
            throw new RateConfigurationException("failed to create DBConnectionFactory with key:" + connFactoryKey, e);
        } catch (ClassCastException e) {
            throw new RateConfigurationException("the created instance is not type of DBConnectionFactory, key:" +
                connFactoryKey, e);
        }

        // creates AuditManager via ObjectFactory
        String auditManagerKey = ConfigHelper.getStringProperty(namespace, "auditManagerKey", true);

        try {
            this.auditManager = (AuditManager) factory.createObject(auditManagerKey);
        } catch (InvalidClassSpecificationException e) {
            throw new RateConfigurationException("failed to create AuditManager with key:" + auditManagerKey, e);
        } catch (ClassCastException e) {
            throw new RateConfigurationException("the created instance is not type of AuditManager, key:" +
                auditManagerKey, e);
        }
    }

    /**
     * Adds a collection of rates into persistence. In addition, this method may optionally add audit records
     * for each persistence action taken. This tries to add as many rates as possible - an exception is only thrown if
     * no rates can be added. Otherwise, every rate that can be is added using the SQL_INSERT_RATE statement, and the
     * insert is optionally audited. The rates that couldn't be added are skipped, not audited, and their information
     * is logged at ERROR.
     *
     * @param rates The array of Rates to add - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException any error occurs in persistence
     * @throws IllegalArgumentException if the array of rates is null, or contains null values, or is empty
     */
    public void addRates(Rate[] rates, boolean audit) throws RatePersistenceException {
        ParameterCheck.checkArray("rates", rates);

        Connection conn = null;
        PreparedStatement pstmt = null;

        int i = 0;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL_INSERT_RATE);
            final Date now = new Date(System.currentTimeMillis());

            for (; i < rates.length; ++i) {
                Company comp = rates[i].getCompany();

                //if company is null, skip this record
                if (comp == null) {
                    String msg = "failed to persist rate[" + i + "] id:" + rates[i].getId() +
                        " msg: company is not set";
                    logErr(msg);
                    throw new RatePersistenceException(msg);
                }

                rates[i].setCreationDate(now);
                rates[i].setModificationUser(rates[i].getCreationUser());
                rates[i].setModificationDate(now);

                pstmt.setLong(1, comp.getId());
                pstmt.setLong(2, rates[i].getId());
                pstmt.setDouble(3, rates[i].getRate());
                pstmt.setDate(4, now);
                pstmt.setString(5, rates[i].getCreationUser());
                pstmt.setDate(6, now);
                pstmt.setString(7, rates[i].getModificationUser());
                pstmt.executeUpdate();

                //audit if successfully persist
                auditAction(null, rates[i], audit);
                rates[i].setChanged(false);
            }
        } catch (SQLException e) {
            String msg = "failed to persist rate[" + i + "] id: " + rates[i].getId() + " compId: " +
                rates[i].getCompany();
            logErr(msg + " msg: " + e.getMessage());
            throw new RatePersistenceException(msg, e);
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * Removes a collection of rates from persistence, identified by the passed rate Ids. In addition, this
     * method may optionally add audit records for each persistence action taken. This tries to remove as many rates
     * as possible - an exception is only thrown if no rates can be deleted. Otherwise, every rate that can be is
     * removed using the SQL_DELETE_RATE statement, and the removal is optionally audited.
     *
     * @param rates The array of Rates to removed - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException any error occurs in persistence
     * @throws IllegalArgumentException if the array of rates is null, or contains null values or is empty
     */
    public void deleteRates(Rate[] rates, boolean audit)
        throws RatePersistenceException {
        ParameterCheck.checkArray("rates", rates);

        Connection conn = null;
        PreparedStatement pstmt = null;
        int i = 0;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL_DELETE_RATE);

            for (; i < rates.length; i++) {
                Company comp = rates[i].getCompany();

                //if company is null, skip this record
                if (comp == null) {
                    String msg = "failed to delete rate[" + i + "] id:" + rates[i].getId() +
                        " msg: company is not set";
                    logErr(msg);
                    throw new RatePersistenceException(msg);
                }

                pstmt.setLong(1, comp.getId());
                pstmt.setLong(2, rates[i].getId());

                int result = pstmt.executeUpdate();

                if (result == 1) {
                    //audit if successfully persist
                    auditAction(rates[i], null, audit);
                } else {
                    logErr("failed to delete rate[" + i + "] id:" + rates[i].getId() + " compId:" + comp.getId() +
                        " msg: record not exists");
                }
            }
        } catch (SQLException e) {
            String msg = "failed to delete rate[" + i + "] id: " + rates[i].getId() + " compId: " +
                rates[i].getCompany().getId();

            logErr(msg + " msg: " + e.getMessage());
            throw new RatePersistenceException(msg, e);
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * Given the id of the rate type, as well as the id of the company that uses the rate, this returns the
     * rate instance matching the given parameters. If no match is found, null is returned, and no exception is
     * thrown. If there are problems communicating with the database, it is logged at ERROR and a
     * RatePersistenceException is to be thrown.
     *
     * @param rateId The ID of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id pair, or null if no match is found.
     *
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(long rateId, long companyId)
        throws RatePersistenceException {
        Connection conn = null;

        try {
            conn = getConnection();

            return getRate(conn, rateId, companyId);
        } catch (SQLException e) {
            String msg = "failed to retrieve rate id: " + rateId + " companyId: " + companyId;
            logErr(msg + " msg: " + e.getMessage());
            throw new RatePersistenceException(msg, e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Given the description of the rate type, as well as the id of the company that uses the rate, this
     * returns the rate instance matching the given parameters. If no match is found, null is returned, and no
     * exception is thrown. If there are problems communicating with the database, it is logged at ERROR and a
     * RatePersistenceException is to be thrown.
     *
     * @param description The description of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id/description pair, or null if no match is found.
     *
     * @throws IllegalArgumentException If the description is null.
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(String description, long companyId)
        throws RatePersistenceException {
        ParameterCheck.checkNull("description", description);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_DESC);

            pstmt.setLong(1, companyId);
            pstmt.setString(2, description);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return parseRate(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            String msg = "failed to retrieve rate, companyId: " + companyId + " desc: " + description;
            logErr(msg + " msg: " + e.getMessage());
            throw new RatePersistenceException(msg, e);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * Retrieves all rates used by a company from persistence. They are returned as an array of (non-null) Rate
     * instances, this array can be empty but will not be null.If there are problems reading the information, it is
     * logged at ERROR and a RatePersistenceException is thrown.
     *
     * @param companyId The Id of the company using the rates.
     *
     * @return An array of Rates used by the given company.
     *
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate[] retrieveRates(long companyId) throws RatePersistenceException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL_SELECT_RATES);

            pstmt.setLong(1, companyId);

            rs = pstmt.executeQuery();
            List list = new ArrayList();

            while (rs.next()) {
                list.add(parseRate(rs));
            }

            return (Rate[]) list.toArray(new Rate[list.size()]);
        } catch (SQLException e) {
            String msg = "failed to retrieve rate, companyId: " + companyId;
            logErr(msg + " msg: " + e.getMessage());
            throw new RatePersistenceException(msg, e);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * Updates a collection of rates within persistence, identified by the passed rate/company id pairs. In
     * addition, this method may optionally add audit records for each persistence action taken. This tries to update
     * as many rates as possible - an exception is only thrown if no rates can be updated. Otherwise, every rate that
     * can be is updated using the SQL_UPDATE_RATE statement, and the update is optionally audited. The rates that
     * couldn't be updated are skipped, not audited, and their information is logged at ERROR.<p></p>
     *
     * @param rates The array of Rates to update - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException any error occurs in persistence
     * @throws IllegalArgumentException if the array of rates is null, or contains null values, or it's empty
     */
    public void updateRates(Rate[] rates, boolean audit)
        throws RatePersistenceException {
        ParameterCheck.checkArray("rates", rates);

        Connection conn = null;
        PreparedStatement pstmt = null;
        int i = 0;

        try {
            conn = getConnection();

            Rate[] oldRates = getOldRates(conn, rates); //retrieves the old rates, for audit purpose

            pstmt = conn.prepareStatement(SQL_UPDATE_RATE);

            for (; i < rates.length; i++) {
                Company comp = rates[i].getCompany();

                //if company is null, skip this record
                if (comp == null) {
                    String msg = "failed to update rate[" + i + "] id:" + rates[i].getId() +
                        " msg: company is not set";
                    logErr(msg);
                    throw new RatePersistenceException(msg);
                }

                pstmt.setTimestamp(1, new Timestamp(rates[i].getCreationDate().getTime()));
                pstmt.setString(2, rates[i].getCreationUser());
                pstmt.setDouble(3, rates[i].getRate());
                pstmt.setTimestamp(4, new Timestamp(rates[i].getModificationDate().getTime()));
                pstmt.setString(5, rates[i].getModificationUser());
                pstmt.setLong(6, comp.getId());
                pstmt.setLong(7, rates[i].getId());

                int result = pstmt.executeUpdate();

                if (result == 1) {
                    //audit if successfully persist
                    auditAction(oldRates[i], rates[i], audit);
                    rates[i].setChanged(false);
                } else {
                    logErr("failed to update rate[" + i + "] id: " + rates[i].getId() + " compId: " + comp.getId() +
                        " msg: record does not exist");
                }
            }
        } catch (SQLException e) {
            String msg = "failed to update rate[" + i + "] id: " + rates[i].getId() + " compId: " +
                rates[i].getCompany().getId();

            logErr(msg + " msg: " + e.getMessage());
            throw new RatePersistenceException(msg, e);
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * Close the connection if it's not null.
     *
     * @param conn Connection to close
     */
    private static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            //does nothing
        }
    }

    /**
     * Close the statement if it's not null.
     *
     * @param pstmt Statement to close.
     */
    private static void closeStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //does nothing
            }
        }
    }

    /**
     * Close the result set if it's not null.
     *
     * @param rs Result set to close.
     */
    private static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                //does nothing
            }
        }
    }

    /**
     * Creates an audit record for an action that has just taken place.
     *
     * @param oldValue The previous rate information being changed
     * @param newValue The new rate information being persisted
     * @param audit boolean flag indicating that whether the audit should be performed
     *
     * @throws RatePersistenceException if there are problems persisting the audit
     * @throws RatePersistenceException if failed to create audit
     */
    private void auditAction(Rate oldValue, Rate newValue, boolean audit)
        throws RatePersistenceException {
        if (audit) {
            AuditHeader header = new AuditHeader();

            //operation type
            int type;

            if (oldValue == null) {
                type = AuditType.INSERT;
            } else if (newValue == null) {
                type = AuditType.DELETE;
            } else {
                type = AuditType.UPDATE;
            }

            //columns changed detail
            String[] oldColumns = parseValues(oldValue);
            String[] newColumns = parseValues(newValue);
            List details = new ArrayList();

            for (int i = 0; i < oldColumns.length; i++) {
                //when in update type, the non-changed columns will not be audited
                if (type == AuditType.UPDATE && oldColumns[i].equals(newColumns[i])) {
                    continue;
                }

                AuditDetail detail = new AuditDetail();
                detail.setColumnName(COMPANY_RATE_COLUMNS[i]);
                detail.setOldValue(oldColumns[i]);
                detail.setNewValue(newColumns[i]);
                details.add(detail);
            }

            header.setDetails((AuditDetail[]) details.toArray(new AuditDetail[] {  }));

            //the entity used to create header
            Rate entity = (newValue == null) ? oldValue : newValue;

            header.setActionType(type);
            header.setApplicationArea(ApplicationArea.TT_CONFIGURATION);
            header.setEntityId((int) entity.getId());
            header.setTableName(AUDIT_TABLE_NAME);
            header.setCompanyId((int) entity.getCompany().getId());
            header.setCreationDate(new Timestamp(System.currentTimeMillis()));

            if (type == AuditType.INSERT) {
                header.setCreationUser(entity.getCreationUser());
            } else {
                header.setCreationUser(entity.getModificationUser());
            }

            try {
                auditManager.createAuditRecord(header);
            } catch (AuditManagerException e) {
                String msg = "error occurs while auditing";
                logErr(msg);
                throw new RatePersistenceException(msg, e);
            }
        }
    }

    /**
     * Get connection with connection name, if it's null, use th default connection.
     *
     * @return Connection crated by DBConnectionFactory
     *
     * @throws RatePersistenceException if failed to get connection
     */
    private Connection getConnection() throws RatePersistenceException {
        try {
            return (connectionName == null) ? connectionFactory.createConnection()
                                            : connectionFactory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            String msg = "failed to get connection";
            logErr(msg + " msg: " + e.getMessage());
            throw new RatePersistenceException(msg, e);
        }
    }

    /**
     * Retrieves the old value of Rate from persistence. It's used to collect the audit info for update method.
     *
     * @param conn connection
     * @param rates the rates according to which its old values will be retrieved from persistence
     *
     * @return array of Rate representing the current values
     */
    private Rate[] getOldRates(Connection conn, Rate[] rates) {
        Rate[] result = new Rate[rates.length];

        for (int i = 0; i < result.length; i++) {
            Company comp = rates[i].getCompany();

            //skips the rate with company empty
            if (comp == null) {
                continue;
            }

            try {
                result[i] = getRate(conn, rates[i].getId(), comp.getId());
            } catch (SQLException e) {
                //just ignore if failed to get this rate
            }

            if (result[i] != null) {
                result[i].setCompany(comp); //sets the company to the old rate
            }
        }

        return result;
    }

    /**
     * Get a Rate from the persistence given with its rateId and companyId.
     *
     * @param connection connection
     * @param rateId rate id
     * @param companyId company id
     *
     * @return the Rate retrieved from persistence
     * @throws SQLException if any error occurs in persistence
     */
    private Rate getRate(Connection connection, long rateId, long companyId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, companyId);
            pstmt.setLong(2, rateId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return parseRate(rs);
            } else {
                return null;
            }
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
        }
    }

    /**
     * Logs the given message to logger with Error level.
     *
     * @param msg the message to be logged
     */
    private void logErr(String msg) {
        if (log != null) {
            log.log(Level.ERROR, msg);
        }
    }

    /**
     * Utility method that takes a ResultSet row from a database, and converts it into a rate.
     *
     * @param res The result set cursor at the rate row to be parsed, not null.
     *
     * @return Returns a Rate representation of the value the result set currently points to.
     *
     * @throws SQLException if any persistence error occurs
     */
    private Rate parseRate(ResultSet res) throws SQLException {
        Rate rate = new Rate();
        rate.setId(res.getLong(1));
        rate.setRate(res.getDouble(2));
        rate.setCreationDate(res.getTimestamp(3));
        rate.setCreationUser(res.getString(4));
        rate.setModificationDate(res.getTimestamp(5));
        rate.setModificationUser(res.getString(6));
        rate.setDescription(res.getString(7));
        rate.setChanged(false);

        return rate;
    }

    /**
     * Extracts the bean into string array based on the database's column structure.
     *
     * @param bean the bean to parsed
     *
     * @return the array stored the bean fields
     */
    private String[] parseValues(Rate bean) {
        if (bean == null) {
            return new String[COMPANY_RATE_COLUMNS.length];
        } else {
            return new String[] {
                String.valueOf(bean.getCompany().getId()), String.valueOf(bean.getId()), String.valueOf(bean.getRate()),
                bean.getCreationDate().toString(), bean.getCreationUser(), bean.getModificationDate().toString(),
                bean.getModificationUser()
            };
        }
    }
}
