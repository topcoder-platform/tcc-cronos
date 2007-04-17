/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.db.BaseDAO;

/**
 * <p>
 * mock the BaseDAO class for the failure test.
 * </p>
 *
 * @author KLW
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
     * @throws IllegalArgumentException if connFactory is null or any one of connName, idGen and
     * searchStrategyNamespace is empty string when it is not null
     * @throws ConfigurationException if unable to create the search strategy instance according to the given
     * namespace or create the id generator according to the given id generator name
     */
    public MockBaseDAO(DBConnectionFactory connFactory, String connName, String idGen, String searchStrategyNamespace,
        AuditManager auditor) throws ConfigurationException {
        super(connFactory, connName, idGen, searchStrategyNamespace, auditor);
    }
}
