/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Defines a mock class which implements the <code>AuditManager</code> interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {
    /** Represents the audit headers hold by this class. */
    private Map auditHeaders = new HashMap();

    /** Represents the flag which will make all the methods of this mock class always throw exception. */
    private boolean alwaysThrowException;

    /**
     * Creates a new MockAuditManager object.
     *
     * @param alwaysThrowException the flag which will make all the methods of this mock class always throw exception.
     */
    public MockAuditManager(boolean alwaysThrowException) {
        this.alwaysThrowException = alwaysThrowException;
    }

    /**
     * <p>
     * Get the audit headers  hold by this class.
     * </p>
     *
     * @return the audit headers  hold by this class.
     */
    public AuditHeader[] getAuditHeaders() {
        return (AuditHeader[]) auditHeaders.values().toArray(new AuditHeader[auditHeaders.size()]);
    }

    /**
     * <p>
     * Adds an audit record header, and all of its details, to persistence. This method is delegated to the persistence
     * layer.
     * </p>
     *
     * @param record The audit record header containing information to be added to the database - cannot be null.
     *
     * @throws AuditManagerException if it is required to be thrown.
     */
    public void createAuditRecord(AuditHeader record) throws AuditManagerException {
        if (record == null) {
            throw new IllegalArgumentException("record is null.");
        }

        if (alwaysThrowException) {
            throw new AuditManagerException("AuditManagerException");
        }

        auditHeaders.put(new Long(record.getId()), record);
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param auditHeaderId The ID of the audit header to remove.
     *
     * @return boolean - true if anything was removed from the database, otherwise false.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param filter A filter describing the search constraints against which the audits are to be tested. Can be null
     *        if no filter is to be used.
     *
     * @return An array of <code>AuditHeader</code> objects that match the given filter. This array may be empty if no
     *         matches are found, but will never be null.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public AuditHeader[] searchAudit(Filter filter) {
        throw new UnsupportedOperationException("not implemented.");
    }
}
