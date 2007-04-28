/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.sql.Connection;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;

/**
 * <p>
 * This is a base DAO class that encapsulates the common elements that may be found within a DAO
 * such as the connection details, id generator, search strategy and audit manager.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe due to immutability, and the thread safety of the
 * sub components that it uses.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public abstract class BaseDAO {
    /**
     * <p>
     * This is the connection name that is provided to the connection factory when a connection is acquired.
     * </p>
     *
     * <p>
     * If not specified, then the default connection is used.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It can be null but cannot be empty.
     * </p>
     */
    private final String connName;

    /**
     * <p>
     * This is the connection factory that is used to acquire a connection to the persistent store
     * when it is needed.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private DBConnectionFactory connFactory;

    /**
     * <p>
     * This is the <code>SearchBundle</code> used to search the database.
     * </p>
     *
     * <p>
     * It may be null in case the concrete class passes <code>null</code> to the constructor.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     */
    private SearchBundle searchBundle;

    /**
     * <p>
     * This is the id generator that is used to generate an id for any
     * new entities that are added to the persistent store.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It may be null in case the concrete class passes <code>null</code> to the constructor.
     * </p>
     */
    private IDGenerator idGenerator;

    /**
     * <p>
     * This is the <code>AuditManager</code> from the Time Tracker Audit component that is used to
     * perform audits upon a database change.
     * </p>
     *
     * <p>
     * It may be null in case the concrete class passes null to the constructor.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Constructor for a <code>BaseDAO</code> that accepts the necessary parameters
     * for the DAO to function property.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param idGen The name of the id generator to use, can be null
     * @param searchBundleManagerNamespace The configuration namespace of the search bundle manager
     * that will be used, can be null
     * @param searchBundleName the name of the search bundle, can be null
     * @param auditor The AuditManager used to create audit entries, can be null
     *
     * @throws IllegalArgumentException if connFactory is null, or if idGen, connName or
     * searchStrategyNamespace is an empty String or searchBundleName is empty string when the
     * searchBundleManagerNamespace is null or searchBundleName is null or empty string when
     * searchBundleManagerNamespace is not null and not empty
     * @throws ConfigurationException if unable to create the search bundle instance according to the given
     * namespace and search bundle name
     */
    protected BaseDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchBundleManagerNamespace, String searchBundleName, AuditManager auditor)
        throws ConfigurationException {
        Util.checkNull(connFactory, "connFactory");
        checkEmptyString(connName, "connName");
        checkEmptyString(idGen, "idGen");
        checkEmptyString(searchBundleManagerNamespace, "searchBundleManagerNamespace");
        if (searchBundleManagerNamespace == null) {
            checkEmptyString(searchBundleName, "searchBundleName");
        } else {
            Util.checkString(searchBundleName, "searchBundleName");
        }

        this.connFactory = connFactory;
        this.connName = connName;
        this.idGenerator = (idGen == null) ? null : createIDGenerator(idGen);

        // create the searchStrategy using the given namespace
        // note, it is null if the given search strategy namespace is null
        try {
            this.searchBundle = (searchBundleManagerNamespace == null) ? null : new SearchBundleManager(
                searchBundleManagerNamespace).getSearchBundle(searchBundleName);

            if (searchBundleManagerNamespace != null && this.searchBundle == null) {
                throw new ConfigurationException("The given search bundle [" + searchBundleName + "] doesn't exist.");
            }

        } catch (SearchBuilderConfigurationException e) {
            throw new ConfigurationException("SearchBuilderConfigurationException occurs "
                + "when creating SearchBundleManager instance.", e);
        }

        this.auditManager = auditor;
    }

    /**
     * <p>
     * This method creates a <code>IDGenerator</code> instance based on the given <code>key</code>.
     * </p>
     *
     * @param key the id generator name
     * @return the <code>IDGenerator</code> instance for the given <code>key</code>
     *
     * @throws ConfigurationException if fails to create the <code>IDGenerator</code> instance
     */
    private IDGenerator createIDGenerator(String key) throws ConfigurationException {
        try {
            return IDGeneratorFactory.getIDGenerator(key);
        } catch (IDGenerationException e) {
            throw new ConfigurationException("IDGenerationException occurs when getting " + "the id generator for "
                + key, e);
        }
    }

    /**
     * <p>
     * Checks whether the given String is empty when it is not null.
     * </p>
     *
     * @param arg the String to check
     * @param name the name of the String argument to check
     *
     * @throws IllegalArgumentException if the given string empty when it is not null
     */
    private void checkEmptyString(String arg, String name) {
        if (arg != null && arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * <p>
     * Retrieves a connection to the database.
     * </p>
     *
     * <p>
     * It is expected to be used by concrete subclasses.
     * </p>
     *
     * @return a connection to the database.
     *
     * @throws DBConnectionException if unable to create a database connection
     */
    protected Connection getConnection() throws DBConnectionException {
        return (connName == null) ? connFactory.createConnection() : connFactory.createConnection(connName);
    }

    /**
     * <p>
     * Retrieves a unique identifier to use.
     * </p>
     *
     * <p>
     * It is expected to be used by concrete subclasses.
     * </p>
     *
     * @return a unique identifier to use.
     *
     * @throws IDGenerationException if unable to generate a new id to use
     * @throws DataAccessException if the id generator is null (null id generator name is passed
     * to the constructor)
     */
    protected long getNextId() throws IDGenerationException, DataAccessException {
        if (idGenerator == null) {
            throw new DataAccessException("The id generator is null.");
        }

        return idGenerator.getNextID();
    }

    /**
     * <p>
     * Retrieves the <code>SearchBundle</code> used to search the database.
     * </p>
     *
     * <p>
     * Note, the return value may be null.
     * </p>
     *
     * @return the <code>SearchBundle</code> used to search the database.
     */
    protected SearchBundle getSearchBundle() {
        return searchBundle;
    }

    /**
     * <p>
     * Retrieves the <code>AuditManager</code> from the Time Tracker Audit component that is used to
     * perform audits upon a database change.
     * </p>
     *
     * <p>
     * Note, the return value may be null.
     * </p>
     *
     * @return the <code>AuditManager</code> from the Time Tracker Audit component that is used to
     * perform audits upon a database change.
     */
    protected AuditManager getAuditManager() {
        return auditManager;
    }
}