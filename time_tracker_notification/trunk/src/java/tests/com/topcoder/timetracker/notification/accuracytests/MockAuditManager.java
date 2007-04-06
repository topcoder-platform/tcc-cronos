/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;

/**
 * <p>
 * This is a mock implementation of <code>AuditManager</code> and it is used to help accuracy tests.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {
    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param record not used
     */
    public void createAuditRecord(AuditHeader record) {
        // empty
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param auditHeaderId not used
     *
     * @return boolean false always
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param filter not used
     *
     * @return An empty array of <code>AuditHeader</code>.
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return new AuditHeader[0];
    }
}