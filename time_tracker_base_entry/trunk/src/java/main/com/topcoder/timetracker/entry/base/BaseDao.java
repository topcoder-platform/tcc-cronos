/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.audit.AuditManager;

import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.sql.Connection;


/**
 * <p>This is an abstraction of a dao, which implements such common operations as getting and setting a connection
 * as well as id generation. This way other database implementations can be quickly implemented.</p>
 *  <p>Thread Safety: It is assumed that implementation will be thread-safe.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
  */
public abstract class BaseDao {
    /**
     * <p>This is the AuditManager from the Time Tracker Audit component that is used to perform audits upon a
     * database change. It's initialized in ctor to a non-null object and never changed afterwards.</p>
     */
    private final AuditManager auditManager;

    /**
     * <p>This is an instance of DBConnectionFactory used to create connections used by the Dao. This is
     * initialized through the constructor and doesn't change after that.</p>
     */
    private final DBConnectionFactory dbConnectionFactory;

    /**
     * <p>This is an id generator used to generate unique pks for all create operations in the DAO. This
     * generator must be set and cannot be null. It is set in constructor (via the the generator name). Once set it is
     * immutable. Of course this is pretty generic and can be used by any descendant DAO fro a number of purposes.
     * This is use in the netxId() method.</p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>This is a connection name as used for configured connection creation via the Connection Manager
     * component. It is initialized in the constructor and then used in the getConnection method. It can be null which
     * simply means that we would be using whatever default the Connection Manager uses. While it can be set to null,
     * it cannot be an empty string.</p>
     */
    private final String connectionName;

/**
     * <p>
     * Creates a new BaseDao and initializes the member variables with corresponding parameters.
     * </p>
     *
     * @param connectionName configured connection name.Can be null, but not empty. If null is given, the default
     *  connection will be retrieved
     * @param idGeneratorName configured id generator name, not null
     * @param dbConnectionFactory connection factory to use when generating new connections, not null.
     * @param auditManager Audit Manager to audit methods that change data when necessary. not null
     * @throws ConfigurationException if failed to get IDGenerator from the given name
     * @throws IllegalArgumentException if any argument is invalid
     */
    protected BaseDao(String connectionName, String idGeneratorName, DBConnectionFactory dbConnectionFactory,
        AuditManager auditManager) throws ConfigurationException {
        ParameterCheck.checkEmpty("connectionName", connectionName);
        ParameterCheck.checkNull("idGeneratorName", idGeneratorName);
        ParameterCheck.checkNull("dbConnectionFactory", dbConnectionFactory);
        ParameterCheck.checkNull("auditManager", auditManager);

        this.connectionName = connectionName;

        try {
            this.idGenerator = IDGeneratorFactory.getIDGenerator(idGeneratorName);
        } catch (com.topcoder.util.idgenerator.IDGenerationException e) {
            throw new ConfigurationException("failed to obtain IDGenerator:" + idGeneratorName, e);
        }

        this.auditManager = auditManager;
        this.dbConnectionFactory = dbConnectionFactory;
    }

    /**
     * <p>Retrieves the AuditManager from the Time Tracker Audit component that is used to perform audits upon
     * a database change.</p>
     *
     * @return AuditManager audit manager used to audit database change, not null
     */
    protected AuditManager getAuditManager() {
        return this.auditManager;
    }

    /**
     * <p>Using the connectionName and DBConnectionFactory to create a new named connection. If connectionName
     * is null then the default connection will be used for creation.</p>
     *
     * @return new connection object
     *
     * @throws PersistenceException if failed to create the connection
     */
    protected Connection getConnection() throws PersistenceException {
        try {
            if (this.connectionName == null) {
                return dbConnectionFactory.createConnection();
            } else {
                return dbConnectionFactory.createConnection(connectionName);
            }
        } catch (DBConnectionException e) {
            StringBuffer sb = new StringBuffer("failed to obtain connection:");
            sb.append((connectionName == null) ? "default connection" : connectionName);
            throw new PersistenceException(sb.toString(), e);
        }
    }

    /**
     * <p>Generates the next unique id.</p>
     *
     * @return next id, &gt;=0
     *
     * @throws IdGenerationException there were any issues with the id generator
     */
    protected long nextId() throws IdGenerationException {
        long nextId;

        try {
            nextId = idGenerator.getNextID();
        } catch (com.topcoder.util.idgenerator.IDGenerationException e) {
            throw new IdGenerationException("fail to retrieve id", e);
        }

        if (nextId < 0) {
            throw new IdGenerationException("invalid id:" + nextId);
        }

        return nextId;
    }
}
