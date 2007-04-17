/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a mock implementation of AuditManager interface for test purpose.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {
    /**
     * A reference to AuditHeader object. It's initialized to an empty list and if the method createAuditRecord is
     * call, the audit header to record is added.
     */
    private List headerList = new ArrayList();

    /**
     * Creates a new instance of MockAuditManager.
     */
    public MockAuditManager() {
        // Do nothing
    }

    /**
     * Simply adds the passed in header to the inner AuditHeader list.
     *
     * @param record the header to record.
     *
     * @throws AuditManagerException never.
     */
    public void createAuditRecord(AuditHeader record) throws AuditManagerException {
        headerList.add(record);
    }

    /**
     * Gets the audit header list being recorded.
     *
     * @return the audit header list being recorded.
     */
    public List getAuditHeader() {
        return headerList;
    }

    /**
     * Returns false.
     *
     * @param auditHeaderId not used.
     *
     * @return false;
     *
     * @throws AuditManagerException never.
     */
    public boolean rollbackAuditRecord(long auditHeaderId)
        throws AuditManagerException {
        return false;
    }

    /**
     * Returns null.
     *
     * @param filter not used.
     *
     * @return null.
     *
     * @throws AuditManagerException never.
     */
    public AuditHeader[] searchAudit(Filter filter) throws AuditManagerException {
        return null;
    }
}
