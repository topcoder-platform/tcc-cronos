/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.stresstests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;


/**
 * MockAuditManager for testing.
 *
 * @author Hacker_QC
 * @version1.0
 */
public class MockAuditManager implements AuditManager {
    /**
     * Ctor.
     */
    public MockAuditManager() {
    }

    /**
     * Create audit record.
     *
     * @param record AuditHeader instance
     */
    public void createAuditRecord(AuditHeader record) {
        return;
    }

    /**
     * Rollback the audit record.
     *
     * @param auditHeaderId audit header id
     *
     * @return false
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }

    /**
     * <p>
     * Search AuditHeader with the filter.
     * </p>
     *
     * @param filter Filter instance
     *
     * @return AuditHeader array
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return null;
    }
}
