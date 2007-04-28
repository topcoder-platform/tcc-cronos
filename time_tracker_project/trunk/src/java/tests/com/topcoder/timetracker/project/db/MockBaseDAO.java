/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.project.ConfigurationException;

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
     * Constructor for a <code>MockBaseDAO</code> that accepts the necessary parameters
     * for the DAO to function property.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param idGen The name of the id generator to use, can be null
     * @param searchBundleManagerNamespace The configuration namespace of the search bundle manager
     * @param searchBundleName the name of the search bundle
     * that will be used, can be null
     * @param auditor The AuditManager used to create audit entries, can be null
     *
     * @throws IllegalArgumentException if connFactory is null, or if idGen, connName or
     * searchStrategyNamespace is an empty String.
     * @throws ConfigurationException if error occurs.
     */
    public MockBaseDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchBundleManagerNamespace, String searchBundleName, AuditManager auditor)
        throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleManagerNamespace, searchBundleName, auditor);
    }

}
