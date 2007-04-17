/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;

/**
 * <p>
 * This is a mock implementation of <code>AuditManager</code> and it is used to help accuracy tests.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {
    /**
     * <p>
     * Private audit header.
     * </p>
     */
    private List headers = new ArrayList();
    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param record not used
     */
    public void createAuditRecord(AuditHeader record) {
        headers.add(record);
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

    /**
     * <p>
     * Get the audit headers.
     * </p>
     * @return the audit header
     */
    public List getAuditHeaders() {
        return headers;
    }
    
    /**
     * <p>
     * Clear the header list.
     * </p>
     *
     */
    public void clear() {
        headers.clear();
    }
}