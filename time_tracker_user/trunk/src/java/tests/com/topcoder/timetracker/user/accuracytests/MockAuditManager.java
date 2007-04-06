/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;

/**
 * <p>
 * This class implements AuditManager interface.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {
    /**
     * <p>
     * Represents all the <code>AuditHeader</code> via the createAuditRecord method.
     * </p>
     */
    private List auditHeaders = new ArrayList();

    /**
     * <p>
     * Creates a audit record for the given <code>AuditHeader</code>.
     * </p>
     *
     * @param record a <code>AuditHeader</code> instance
     */
    public void createAuditRecord(AuditHeader record) {
        auditHeaders.add(record);
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param auditHeaderId not used
     * @return false always
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
     * @return an empty array
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return new AuditHeader[0];
    }

    /**
     * <p>
     * Returns all the <code>AuditHeader</code> in this manager.
     * </p>
     *
     * @return all the <code>AuditHeader</code> in this manager.
     */
    public AuditHeader[] getAuditHeaders() {
        return (AuditHeader[]) auditHeaders.toArray(new AuditHeader[auditHeaders.size()]);
    }
}
