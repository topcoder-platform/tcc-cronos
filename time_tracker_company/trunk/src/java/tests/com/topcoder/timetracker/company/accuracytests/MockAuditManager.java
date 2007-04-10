/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

/**
 * The mock AuditManager for testing.
 *
 * @author mittu
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {
    /**
     * Represents the mock persistence.
     */
    private List records = new ArrayList();

    /**
     * Adds the record to the mock persistence.
     *
     * @see com.topcoder.timetracker.audit.AuditManager#createAuditRecord(com.topcoder.timetracker.audit.AuditHeader)
     */
    public void createAuditRecord(AuditHeader record) throws AuditManagerException {
        records.add(record);
    }

    /**
     * @see com.topcoder.timetracker.audit.AuditManager#rollbackAuditRecord(int)
     */
    public boolean rollbackAuditRecord(int auditHeaderId) throws AuditManagerException {
        return false;
    }

    /**
     * @see com.topcoder.timetracker.audit.AuditManager#searchAudit(com.topcoder.search.builder.filter.Filter)
     */
    public AuditHeader[] searchAudit(Filter filter) throws AuditManagerException {
        return null;
    }

    /**
     * <p>
     * Gets the list of audit headers persisted.
     * </p>
     *
     * @return
     */
    public AuditHeader[] listRecords() {
        return (AuditHeader[]) records.toArray(new AuditHeader[records.size()]);
    }

    public boolean rollbackAuditRecord(long auditHeaderId) throws AuditManagerException {
        return false;
    }
}
