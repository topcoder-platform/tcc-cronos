/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

/**
 * <p>
 * A mock class for audit manager.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class MockAuditManager implements AuditManager {

    /**
     * A mock method.
     */
    private static final String[] ACTION_TYPES = {"", "INSERT", "UPDATE", "DELETE" };

    /**
     * A mock method.
     */
    private AuditHeader header;

    /**
     * A mock method.
     */
    public MockAuditManager() {
    }

    /**
     * A mock method.
     */
    public void createAuditRecord(AuditHeader record) throws AuditManagerException {
        if ("AuditManagerException".equals(System.getProperty("exception"))) {
            throw new AuditManagerException("AuditManagerException.");
        }
        this.header = record;
        StringBuffer sb = new StringBuffer("id:").append(record.getEntityId()).append(" cId:").append(
            record.getCompanyId()).append(" t:").append(ACTION_TYPES[record.getActionType()]).append(" table:")
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
     * A mock method.
     */
    public AuditHeader getHeader() {
        AuditHeader header = this.header;
        this.header = null;
        return header;
    }

    /**
     * A mock method.
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }
}
