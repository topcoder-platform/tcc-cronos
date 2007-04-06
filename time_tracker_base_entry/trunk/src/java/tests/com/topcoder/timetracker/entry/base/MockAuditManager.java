/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;


/**
 * Mock implementation of AuditManager used in test.
 *
 * @author TCSDEVELOPER
 * @version 3.2
  */
public class MockAuditManager implements AuditManager {
    /**
     *Action type.
     */
    private static final String[] ACTION_TYPES = {"", "INSERT", "UPDATE", "DELETE" };

    /**
     *Audit header that created.
     */
    private AuditHeader header;

    /**
     * Creates a new MockAuditManager object.
     */
    public MockAuditManager() {
    }

    /**
     * Create an audit.
     *
     * @param record the audit to be created.
     *
     * @throws AuditManagerException if System property "exception" is set to "AuditManagerException"
     */
    public void createAuditRecord(AuditHeader record) throws AuditManagerException {
        if ("AuditManagerException".equals(System.getProperty("exception"))) {
            throw new AuditManagerException("AuditManagerException.");
        }

        this.header = record;

        StringBuffer sb = new StringBuffer("id:").append(record.getEntityId()).append(" cId:")
                                                 .append(record.getCompanyId()).append(" t:")
                                                 .append(ACTION_TYPES[record.getActionType()]).append(" table:")
                                                 .append(record.getTableName());
        sb.append(" area:").append(record.getApplicationArea()).append("[");

        AuditDetail[] details = record.getDetails();

        for (int i = 0; i < details.length; i++) {
            sb.append(details[i].getColumnName()).append(":").append(details[i].getOldValue()).append("->")
              .append(details[i].getNewValue()).append(" ");
        }

        sb.append("]");

        System.out.println(sb);
    }

    /**
     * Gets the header just audited, and set the header to null after that. So if no record is audited after
     * this, the subsequent calls on this method will just get null.
     *
     * @return the header just audited
     */
    public AuditHeader getHeader() {
        AuditHeader result = this.header;
        this.header = null;

        return result;
    }

    /**
     * Ignore this.
     *
     * @param auditHeaderId Ignore this.
     *
     * @return Ignore this.
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }
}
