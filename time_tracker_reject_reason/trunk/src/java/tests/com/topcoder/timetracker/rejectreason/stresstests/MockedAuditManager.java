/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;

/**
 *
 * <p>
 * This is a mocked implementation of AuditManager.
 * </p>
 *
 * @author restarter
 * @version 3.2
 */
public class MockedAuditManager implements AuditManager {

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param record the argument
     */
    public void createAuditRecord(AuditHeader record) {
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null
     */
    public AuditHeader getAuditRecord() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param auditHeaderId the argument
     * @return false
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param filter the argument
     * @return null
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return null;
    }

}
