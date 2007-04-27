/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;


/**
 * Mock the BaseDAO for testing.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class MockBaseDAO extends BaseDAO {
    /**
     * Mock the constructor.
     *
     * @param connFactory for testing.
     * @param connName for testing.
     * @param idGen for testing.
     * @param searchBundleNamespace for testing.
     * @param searchBundleName for testing.
     * @param auditor for testing.
     *
     * @throws ConfigurationException for testing.
     */
    public MockBaseDAO(DBConnectionFactory connFactory, String connName, String idGen, String searchBundleNamespace,
        String searchBundleName, AuditManager auditor) throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleNamespace, searchBundleName, auditor);
    }
}
