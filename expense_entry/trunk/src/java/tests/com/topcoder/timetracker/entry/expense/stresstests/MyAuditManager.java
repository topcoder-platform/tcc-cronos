/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

/**
 * This is a mocked class implements interface AuditManager.
 *
 * @author Chenhong
 * @version 3.2
 */
public class MyAuditManager implements AuditManager {
    /**
     * It will store all the AuditHeader instances.
     */
    private List headers = new ArrayList();

    /**
     * @see com.topcoder.timetracker.audit.AuditManager#createAuditRecord(com.topcoder.timetracker.audit.AuditHeader)
     */
    public void createAuditRecord(AuditHeader header) throws AuditManagerException {
        headers.add(header);
    }

    /**
     * @see com.topcoder.timetracker.audit.AuditManager#rollbackAuditRecord(long)
     */
    public boolean rollbackAuditRecord(long arg0) throws AuditManagerException {
        return false;
    }

    /**
     * <p>
     * In this implementation, all AuditHeader instance will be returned.
     * </p>
     *
     * @see com.topcoder.timetracker.audit.AuditManager#searchAudit(com.topcoder.search.builder.filter.Filter)
     */
    public AuditHeader[] searchAudit(Filter arg0) throws AuditManagerException {
        return (AuditHeader[]) headers.toArray(new AuditHeader[headers.size()]);
    }
}