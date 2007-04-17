/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

/**
 * <p>
 * Mock <code>AuditManager</code> for testing.
 * </p>
 *
 * @author mittu
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {

    /**
     * @throws AuditManagerException
     *             always
     * @see com.topcoder.timetracker.audit.AuditManager#createAuditRecord(com.topcoder.timetracker.audit.AuditHeader)
     */
    public void createAuditRecord(AuditHeader arg0) throws AuditManagerException {
        throw new AuditManagerException("");
    }

    /**
     * @see com.topcoder.timetracker.audit.AuditManager#rollbackAuditRecord(long)
     */
    public boolean rollbackAuditRecord(long arg0) {
        return false;
    }

    /**
     * @see com.topcoder.timetracker.audit.AuditManager#searchAudit(com.topcoder.search.builder.filter.Filter)
     */
    public AuditHeader[] searchAudit(Filter arg0) {
        return null;
    }
}