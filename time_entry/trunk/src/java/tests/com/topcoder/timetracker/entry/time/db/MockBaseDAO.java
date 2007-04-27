/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.time.ConfigurationException;

/**
 * <p>
 * This class extends BaseDAO class.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockBaseDAO extends BaseDAO {
    /**
     * <p>
     * Constructor for a <code>MockBaseDAO</code> that accepts the necessary parameters for the DAO to
     * function property.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param idGen The name of the id generator to use.
     * @param searchStrategyNamespace The configuration namespace of the database search strategy that will be used.
     * @param auditor The AuditManager used to create audit entries.
     *
     * @throws IllegalArgumentException if connFactory is null, or if idGen, connName or
     * searchStrategyNamespace is an empty String or searchBundleName is empty string when the
     * searchBundleManagerNamespace is null or searchBundleName is null or empty string when
     * searchBundleManagerNamespace is not null and not empty
     @throws ConfigurationException if unable to create the search bundle instance according to the given
     * namespace and search bundle name or create the id generator according to the given id generator name
     */
    protected MockBaseDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchBundleManagerNamespace, String searchBundleName, AuditManager auditor)
        throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleManagerNamespace, searchBundleName, auditor);
    }

}
