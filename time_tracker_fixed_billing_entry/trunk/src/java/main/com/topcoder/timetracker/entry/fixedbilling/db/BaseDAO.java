/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.Helper;

import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.sql.Connection;


/**
 * <p>
 * This is a base DAO class that encapsulates the common elements that may be found within a DAO such as the connection
 * details, id generator, search strategy and audit manager.
 * </p>
 *
 * <p>
 * Thread Safety: This implementation relies on Container-Managed Transactions to maintain thread-safety. All its state
 * is initialized during construction.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public abstract class BaseDAO {
    /**
     * <p>
     * This is the connection name that is provided to the connection factory when a connection is acquired. If not
     * specified, then the default connection is used.
     * </p>
     */
    private final String connName;

    /**
     * <p>
     * This is the connection factory that is used to acquire a connection to the persistent store when it is needed.
     * </p>
     */
    private final DBConnectionFactory connFactory;

    /**
     * <p>
     * This is the id generator that is used to generate an id for any new entities that are added to the persistent
     * store.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * This is the SearchBundle used to search the database. It may be null in case the concrete class
     * doesn't pass any to the constructor.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * This is the AuditManager from the Time Tracker Audit component that is used to perform audits upon a database
     * change. It may be null in case the concrete class doesn't pass any to the constructor.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * Constructor for a BaseDAO that accepts the necessary parameters for the DAO to function property.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param idGen The name of the id generator to use.
     * @param searchBundleNamespace The configuration namespace of the database search bundle that will be used.
     * @param searchBundleName The search bundle name which will be used.
     * @param auditor The AuditManager used to create audit entries.
     *
     * @throws IllegalArgumentException if any argument except for connName, searchStrategyNamespace or auditor is null
     *         or if connName is an empty String.
     * @throws ConfigurationException if any configuration error.
     */
    protected BaseDAO(DBConnectionFactory connFactory, String connName, String idGen, String searchBundleNamespace,
        String searchBundleName, AuditManager auditor) throws ConfigurationException {
        Helper.checkNull("connFactory", connFactory);
        Helper.checkNotEmpty("connName", connName);
        Helper.checkNotEmpty("idGen", idGen);
        Helper.checkNotEmpty("searchBundleNamespace", searchBundleNamespace);
        Helper.checkNotEmpty("searchBundleName", searchBundleName);

        this.connFactory = connFactory;
        this.connName = connName;

        try {
            if (idGen == null) {
                this.idGenerator = null;
            } else {
                this.idGenerator = IDGeneratorFactory.getIDGenerator(idGen);
            }

            if ((searchBundleNamespace == null) || (searchBundleName == null)) {
                this.searchBundle = null;
            } else {
                this.searchBundle = new SearchBundleManager(searchBundleNamespace).getSearchBundle(searchBundleName);
            }
        } catch (IDGenerationException idge) {
            throw new ConfigurationException("Unable to create the instance by invalid idGen.", idge);
        } catch (SearchBuilderConfigurationException sbce) {
            throw new ConfigurationException("Unable to create the instance by invalid search namespace.", sbce);
        }

        this.auditManager = auditor;
    }

    /**
     * <p>
     * Retrieves a connection to the database.  It is expected to be used by concrete subclasses.
     * </p>
     *
     * @return a connection to the database.
     *
     * @throws DataAccessException if any error occurs.
     */
    protected Connection getConnection() throws DataAccessException {
        Connection conn = null;

        try {
            if (connName == null) {
                conn = connFactory.createConnection();
            } else {
                conn = connFactory.createConnection(connName);
            }
        } catch (DBConnectionException dbce) {
            throw new DataAccessException("Unable to get the connection by database error.", dbce);
        }

        return conn;
    }

    /**
     * <p>
     * Retrieves a unique identifier to use.  It is expected to be used by concrete subclasses.
     * </p>
     *
     * @return a unique identifier to use.
     *
     * @throws DataAccessException if any error occurs.
     */
    protected long getNextId() throws DataAccessException {
        try {
            if (idGenerator != null) {
                return idGenerator.getNextID();
            } else {
                throw new DataAccessException("The idGenerator should not be null.");
            }
        } catch (IDGenerationException idge) {
            throw new DataAccessException("Unable to get the next id by idGenerator error.", idge);
        }
    }

    /**
     * <p>
     * Retrieves the SearchBundle used to search the database.
     * </p>
     *
     * @return the SearchBundle used to search the database.
     */
    protected SearchBundle getSearchBundle() {
        return searchBundle;
    }

    /**
     * <p>
     * Retrieves the AuditManager from the Time Tracker Audit component that is used to perform audits upon a database
     * change.
     * </p>
     *
     * @return the AuditManager from the Time Tracker Audit component that is used to perform audits upon a database
     *         change.
     */
    protected AuditManager getAuditManager() {
        return auditManager;
    }
}
