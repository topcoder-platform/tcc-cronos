/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.accuracytests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;


/**
 * <p>MockAuditManager for testing.</p>
 *
 * @author liuliquan
 * @version1.0
 */
public class MockAccuracyAuditManager implements AuditManager {
    /**
     * <p>
     * The records.
     * </p>
     */
    private static final Map MANAGERS = new HashMap();

    /**
     * <p>
     * The records.
     * </p>
     */
    private final List records = new ArrayList();

    /**
     * Ctor.
     * @param type The type of this manager. Either be "entry" or "reject".
     */
    public MockAccuracyAuditManager(String type) {
        MANAGERS.put(type, this);
    }

    /**
     * <p>
     * Get instance of <code>MockAccuracyAuditManager</code> used by <code>DbFixedBillingEntryDAO</code>.
     * </p>
     * @return last instance of <code>MockAccuracyAuditManager</code>.
     */
    public static AuditManager getEntyAuditManager() {
        return (AuditManager) MANAGERS.get("entry");
    }

    /**
     * <p>
     * Get instance of <code>MockAccuracyAuditManager</code> used by <code>DbFixedBillingEntryRejectReasonDAO</code>.
     * </p>
     * @return last instance of <code>MockAccuracyAuditManager</code>.
     */
    public static AuditManager getRejectAuditManager() {
        return (AuditManager) MANAGERS.get("reject");
    }

    /**
     * Create audit record.
     *
     * @param record AuditHeader instance
     */
    public void createAuditRecord(AuditHeader record) {
        this.records.add(record);
    }

    /**
     * Rollback all the audit records.
     *
     * @param auditHeaderId audit header id
     *
     * @return false
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        this.records.clear();

        return true;
    }

    /**
     * <p>
     * Return all the audit records.
     * </p>
     *
     * @param filter Filter instance
     *
     * @return AuditHeader array
     */
    public AuditHeader[] searchAudit(Filter filter) {
        return (AuditHeader[]) this.records.toArray(new AuditHeader[this.records.size()]);
    }
}
