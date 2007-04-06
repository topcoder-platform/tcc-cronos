/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.audit.AuditManager;


/**
 * Stub implementation of BaseDao used in test.
 *
 * @author TCSDEVELOPER
 * @version 3.2
  */
public class StubBaseDao extends BaseDao {
    /**
     * Creates a new StubBaseDao object.
     *
     * @param connectionName connectionName
     * @param idGeneratorName idGeneratorName
     * @param dbConnectionFactory dbConnectionFactory
     * @param auditManager auditManager
     *
     * @throws ConfigurationException ConfigurationException
     */
    protected StubBaseDao(String connectionName, String idGeneratorName, DBConnectionFactory dbConnectionFactory,
        AuditManager auditManager) throws ConfigurationException {
        super(connectionName, idGeneratorName, dbConnectionFactory, auditManager);
    }
}
