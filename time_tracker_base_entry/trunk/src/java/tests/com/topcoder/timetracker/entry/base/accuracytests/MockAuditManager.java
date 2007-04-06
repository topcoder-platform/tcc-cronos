/*
 * Copyright (C) 2006 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.accuracytests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;


/**
 * MockAuditManager for testing. The record will be used to assert the audit result.
 * 
 * @author waits
 * @version1.0
 */
public class MockAuditManager implements AuditManager {
    /** AuditHeader record. */
    private AuditHeader record = null;

    /**
     * Ctor.
     */
    public MockAuditManager() {
    }

    /**
     * Create audit record.
     *
     * @param record AuditHeader instance
     */
    public void createAuditRecord(AuditHeader record) {
    	this.record = record;
    }

    /**
     * Rollback the audit record.
     *
     * @param auditHeaderId audit header id
     *
     * @return false
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }

    /**
     * <p>
     * Search AuditHeader with the filter.
     * </p>
     *
     * @param filter Filter instance
     *
     * @return AuditHeader array
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return null;
    }
    
    /**
     * Return the record.
     * @return the AuditHeader record
     */
    public AuditHeader getRecord(){
    	return this.record;
    }
}
