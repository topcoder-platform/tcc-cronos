/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditPersistence;
import com.topcoder.timetracker.audit.AuditPersistenceException;

/**
 * <p>
 * mock class for failure tests.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockAuditPersistence implements AuditPersistence {

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public void createAuditRecord(AuditHeader record) throws AuditPersistenceException {
        throw new AuditPersistenceException("mock");
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public boolean rollbackAuditRecord(int auditHeaderId) throws AuditPersistenceException {
        throw new AuditPersistenceException("mock");
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public AuditHeader[] searchAudit(Filter filter) throws AuditPersistenceException {
        throw new AuditPersistenceException("mock");
    }

    public boolean rollbackAuditRecord(long auditHeaderId) throws AuditPersistenceException {
        throw new AuditPersistenceException("mock");
    }

}
