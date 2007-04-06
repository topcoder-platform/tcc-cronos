/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;

/**
 * This class is a mock implementation of AuditManager. It is only used in stress tests.
 *
 * @author vividmxx
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {

    /**
     * Represents the AuditHeader to the mock.
     */
    private static AuditHeader auditHeader;

    /**
     * Creates the audit record.
     *
     * @param record
     *            the record to audit.
     */
    public void createAuditRecord(AuditHeader record) {
        auditHeader = record;
    }

    /**
     * Not implemented.
     *
     * @param auditHeaderId
     *            audit header id
     * @return always false.
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param filter
     *            filter
     * @return always null.
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return null;
    }

    /**
     * Gets the audit header for testing.
     *
     * @return the audit header
     */
    public static AuditHeader getHeader() {
        return auditHeader;
    }
}
