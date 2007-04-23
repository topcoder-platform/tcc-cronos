/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;

/**
 * This is a mock implementation of <code>AuditManager</code>. It is only used in stress tests.
 *
 * @author vividmxx
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {

    /**
     * The default constructor.
     */
    public MockAuditManager() {
    }

    /**
     * Returns directly.
     *
     * @param record
     *            ignored
     */
    public void createAuditRecord(AuditHeader record) {
    }

    /**
     * Returns false.
     *
     * @param auditHeaderId
     *            ignored
     * @return always false
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }

    /**
     * Returns null.
     *
     * @param filter
     *            ignored
     * @return always null
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return null;
    }
}
