/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.topcoder.search.builder.filter.Filter;
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
     * Creates a new MockAuditManager object.
     */
    public MockAuditManager() {
    }

    /**
     * Just print the audit message.
     *
     * @param record AuditHeader to audit
     */
    public void createAuditRecord(AuditHeader record) {
        StringBuffer sb = new StringBuffer("id:").append(record.getEntityId()).append(" cId:")
                                                 .append(record.getCompanyId()).append(" t:")
                                                 .append(record.getActionType()).append(" table:")
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
     * Not implement.
     *
     * @param auditHeaderId ignore this
     *
     * @return ignore this
     */
    public boolean rollbackAuditRecord(long auditHeaderId) {
        return false;
    }

    public boolean rollbackAuditRecord(int auditHeaderId) throws AuditManagerException {
        // TODO Auto-generated method stub
        return false;
    }

    public AuditHeader[] searchAudit(Filter filter) throws AuditManagerException {
        // TODO Auto-generated method stub
        return null;
    }
}
