/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;

/**
 * Mock for <code>AuditManager</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuditManager implements AuditManager {

    /**
     * Mock method.
     *
     * @param record
     *            not used in the mock
     */
    public void createAuditRecord(AuditHeader record) {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @return false
     *
     * @param auditHeaderId
     *            not used in the mock
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        // nothing to do
        return false;
    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @param filter
     *            not used in the mock
     */
    public AuditHeader[] searchAudit(Filter filter) {
        // nothing to do
        return null;
    }

}
