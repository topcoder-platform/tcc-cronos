/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

/**
 * This is a mock implementation of <code>AuditManager</code>.
 * It is only used in stress tests.
 *
 * @author myxgyy
 * @version 1.0
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

    /**
     * return false.
     * @param auditHeaderId
     *            the id
     * @return always false
     */
	public boolean rollbackAuditRecord(int auditHeaderId) throws AuditManagerException {
		return false;
	}
}
